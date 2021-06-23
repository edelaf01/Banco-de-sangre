package ule.edi.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ule.edi.dao.CampoDAO;
import ule.edi.dao.CampoImpl;

import ule.edi.model.Donantevalidar;
import ule.edi.model.User;
import ule.edi.util.SessionUtils;

@ManagedBean(name = "campoNombre")
@SessionScoped
public class Campo implements Serializable {    

    private static final long serialVersionUID = 1094801825228386362L;
    CampoDAO cdao;
    private Integer id;
    private String msg;
    private String valido;
    private String tipoSangre;

    private List<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer pwd) {
        this.id = pwd;
    }

    public String getValido() {
        return valido;
    }

    public void setValido(String pwd) {
        this.valido = pwd;
    }

    public List<User> getUserInfo() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public void anyadirSangre() {
        String metodo = "login";

        if ("si".equals(valido)) {

            CampoImpl campdao = new CampoImpl();
            Donantevalidar u = new Donantevalidar();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            
            u.setFecha(date);
            u.setTipo(tipoSangre);

            campdao.addSangre(u);
            FacesContext.getCurrentInstance().addMessage(
                    "MessageId",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Sangre anyadido correctamente", ""
                    ));

        } else {
            FacesContext.getCurrentInstance().addMessage(
                    "MessageId",
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Sangre no valido",
                            ""));

        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
