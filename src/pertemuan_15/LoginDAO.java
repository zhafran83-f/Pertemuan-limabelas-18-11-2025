/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class LoginDAO {
    
    public Login_1 findByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Login_1> q = em.createQuery("SELECT l FROM Login_1 l WHERE l.username = :u", Login_1.class);
            q.setParameter("u", username);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean create(Login_1 login) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(login);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updatePassword(String username, String newPw, String newPertanyaan, String newJawaban) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Login_1> q = em.createQuery("SELECT l FROM Login_1 l WHERE l.username = :u", Login_1.class);
            q.setParameter("u", username);
            Login_1 l = q.getSingleResult();
            l.setPw(newPw);
            if (newPertanyaan != null) l.setPertanyaan(newPertanyaan);
            if (newJawaban != null) l.setJawaban(newJawaban);
            em.merge(l);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
