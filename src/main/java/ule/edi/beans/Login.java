package ule.edi.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import ule.edi.dao.LoginDAO;
import ule.edi.model.User;
import ule.edi.util.SessionUtils;

@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386362L;
    LoginDAO ldao = new LoginDAO();

    private String pwd;
    private String msg;
    private String user;
    private String type;
    private String user2;
    private String type2;
    private String nomapel;
    private String ulticonfec;
    private List<User> users;
    private List<User> usersFiltrados;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String pwd) {
        this.user2 = pwd;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsersFiltrados(List<User> users) {
        this.usersFiltrados = users;
    }

    public List<User> getUsersFiltrados() {
        return usersFiltrados;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getNomapel() {
        return nomapel;
    }

    public void setNomapel(String pwd) {
        this.nomapel = pwd;
    }

    public String getUlticonfec() {
        return ulticonfec;
    }

    public void setulticonfec(String pwd) {
        this.ulticonfec = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    //validate login
    public String validateUsernamePassword() {
        String metodo = "login";
        if (user.isEmpty() || pwd.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ,",
                    "Contraseña y usuario no pueden estar vacios"));

            return "login";
        }
        boolean valid = ldao.validate(user, pwd, type, metodo,0);

        if (valid) {
            //ultimo login
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            type2 = type;
            user2 = user;
            return type;

        } else {
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o contraseña incorrectos", ""));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        System.out.println("Haciendo logout...");
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
