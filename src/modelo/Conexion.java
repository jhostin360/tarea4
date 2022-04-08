package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String user = "root";
	private static final String pass = "1236";
	private static final String url ="jdbc:mysql://localhost:3306/CrudDatos";
	
	public Connection conex() {
		Connection con = null;
		
		try 
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				System.out.println("Conectado perra");
			}
		}
		catch (ClassNotFoundException | SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		return con;
	}
	
}
