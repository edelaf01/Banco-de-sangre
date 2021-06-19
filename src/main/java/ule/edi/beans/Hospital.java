package ule.edi.beans;

import ule.edi.model.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ule.edi.dao.HospitalImpl;
import ule.edi.model.Inventariohospital;
import ule.edi.model.User;

import ule.edi.util.SessionUtils;
@ManagedBean(name = "hospital")
@SessionScoped
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1094801825228386354L;
    //tipo de la sangre que vamos a gastar
    private String tipoSangre;
    private Integer cantidadGastar;
    private Integer dosis;
    private Date fecha;
    private String msg;
    private String tipo;
    private String nombreUsuario;
    //
    private List<Inventariohospital> listaAlmacen;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String pwd) {
        this.nombreUsuario = pwd;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date pwd) {
        this.fecha = pwd;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String pwd) {
        this.tipoSangre = pwd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String pwd) {
        this.tipo = pwd;
    }

    public Integer getDosis() {
        return dosis;
    }

    public void setDosis(Integer pwd) {
        this.dosis = pwd;
    }

    public Integer getCantidadGastar() {
        return cantidadGastar;
    }

    public void setCantidadGastar(Integer pwd) {
        this.cantidadGastar = pwd;
    }

    public List<Inventariohospital> getListaAlmacen() {
        return listaAlmacen;
    }

    public void setListaAlmacen(List<Inventariohospital> users) {
        this.listaAlmacen = users;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //Para enviar necesitamos recibir un id
    public void enviarPedido() {
        ule.edi.model.Pedidos p = new ule.edi.model.Pedidos();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        p.setFecha(date);
        p.setTipo(tipo);
        p.setDosis(dosis);
        p.setUsuarioDestino(nombreUsuario);
        HospitalImpl hdao = new HospitalImpl();
        List<User> jeje = hdao.getUserId(nombreUsuario);
        if (jeje.isEmpty()) {
               FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error no se ha encontrado usuario", ""
                    ));
        } else {
            if (!jeje.get(0).getType().equals("Hospital")) {
                  FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error el usuario no es de tipo hospital no se le puede enviar", ""
                    ));
            } else {
                p.setUsuarioDestino(jeje.get(0).getUsername());
                //p.setDestinatarioid(jeje.get(0).getId());
                hdao.hacerPedido(p);
                 FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Pedido realizado correctamente shur", ""
                    ));
            }
        }
        System.out.println(nombreUsuario);
        //TO-DO p.setDestinatarioid();

    }

    public List<Inventariohospital> listaStockAlmacen() {
        HospitalImpl hdao = new HospitalImpl();
        setListaAlmacen(hdao.generarTablaAlmacen(nombreUsuario));

        return listaAlmacen;

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
