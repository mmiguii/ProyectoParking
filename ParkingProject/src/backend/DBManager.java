package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import backend.customer.OrdinaryCustomer;

public class DBManager {

	private Connection conn = null;

	// Abrir conexion a la BD
	public void connect(String dbPath) throws DBException {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (ClassNotFoundException e) {
			throw new DBException("Error cargando el driver de la BD", e);
		} catch (SQLException e) {
			throw new DBException("Error conectando a la BD", e);
		}
	}

	// Cerrar conexion con la BD
	public void disconnet() throws DBException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DBException("Error cerrando la conexión con la BD", e);
		}
	}

	// Obtiene la lista de todos los clientes ordinarios
	public List<OrdinaryCustomer> getAllOrdinaryCustomers() throws DBException {
		List<OrdinaryCustomer> oCustomers = new ArrayList<OrdinaryCustomer>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery("SELECT licensePlate, vehicleType, fare FROM ordinaryCustomer");
			while (rs.next()) {
				OrdinaryCustomer nOC = new OrdinaryCustomer();
				nOC.setLicensePlate(rs.getString("licensePlate"));
				nOC.setVehicleType(rs.getInt("vehicleType"));
				nOC.setFare(rs.getDouble("fare"));
				oCustomers.add(nOC);
			}
			return oCustomers;
		} catch (SQLException e) {
			throw new DBException("Error obteniendo todos los clientes ordinarios'", e);
		}		
	}

	// Obtiene un cliente ordinario pasando la matricula como parametro
	public OrdinaryCustomer getOrdinaryCustomer(String licensePlateValor) throws DBException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT licensePlate, vehicleType, fare FROM ordinaryCustomer WHERE licensePlate = ?")) {
			stmt.setString(1, licensePlateValor);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				OrdinaryCustomer nOC = new OrdinaryCustomer();
				nOC.setLicensePlate(rs.getString("licensePlate"));
				nOC.setVehicleType(rs.getInt("vehicleType"));
				nOC.setFare(rs.getDouble("fare"));
				return nOC;
			} else {
				// System.out.println("No se ha encontrado el vehiculo introducido");
				throw new DBException("Error obteniendo el vehiculo con matricula " + licensePlateValor);
			}

		} catch (SQLException e) {
			throw new DBException("Error. No se ha encontrado ningun vehicuo con la matricula  " + licensePlateValor, e);
		}
	}

	// Guardar un nuevo cliente ordinario en el parking por primera vez
	public void storeOrdinaryCustomer(OrdinaryCustomer oC) throws DBException {
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO ordinaryCustomer (licensePlate, vehicleType, fare) VALUES (?, ?, ?)")) {

			stmt.setString(1, oC.getLicensePlate());
			stmt.setInt(2, oC.getVehicleType());
			stmt.setDouble(3, oC.getFare());
			
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DBException("No se pudo guardar el usuario en la BD", e);
		}
	}
	
	
	
	// Elimino un cliente ordinario de la BD
	public void delete(OrdinaryCustomer oC) throws DBException {
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM ordinaryCustomer WHERE licensePlate = ?")) {
			stmt.setString(1, oC.getLicensePlate());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("No se pudo elimiar el usuario con id " + oC.getLicensePlate(), e);
		}
	}

}


