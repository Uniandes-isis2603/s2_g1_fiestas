/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.BlogPersistence;
import java.util.*;
import java.util.logging.*;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Blog.
 *
 * @author mc.gonzalez15
 */
public class BlogLogic {

    private static final Logger LOGGER = Logger.getLogger(BlogLogic.class.getName());

    @Inject
    private BlogPersistence persistence;
    
    /**
     * Obtiene la lista de los registros de Blog.
     *
     * @return Colección de objetos de BlogEntity.
     */
    public List<BlogEntity> getBlogs() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Blog a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BlogEntity con los datos del Blog consultado.
     */
    public BlogEntity getBlog(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un blog con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Blog en la base de datos.
     *
     * @param entity Objeto de BlogEntity con los datos nuevos
     * @return Objeto de BlogEntity con los datos nuevos y su ID.
     */
    public BlogEntity createBlog(BlogEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un blog ");

        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Blog.
     *
     * @param entity Instancia de BlogEntity con los nuevos datos.
     * @return Instancia de BlogEntity con los datos actualizados.
     */
    public BlogEntity updateBlog(BlogEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un blog ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Blog de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteBlog(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor ");
        persistence.delete(id);
    }
}
