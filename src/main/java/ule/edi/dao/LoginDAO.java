package ule.edi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ule.edi.model.User;
import ule.edi.util.DataConnect;

public class LoginDAO {

    private Session session;

    public boolean validate(String user, String password, String type, String metodo) {
        if ("login".equals(metodo)) {
            Connection con = null;
            con = DataConnect.getConnection();

            try {

                session = HibernateUtil.getSessionFactory().openSession();

                String hql = "FROM User WHERE username='" + user + "' and password='" + password + "'and type='" + type + "'";

                session.beginTransaction();

                Query query = session.createQuery(hql);
                List<User> us = query.list();
                if (!us.isEmpty()) {
                    User u = us.get(0);
                    
                    updateLoginTime(u);
                    return true;
                }

                session.flush();

            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();

            } finally {
                DataConnect.close(con);
               // session.close();
            }
            return false;
        } else if ("registro".equals(metodo)) {
            // Connection con = null;
            // con = DataConnect.getConnection();

            try {
                session = HibernateUtil.getSessionFactory().openSession();

                String hql = "FROM User WHERE username='" + user + "' and type='" + type + "'";

                session.beginTransaction();
                Query query = session.createQuery(hql);
                session.flush();

                if (!query.list().isEmpty()) {
                    session.close();
                    return true;
                }

            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            } finally {
                //  DataConnect.close(con);
                session.close();

            }
            return false;
        } else {
            try {
                session = HibernateUtil.getSessionFactory().openSession();

                String hql = "FROM User WHERE username='" + user + "'";

                session.beginTransaction();
                Query query = session.createQuery(hql);
                session.flush();

                if (!query.list().isEmpty()) {
                    return true;
                }

            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                return false;
            } finally {
                //  DataConnect.close(con);
                session.close();

            }
            return false;
        }

    }

    private void updateLoginTime(User u) {
        Transaction transaction = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            session = HibernateUtil.getSessionFactory().openSession();
            // session = HibernateUtil.getSessionFactory().getCurrentSession();
            u.setUlticonfec(date);
            transaction = session.beginTransaction();

            session.saveOrUpdate(u);

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
}
