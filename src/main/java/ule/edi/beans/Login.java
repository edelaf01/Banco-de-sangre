package ule.edi.beans;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ule.edi.dao.BorrarDAO;

import ule.edi.dao.LoginDAO;
import ule.edi.dao.RegistroDAO;
import ule.edi.dao.RegistroImpl;
import ule.edi.model.User;
import ule.edi.util.SessionUtils;

@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<User> getUserInfo() {
        return users;
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
        boolean valid = ldao.validate(user, pwd, type, metodo);
        if (valid) {
            //ultimo login

            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            type2 = type;
            user2 = user;
            return type;

        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Password",
                            "Please enter correct username and Password"));
            return "login";
        }
    }
    public void backup(){
        try {
      Process p = Runtime
            .getRuntime().exec("F:/sql/bin/mysqldump -u root -ptoor aderlass");
           

      InputStream is = p.getInputStream();
      FileOutputStream fos = new FileOutputStream("BackupBaseDatosAderlass.sql");
      byte[] buffer = new byte[1000];

      int leido = is.read(buffer);
      while (leido > 0) {
         fos.write(buffer, 0, leido);
         leido = is.read(buffer);
      }
       FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Backup completado satisfactoriamente en :",
                            "C:/Users/kkkk/GlassFish_Server/glassfish/domains/domainNOSE/config/BackupBaseDatosAderlass.sql"));
      fos.close();

   } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error al hacer backup de la base de datos:",
                            "Mira los logs chato"));
      e.printStackTrace();
   }
    }
    public String registrar() {
        String metodo = "login";
        boolean valid = ldao.validate(user, pwd, type, metodo);
        if (!valid) {
            
            RegistroImpl rdao = new RegistroImpl();
            User u = new User();

            byte[] b = pwd.getBytes();
            u.setPassword(b);
            u.setType(type);
            u.setUsername(user);
            u.setNomapel(nomapel);
            java.util.Date dt = new java.util.Date();
            u.setUlticonfec(dt);
            System.out.println("El id es;" + u.getId());
            rdao.addUser(u);
            user = user2;
             FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Usuario anyadido satisfactoriamente",
                            ""));
            return "register";

        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error usuario ya existe",
                            "Introduzca otro usuario"));
            return type2;
        }
    }

    public String borrar() {
        String metodo = "borrar";
        boolean valid = ldao.validate(user, pwd, type, metodo);
        if (valid) {

            BorrarDAO rdao = new BorrarDAO();
            User u = new User();

            u.setUsername(user);

            rdao.borrarUser(u);
            user = user2;
             FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Usuario borrado satisfactoriamente: ",
                            ""+user));

        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error usuario no encontrado, no se ha borrado ningun usuario",
                            "Introduzca otro usuario"));
        }
        return "borrar";
    }

    public List<User> listaUsuario() {
        // String metodo = "borrar";
        //boolean valid = ldao.validate(user, pwd, type, metodo);

        BorrarDAO rdao = new BorrarDAO();
        User u = new User();

        u.setUsername(user);

        setUsers(rdao.generarTabla());

        return users;

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
