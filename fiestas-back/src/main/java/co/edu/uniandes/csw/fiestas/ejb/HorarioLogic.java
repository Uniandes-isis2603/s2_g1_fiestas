/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.HorarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author df.nino10
 */
@Stateless
public class HorarioLogic {
    
    private static final Logger LOGGER= Logger.getLogger(HorarioLogic.class.getName());
    
    @Inject
    private HorarioPersistence persistence;
    
            
    public List<HorarioEntity> getHorarios(){
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los horarios.");
        return persistence.findAll();
    }

    public HorarioEntity getHorario(Long id) {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar el horario con id={0}", id);
        return persistence.find(id);
    }

    public HorarioEntity createHorario(HorarioEntity entity)throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación del horario con id={0}");
        if(persistence.find(entity.getId())!= null)
            throw new BusinessLogicException("El horario con el id\""+ entity.getId()+"\" ya existe");
        
        LOGGER.info("Termina proceso de creación del horario");
        return persistence.create(entity);
    }

    public HorarioEntity updateHorario(HorarioEntity entity) {
         LOGGER.log(Level.INFO,"Inicia proceso de actualización del horario con id={0}", entity.getId());
         HorarioEntity newEntity = persistence.update(entity);
         LOGGER.log(Level.INFO,"Termina proceso de actualización del horario con id={0}", entity.getId());
         return newEntity;
    }

    public void deleteHorario(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}