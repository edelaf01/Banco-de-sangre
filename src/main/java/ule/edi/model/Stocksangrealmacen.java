package ule.edi.model;
// Generated 30-may-2021 2:29:48 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Stocksangrealmacen generated by hbm2java
 */
public class Stocksangrealmacen  implements java.io.Serializable {


     private int id;
     private Date fecha;
     private String tipo;

    public Stocksangrealmacen() {
    }

    public Stocksangrealmacen(int id, Date fecha, String tipo) {
       this.id = id;
       this.fecha = fecha;
       this.tipo = tipo;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
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


