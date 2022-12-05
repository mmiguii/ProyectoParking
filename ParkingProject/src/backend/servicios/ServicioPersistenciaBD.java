package backend.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.clientes.Usuario;
import backend.clases.personas.personal.Empleado;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;

// Clase de gestion de la BD del sistema
public class ServicioPersistenciaBD {

	private static Connection conn;
	private static Statement stmt;
	private static Exception lastError = null; // Informacion del ultimo error SQL ocurrido
	private static Logger logger = null;

	/**
	 * Inicializa una BD SQLITE y devuelve una conexion con ella
	 * 
	 * @return Conexion con la base de datos indicada
	 */
	public static Connection connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:Parking.db");
			log(Level.INFO, "Conectada la base de datos Parking.db", null);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en conexion de base de datos Parking.db", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Devuelve statement para usar la base de datos
	 * 
	 * @param conn Conexion ya creada y abierta a la base de datos
	 * @return Sentencia de trabajo si se crea correctamente, null si hay cualquier
	 *         error
	 */
	public static Statement usarBD(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30); // poner timeout 30 msg
			return stmt;
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en uso de base de datos", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 *
	 * @param conn Conexion ya creada y abierta a la base de datos
	 * @return Sentencia de trabajo si se crea correctamente, null si hay cualquier
	 *         error
	 */
	public static Statement usarCrearTablasBD(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30); // poner timeout 30 msg
			try {
				stmt.executeUpdate(
						"CREATE TABLE CLIENTES_ORDINARIOS " + "(matricula string, " + "tipo_vehiculo string, "
								+ "fecha_entrada long, " + "fecha_salida long, " + "tarifa double, " + "importe)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			}
			try {
				stmt.executeUpdate("CREATE TABLE CLIENTES_SUBSCRITOS " + "(matricula string, "
						+ "tipo_vehiculo string, " + "tipo_cuota string, " // Semanal, Mensual,
																			// Anual
						+ "precio_cuota double, " // Cuota fija correspondiente al tiempo que se reserva (semana, mes,
													// año)
						+ "numero_plaza_ocupada integer, " + "fecha_comienzo long, " + "fecha_final long, "
						+ "numero_entradas_y_salidas integer, " + "tiempo_total_uso_parking double)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			}
			try {
				stmt.executeUpdate("CREATE TABLE TRABAJADORES " + "(id_trabajador integer, " + "dni string, "
						+ "nombre string, " + "apellido string, " + "email string," + "puesto string, "
						+ "fecha_comienzo long, " + "antiguedad integer, " + "salario_mes double)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			}
			try {
				stmt.executeUpdate("CREATE TABLE PLANTAS " + "(numero_planta integer, " + "ingresos_planta double, "
						+ "cantidad_plazas_normales integer, " + "cantidad_plazas_hibridos_o_electricos integer, "
						+ "cantidad_plazas_movilidad_reducida integer, " + "cantidad_plazas_libres integer, "
						+ "cantidad_plazas_ocupadas integer)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			}
			try {
				stmt.executeUpdate("CREATE TABLE PLAZAS " + "(numero_plaza integer, " + "numero_planta integer, "
						+ "estado_plaza string, " // Ocupado, libre
						+ "tipo_plaza string, " + "tiempo_uso_parking_ultima_estancia double," // Tiempo que ha estado
																								// ocupado la ultima vez
						+ "importe integer)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			}
			log(Level.INFO, "Creada la base de datos", null);
			return stmt;
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en creacion de base de datos", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reinicia en blanco las tablas de la base de datos. Borra todos los datos que
	 * hubiera ya en las tablas
	 * 
	 * @param conn Conexion ya creada y abierta a la base de datos
	 * @return sentencia de trabajo si se borra correctamente, null si hay cualquier
	 *         error
	 */
	public static Statement reiniciarBD(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30); // poner timeout 30 msg
			stmt.executeUpdate("DROP TABLE IF EXISTS TABLE CLIENTES_ORDINARIOS");
			stmt.executeUpdate("DROP TABLE IF EXIST TABLE CLIENTES_SUBSCRITOS");
			stmt.executeUpdate("DROP TABLE IF EXIST PLAZAS");
			stmt.executeUpdate("DROP TABLE IF EXIST PLANTA");
			stmt.executeUpdate("DROP TABLE IF EXIST TRABAJADORES");
			log(Level.INFO, "Reiniciada la base de datos", null);
			return usarCrearTablasBD(conn);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en reinicio de base de datos", e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Cierra la base de datos abierta
	 */
	public static void disconnect() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
			log(Level.INFO, "Cierre de base de datos", null);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en cierre de base de datos", e);
			e.printStackTrace();
		}
	}

	/**
	 * Informacion del ultimo error SQL ocurrido
	 * 
	 * @return Devuelve la informacion de excepcion del ultimo error producido por
	 *         cualquiera de los metodos de gestion de la base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones de clientes ordinarios
	/////////////////////////////////////////////////////////////////////

	/**
	 * Realiza una consulta a la tabla abierta de clientes ordinarios de la BD,
	 * usando la sentencia SELECT de SQL
	 * 
	 * @return Devuelve una lista de todos los clientes ordinarios cargados desde la
	 *         base de datos, null si hay cualquier error
	 */
	public static ArrayList<ClienteOrdinario> ordinariosSelect() {
		String sentSQL = "";
		List<ClienteOrdinario> ret = new ArrayList<>();
		try {
			sentSQL = "SELECT matricula, tipo_vehiculo, tarifa, fecha_entrada FROM clientes_ordinarios";
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			while (rs.next()) {
				ClienteOrdinario ordinario = new ClienteOrdinario();
				ordinario.setMatricula(rs.getString("matricula"));
				ordinario.setTipoVehiculo(rs.getString("tipo_vehiculo"));
				ordinario.setTarifa(rs.getDouble("tarifa"));
				ordinario.setFechaEntrada(rs.getLong("fecha_entrada"));
				ret.add(ordinario);
			}
			rs.close();
			return (ArrayList<ClienteOrdinario>) ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Realiza una consulta a la tabla abierta de centros de la BD, usando la
	 * sentencia SELECT de SQL
	 * 
	 * @param matricula Matricula del cliente ordinario a buscar
	 * @return Devuelve un cliente ordinario con esa matricula (exacta), null si no
	 *         se encuentra
	 */
	public static ClienteOrdinario ordinarioSelect(String matricula) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT matricula, tipo_vehiculo, tarifa, fecha_entrada FROM clientes_ordinarios WHERE matricula = '"
					+ securizer(matricula) + "'";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			if (rs.next()) {
				ClienteOrdinario ret = new ClienteOrdinario();
				ret.setMatricula(rs.getString("matricula"));
				ret.setTipoVehiculo(rs.getString("tipo_vehiculo"));
				ret.setTarifa(rs.getDouble("tarifa"));
				ret.setFechaEntrada(rs.getLong("hora_entrada"));
				rs.close();
				return ret;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return null;
		}
	}

	/**
	 * Añade un cliente ordinario a la tabla abierta de BD, usando la sentencia
	 * INSERT de SQL
	 * 
	 * @param ordinario Cliente ordinario a añadir en la base de datos
	 * @return Devuelve true si la insercion es correcta, false en caso contrario
	 */
	public static boolean ordinarioInsert(ClienteOrdinario ordinario) {
		String sentSQL = "";
		try {
			sentSQL = "INSERT INTO clientes_ordinarios (matricula, tipo_vehiculo, tarifa, fecha_entrada) VALUES (" + "'"
					+ securizer(ordinario.getMatricula()) + "', " + "'" + securizer(ordinario.getTipoVehiculo()) + "', "
					+ "'" + securizer(String.valueOf(ordinario.getTarifa())) + "', " + "'" + ordinario.getFechaEntrada()
					+ "');";
			if (ordinario.getTarifa() < 0) {
				log(Level.WARNING, "Error en insert de base de datos por tarifa negativa\t" + sentSQL, null);
				return false;
			}
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = usarBD(connect()).executeUpdate(sentSQL);
			log(Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null);
			if (val != 1) { // Se tiene que añadir 1 - error si no
				log(Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null);
				return false;
			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Borrar un cliente ordinario de la tabla abierta de BD, usando la sentencia
	 * DELETE de SQL
	 * 
	 * @param matricula Cliente ordinario a borrar de la base de datos (se toma su
	 *                  matricula para el borrado como referencia)
	 * @return Devuelve true si el borrado es correcto, false en caso contrario
	 */
	public static boolean ordinarioDelete(String matricula) {
		String sentSQL = "";
		try {
			sentSQL = "DELETE FROM clientes_ordinarios WHERE matricula = '" + securizer(matricula) + "'";
			int val = usarBD(connect()).executeUpdate(sentSQL);
			log(Level.INFO, "BD tabla clientes ordinarios eliminada " + val + " fila\t" + sentSQL, null);
			if (val != 1) { // Se tiene que eliminar 1 - error si no
				log(Level.SEVERE, "Error en delete de BD\t" + sentSQL, null);
				return false;
			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones de clientes suscritos
	/////////////////////////////////////////////////////////////////////

	/**
	 * Realiza una consulta a la tabla abierta de clientes subscritos de la BD,
	 * usando la sentencia SELECT de SQL
	 * 
	 * @return Devuelve una lista de todos los clientes subscritos cargados desde la
	 *         base de datos, null si hay cualquier error
	 */
	public static ArrayList<ClienteSubscrito> subscritosSelect() {
		String sentSQL = "";
		ArrayList<Plaza> plazas = plazasSelect();
		List<ClienteSubscrito> ret = new ArrayList<>();
		try {
			sentSQL = "SELECT matricula, tipo_vehiculo, tipo_cuota, precio_cuota, numero_plaza_ocupada, fecha_comienzo, fecha_final FROM clientes_subscritos";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				ClienteSubscrito subscrito = new ClienteSubscrito();
				subscrito.setMatricula(rs.getString("matricula"));
				subscrito.setTipoVehiculo(rs.getString("tipo_vehiculo"));
				subscrito.setTipoCuota(rs.getString("tipo_cuota"));
				subscrito.setPrecioCuota(rs.getDouble("precio_cuota"));
				int numPlaza = rs.getInt("numero_plaza");
				Plaza p = plazas.get(numPlaza - 1);
				subscrito.setPlazaOcupada(p);
				subscrito.setFechaEntrada(rs.getLong("fecha_comienzo"));
				subscrito.setFechaSalida(rs.getLong("fecha_final"));
				ret.add(subscrito);
			}
			rs.close();
			return (ArrayList<ClienteSubscrito>) ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Realiza una consulta a la tabla abierta de centros de la BD, usando la
	 * sentencia SELECT de SQL
	 * 
	 * @param matricula Matricula del cliente subscrito a buscar
	 * @return Devuelve cliente subscrito con esa matricula (exacta), null si no se
	 *         encuentra
	 */
	public static ClienteSubscrito subscritoSelect(String matricula) {
		String sentSQL = "";
		ArrayList<Plaza> plazas = plazasSelect();
		try {
			sentSQL = "SELECT matricula, tipo_vehiculo, tipo_cuota, precio_cuota, numero_plaza_ocupada, hora_entrada, hora_salida FROM clientes_subscritos WHERE matricula = '"
					+ securizer(matricula) + "'";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			if (rs.next()) {
				ClienteSubscrito ret = new ClienteSubscrito();
				ret.setMatricula(rs.getString("matricula"));
				ret.setTipoVehiculo(rs.getString("tipo_vehiculo"));
				ret.setTipoCuota(rs.getString("tipo_cuota"));
				ret.setPrecioCuota(rs.getDouble("precio_cuota"));
				int numPlaza = rs.getInt("numero_plaza");
				Plaza p = plazas.get(numPlaza - 1);
				ret.setPlazaOcupada(p);
				ret.setFechaEntrada(rs.getLong("fecha_entrada"));
				ret.setFechaSalida(rs.getLong("fecha_salida"));
				rs.close();
				return ret;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return null;
		}
	}

	/**
	 * Añade un cliente subscrito a la tabla abierta de BD, usando la sentencia
	 * INSERT de SQL
	 * 
	 * @param subscrito Cliente subscrito a añadir en la base de datos
	 * @return Devuelve true si la insercion es correcta, false en caso contrario
	 */
	public static boolean subscritoInsert(ClienteSubscrito subscrito) {
		String sentSQL = "";
		try {
			sentSQL = "INSERT INTO clientes_subscritos (matricula, tipo_vehiculo, tipo_cuota, precio_cuota, numero_plaza_ocupada, fecha_comienzo, fecha_final) VALUES ("
					+ "'" + securizer(subscrito.getMatricula()) + "', " + "'"
					+ securizer(String.valueOf(subscrito.getTipoVehiculo())) + "', " + "'"
					+ securizer(String.valueOf(subscrito.getTipoCuota())) + "', " + "'"
					+ securizer(String.valueOf(subscrito.getPrecioCuota())) + "', " + "'"
					+ securizer(String.valueOf(subscrito.getPlazaOcupada().getNumeroPlaza())) + "', " + "'"
//					+ securizer(String.valueOf(subscrito.getTipoVehiculo())) + "', " + "'" 
					+ subscrito.getFechaEntrada() + "', " + "'" + subscrito.getFechaSalida() + "')";
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = usarBD(connect()).executeUpdate(sentSQL);
			log(Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null);
			if (val != 1) { // Se tiene que añadir 1 - error si no
				log(Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null);
				return false;
			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina un cliente subscrito de la tabla abierta de BD, usando la sentencia
	 * DELETE de SQL
	 * 
	 * @param subscrito Cliente subscrito a borrar de la base de datos (se toma su
	 *                  matricula para el borrado como referencia)
	 * @return Devuelve true si el borrado es correcto, false en caso contrario
	 */
	public static boolean subscritoDelete(String matricula) {
		String sentSQL = "DELETE FROM clientes_subscritos WHERE matricula = ?";
		try (PreparedStatement pStmt = connect().prepareStatement(sentSQL)) {
			pStmt.setString(1, matricula);
			int val = pStmt.executeUpdate();
			log(Level.INFO, "BD tabla clientes subscritos eliminada " + val + " fila\t" + sentSQL, null);
			if (val != 1) { // Se tiene que eliminar 1 - error si no
				log(Level.SEVERE, "Error en delete de BD\t" + sentSQL, null);
				return false;
			}
			return true;

		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones con plazas
	/////////////////////////////////////////////////////////////////////

	/**
	 * Realiza una consulta a la tabla abierta de centros de la BD, usando la
	 * sentencia SELECT de SQL
	 * 
	 * @return Devuelve una lista de todas las plazas del parking
	 */
	public static ArrayList<Plaza> plazasSelect() {
		String sentSQL = "";
		List<Plaza> ret = new ArrayList<>();
		try {
			sentSQL = "SELECT * FROM plazas";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				Plaza plaza = new Plaza();
				plaza.setNumeroPlanta(rs.getInt("numero_planta"));
				plaza.setNumeroPlaza(rs.getInt("numero_plaza"));
				boolean estado;
				if (rs.getString("estado_plaza").equals("Disponible")) {
					estado = false;
				} else {
					estado = true;
				}
				plaza.setEstadoPlaza(estado);
				plaza.setTipoPlaza(rs.getString("tipo_plaza"));
//				plaza.setMatricula(rs.getString("matricula"));
				ret.add(plaza);
			}
			rs.close();
			return (ArrayList<Plaza>) ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

//	public static ArrayList<Plaza> plazasSelect(int numeroPlanta) {
//		String sentSQL = "SELECT numero_planta, numero_plaza, tipo_plaza, estado_plaza FROM plazas WHERE numero_planta = ?";
//		List<Plaza> ret = new ArrayList<>();
//		
//		try (PreparedStatement pStmt = connect().prepareStatement(sentSQL)) {
//			pStmt.setInt(1, numeroPlanta);
//			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
//			ResultSet rs = pStmt.executeQuery(sentSQL);
//			while (rs.next()) {
//				Plaza plaza = new Plaza();
//				plaza.setNumeroPlaza(rs.getInt("numero_plaza"));
//				plaza.setTipoPlaza(rs.getString("tipo_plaza"));
//				boolean estado;
//				if (rs.getString("estado_plaza").equals("Disponible")) {
//					estado = false;
//				} else {
//					estado = true;
//				}
//				plaza.setEstadoPlaza(estado);
//			
//				ret.add(plaza);
//			}
//			rs.close();
//			return (ArrayList<Plaza>) ret;
//		} catch (SQLException e) {
//			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
//			lastError = e;
//			e.printStackTrace();
//			return null;
//		}
//	}

	public static ArrayList<Plaza> plazasSelect(int numeroPlanta, String tipoPlaza) {
		String sentSQL = "";
		List<Plaza> ret = new ArrayList<>();

		try {
			sentSQL = "SELECT numero_planta, numero_plaza, tipo_plaza, estado_plaza FROM plazas WHERE numero_planta = "
					+ securizer(String.valueOf(numeroPlanta)) + " AND tipo_plaza = '" + securizer(tipoPlaza) + "'";
			;
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				Plaza plaza = new Plaza();
				plaza.setNumeroPlanta(rs.getInt("numero_planta"));
				plaza.setNumeroPlaza(rs.getInt("numero_plaza"));
				plaza.setTipoPlaza(rs.getString("tipo_plaza"));
				boolean estado;
				if (rs.getString("estado_plaza").equals("Disponible")) {
					estado = false;
				} else {
					estado = true;
				}
				plaza.setEstadoPlaza(estado);

				ret.add(plaza);
			}
			rs.close();
			return (ArrayList<Plaza>) ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static void update(Plaza plaza, String estado, String matricula) {
		try (PreparedStatement stmt = connect()
				.prepareStatement("UPDATE plazas SET estado_plaza = ?, matricula = ? WHERE numero_plaza = ?")) {
			stmt.setString(1, estado);
			stmt.setString(2, matricula);
			stmt.setInt(3, plaza.getNumeroPlaza());
			stmt.executeUpdate();
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: ", e);
			lastError = e;
			e.printStackTrace();
		}
	}

	public void updateDel(int numeroPlaza, String estado) {
		try (PreparedStatement stmt = connect().prepareStatement("UPDATE plazas SET estado_plaza = ?, matricula = ? WHERE numero_plaza = ?")) {
			stmt.setString(1, estado);
			stmt.setString(2, "");
			stmt.setInt(3, numeroPlaza);
			stmt.executeUpdate();
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: ", e);
			lastError = e;
			e.printStackTrace();
		}
	}

	public static int getPlaza(String matricula) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT numero_plaza FROM plazas WHERE matricula = '" + securizer(matricula) + "';";
			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return 0;
		}
	}

	/**
	 * Cuenta el numero de plazas que se encuentran disponibles en todo el parking
	 * 
	 * @return numero de plazas que se encuentran libres
	 */
	public static int getPlazasDisponibles() {
		String sentSQL = "";
		try {
			sentSQL = "SELECT COUNT(*) FROM plazas WHERE estado_plaza = 'Disponible' ";
			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return 0;
		}
	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones con plantas ///
	/////////////////////////////////////////////////////////////////////

//	/**
//	 * Cuenta el total de ingresos que se han obtnido por planta
//	 * 
//	 * @return cantidad de ingresos totales por planta
//	 */
//	public static double getIngresosPlanta() {
//		String sentSQL = "";
//		try {
//			sentSQL = "SELECT ingresos_planta FROM planta GROUP BY numero_planta";
//			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
//			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
//			rs.next();
//			return rs.getDouble(1);
//		} catch (SQLException e) {
//			lastError = e;
//			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
//			return 0;
//		}
//	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones con ingresos por plaza en plantas ///
	/////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////
	/// Operaciones por tipo de cliente ///
	/////////////////////////////////////////////////////////////////////

//	public static int getClientesPorTipo() {
//		String sentSQL = "";
//		try {
//			sentSQL = "SELECT COUNT(*) FROM tipo_cliente GROUP BY id_tipo_cliente";
//			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
//			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
//			rs.next();
//			return rs.getInt(1);
//		} catch (SQLException e) {
//			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
//			lastError = e;
//			e.printStackTrace();
//			return 0;
//		}
//	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones por tipo de tarifa ///
	/////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////
	/// Operaciones con trabajadores ///
	/////////////////////////////////////////////////////////////////////
	public static ArrayList<ClienteOrdinario> usuariosOrds() {
		String sentSQL = "";
		try {

			List<ClienteOrdinario> ret = new ArrayList<>();
			sentSQL = "SELECT matricula, tipo_vehiculo, fecha_entrada, fecha_salida FROM clientes_ordinarios;";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				ClienteOrdinario ordinario = new ClienteOrdinario();
				ordinario.setMatricula(rs.getString("matricula"));
				ordinario.setTipoVehiculo(rs.getString("tipo_vehiculo"));
				ordinario.setFechaEntrada(rs.getLong("fecha_entrada"));
				ordinario.setFechaSalida(rs.getLong("fecha_salida"));
				ret.add(ordinario);
			}
			rs.close();
			return (ArrayList<ClienteOrdinario>) ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<ClienteSubscrito> usuariosSubs() {
		String sentSQL = "";
		try {
			List<ClienteSubscrito> ret = new ArrayList<>();
			sentSQL = "SELECT matricula, tipo_vehiculo, fecha_comienzo, fecha_final FROM clientes_subscritos";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				ClienteSubscrito subscrito = new ClienteSubscrito();
				subscrito.setMatricula(rs.getString("matricula"));
				subscrito.setTipoVehiculo(rs.getString("tipo_vehiculo"));
				subscrito.setFechaEntrada(rs.getLong("fecha_comienzo"));
				subscrito.setFechaSalida(rs.getLong("fecha_final"));
				ret.add(subscrito);
			}
			rs.close();
			return (ArrayList<ClienteSubscrito>) ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Carga todos los clientes del parking en una lista
	 * 
	 * @return Devuelve una lista con todos los clientes del parking
	 */
	public static ArrayList<Usuario> usuarios() {
		List<Usuario> ret = new ArrayList<>();
		ArrayList<ClienteOrdinario> o = usuariosOrds();
		ArrayList<ClienteSubscrito> s = usuariosSubs();
		ret.addAll(o);
		ret.addAll(s);
		log(Level.INFO, "Carga de todos los usuarios de la base de datos realizada", null);
		return (ArrayList<Usuario>) ret;

	}

	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public static Usuario usuario(String matricula) {

		List<Usuario> ret = new ArrayList<>();
		ArrayList<ClienteOrdinario> o = usuariosOrds();
		ArrayList<ClienteSubscrito> s = usuariosSubs();
		log(Level.INFO, "Carga de todos los usuarios de la base de datos realizada", null);
		ret.addAll(o);
		ret.addAll(s);
		for (Usuario u : ret) {
			if (u.getMatricula().equals(matricula)) {
				if (u instanceof ClienteOrdinario) {
					ClienteOrdinario co = (ClienteOrdinario) u;
					return co;
				} else {
					ClienteSubscrito cs = (ClienteSubscrito) u;
					return cs;
				}
			}
		}

		return null;
	}

	public static Manager managerSelect(String dni) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT dni, nombre_usuario, password, puesto FROM trabajadores WHERE dni = '" + securizer(dni)
					+ "';";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			if (rs.next()) {
				Manager ret = new Manager();
				ret.setDni(rs.getString("dni"));
				ret.setNombreUsuario(rs.getString("nombre_usuario"));
				ret.setPassword(rs.getString("password"));
				ret.setPuesto(rs.getString("puesto"));
				rs.close();
				return ret;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return null;
		}
	}

	public static Empleado empleadoSelect(String dni) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT dni, nombre_usuario, password, puesto FROM trabajadores WHERE dni = '" + securizer(dni)
					+ "';";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			if (rs.next()) {
				Empleado ret = new Empleado();
				ret.setDni(rs.getString("dni"));
				ret.setNombreUsuario(rs.getString("nombre_usuario"));
				ret.setPassword(rs.getString("password"));
				ret.setPuesto(rs.getString("puesto"));
				rs.close();
				return ret;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return null;
		}
	}

	public static ArrayList<Empleado> empleadosSelect() {
		String sentSQL = "";
		try {
			List<Empleado> ret = new ArrayList<>();
			sentSQL = "SELECT dni, nombre_usuario, password, email, puesto FROM trabajadores";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				Empleado empleado = new Empleado();
				empleado.setDni(rs.getString("dni"));
				empleado.setNombreUsuario(rs.getString("nombre_usuario"));
				empleado.setPassword(rs.getString("password"));
				empleado.setEmail(rs.getString("email"));
				empleado.setPuesto(rs.getString("puesto"));
				ret.add(empleado);
			}
			rs.close();
			return (ArrayList<Empleado>) ret;
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return null;
		}
	}

	public static ArrayList<Manager> managersSelect() {
		String sentSQL = "";
		try {
			List<Manager> ret = new ArrayList<>();
			sentSQL = "SELECT dni, nombre_usuario, password, email, puesto FROM trabajadores;";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = usarBD(connect()).executeQuery(sentSQL);
			while (rs.next()) {
				Manager manager = new Manager();
				manager.setDni(rs.getString("dni"));
				manager.setNombreUsuario(rs.getString("nombre_usuario"));
				manager.setPassword(rs.getString("password"));
				manager.setEmail(rs.getString("email"));
				manager.setPuesto(rs.getString("puesto"));
				ret.add(manager);
			}
			rs.close();
			return (ArrayList<Manager>) ret;
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return null;
		}
	}

	public static ArrayList<Trabajador> trabajadoresSelect() {
		List<Trabajador> ret = new ArrayList<>();
		ArrayList<Manager> m = managersSelect();
		ArrayList<Empleado> e = empleadosSelect();
		ret.addAll(m);
		ret.addAll(e);
		log(Level.INFO, "Carga de todos los trabajadores de la base de datos realizada", null);
		return (ArrayList<Trabajador>) ret;

	}

	/////////////////////////////////////////////////////////////////////
	/// Operaciones de ingresos por plaza de clientes ordinarios ///
	/////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////
	/// Operaciones de ingresos por plaza de clientes suscritos ///
	/////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////
	/// Operaciones por tipo de trabajador ///
	/////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////
	/// Metodos privados
	/////////////////////////////////////////////////////////////////////

	/**
	 * Volcado de informacion segura en SQL. Mantiene solo los caracteres seguros en
	 * español y sustituye ' por ''.
	 * 
	 * @param string Palabra que va ser introducida por el usuario
	 * @return Devuelve un String "securizado"
	 */
	private static String securizer(String string) {
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZñÑáéíóúüÁÉÍÓÚÚ.,:;-_(){}[]-+*=<>'\"¿?¡!&%$@#/\\0123456789 "
					.indexOf(c) >= 0) {
				ret.append(c);
			}
		}
		return ret.toString().replaceAll("'", "''");
	}

	/////////////////////////////////////////////////////////////////////
	/// Logging ///
	/////////////////////////////////////////////////////////////////////

	/**
	 * Metodo local para poder loggear. (si no se asigna un logger externo, se
	 * asigna uno local)
	 * 
	 * @param level     Indica la severidad del error
	 * @param message   Mensaje que queremos registrar
	 * @param exception Descripcion muy detallada del estado del programa en el
	 *                  momento que sucede el error
	 */
	@SuppressWarnings("static-access")
	private static void log(Level level, String message, Throwable exception) {
		if (logger == null) { // Logger por defecto local
			logger = Logger.getLogger("Base de Datos: Local"); // Nombre del logger
			logger.setLevel(level.ALL); // Loguea todos los niveles
			try {
				logger.addHandler(new FileHandler("bd.log.xlm", true)); // Saca el log a un fichero xlm
			} catch (Exception e) {
				logger.log(Level.SEVERE, "No ha sido posible crear el fichero de log", e);
			}
		}
		if (exception == null) {
			logger.log(level, message);
		} else {
			logger.log(level, message, exception);
		}

	}

//
}
