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

import ule.edi.dao.RegistroImpl;
import ule.edi.model.User;
import ule.edi.util.SessionUtils;

@ManagedBean(name = "admin")
@SessionScoped
public class Admin implements Serializable {

    private static final long serialVersionUID = 1094801825228386322L;
    LoginDAO ldao = new LoginDAO();
    private int id;
    private String pwd;
    private String msg;
    private String user;
    private String type;
    private String user2;
   
    private String nomapel;
    private String ulticonfec;
    private List<User> users;
    private List<User> usersFiltrados;
    
    public int getId() {
        return id;
    }

    public void setId(int pwd) {
        this.id = pwd;
    }

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
     public void setUsers(List<User> users) {
        this.users = users;
    }
    public void setUsersFiltrados(List<User> users) {
        this.usersFiltrados = users;
    }

    public List<User> getUsersFiltrados() {
        return usersFiltrados;
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
    
    public void backup() {
        try {
            Process p = Runtime
                    .getRuntime().exec("F:/sql/bin/mysqldump -u root -ptoor aderlass");

            InputStream is = p.getInputStream();
            FileOutputStream fos = new FileOutputStream("backup_pruebasA.sql");
            byte[] buffer = new byte[1000];

            int leido = is.read(buffer);
            while (leido > 0) {
                fos.write(buffer, 0, leido);
                leido = is.read(buffer);
            }
              FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado correctamente la copia de seguridad de la base de datos",
                    "Ruta : C:\\Users\\kkkk\\GlassFish_Server\\glassfish\\domains\\domainNOSE\\config"));
            fos.close();

        } catch (Exception e) {
               FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Se error algo ha salido mal al hacer la copia de seguridad",
                    "Ruta : C:\\Users\\kkkk\\GlassFish_Server\\glassfish\\domains\\domainNOSE\\config"));
            e.printStackTrace();
        }
    }

    public void registrar() {
        String metodo = "login";
       
        if (user.isEmpty() || nomapel.isEmpty() || pwd.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error ,",
                    "Contraseña y usuario no pueden estar vacios"));
                   
        } else {
            boolean existe = ldao.validate(user, pwd, type, metodo,0);
            //Comprueba si el usuario existe o no
            if (!existe) {
                
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
                FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha registrado ,",
                        "Satisfactoriamente a " + user));

            } else {
                FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error usuario '" + user + "' ya existe",
                        "Introduzca otro usuario"));

            }
        }
    }
    public void borrarSangre(){
         String metodo = "borrarSangre";

        boolean valid = ldao.validate(user, pwd, type, metodo,id);
          if (valid) {
              ldao.borrarSangre(id);
               FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Se ha borrado la sangre correctamente",
                    ""));
          }else{
               FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error sangre con el id introducido no encontrado",
                    "Introduzca otro id"));
          }
        
    }
    public void borrar() {
        String metodo = "borrar";

        boolean valid = ldao.validate(user, pwd, type, metodo,0);
        if (valid) {

            BorrarDAO rdao = new BorrarDAO();
            User u = new User();

            u.setUsername(user);

            rdao.borrarUser(u);
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_INFO, " ",
                    "Se ha borrado a " + user));

        } else {
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error usuario no encontrado",
                    "Introduzca otro usuario"));

        }

    }
    
    public void borrarSangreAlmacen() {
        String metodo = "borrar";

        boolean valid = ldao.validate(user, pwd, type, metodo,0);
        if (valid) {

            BorrarDAO rdao = new BorrarDAO();
            User u = new User();

            u.setUsername(user);

            rdao.borrarUser(u);
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_INFO, " ",
                    "Se ha borrado a " + user));

        } else {
            FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error usuario no encontrado",
                    "Introduzca otro usuario"));

        }

    }

    public void listaUsuario() {
        // String metodo = "borrar";
        //boolean valid = ldao.validate(user, pwd, type, metodo);

        BorrarDAO rdao = new BorrarDAO();
       

        setUsers(rdao.generarTabla());

    }

    public void listaUsuario2() {
        // String metodo = "borrar";
        //boolean valid = ldao.validate(user, pwd, type, metodo);
//\b+user+\b
        

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }
}
