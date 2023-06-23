package edu.unicauca.apliweb.persistence.entities;

import edu.unicauca.apliweb.persistence.entities.Videojuegos;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-06-22T21:47:31")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, String> clave;
    public static volatile ListAttribute<Usuarios, Videojuegos> videojuegosList;
    public static volatile SingularAttribute<Usuarios, Integer> idUsuarioPk;
    public static volatile SingularAttribute<Usuarios, String> cargo;
    public static volatile SingularAttribute<Usuarios, String> nombre;
    public static volatile SingularAttribute<Usuarios, Integer> edad;
    public static volatile SingularAttribute<Usuarios, String> email;

}