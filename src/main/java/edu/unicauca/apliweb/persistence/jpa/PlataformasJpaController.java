/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import edu.unicauca.apliweb.persistence.entities.Plataformas;
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
public class PlataformasJpaController implements Serializable {

    public PlataformasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plataformas plataformas) {
        if (plataformas.getVideojuegosList() == null) {
            plataformas.setVideojuegosList(new ArrayList<Videojuegos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Videojuegos> attachedVideojuegosList = new ArrayList<Videojuegos>();
            for (Videojuegos videojuegosListVideojuegosToAttach : plataformas.getVideojuegosList()) {
                videojuegosListVideojuegosToAttach = em.getReference(videojuegosListVideojuegosToAttach.getClass(), videojuegosListVideojuegosToAttach.getIdVideojuegoPk());
                attachedVideojuegosList.add(videojuegosListVideojuegosToAttach);
            }
            plataformas.setVideojuegosList(attachedVideojuegosList);
            em.persist(plataformas);
            for (Videojuegos videojuegosListVideojuegos : plataformas.getVideojuegosList()) {
                Plataformas oldIdPlataformaFkOfVideojuegosListVideojuegos = videojuegosListVideojuegos.getIdPlataformaFk();
                videojuegosListVideojuegos.setIdPlataformaFk(plataformas);
                videojuegosListVideojuegos = em.merge(videojuegosListVideojuegos);
                if (oldIdPlataformaFkOfVideojuegosListVideojuegos != null) {
                    oldIdPlataformaFkOfVideojuegosListVideojuegos.getVideojuegosList().remove(videojuegosListVideojuegos);
                    oldIdPlataformaFkOfVideojuegosListVideojuegos = em.merge(oldIdPlataformaFkOfVideojuegosListVideojuegos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plataformas plataformas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plataformas persistentPlataformas = em.find(Plataformas.class, plataformas.getIdPlataformaPk());
            List<Videojuegos> videojuegosListOld = persistentPlataformas.getVideojuegosList();
            List<Videojuegos> videojuegosListNew = plataformas.getVideojuegosList();
            List<Videojuegos> attachedVideojuegosListNew = new ArrayList<Videojuegos>();
            for (Videojuegos videojuegosListNewVideojuegosToAttach : videojuegosListNew) {
                videojuegosListNewVideojuegosToAttach = em.getReference(videojuegosListNewVideojuegosToAttach.getClass(), videojuegosListNewVideojuegosToAttach.getIdVideojuegoPk());
                attachedVideojuegosListNew.add(videojuegosListNewVideojuegosToAttach);
            }
            videojuegosListNew = attachedVideojuegosListNew;
            plataformas.setVideojuegosList(videojuegosListNew);
            plataformas = em.merge(plataformas);
            for (Videojuegos videojuegosListOldVideojuegos : videojuegosListOld) {
                if (!videojuegosListNew.contains(videojuegosListOldVideojuegos)) {
                    videojuegosListOldVideojuegos.setIdPlataformaFk(null);
                    videojuegosListOldVideojuegos = em.merge(videojuegosListOldVideojuegos);
                }
            }
            for (Videojuegos videojuegosListNewVideojuegos : videojuegosListNew) {
                if (!videojuegosListOld.contains(videojuegosListNewVideojuegos)) {
                    Plataformas oldIdPlataformaFkOfVideojuegosListNewVideojuegos = videojuegosListNewVideojuegos.getIdPlataformaFk();
                    videojuegosListNewVideojuegos.setIdPlataformaFk(plataformas);
                    videojuegosListNewVideojuegos = em.merge(videojuegosListNewVideojuegos);
                    if (oldIdPlataformaFkOfVideojuegosListNewVideojuegos != null && !oldIdPlataformaFkOfVideojuegosListNewVideojuegos.equals(plataformas)) {
                        oldIdPlataformaFkOfVideojuegosListNewVideojuegos.getVideojuegosList().remove(videojuegosListNewVideojuegos);
                        oldIdPlataformaFkOfVideojuegosListNewVideojuegos = em.merge(oldIdPlataformaFkOfVideojuegosListNewVideojuegos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plataformas.getIdPlataformaPk();
                if (findPlataformas(id) == null) {
                    throw new NonexistentEntityException("The plataformas with id " + id + " no longer exists.");
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
            Plataformas plataformas;
            try {
                plataformas = em.getReference(Plataformas.class, id);
                plataformas.getIdPlataformaPk();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plataformas with id " + id + " no longer exists.", enfe);
            }
            List<Videojuegos> videojuegosList = plataformas.getVideojuegosList();
            for (Videojuegos videojuegosListVideojuegos : videojuegosList) {
                videojuegosListVideojuegos.setIdPlataformaFk(null);
                videojuegosListVideojuegos = em.merge(videojuegosListVideojuegos);
            }
            em.remove(plataformas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plataformas> findPlataformasEntities() {
        return findPlataformasEntities(true, -1, -1);
    }

    public List<Plataformas> findPlataformasEntities(int maxResults, int firstResult) {
        return findPlataformasEntities(false, maxResults, firstResult);
    }

    private List<Plataformas> findPlataformasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plataformas.class));
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

    public Plataformas findPlataformas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plataformas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlataformasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plataformas> rt = cq.from(Plataformas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
