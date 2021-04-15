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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kkkk
 */
@Entity
@Table(name = "stocksangre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stocksangre.findAll", query = "SELECT s FROM Stocksangre s"),
    @NamedQuery(name = "Stocksangre.findById", query = "SELECT s FROM Stocksangre s WHERE s.id = :id"),
    @NamedQuery(name = "Stocksangre.findByTipo", query = "SELECT s FROM Stocksangre s WHERE s.tipo = :tipo"),
    @NamedQuery(name = "Stocksangre.findByFecha", query = "SELECT s FROM Stocksangre s WHERE s.fecha = :fecha")})
public class Stocksangre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Stocksangre() {
    }

    public Stocksangre(Integer id) {
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
        if (!(object instanceof Stocksangre)) {
            return false;
        }
        Stocksangre other = (Stocksangre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "view.Stocksangre[ id=" + id + " ]";
    }
    
}
