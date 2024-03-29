package ule.edi.beans;
// Generated 30-may-2021 21:47:07 by Hibernate Tools 4.3.1


import java.text.ParseException;
import java.text.SimpleDateFormat;
import ule.edi.model.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pedidoshechos generated by hbm2java
 */
public class Pedidoshechos  implements java.io.Serializable {


     private int id;
     private Date fecha;
     private Date fechacompletarpedido;
     private int dosis;
    private String usuarioDestino;
     private String tipo;

    public Pedidoshechos() {
    }

    public Pedidoshechos(int id, Date fecha, Date fechacompletarpedido, int dosis, String usuarioDestino, String tipo) {
       this.id = id;
       this.fecha = fecha;
       this.fechacompletarpedido = fechacompletarpedido;
       this.dosis = dosis;
       this.usuarioDestino = usuarioDestino;
       this.tipo = tipo;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Date getFecha() {
       
        String a="";
        a=fecha.toString();
         try {  
             Date date1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(a);
         } catch (ParseException ex) {
             Logger.getLogger(Pedidoshechos.class.getName()).log(Level.SEVERE, null, ex);
         }
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getFechacompletarpedido() {
        return this.fechacompletarpedido;
    }
    
    public void setFechacompletarpedido(Date fechacompletarpedido) {
        this.fechacompletarpedido = fechacompletarpedido;
    }
    public int getDosis() {
        return this.dosis;
    }
    
    public void setDosis(int dosis) {
        this.dosis = dosis;
    }
     public String getUsuarioDestino() {
        return this.usuarioDestino;
    }
    
    public void setUsuarioDestino(String usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




}


