package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
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
 * @author nm.hernandez10
 */

@RunWith(Arquillian.class)
public class ProveedorPersistenceTest
{
      /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Proveedor, el descriptor de la
     * base de datos y el archivo benas.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase ProveedorPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ProveedorPersistence proveedorPersistence;

    /**
     * Contexto de Persostencia que se va autilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     *
     */
    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
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
    }

    /**
     *
     */
    private List<ProveedorEntity> data = new ArrayList<ProveedorEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Proveedor.
     *
     *
     */
    @Test
    public void createProveedorTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = proveedorPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ProveedorEntity entity = em.find(ProveedorEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDocumento(), entity.getDocumento());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
    }

    /**
     * Prueba para consultar la lista de Proveedores.
     *
     *
     */
    @Test
    public void getProveedoresTest() {
        List<ProveedorEntity> list = proveedorPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ProveedorEntity ent : list) {
            boolean found = false;
            for (ProveedorEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Proveedor.
     *
     *
     */
    @Test
    public void getProveedorTest() {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = proveedorPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDocumento(), entity.getDocumento());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
    }

    /**
     * Prueba para eliminar un Proveedor.
     *
     *
     */
    @Test
    public void deleteProveedorTest() {
        ProveedorEntity entity = data.get(0);
        proveedorPersistence.delete(entity.getId());
        ProveedorEntity deleted = em.find(ProveedorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Proveedor.
     *
     *
     */
    @Test
    public void updateProveedorTest() {
        ProveedorEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);

        newEntity.setId(entity.getId());

        proveedorPersistence.update(newEntity);

        ProveedorEntity resp = em.find(ProveedorEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDocumento(), resp.getDocumento());
        Assert.assertEquals(newEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(newEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(newEntity.getTelefono(), resp.getTelefono());
    }
    
}
