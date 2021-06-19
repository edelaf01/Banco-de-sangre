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
import ule.edi.model.Donantevalidar;

import ule.edi.model.User;
import ule.edi.util.DataConnect;

@Repository
public class CampoImpl  {

    private static final Logger logger = LoggerFactory.getLogger(CampoImpl.class);
    private Session session;


  

   
    @Transactional
    public void addSangre(Donantevalidar u) {
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();

            session.save(u);

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

    
    public List<Donantevalidar> listPersons() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
