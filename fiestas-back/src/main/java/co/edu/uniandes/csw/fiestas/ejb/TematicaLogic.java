/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import co.edu.uniandes.csw.fiestas.persistence.TematicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author af.losada
 */
public class TematicaLogic 
{
    private static final Logger LOGGER = Logger.getLogger(TematicaLogic.class.getName());
    
    @Inject
    private TematicaPersistence persistence;
    
    @Inject
    private ServicioPersistence servicioPer;
    
    /**
     * Obtiene la lista de los registros de Tematica.
     *
     * @return Colección de objetos de TematicaEntity.
     */
    public List<TematicaEntity> getTematicas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return persistence.findAll();
    }
    
    /**
     * Obtiene la Tematica.
     *
     * @return Objetos de TematicaEntity.
     */
    public TematicaEntity getTematica(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return persistence.find(id);
    }
    
    /**
     * Obtiene la lista de los registros de Tematica.
     *
     * @return Objetos de TematicaEntity.
     */
    public ServicioEntity getServicio(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return servicioPer.find(id);
    }
    

    /**
     * Se encarga de crear un tematica en la base de datos.
     *
     * @param entity Objeto de TematicaEntity con los datos nuevos
     * @return Objeto de TematicaEntity con los datos nuevos y su ID.
     */
    public TematicaEntity createTematica(TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un tematica ");
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Tematica.
     *
     * @param entity Instancia de TematicaEntity con los nuevos datos.
     * @return Instancia de TematicaEntity con los datos actualizados.
     */
    public TematicaEntity updateTematica(TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar tematica con id={0}", entity.getId());
        TematicaEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar tematica con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un tematica por ID
     *
     * @param id El ID del tematica a eliminar
     */
    public void deleteTematica(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar tematica con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar tematica con id={0}", id);
    }
    
    /**
     * Obtiene una colección de instancias de ServicioEntity asociadas a una
     * instancia de Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @return Colección de instancias de ServicioEntity asociadas a la instancia de
     * Tematica
     */
    public List<ServicioEntity> listServicios(Long TematicaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros del autor con id = {0}", TematicaId);
        return getTematica(TematicaId).getServicios();
    }

    /**
     * Obtiene una instancia de ServicioEntity asociada a una instancia de Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @param serviciosId Identificador de la instancia de Servicio
     * @return La entidadd de Servicio de la tematica
     */
    public ServicioEntity getServicio(Long TematicaId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un libro con id = {0}", serviciosId);
        List<ServicioEntity> list = getTematica(TematicaId).getServicios();
        ServicioEntity serviciosEntity = new ServicioEntity();
        serviciosEntity.setId(serviciosId);
        int index = list.indexOf(serviciosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Servicio existente a un Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @param serviciosId Identificador de la instancia de Servicio
     * @return Instancia de ServicioEntity que fue asociada a Tematica
     */
    public ServicioEntity addServicio(Long TematicaId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al Tematica con id = {0}", TematicaId);
        return persistence.find(TematicaId).setServicios((persistence.find(TematicaId).getServicios().add(servicioPer.find(serviciosId))));
    }
    
}