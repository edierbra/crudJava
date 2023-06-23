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
        if (videojuegos.getUsuariosList() == null) {
            videojuegos.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias idCategoriaFk = videojuegos.getIdCategoriaFk();
            if (idCategoriaFk != null) {
                idCategoriaFk = em.getReference(idCategoriaFk.getClass(), idCategoriaFk.getIdCategoriaPk());
                videojuegos.setIdCategoriaFk(idCategoriaFk);
            }
            Plataformas idPlataformaFk = videojuegos.getIdPlataformaFk();
            if (idPlataformaFk != null) {
                idPlataformaFk = em.getReference(idPlataformaFk.getClass(), idPlataformaFk.getIdPlataformaPk());
                videojuegos.setIdPlataformaFk(idPlataformaFk);
            }
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : videojuegos.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getIdUsuarioPk());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            videojuegos.setUsuariosList(attachedUsuariosList);
            em.persist(videojuegos);
            if (idCategoriaFk != null) {
                idCategoriaFk.getVideojuegosList().add(videojuegos);
                idCategoriaFk = em.merge(idCategoriaFk);
            }
            if (idPlataformaFk != null) {
                idPlataformaFk.getVideojuegosList().add(videojuegos);
                idPlataformaFk = em.merge(idPlataformaFk);
            }
            for (Usuarios usuariosListUsuarios : videojuegos.getUsuariosList()) {
                usuariosListUsuarios.getVideojuegosList().add(videojuegos);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Videojuegos videojuegos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Videojuegos persistentVideojuegos = em.find(Videojuegos.class, videojuegos.getIdVideojuegoPk());
            Categorias idCategoriaFkOld = persistentVideojuegos.getIdCategoriaFk();
            Categorias idCategoriaFkNew = videojuegos.getIdCategoriaFk();
            Plataformas idPlataformaFkOld = persistentVideojuegos.getIdPlataformaFk();
            Plataformas idPlataformaFkNew = videojuegos.getIdPlataformaFk();
            List<Usuarios> usuariosListOld = persistentVideojuegos.getUsuariosList();
            List<Usuarios> usuariosListNew = videojuegos.getUsuariosList();
            if (idCategoriaFkNew != null) {
                idCategoriaFkNew = em.getReference(idCategoriaFkNew.getClass(), idCategoriaFkNew.getIdCategoriaPk());
                videojuegos.setIdCategoriaFk(idCategoriaFkNew);
            }
            if (idPlataformaFkNew != null) {
                idPlataformaFkNew = em.getReference(idPlataformaFkNew.getClass(), idPlataformaFkNew.getIdPlataformaPk());
                videojuegos.setIdPlataformaFk(idPlataformaFkNew);
            }
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getIdUsuarioPk());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            videojuegos.setUsuariosList(usuariosListNew);
            videojuegos = em.merge(videojuegos);
            if (idCategoriaFkOld != null && !idCategoriaFkOld.equals(idCategoriaFkNew)) {
                idCategoriaFkOld.getVideojuegosList().remove(videojuegos);
                idCategoriaFkOld = em.merge(idCategoriaFkOld);
            }
            if (idCategoriaFkNew != null && !idCategoriaFkNew.equals(idCategoriaFkOld)) {
                idCategoriaFkNew.getVideojuegosList().add(videojuegos);
                idCategoriaFkNew = em.merge(idCategoriaFkNew);
            }
            if (idPlataformaFkOld != null && !idPlataformaFkOld.equals(idPlataformaFkNew)) {
                idPlataformaFkOld.getVideojuegosList().remove(videojuegos);
                idPlataformaFkOld = em.merge(idPlataformaFkOld);
            }
            if (idPlataformaFkNew != null && !idPlataformaFkNew.equals(idPlataformaFkOld)) {
                idPlataformaFkNew.getVideojuegosList().add(videojuegos);
                idPlataformaFkNew = em.merge(idPlataformaFkNew);
            }
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    usuariosListOldUsuarios.getVideojuegosList().remove(videojuegos);
                    usuariosListOldUsuarios = em.merge(usuariosListOldUsuarios);
                }
            }
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    usuariosListNewUsuarios.getVideojuegosList().add(videojuegos);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = videojuegos.getIdVideojuegoPk();
                if (findVideojuegos(id) == null) {
                    throw new NonexistentEntityException("The videojuegos with id " + id + " no longer exists.");
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
