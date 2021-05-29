package ule.edi.model;
// Generated 29-may-2021 1:18:14 by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private byte[] password;
    private String type;
    private String nomapel;
    private Date ulticonfec;

    public User() {
    }

    public User(String username, byte[] password, String type, String nomapel, Date ulticonfec) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.nomapel = nomapel;
        this.ulticonfec = ulticonfec;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNomapel() {
        return this.nomapel;
    }

    public void setNomapel(String nomapel) {
        this.nomapel = nomapel;
    }

    public Date getUlticonfec() {
        return this.ulticonfec;
    }

    public void setUlticonfec(Date ulticonfec) {
        this.ulticonfec = ulticonfec;
    }

}
