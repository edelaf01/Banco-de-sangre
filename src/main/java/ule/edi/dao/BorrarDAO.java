/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ule.edi.dao;

/**
 *
 * @author kkkk
 */
import java.sql.Connection;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ule.edi.model.User;
import ule.edi.util.DataConnect;

@Repository
public class BorrarDAO {

    private static final Logger logger = LoggerFactory.getLogger(BorrarDAO.class);
    private Session session;

    @Transactional
    public void borrarUser(User p) {
        // Connection con = null;
        // con = DataConnect.getConnection();
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           //  session = HibernateUtil.getSessionFactory().getCurrentSession();
            String hql = "delete from User where username=:uname";
            Query query = session.createQuery(hql);
            transaction = session.beginTransaction();
            query.setString("uname", p.getUsername());
            query.executeUpdate();
            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            //DataConnect.close(con);
            session.close();
        }
    }

    public List<User> generarTabla() {

        Connection con = null;
        con = DataConnect.getConnection();
        List<User> users = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            String hql = "FROM User ";

            session.beginTransaction();

            Query query = session.createQuery(hql);

            if (!query.list().isEmpty()) {
                
                users=query.list();
                System.out.println(users.toString());
                return users;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.close();
        }
        return users;
    }

}
