/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author mc.gonzalez15
 */
@Entity
public class BlogEntity extends BaseEntity implements Serializable{
     
     /**
      * Titulo del Blog
      */
     private String titulo;
     
     /**
      * Cuerpo del Blog 
      */
     private String cuerpo;
     
     /**
      * Número de likes
      */
     private int likes;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
     
     
   
}