/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import edu.unicauca.apliweb.persistence.entities.Categorias;
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
public class CategoriasJpaController implements Serializable {

    public CategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categorias categorias) {
        if (categorias.getVideojuegosList() == null) {
            categorias.setVideojuegosList(new ArrayList<Videojuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Videojuegos> attachedVideojuegosList = new ArrayList<Videojuegos>();
            for (Videojuegos videojuegosListVideojuegosToAttach : categorias.getVideojuegosList()) {
                videojuegosListVideojuegosToAttach = em.getReference(videojuegosListVideojuegosToAttach.getClass(), videojuegosListVideojuegosToAttach.getIdVideojuegoPk());
                attachedVideojuegosList.add(videojuegosListVideojuegosToAttach);
            }
            categorias.setVideojuegosList(attachedVideojuegosList);
            em.persist(categorias);
            for (Videojuegos videojuegosListVideojuegos : categorias.getVideojuegosList()) {
                Categorias oldIdCategoriaFkOfVideojuegosListVideojuegos = videojuegosListVideojuegos.getIdCategoriaFk();
                videojuegosListVideojuegos.setIdCategoriaFk(categorias);
                videojuegosListVideojuegos = em.merge(videojuegosListVideojuegos);
                if (oldIdCategoriaFkOfVideojuegosListVideojuegos != null) {
                    oldIdCategoriaFkOfVideojuegosListVideojuegos.getVideojuegosList().remove(videojuegosListVideojuegos);
                    oldIdCategoriaFkOfVideojuegosListVideojuegos = em.merge(oldIdCategoriaFkOfVideojuegosListVideojuegos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categorias categorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias persistentCategorias = em.find(Categorias.class, categorias.getIdCategoriaPk());
            List<Videojuegos> videojuegosListOld = persistentCategorias.getVideojuegosList();
            List<Videojuegos> videojuegosListNew = categorias.getVideojuegosList();
            List<Videojuegos> attachedVideojuegosListNew = new ArrayList<Videojuegos>();
            for (Videojuegos videojuegosListNewVideojuegosToAttach : videojuegosListNew) {
                videojuegosListNewVideojuegosToAttach = em.getReference(videojuegosListNewVideojuegosToAttach.getClass(), videojuegosListNewVideojuegosToAttach.getIdVideojuegoPk());
                attachedVideojuegosListNew.add(videojuegosListNewVideojuegosToAttach);
            }
            videojuegosListNew = attachedVideojuegosListNew;
            categorias.setVideojuegosList(videojuegosListNew);
            categorias = em.merge(categorias);
            for (Videojuegos videojuegosListOldVideojuegos : videojuegosListOld) {
                if (!videojuegosListNew.contains(videojuegosListOldVideojuegos)) {
                    videojuegosListOldVideojuegos.setIdCategoriaFk(null);
                    videojuegosListOldVideojuegos = em.merge(videojuegosListOldVideojuegos);
                }
            }
            for (Videojuegos videojuegosListNewVideojuegos : videojuegosListNew) {
                if (!videojuegosListOld.contains(videojuegosListNewVideojuegos)) {
                    Categorias oldIdCategoriaFkOfVideojuegosListNewVideojuegos = videojuegosListNewVideojuegos.getIdCategoriaFk();
                    videojuegosListNewVideojuegos.setIdCategoriaFk(categorias);
                    videojuegosListNewVideojuegos = em.merge(videojuegosListNewVideojuegos);
                    if (oldIdCategoriaFkOfVideojuegosListNewVideojuegos != null && !oldIdCategoriaFkOfVideojuegosListNewVideojuegos.equals(categorias)) {
                        oldIdCategoriaFkOfVideojuegosListNewVideojuegos.getVideojuegosList().remove(videojuegosListNewVideojuegos);
                        oldIdCategoriaFkOfVideojuegosListNewVideojuegos = em.merge(oldIdCategoriaFkOfVideojuegosListNewVideojuegos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorias.getIdCategoriaPk();
                if (findCategorias(id) == null) {
                    throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.");
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
            Categorias categorias;
            try {
                categorias = em.getReference(Categorias.class, id);
                categorias.getIdCategoriaPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.", enfe);
            }
            List<Videojuegos> videojuegosList = categorias.getVideojuegosList();
            for (Videojuegos videojuegosListVideojuegos : videojuegosList) {
                videojuegosListVideojuegos.setIdCategoriaFk(null);
                videojuegosListVideojuegos = em.merge(videojuegosListVideojuegos);
            }
            em.remove(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categorias> findCategoriasEntities() {
        return findCategoriasEntities(true, -1, -1);
    }

    public List<Categorias> findCategoriasEntities(int maxResults, int firstResult) {
        return findCategoriasEntities(false, maxResults, firstResult);
    }

    private List<Categorias> findCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorias.class));
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

    public Categorias findCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> rt = cq.from(Categorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
