package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;

/**
 * TematicaDTO Objeto de transferencia de datos de la entidad de Tematica. Los
 * DTO contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer
 *      "descripcion": String
 *   }
 * </pre> Por ejemplo una entidad de Servicio se representa asi:<br>
 * <pre>
 *
 *   {
 *      "id": 97971354
 *      "descripcion": Boda
 *   }
 *
 * </pre>
 *
 * @author af.losada
 */
public class TematicaDTO {

    private long id;
    private String descripcion;

    /**
     * Constructor por defecto
     */
    public TematicaDTO() {
        //COnstructor vacio
    }

    /**
     * Crea un objeto TematicaDTO a partir de un objeto TematicaEntity.
     *
     * @param entity Entidad TematicaEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public TematicaDTO(TematicaEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.descripcion = entity.getDescripcion();
        }
    }

    /**
     * Convierte un objeto TematicaDetailDTO a TematicaEntity incluyendo los
     * atributos de TematicaDTO.
     *
     * @return Nueva objeto TematicaEntity.
     *
     */
    public TematicaEntity toEntity() {
        TematicaEntity entity = new TematicaEntity();
        entity.setId(id);
        entity.setDescripcion(descripcion);
        return entity;
    }


    /**
     *
     * @return La descripción de la temática
     */
    public String darDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return La ID de la temática
     */
    public long darID() {
        return id;
    }

    /**
     *
     * @param pID La nueva ID
     */
    public void setID(long pID) {
        id = pID;
    }

    /**
     *
     * @param pDesc La nueva descripción de la temática
     */
    public void setDescripcion(String pDesc) {
        descripcion = pDesc;
    }
}
