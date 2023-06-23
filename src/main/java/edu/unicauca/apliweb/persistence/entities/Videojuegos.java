/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "videojuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videojuegos.findAll", query = "SELECT v FROM Videojuegos v"),
    @NamedQuery(name = "Videojuegos.findByIdVideojuegoPk", query = "SELECT v FROM Videojuegos v WHERE v.idVideojuegoPk = :idVideojuegoPk"),
    @NamedQuery(name = "Videojuegos.findByTitulo", query = "SELECT v FROM Videojuegos v WHERE v.titulo = :titulo"),
    @NamedQuery(name = "Videojuegos.findByPrecio", query = "SELECT v FROM Videojuegos v WHERE v.precio = :precio"),
    @NamedQuery(name = "Videojuegos.findByTamano", query = "SELECT v FROM Videojuegos v WHERE v.tamano = :tamano")
})

public class Videojuegos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_videojuego_pk")
    private Integer idVideojuegoPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "titulo")
    private String titulo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tamano")
    private BigDecimal tamano;
    @ManyToMany(mappedBy = "videojuegosList")
    private List<Usuarios> usuariosList;
    @JoinColumn(name = "id_categoria_fk", referencedColumnName = "id_categoria_pk")
    @ManyToOne
    private Categorias idCategoriaFk;
    @JoinColumn(name = "id_plataforma_fk", referencedColumnName = "id_plataforma_pk")
    @ManyToOne
    private Plataformas idPlataformaFk;

    public Videojuegos() {
    }

    public Videojuegos(Integer idVideojuegoPk) {
        this.idVideojuegoPk = idVideojuegoPk;
    }

    public Videojuegos(Integer idVideojuegoPk, String titulo, BigDecimal precio, BigDecimal tamano) {
        this.idVideojuegoPk = idVideojuegoPk;
        this.titulo = titulo;
        this.precio = precio;
        this.tamano = tamano;
    }

    public Integer getIdVideojuegoPk() {
        return idVideojuegoPk;
    }

    public void setIdVideojuegoPk(Integer idVideojuegoPk) {
        this.idVideojuegoPk = idVideojuegoPk;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTamano() {
        return tamano;
    }

    public void setTamano(BigDecimal tamano) {
        this.tamano = tamano;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public Categorias getIdCategoriaFk() {
        return idCategoriaFk;
    }

    public void setIdCategoriaFk(Categorias idCategoriaFk) {
        this.idCategoriaFk = idCategoriaFk;
    }

    public Plataformas getIdPlataformaFk() {
        return idPlataformaFk;
    }

    public void setIdPlataformaFk(Plataformas idPlataformaFk) {
        this.idPlataformaFk = idPlataformaFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVideojuegoPk != null ? idVideojuegoPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videojuegos)) {
            return false;
        }
        Videojuegos other = (Videojuegos) object;
        if ((this.idVideojuegoPk == null && other.idVideojuegoPk != null) || (this.idVideojuegoPk != null && !this.idVideojuegoPk.equals(other.idVideojuegoPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Videojuegos[ idVideojuegoPk=" + idVideojuegoPk + " ]";
    }

}
