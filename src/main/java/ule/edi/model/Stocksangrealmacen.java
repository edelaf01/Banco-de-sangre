package ule.edi.model;
// Generated 18-jun-2021 23:36:29 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Stocksangrealmacen generated by hbm2java
 */
public class Stocksangrealmacen  implements java.io.Serializable {


     private Integer id;
     private Date fecha;
     private String tipo;

    public Stocksangrealmacen() {
    }

    public Stocksangrealmacen(Date fecha, String tipo) {
        
       this.fecha = fecha;
       this.tipo = tipo;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




}


