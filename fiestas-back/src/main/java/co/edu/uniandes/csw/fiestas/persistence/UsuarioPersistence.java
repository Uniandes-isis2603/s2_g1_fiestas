/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author df.nino10
 */
@Stateless
public class UsuarioPersistence {
    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    public UsuarioEntity find(Long id){
        return em.find(UsuarioEntity.class, id);
        
    }
    public List<UsuarioEntity> findAll(){
        Query q = em.createQuery("select u form UsuarioEntity u");
        return q.getResultList();
    }
    
    public UsuarioEntity create (UsuarioEntity entity){
        em.persist(entity);
        return entity;
    }
    
    public UsuarioEntity Update (UsuarioEntity entity){
        return em.merge(entity);
    }
    
    public void delete (Long id){
        UsuarioEntity entity = em.find(UsuarioEntity.class, id);
        em.remove(entity);
    }
}