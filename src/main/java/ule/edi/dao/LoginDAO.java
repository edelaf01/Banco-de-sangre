package ule.edi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ule.edi.util.DataConnect;

public class LoginDAO {

	public static boolean validate(String user, String password,String type) {
		Connection con = null;
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
		return false;
	}
}