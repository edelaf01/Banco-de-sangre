package ule.edi.model;
// Generated 30-may-2021 21:47:07 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Donantevalidar generated by hbm2java
 */
public class DonantevalidarAUX  implements java.io.Serializable {


     private String id;
     private String tipo;
     private Date fecha;

    public DonantevalidarAUX() {
    }

	
    public DonantevalidarAUX(Date fecha) {
        this.fecha = fecha;
    }
    public DonantevalidarAUX(String tipo, Date fecha) {
       this.tipo = tipo;
       this.fecha = fecha;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


