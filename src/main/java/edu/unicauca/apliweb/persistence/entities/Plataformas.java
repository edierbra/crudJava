/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edier
 */
@Entity
@Table(name = "plataformas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plataformas.findAll", query = "SELECT p FROM Plataformas p"),
    @NamedQuery(name = "Plataformas.findByIdPlataformaPk", query = "SELECT p FROM Plataformas p WHERE p.idPlataformaPk = :idPlataformaPk"),
    @NamedQuery(name = "Plataformas.findByNombre", query = "SELECT p FROM Plataformas p WHERE p.nombre = :nombre")})
public class Plataformas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plataforma_pk")
    private Integer idPlataformaPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idPlataformaFk")
    private List<Videojuegos> videojuegosList;

    public Plataformas() {
    }

    public Plataformas(Integer idPlataformaPk) {
        this.idPlataformaPk = idPlataformaPk;
    }

    public Plataformas(Integer idPlataformaPk, String nombre) {
        this.idPlataformaPk = idPlataformaPk;
        this.nombre = nombre;
    }

    public Integer getIdPlataformaPk() {
        return idPlataformaPk;
    }

    public void setIdPlataformaPk(Integer idPlataformaPk) {
        this.idPlataformaPk = idPlataformaPk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Videojuegos> getVideojuegosList() {
        return videojuegosList;
    }

    public void setVideojuegosList(List<Videojuegos> videojuegosList) {
        this.videojuegosList = videojuegosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlataformaPk != null ? idPlataformaPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plataformas)) {
            return false;
        }
        Plataformas other = (Plataformas) object;
        if ((this.idPlataformaPk == null && other.idPlataformaPk != null) || (this.idPlataformaPk != null && !this.idPlataformaPk.equals(other.idPlataformaPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "edu.unicauca.apliweb.persistence.entities.Plataformas[ idPlataformaPk=" + idPlataformaPk + " ]";
        return nombre;
    }
    
}
