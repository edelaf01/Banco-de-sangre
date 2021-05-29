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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ule.edi.model.User;
import ule.edi.util.DataConnect;

@Repository
public class RegistroImpl implements RegistroDAO {

    private static final Logger logger = LoggerFactory.getLogger(RegistroImpl.class);
    private Session session;

    @Override
    @Transactional
    public void addUser(User p) {
        // Connection con = null;
        // con = DataConnect.getConnection();
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();

            session.save(p);

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

    @Override
    public List<User> listPersons() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
