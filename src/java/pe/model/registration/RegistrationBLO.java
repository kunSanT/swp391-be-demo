/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.model.registration;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Thanh
 */
public class RegistrationBLO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PE_Prj301_JPAPU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Registration checkLogin(String username, String password) {
        Registration result = null;

        //1. Create entity manager
        EntityManager em = emf.createEntityManager();
        try {
            //2. Create jpql String
            String jpql = "Select registration "
                    + "From Registration registration "
                    + "Where registration.username = :username "
                    + "And registration.password = :password";
            //3. Create query object
            Query query = em.createQuery(jpql);
            query.setParameter("username", username);
            query.setParameter("password", password);
            //4. Execute query 
            result = (Registration) query.getSingleResult();
        } finally {
            em.close();
        }
        return result;
    }

    public List<Registration> searchLastname(String searchValue) {
        List<Registration> result = null;
        //1. Create entity manager
        EntityManager em = emf.createEntityManager();
        try {
            //2. Create jpql String
            String jpql = "Select registration "
                    + "From Registration registration "
                    + "Where registration.lastname Like :lastname ";
            //3. Create query object
            Query query = em.createQuery(jpql);
            query.setParameter("lastname", "%" + searchValue + "%");
            //4. Execute query 
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public boolean deleteAccount(String username) {
        boolean result = false;

        //1. Create entity manager
        EntityManager em = emf.createEntityManager();
        try {
            //2. Find candidate entity
            Registration regTmp = em.find(Registration.class, username);
            //3. Destroy entity
            if (regTmp != null) {
                em.getTransaction().begin();
                em.remove(regTmp);
                em.getTransaction().commit();

                result = true;
            }
        } finally {
            em.close();
        }
        return result;
    }

    public boolean updateAccount(String username, String password, boolean role) {
        boolean result = false;

        //1. Create entity manager
        EntityManager em = emf.createEntityManager();
        try {
            //2. Find candidate entity
            Registration regTmp = em.find(Registration.class, username);
            //3. Update entity
            if (regTmp != null) {
                em.getTransaction().begin();
                regTmp.setPassword(password);
                regTmp.setIsAdmin(role);
                em.merge(regTmp);
                em.getTransaction().commit();

                result = true;
            }
        } finally {
            em.close();
        }
        return result;
    }
    
    public boolean createAccount(Registration account) {
        boolean result = false;

        //1. Create entity manager
        EntityManager em = emf.createEntityManager();
        try {
            //2. Find candidate entity
            Registration regTmp = em.find(Registration.class, account.getUsername());
            //3. Insert entity
            if (regTmp == null) {
//                this.persist(regTmp);
                this.persist(account);
                result = true;
            }
        } finally {
            em.close();
        }
        return result;
    }
}
