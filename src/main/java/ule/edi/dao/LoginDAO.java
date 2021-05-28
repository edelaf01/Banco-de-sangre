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

    public boolean validate(String user, String password, String type) {
        /*Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataConnect.getConnection();
              
			ps = con.prepareStatement("Select username, password from user where username = ? and password = ? and type= ?");
			ps.setString(1, user);
			ps.setString(2, password);
                        ps.setString(3, type);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
		return false;*/
        Connection con = null;
        con = DataConnect.getConnection();
        System.out.println("Lol1");
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            System.out.println("Lol1");
            String hql = "FROM User WHERE username='" + user + "' and password='" + password + "' and type='" + type + "'";
            System.out.println("Lol1");
            session.beginTransaction();
            Query query = session.createQuery(hql);
            System.out.println("Lol1");
            if (!query.list().isEmpty()) {
                return true;
            }
            System.out.println("Lol" + query.list().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataConnect.close(con);
        }
        return false;
    }
}
