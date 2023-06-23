package edu.unicauca.apliweb.persistence.entities;

import edu.unicauca.apliweb.persistence.entities.Categorias;
import edu.unicauca.apliweb.persistence.entities.Plataformas;
import edu.unicauca.apliweb.persistence.entities.Usuarios;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-06-22T20:19:24")
@StaticMetamodel(Videojuegos.class)
public class Videojuegos_ { 

    public static volatile SingularAttribute<Videojuegos, Categorias> idCategoriaFk;
    public static volatile ListAttribute<Videojuegos, Usuarios> usuariosList;
    public static volatile SingularAttribute<Videojuegos, BigDecimal> precio;
    public static volatile SingularAttribute<Videojuegos, Integer> idVideojuegoPk;
    public static volatile SingularAttribute<Videojuegos, Plataformas> idPlataformaFk;
    public static volatile SingularAttribute<Videojuegos, String> titulo;
    public static volatile SingularAttribute<Videojuegos, BigDecimal> tamano;

}