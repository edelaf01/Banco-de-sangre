package ule.edi.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ule.edi.dao.LaboratorioDAO;
import ule.edi.dao.LaboratorioImpl;

import ule.edi.model.Donantevalidar;
import ule.edi.model.Stocksangrealmacen;

import ule.edi.util.SessionUtils;

@ManagedBean(name = "laboratorio")
@SessionScoped
public class Laboratorio implements Serializable {

    private static final long serialVersionUID = 1094801825228386322L;
    private LaboratorioDAO ldao;
    private Integer id;
    private String msg;

    private String tipoSangre;

    private List<Donantevalidar> users;
    private List<Donantevalidar> usersFiltrados;

    public Integer getId() {

        return id;
    }

    public void setId(Integer pwd) {
        this.id = pwd;
    }

    public List<Donantevalidar> getUsers() {
        return users;
    }

    public void setListavalidar(List<Donantevalidar> users) {
        this.users = users;
    }

    public void setUsersFiltrados(List<Donantevalidar> users) {
        this.usersFiltrados = users;
    }

    public List<Donantevalidar> getUsersFiltrados() {
        return usersFiltrados;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String pwd) {
        this.tipoSangre = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void borrarSangre() {
        //Hacer una query para 
        LaboratorioImpl ldao2 = new LaboratorioImpl();
        Donantevalidar u = new Donantevalidar();

        u.setId(id);
        Boolean jeje = ldao2.borrarSangre(u);
        if (jeje == true) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Sangre borrada correctamente", ""
                    ));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Sangre no encontrada no se ha borrado nada", ""
                    ));
        }

    }

    public void addSangre() {
        LaboratorioImpl ldao2 = new LaboratorioImpl();

        Donantevalidar u = new Donantevalidar();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        u.setFecha(date);
        u.setId(id);
        Stocksangrealmacen s = new Stocksangrealmacen();
        s.setFecha(date);
        String tipoS = "";
        List<Donantevalidar> lista = ldao2.generarTabla();
        for (int i = 0; i < lista.size(); i++) {
            if (id == lista.get(i).getId()) {
                tipoS = lista.get(i).getTipo();
                break;
            }
        }
        if (tipoS == "") {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se ha encontrado el id error", ""
                    ));
        } else {
            s.setTipo(tipoS);
            //Borro el objeto que voy a mover
            ldao2.borrarSangre(u);
            ldao2.addSangre(s);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Sangre anyadido correctamente", ""
                    ));
        }
    }

    public void listaSangre() {
        LaboratorioImpl ldao2 = new LaboratorioImpl();
        setListavalidar(ldao2.generarTabla());

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
