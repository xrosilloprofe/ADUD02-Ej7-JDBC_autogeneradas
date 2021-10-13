package jdbc_autogeneradas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_autogeneradas {

	public static void muestraErrorSQL(SQLException e) {
		System.err.println("SQL ERROR mensaje: " + e.getMessage());
		System.err.println("SQL Estado: " + e.getSQLState());
		System.err.println("SQL código específico: " + e.getErrorCode());
	}

	public static void main(String[] args) {

		String basedatos = "exemples_accessDades";
		String host = "localhost";
		String port = "3306";
		String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		String user = "xavier";
		String pwd = "1234";

		try (Connection c = DriverManager.getConnection(urlConnection, user, pwd)) {
			try (PreparedStatement sInsertF = c
					.prepareStatement("INSERT INTO FACTURAS(DNI_CLIENTE) VALUES (?);",PreparedStatement.RETURN_GENERATED_KEYS);
				 PreparedStatement sInsertL = c
							.prepareStatement("INSERT INTO LINEAS_FACTURA(num_factura, linea_factura, concepto, cantidad) "
									+ "VALUES (?,?,?,?);")) {

				c.setAutoCommit(false);

				int i = 0;
				sInsertF.setString(++i, "78901234X");
				sInsertF.executeUpdate();
				ResultSet rs = sInsertF.getGeneratedKeys();
				rs.next();
				int numFact = rs.getInt(1);

				i = 0;
				sInsertL.setInt(++i, numFact);
				sInsertL.setInt(++i, 1); //Linea de Factura 1
				sInsertL.setString(++i, "MONITOR");
				sInsertL.setInt(++i, 50); 
				sInsertL.executeUpdate();

				i = 0;
				sInsertL.setInt(++i, numFact);
				sInsertL.setInt(++i, 2); //Linea de Factura 2
				sInsertL.setString(++i, "SSD Externo");
				sInsertL.setInt(++i, 73); 
				sInsertL.executeUpdate();

				c.commit();
				

			} catch (SQLException e) {
				muestraErrorSQL(e);
				try {
					c.rollback();
				} catch (Exception er) {
					System.err.println("ERROR haciendo ROLLBACK");
					er.printStackTrace(System.err);
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR de conexión");
			e.printStackTrace(System.err);
		}

	}
}
