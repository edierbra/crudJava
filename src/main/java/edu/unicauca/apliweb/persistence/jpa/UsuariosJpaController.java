/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import edu.unicauca.apliweb.persistence.entities.Usuarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.unicauca.apliweb.persistence.entities.Videojuegos;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author edier
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getVideojuegosList() == null) {
            usuarios.setVideojuegosList(new ArrayList<Videojuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Videojuegos> attachedVideojuegosList = new ArrayList<Videojuegos>();
            for (Videojuegos videojuegosListVideojuegosToAttach : usuarios.getVideojuegosList()) {
                videojuegosListVideojuegosToAttach = em.getReference(videojuegosListVideojuegosToAttach.getClass(), videojuegosListVideojuegosToAttach.getIdVideojuegoPk());
                attachedVideojuegosList.add(videojuegosListVideojuegosToAttach);
            }
            usuarios.setVideojuegosList(attachedVideojuegosList);
            em.persist(usuarios);
            for (Videojuegos videojuegosListVideojuegos : usuarios.getVideojuegosList()) {
                videojuegosListVideojuegos.getUsuariosList().add(usuarios);
                videojuegosListVideojuegos = em.merge(videojuegosListVideojuegos);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuarioPk());
            List<Videojuegos> videojuegosListOld = persistentUsuarios.getVideojuegosList();
            List<Videojuegos> videojuegosListNew = usuarios.getVideojuegosList();
            List<Videojuegos> attachedVideojuegosListNew = new ArrayList<Videojuegos>();
            for (Videojuegos videojuegosListNewVideojuegosToAttach : videojuegosListNew) {
                videojuegosListNewVideojuegosToAttach = em.getReference(videojuegosListNewVideojuegosToAttach.getClass(), videojuegosListNewVideojuegosToAttach.getIdVideojuegoPk());
                attachedVideojuegosListNew.add(videojuegosListNewVideojuegosToAttach);
            }
            videojuegosListNew = attachedVideojuegosListNew;
            usuarios.setVideojuegosList(videojuegosListNew);
            usuarios = em.merge(usuarios);
            for (Videojuegos videojuegosListOldVideojuegos : videojuegosListOld) {
                if (!videojuegosListNew.contains(videojuegosListOldVideojuegos)) {
                    videojuegosListOldVideojuegos.getUsuariosList().remove(usuarios);
                    videojuegosListOldVideojuegos = em.merge(videojuegosListOldVideojuegos);
                }
            }
            for (Videojuegos videojuegosListNewVideojuegos : videojuegosListNew) {
                if (!videojuegosListOld.contains(videojuegosListNewVideojuegos)) {
                    videojuegosListNewVideojuegos.getUsuariosList().add(usuarios);
                    videojuegosListNewVideojuegos = em.merge(videojuegosListNewVideojuegos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUsuarioPk();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuarioPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<Videojuegos> videojuegosList = usuarios.getVideojuegosList();
            for (Videojuegos videojuegosListVideojuegos : videojuegosList) {
                videojuegosListVideojuegos.getUsuariosList().remove(usuarios);
                videojuegosListVideojuegos = em.merge(videojuegosListVideojuegos);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
