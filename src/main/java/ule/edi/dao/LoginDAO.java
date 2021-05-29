package ule.edi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hibernate.Query;
import org.hibernate.Session;

import ule.edi.util.DataConnect;

public class LoginDAO {

    private Session session;

    public boolean validate(String user, String password, String type, String metodo) {
        if ("login".equals(metodo)) {
            Connection con = null;
            con = DataConnect.getConnection();

            try {
                System.out.println("AVER");
                session = HibernateUtil.getSessionFactory().openSession();

                String hql = "FROM User WHERE username='" + user + "' and password='" + password + "'and type='" + type + "'";
                   System.out.println("AVER");
                session.beginTransaction();
                   System.out.println("AVER");
                Query query = session.createQuery(hql);
   System.out.println("AVER");
                if (!query.list().isEmpty()) {
                    return true;
                }
                   System.out.println("AVER");
                session.flush();

            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
               
            } finally {
                DataConnect.close(con);
                session.close();
            }
            return false;
        } else {
           // Connection con = null;
           // con = DataConnect.getConnection();

            try {
                session = HibernateUtil.getSessionFactory().openSession();

                String hql = "FROM User WHERE username='" + user + "' and type='" + type + "'";
                
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
}
