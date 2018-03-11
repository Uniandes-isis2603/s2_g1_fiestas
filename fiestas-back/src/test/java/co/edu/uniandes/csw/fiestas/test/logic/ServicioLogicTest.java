package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author ls.arias
 */
@RunWith(Arquillian.class)
public class ServicioLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ServicioEntity> data = new ArrayList<ServicioEntity>();

    private List<ProveedorEntity> proveedorData = new ArrayList();
    
    private List<ValoracionEntity> valoracionData = new ArrayList();


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     *
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from ProveedorEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from ValoracionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
       List<ProveedorEntity> listaProveedor = new ArrayList<ProveedorEntity>();
       for (int i = 0; i < 3; i++) {
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(proveedor);
            proveedorData.add(proveedor);
            listaProveedor.add(proveedor);
        }
       List<ValoracionEntity> listaValoracion = new ArrayList<ValoracionEntity>();
       for (int i = 0; i < 3; i++) {
            ValoracionEntity valoracion = factory.manufacturePojo(ValoracionEntity.class);
            em.persist(valoracion);
            valoracionData.add(valoracion);
            listaValoracion.add(valoracion);
        }
       
        for (int i = 0; i < 3; i++) 
        {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entity.setProveedores(listaProveedor);
            entity.setValoraciones(listaValoracion);
            em.persist(entity);
            data.add(entity);
        } 
        
    }

    /**
     * Prueba para crear un Servicio
     *
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createServicioTest() throws BusinessLogicException {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        ServicioEntity result = servicioLogic.createServicio(newEntity);
        Assert.assertNotNull(result);
        ServicioEntity entity = em.find(ServicioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getValoraciones(), entity.getValoraciones());
 
    }

    /**
     * Prueba para consultar la lista de Servicios
     *
     *
     */
    @Test
    public void getServiciosTest() {
        List<ServicioEntity> list = servicioLogic.getServicios();
        Assert.assertEquals(data.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Servicio
     *
     *
     */
    @Test
    public void getServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity resultEntity = servicioLogic.getServicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getValoraciones(), resultEntity.getValoraciones());
    }

    /**
     * Prueba para eliminar un Servicio
     *
     *
     */
    @Test
    public void deleteServicioTest() {
        ServicioEntity entity = data.get(0);
        servicioLogic.deleteServicio(entity.getId());
        ServicioEntity deleted = em.find(ServicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Servicio
     *
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void updateServicioTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);

        pojoEntity.setId(entity.getId());

        servicioLogic.updateServicio(pojoEntity);

        ServicioEntity resp = em.find(ServicioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getValoraciones(), resp.getValoraciones());
    }
    
    /**
     * Prueba para obtener una instancia de Proveedor asociada a una instancia
     * Servicio
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void getProveedorTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ProveedorEntity proveedorEntity = proveedorData.get(0);
        ProveedorEntity response = servicioLogic.getProveedor(entity.getId(), proveedorEntity.getId());

        Assert.assertEquals(proveedorEntity.getId(), response.getId());
        Assert.assertEquals(proveedorEntity.getNombre(), response.getNombre());
        Assert.assertEquals(proveedorEntity.getLogin(), response.getLogin());
        Assert.assertEquals(proveedorEntity.getDocumento(), response.getDocumento());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Proveedores asociadas a una
     * instancia Servicio
     *
     * 
     */
    @Test
    public void listProveedoresTest() {
        List<ProveedorEntity> list = servicioLogic.listProveedores(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para asociar un Proveedor existente a un Servicio
     *
     * 
     */
    @Test
    public void addProveedorTest() {
        ServicioEntity entity = data.get(0);
        ProveedorEntity proveedorEntity = proveedorData.get(1);
        ProveedorEntity response = servicioLogic.addProveedor(proveedorEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(proveedorEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para remplazar las instancias de Proveedores asociadas a una instancia
     * de Servicio
     *
     * 
     */
    @Test
    public void replaceProveedoresTest() {
        ServicioEntity entity = data.get(0);
        List<ProveedorEntity> list = proveedorData.subList(1, 3);
        servicioLogic.replaceProveedores(entity.getId(), list);

        entity = servicioLogic.getServicio(entity.getId());
        Assert.assertTrue(entity.getProveedores().contains(proveedorData.get(0)));
        Assert.assertTrue(entity.getProveedores().contains(proveedorData.get(1)));
        Assert.assertTrue(entity.getProveedores().contains(proveedorData.get(2)));
    }
    
    /**
     * Prueba para desasociar un Proveedor existente de un Editorial existente
     *
     * 
     */
    @Test
    public void removeProveedoresTest() {
        servicioLogic.removeProveedor(data.get(0).getId(), proveedorData.get(0).getId());
        ProveedorEntity response = servicioLogic.getProveedor(data.get(0).getId(), proveedorData.get(0).getId());
    }
    
    /**
     * Prueba para obtener una instancia de Valoracion asociada a una instancia
     * Servicio
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void getValoracionTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ValoracionEntity valoracionEntity = valoracionData.get(0);
        ValoracionEntity response = servicioLogic.getValoracion(entity.getId(), valoracionEntity.getId());

        Assert.assertEquals(valoracionEntity.getId(), response.getId());
        Assert.assertEquals(valoracionEntity.getCalificacion(), response.getCalificacion());
        Assert.assertEquals(valoracionEntity.getComentario(), response.getComentario());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Valoraciones asociadas a una
     * instancia Servicio
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void getValoracionesTest() throws BusinessLogicException {
        List<ValoracionEntity> list = servicioLogic.getValoraciones(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para asociar un Valoracion existente a un Servicio
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void addValoracionTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ValoracionEntity valoracionEntity = valoracionData.get(1);
        ValoracionEntity response = servicioLogic.addValoracion(valoracionEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(valoracionEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para remplazar las instancias de Valoraciones asociadas a una instancia
     * de Servicio
     *
     * 
     */
    @Test
    public void replaceValoracionesTest() {
        ServicioEntity entity = data.get(0);
        List<ValoracionEntity> list = valoracionData.subList(1, 3);
        servicioLogic.replaceValoraciones(entity.getId(), list);

        entity = servicioLogic.getServicio(entity.getId());
        Assert.assertFalse(entity.getValoraciones().contains(valoracionData.get(0)));
        Assert.assertTrue(entity.getValoraciones().contains(valoracionData.get(1)));
        Assert.assertTrue(entity.getValoraciones().contains(valoracionData.get(2)));
    }
    
    /**
     * Prueba para desasociar un Valoracion existente de un Editorial existente
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void removeValoracionesTest() throws BusinessLogicException {
        servicioLogic.removeValoracion(data.get(0).getId(), valoracionData.get(0).getId());
        ValoracionEntity response = servicioLogic.getValoracion(data.get(0).getId(), valoracionData.get(0).getId());
    }
    
    
}
