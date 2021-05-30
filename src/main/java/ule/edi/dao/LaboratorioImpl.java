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
import ule.edi.model.Donantevalidar;

import ule.edi.model.User;
import ule.edi.util.DataConnect;

@Repository
public class LaboratorioImpl {

    private static final Logger logger = LoggerFactory.getLogger(LaboratorioImpl.class);
    private Session session;

    @Transactional
    public boolean borrarSangre(Donantevalidar p) {
        // Connection con = null;
        // con = DataConnect.getConnection();
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();

            //  session = HibernateUtil.getSessionFactory().getCurrentSession();
            String hql = "delete from Donantevalidar where id=:id";
            Query query = session.createQuery(hql);

            transaction = session.beginTransaction();

            query.setString("id", "" + p.getId());

            query.executeUpdate();
            if (!transaction.wasCommitted()) {
                transaction.commit();
            }

           return true;
        } catch (Exception e) {
           
            e.printStackTrace();
            return false;

        } finally {
            //DataConnect.close(con)
            session.flush();
            session.clear();
            session.close();

        }

    }

    /*@Override
    @Transactional
    public void addSangre(Donantevalidar p) {
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
    }*/
    public List<Donantevalidar> generarTabla() {
        //Devuelve una lista 
        Connection con = null;
        con = DataConnect.getConnection();
        List<Donantevalidar> listaSangre = null;
        try {
            System.out.println("Probando");
            session = HibernateUtil.getSessionFactory().openSession();
            System.out.println("Probando");
            String hql = "FROM Donantevalidar ";

            session.beginTransaction();
            System.out.println("Probando");
            Query query = session.createQuery(hql);

            if (!query.list().isEmpty()) {
                System.out.println("Probando");
                listaSangre = query.list();
                System.out.println(listaSangre.toString());
                return listaSangre;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.close();
        }
        return listaSangre;
    }

}
