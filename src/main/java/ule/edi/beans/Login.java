package ule.edi.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import ule.edi.dao.LoginDAO;
import ule.edi.dao.RegistroDAO;
import ule.edi.dao.RegistroImpl;
import ule.edi.model.User;
import ule.edi.util.SessionUtils;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    private String pwd;
    private String msg;
    private String user;
    private String type;
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
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
        LoginDAO ldao = new LoginDAO();
        boolean valid = ldao.validate(user, pwd, type);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            
            return type;
            
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }
    
    public String registrar() {
        LoginDAO ldao = new LoginDAO();
        boolean valid = ldao.validate(user, pwd, type);
        if (!valid) {
            //HttpSession session = SessionUtils.getSession();
            //session.setAttribute("username", user);
            RegistroImpl rdao = new RegistroImpl();
            User u = new User();
            byte[] b = pwd.getBytes();
            u.setPassword(b);
            u.setType(type);
            u.setUsername(user);
            rdao.addUser(u);
            return type;
            
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error usuario ya existe",
                            "Introduzca otro usuario"));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
