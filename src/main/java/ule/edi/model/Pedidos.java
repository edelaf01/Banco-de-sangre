package ule.edi.model;
// Generated 30-may-2021 21:09:22 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Pedidos generated by hbm2java
 */
public class Pedidos  implements java.io.Serializable {


     private int id;
     private Date fecha;
     private String tipo;
     private int dosis;
     private int destinatarioid;

    public Pedidos() {
    }

	
    public Pedidos(int id, String tipo, int dosis, int destinatarioid) {
        this.id = id;
        this.tipo = tipo;
        this.dosis = dosis;
        this.destinatarioid = destinatarioid;
    }
    public Pedidos(int id, Date fecha, String tipo, int dosis, int destinatarioid) {
       this.id = id;
       this.fecha = fecha;
       this.tipo = tipo;
       this.dosis = dosis;
       this.destinatarioid = destinatarioid;
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
    public int getDosis() {
        return this.dosis;
    }
    
    public void setDosis(int dosis) {
        this.dosis = dosis;
    }
    public int getDestinatarioid() {
        return this.destinatarioid;
    }
    
    public void setDestinatarioid(int destinatarioid) {
        this.destinatarioid = destinatarioid;
    }




}


