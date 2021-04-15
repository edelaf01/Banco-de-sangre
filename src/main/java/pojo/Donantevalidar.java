/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kkkk
 */
@Entity
@Table(name = "donantevalidar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donantevalidar.findAll", query = "SELECT d FROM Donantevalidar d"),
    @NamedQuery(name = "Donantevalidar.findById", query = "SELECT d FROM Donantevalidar d WHERE d.id = :id"),
    @NamedQuery(name = "Donantevalidar.findByTipo", query = "SELECT d FROM Donantevalidar d WHERE d.tipo = :tipo"),
    @NamedQuery(name = "Donantevalidar.findByFecha", query = "SELECT d FROM Donantevalidar d WHERE d.fecha = :fecha")})
public class Donantevalidar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Donantevalidar() {
    }

    public Donantevalidar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donantevalidar)) {
            return false;
        }
        Donantevalidar other = (Donantevalidar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Donantevalidar[ id=" + id + " ]";
    }
    
}
