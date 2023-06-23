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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuarioPk", query = "SELECT u FROM Usuarios u WHERE u.idUsuarioPk = :idUsuarioPk"),
    @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuarios.findByEdad", query = "SELECT u FROM Usuarios u WHERE u.edad = :edad"),
    @NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email = :email"),
    @NamedQuery(name = "Usuarios.findByClave", query = "SELECT u FROM Usuarios u WHERE u.clave = :clave"),
    @NamedQuery(name = "Usuarios.findByCargo", query = "SELECT u FROM Usuarios u WHERE u.cargo = :cargo")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario_pk")
    private Integer idUsuarioPk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edad")
    private int edad;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "clave")
    private String clave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cargo")
    private String cargo;
    @JoinTable(name = "usuario_videojuego", joinColumns = {
        @JoinColumn(name = "id_usuario_fk", referencedColumnName = "id_usuario_pk")}, inverseJoinColumns = {
        @JoinColumn(name = "id_videojuego_fk", referencedColumnName = "id_videojuego_pk")})
    @ManyToMany
    private List<Videojuegos> videojuegosList;

    public Usuarios() {
    }

    public Usuarios(Integer idUsuarioPk) {
        this.idUsuarioPk = idUsuarioPk;
    }

    public Usuarios(Integer idUsuarioPk, String nombre, int edad, String email, String clave, String cargo) {
        this.idUsuarioPk = idUsuarioPk;
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.clave = clave;
        this.cargo = cargo;
    }

    public Integer getIdUsuarioPk() {
        return idUsuarioPk;
    }

    public void setIdUsuarioPk(Integer idUsuarioPk) {
        this.idUsuarioPk = idUsuarioPk;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        hash += (idUsuarioPk != null ? idUsuarioPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idUsuarioPk == null && other.idUsuarioPk != null) || (this.idUsuarioPk != null && !this.idUsuarioPk.equals(other.idUsuarioPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.unicauca.apliweb.persistence.entities.Usuarios[ idUsuarioPk=" + idUsuarioPk + " ]";
    }
    
}
