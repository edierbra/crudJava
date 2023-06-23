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
@Table(name = "categorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorias.findAll", query = "SELECT c FROM Categorias c"),
    @NamedQuery(name = "Categorias.findByIdCategoriaPk", query = "SELECT c FROM Categorias c WHERE c.idCategoriaPk = :idCategoriaPk"),
    @NamedQuery(name = "Categorias.findByNombre", query = "SELECT c FROM Categorias c WHERE c.nombre = :nombre")})
public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria_pk")
    private Integer idCategoriaPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idCategoriaFk")
    private List<Videojuegos> videojuegosList;

    public Categorias() {
    }

    public Categorias(Integer idCategoriaPk) {
        this.idCategoriaPk = idCategoriaPk;
    }

    public Categorias(Integer idCategoriaPk, String nombre) {
        this.idCategoriaPk = idCategoriaPk;
        this.nombre = nombre;
    }

    public Integer getIdCategoriaPk() {
        return idCategoriaPk;
    }

    public void setIdCategoriaPk(Integer idCategoriaPk) {
        this.idCategoriaPk = idCategoriaPk;
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
        hash += (idCategoriaPk != null ? idCategoriaPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorias)) {
            return false;
        }
        Categorias other = (Categorias) object;
        if ((this.idCategoriaPk == null && other.idCategoriaPk != null) || (this.idCategoriaPk != null && !this.idCategoriaPk.equals(other.idCategoriaPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "edu.unicauca.apliweb.persistence.entities.Categorias[ idCategoriaPk=" + idCategoriaPk + " ]";
        return nombre;
    }
    
}
