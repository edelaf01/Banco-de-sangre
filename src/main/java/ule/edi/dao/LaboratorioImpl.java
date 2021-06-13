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
import ule.edi.model.Stocksangrealmacen;

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

    @Transactional
    public void addSangre(Stocksangrealmacen s) {
        // Connection con = null;
        // con = DataConnect.getConnection();
        Transaction transaction = null;
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();

            transaction = session.beginTransaction();

            session.save(s);

            transaction.commit();
            session.flush();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            //DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
    }

    public List<Donantevalidar> generarTabla() {
        //Devuelve una lista 
        Connection con = null;
        con = DataConnect.getConnection();
        List<Donantevalidar> listaSangre = null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "FROM Donantevalidar ";

            session.beginTransaction();
          
            Query query = session.createQuery(hql);

            if (!query.list().isEmpty()) {
              
                listaSangre = query.list();
                for(int i=0;i<listaSangre.size();i++){
                     System.out.println();
                    System.out.println(listaSangre.get(i).getId());
                     System.out.println(listaSangre.get(i).getTipo());
                      System.out.println(listaSangre.get(i).getFecha());
                       System.out.println();
                }
              
                return listaSangre;
            }

            session.flush();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            DataConnect.close(con);
            session.flush();
            session.clear();
            session.close();
        }
        return listaSangre;
    }

}
