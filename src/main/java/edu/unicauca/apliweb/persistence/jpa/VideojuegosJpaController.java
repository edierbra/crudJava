/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.unicauca.apliweb.persistence.entities.Categorias;
import edu.unicauca.apliweb.persistence.entities.Plataformas;
import edu.unicauca.apliweb.persistence.entities.Usuarios;
import edu.unicauca.apliweb.persistence.entities.Videojuegos;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author edier
 */
public class VideojuegosJpaController implements Serializable {

    public VideojuegosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Videojuegos videojuegos) {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();

        String titulo = videojuegos.getTitulo();
        BigDecimal precio = videojuegos.getPrecio();
        BigDecimal tamaño = videojuegos.getTamano();

        // Validar que los campos obligatorios no sean nulos o vacíos
        if (titulo == null || titulo.isEmpty() || precio == null || tamaño == null) {
            throw new IllegalArgumentException("El título, precio y tamaño son campos obligatorios.");
        }

        // Obtener la categoría y plataforma por su id
        Categorias idCategoriaFk = videojuegos.getIdCategoriaFk();        
        Plataformas idPlataformaFk = videojuegos.getIdPlataformaFk();

        // Validar que la categoría y plataforma no sean nulas
        if (idCategoriaFk == null || idPlataformaFk == null) {
            throw new IllegalArgumentException("La categoría y plataforma son campos obligatorios.");
        }

        videojuegos.setTitulo(titulo);
        videojuegos.setPrecio(precio);
        videojuegos.setTamano(tamaño);

        // Establecer la categoría y plataforma en el videojuego
        videojuegos.setIdCategoriaFk(idCategoriaFk);
        videojuegos.setIdPlataformaFk(idPlataformaFk);

        em.persist(videojuegos);       
        
        em.getTransaction().commit();
    } catch (IllegalArgumentException e) {
        // Capturar excepciones de validación y relanzarlas
        throw e;
    } catch (Exception e) {
        // Capturar excepciones generales y relanzar una excepción personalizada
        throw new RuntimeException("Error al crear el videojuego.", e);
    } finally {
        if (em != null) {
            em.close();
        }
    }
}



    public void edit(Videojuegos videojuego) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
        em = getEntityManager();
        em.getTransaction().begin();
        
        Videojuegos persistentVideojuego = em.find(Videojuegos.class, videojuego.getIdVideojuegoPk());
        persistentVideojuego.setTitulo(videojuego.getTitulo());
        persistentVideojuego.setPrecio(videojuego.getPrecio());
        persistentVideojuego.setTamano(videojuego.getTamano());
        
        Categorias idCategoriaFkNew = videojuego.getIdCategoriaFk();
        if (idCategoriaFkNew != null) {
            idCategoriaFkNew = em.getReference(Categorias.class, idCategoriaFkNew.getIdCategoriaPk());
            persistentVideojuego.setIdCategoriaFk(idCategoriaFkNew);
        }
        
        Plataformas idPlataformaFkNew = videojuego.getIdPlataformaFk();
        if (idPlataformaFkNew != null) {
            idPlataformaFkNew = em.getReference(Plataformas.class, idPlataformaFkNew.getIdPlataformaPk());
            persistentVideojuego.setIdPlataformaFk(idPlataformaFkNew);
        }
        
        videojuego = em.merge(persistentVideojuego);
        
        em.getTransaction().commit();
    } catch (Exception ex) {
        String msg = ex.getLocalizedMessage();
        if (msg == null || msg.length() == 0) {
            Integer id = videojuego.getIdVideojuegoPk();
            if (findVideojuegos(id) == null) {
                throw new NonexistentEntityException("The videojuego with id " + id + " no longer exists.");
            }
        }
        throw ex;
    } finally {
        if (em != null) {
            em.close();
        }
    }
}


    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Videojuegos videojuegos;
            try {
                videojuegos = em.getReference(Videojuegos.class, id);
                videojuegos.getIdVideojuegoPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The videojuegos with id " + id + " no longer exists.", enfe);
            }
            Categorias idCategoriaFk = videojuegos.getIdCategoriaFk();
            if (idCategoriaFk != null) {
                idCategoriaFk.getVideojuegosList().remove(videojuegos);
                idCategoriaFk = em.merge(idCategoriaFk);
            }
            Plataformas idPlataformaFk = videojuegos.getIdPlataformaFk();
            if (idPlataformaFk != null) {
                idPlataformaFk.getVideojuegosList().remove(videojuegos);
                idPlataformaFk = em.merge(idPlataformaFk);
            }
            List<Usuarios> usuariosList = videojuegos.getUsuariosList();
            for (Usuarios usuariosListUsuarios : usuariosList) {
                usuariosListUsuarios.getVideojuegosList().remove(videojuegos);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
            }
            em.remove(videojuegos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Videojuegos> findVideojuegosEntities() {
        return findVideojuegosEntities(true, -1, -1);
    }

    public List<Videojuegos> findVideojuegosEntities(int maxResults, int firstResult) {
        return findVideojuegosEntities(false, maxResults, firstResult);
    }

    private List<Videojuegos> findVideojuegosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Videojuegos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Videojuegos findVideojuegos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Videojuegos.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideojuegosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Videojuegos> rt = cq.from(Videojuegos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
