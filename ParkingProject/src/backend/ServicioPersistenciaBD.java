package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.customer.OrdinaryCustomer;
import backend.customer.SubscriberCustomer;

// Clase de gestion de la BD del sistema
public class ServicioPersistenciaBD {

	private static Exception lastError = null; // Informacion del ultimo error SQL ocurrido
	private static Logger logger = null;
	
	
	/** Inicializa una BD SQLITE y devuelve una conexion con ella
	 * @param dbPath  Nombre del fichero de la base de datos 
	 * @return  Conexion con la base de datos indicada
	 */
	public static Connection connect(String dbPath) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			log(Level.INFO, "Conectada la base de datos " + dbPath, null);
			return conn;
		} catch (ClassNotFoundException  | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexion de base de datos " + dbPath, e );
			e.printStackTrace();
			return null;
			}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param conn	Conexion ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarBD(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30);  // poner timeout 30 msg
			return stmt;
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en uso de base de datos", e);
			e.printStackTrace();
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param conn	Conexion ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30);  // poner timeout 30 msg
			try {
				stmt.executeUpdate("CREATE TABLE CLIENTES_ORDINARIOS " +
					"(id_clientes_ordinario integer primary key autoincrement not null, "
					+ "matricula text not null, "
					+ "id_tipo_cliente integer NOT NULL REFERENCES TIPOCLIENTE(IDTIPOCLIENTE) ON DELETE CASCADE, "
					+ "id_tipo_tarifa integer NOT NULL REFERENCES TIPOTARIFA(IDTIPOTARIFA) ON DELETE CASCADE, "
					+ "numero_entradas_y_salidas integer, "
					+ "tiempo_total_uso_parking real, "
					+ "ingresos_totales real default 0.0)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			try {
				stmt.executeUpdate("CREATE TABLE CLIENTES_SUSCRITOS " +
					"(numero_plaza integer NOT NULL REFERENCES ESTADO(PLAZAS) ON DELETE CASCADE, "
					+ "id_cliente_suscrito integer INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
					+ "matricula TEXT NOT NULL, "
					+ "id_tipo_cliente integer NOT NULL REFERENCES TIPOCLIENTE(IDTIPOCLIENTE) ON DELETE CASCADE, "
					+ "id_tipo_tarifa integer NOT NULL REFERENCES TIPOTARIFA(IDTIPOTARIFA) ON DELETE CASCADE, "
					+ "numero_entradas_y_salidas integer, "
					+ "tiempo_uso_parking_ultima_estancia real, "
					+ "tiempo_total_uso_parking real, "
					+ "cuota integer)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			try {
				stmt.executeUpdate("CREATE TABLE ESTADO_PLAZAS " +
					"(numero_planta integer NOT NULL REFERENCES PLANTA(NUMPLANTA) ON DELETE CASCADE, "
					+ "numero_plaza integer PRIMARY KEY NOT NULL, "
					+ "estado_plaza integer NOT NULL DEFAULT 0, "
					+ "id_tipo_cliente integer NOT NULL REFERENCES TIPOCLIENTE(IDTIPOCLIENTE) ON DELETE CASCADE, "
					+ "id_tipo_tarifa integer NOT NULL REFERENCES TIPOTARIFA(IDTIPOTARIFA) ON DELETE CASCADE, "
					+ "tiempo_uso_parking_ultima_estancia real,"
					+ "importe real)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			try {
				stmt.executeUpdate("CREATE TABLE PLANTA " +
					"(numero_planta integer primary key NOT NULL, "
					+ "nombre_planta text not null, "
					+ "ingresos_planta real, "
					+ "cantidad_plazas_ordinarias integer, "
					+ "cantidad_plazas_hibridos_o_electricos integer, "
					+ "cantidad_plazas_movilidad_reducida integer, "
					+ "cantidad_plazas_libres integer, "
					+ "cantidad_plazas_ocupadas real");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			try {
				stmt.executeUpdate("CREATE TABLE TIPO_CLIENTE " +
					"(id_tipo_cliente integer PRIMARY KEY NOT NULL, "
					+ "cliente text not null)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			try {
				stmt.executeUpdate("CREATE TABLE TIPO_TARIFA " +
					"(id_tipo_tarifa integer PRIMARY KEY NOT NULL, "
					+ "tarifa text not null)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			try {
				stmt.executeUpdate("CREATE TABLE TRABAJADORES " +
					"(id_trabajador integer PRIMARY KEY AUTOINCREMENT NOT NULL, "
					+ "dni text not null, "
					+ "nombre text, "
					+ "apellido text , "
					+ "fecha_inicio integer, " 
					+ "antiguedad real , " // es un long
					+ "salario_mes real)"); // es un long
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			
			try {
				stmt.executeUpdate("CREATE TABLE TIPO_TRABAJADOR " +
					"(id_tipo_trabajador integer primary key not null, "
					+ "trabajador text not null)");
			} catch (SQLException e) {
				// Tabla ya existe. Nada que hacer
			} 
			log(Level.INFO, "Creada la base de datos", null);
			return stmt;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en creacion de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * Borra todos los datos que hubiera ya en las tablas
	 * @param conn	Conexion ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30);  // poner timeout 30 msg
			stmt.executeUpdate("DROP TABLE IF EXISTS TABLE CLIENTES_ORDINARIOS");
			stmt.executeUpdate("DROP TABLE IF EXIST TABLE CLIENTES_SUSCRITOS");
			stmt.executeUpdate("DROP TABLE IF EXIST ESTADO_PLAZAS");
			stmt.executeUpdate("DROP TABLE IF EXIST PLANTA");
			stmt.executeUpdate("DROP TABLE IF EXIST TIPO_CLIENTE");
			stmt.executeUpdate("DROP TABLE IF EXIST TIPO_TARIFA");
			stmt.executeUpdate("DROP TABLE IF EXIST TRABAJADORES");
			stmt.executeUpdate("DROP TABLE IF EXIST TIPO_TRABAJADOR");
			log(Level.INFO, "Reiniciada la base de datos", null);
			return usarCrearTablasBD(conn);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en reinicio de base de datos", e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param conn	Conexion abierta de la base de datos
	 * @param stmt	Sentencia abierta de la base de datos
	 */
	public static void disconnect(Connection conn, Statement stmt) {
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
	
	/** Informacion del ultimo error SQL ocurrido
	 * @return Devuelve la informacion de excepcion del ultimo error producido por cualquiera de los metodos de gestion de la base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	
	
			/////////////////////////////////////////////////////////////////////
			///              Operaciones de clientes ordinarios               ///
			/////////////////////////////////////////////////////////////////////
	
	/** Realiza una consulta a la tabla abierta de clientes ordinarios de la BD, usando la sentencia SELECT de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @return	lista de clientes ordinarios cargados desde la base de datos, null si hay cualquier error
	 */
	public List<OrdinaryCustomer> ordinaryCustomersSelect(Statement stmt) {
		String sentSQL = "";
		List<OrdinaryCustomer> ret = new ArrayList<>();
		try {
			sentSQL = "SELECT matricula, id_tipo_cliente, id_tipo_tarifa FROM clientes_ordinarios";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = stmt.executeQuery(sentSQL);
			while (rs.next()) {
				OrdinaryCustomer nOC = new OrdinaryCustomer();
				nOC.setLicensePlate(rs.getString("licensePlate"));
				nOC.setVehicleType(rs.getInt("vehicleType"));
				nOC.setFare(rs.getDouble("fare"));
				ret.add(nOC);
			}
			rs.close();
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Realiza una consulta a la tabla abierta de centros de la BD, usando la sentencia SELECT de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @param licensePlate	Matricula del cliente ordinario a buscar
	 * @return	Cliente ordinario con esa matricula (exacta), null si no se encuentra
	 */
	public static OrdinaryCustomer ordinaryCustomerSelect(Statement stmt, String licensePlateValor) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT matricula, id_tipo_cliente, id_tipo_tarifa FROM clientes_ordinarios WHERE matricula = '" + securizer(licensePlateValor) + "'";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = stmt.executeQuery(sentSQL);
			if (rs.next()) {
				OrdinaryCustomer ret = new OrdinaryCustomer();
				ret.setLicensePlate(rs.getString("matricula"));
				ret.setVehicleType(rs.getInt("id_tipo_cliente"));
				ret.setFare(rs.getDouble("id_tipo_tarifa"));
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

	/** Añade un cliente ordinario a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @param oC	Cliente ordinario a añadir en la base de datos
	 * @return	true si la insercion es correcta, false en caso contrario
	 */
	public static boolean ordinaryCustomerInsert(Statement stmt, OrdinaryCustomer oC) {
		String sentSQL = "";
		try {
			sentSQL = "INSERT INTO clientes_ordinarios (matricula, id_tipo_cliente, id_tipo_tarifa) VALUES ("
					+ "'" + securizer(oC.getLicensePlate()) + "', "
					+ "'" + securizer(String.valueOf(oC.getVehicleType())) + "', "
					+ "'" + securizer(String.valueOf(oC.getFare())) + "')";
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = stmt.executeUpdate(sentSQL);
			log(Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null);
			if (val != 1) {  // Se tiene que añadir 1 - error si no
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

	/** Borrar un cliente ordinario de la tabla abierta de BD, usando la sentencia DELETE de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @param oC	Cliente ordinario a borrar de la base de datos  (se toma su matricula para el borrado)
	 * @return	true si el borrado es correcto, false en caso contrario
	 */
	public boolean ordinaryCustomerDelete(Statement stmt, OrdinaryCustomer oC) {
		String sentSQL = "";
		try {
			sentSQL = "DELETE FROM clientes_ordinarios WHERE matricula = '" + securizer(oC.getLicensePlate()) + "'";
			int val = stmt.executeUpdate(sentSQL);
			log(Level.INFO, "BD tabla clientes ordinarios eliminada " + val + " fila\t" + sentSQL, null);
			if (val != 1) {  // Se tiene que eliminar 1 - error si no
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
			///              Operaciones de clientes suscritos                ///
			/////////////////////////////////////////////////////////////////////
	
	public List<SubscriberCustomer> subscriberCustomersSelect(Statement stmt) {
		String sentSQL = "";
		List<SubscriberCustomer> ret = new ArrayList<>();
		try {
			sentSQL = "SELECT matricula, id_tipo_cliente, id_tipo_tarifa FROM clientes_suscritos";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = stmt.executeQuery(sentSQL);
			while (rs.next()) {
				SubscriberCustomer nSC = new SubscriberCustomer();
				nSC.setLicensePlate(rs.getString("matricula"));
				nSC.setVehicleType(rs.getInt("id_tipo_cliente"));
				nSC.setFeeType(rs.getInt("id_tipo_tarifa"));
				ret.add(nSC);
			}
			rs.close();
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Realiza una consulta a la tabla abierta de centros de la BD, usando la sentencia SELECT de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @param licensePlate	Matricula del cliente ordinario a buscar
	 * @return	Cliente ordinario con esa matricula (exacta), null si no se encuentra
	 */
	public static SubscriberCustomer subscriberCustomerCustomerSelect(Statement stmt, String licensePlateValor) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT matricula, id_tipo_cliente, id_tipo_tarifa FROM clientes_ordinarios WHERE matricula = '" + securizer(licensePlateValor) + "'";
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = stmt.executeQuery(sentSQL);
			if (rs.next()) {
				SubscriberCustomer ret = new SubscriberCustomer();
				ret.setLicensePlate(rs.getString("matricula"));
				ret.setVehicleType(rs.getInt("id_tipo_cliente"));
				ret.setFeeType(rs.getInt("id_tipo_tarifa"));
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

	/** Añade un cliente ordinario a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @param oC	Cliente ordinario a añadir en la base de datos
	 * @return	true si la insercion es correcta, false en caso contrario
	 */
	public static boolean subscriberCustomerInsert(Statement stmt, SubscriberCustomer sC) {
		String sentSQL = "";
		try {
			sentSQL = "INSERT INTO clientes_suscritos (matricula, id_tipo_cliente, id_tipo_tarifa) VALUES ("
					+ "'" + securizer(sC.getLicensePlate()) + "', "
					+ "'" + securizer(String.valueOf(sC.getVehicleType())) + "', "
					+ "'" + securizer(String.valueOf(sC.getFeeType())) + "')";
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = stmt.executeUpdate(sentSQL);
			log(Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null);
			if (val != 1) {  // Se tiene que añadir 1 - error si no
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

	/** Borrar un cliente ordinario de la tabla abierta de BD, usando la sentencia DELETE de SQL
	 * @param stmt	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al cliente ordinario)
	 * @param oC	Cliente ordinario a borrar de la base de datos  (se toma su matricula para el borrado)
	 * @return	true si el borrado es correcto, false en caso contrario
	 */
	public static boolean subscriberCustomerDelete(Statement stmt, SubscriberCustomer sC) {
		String sentSQL = "";
		try {
			sentSQL = "DELETE FROM clientes_suscritos WHERE matricula = '" + securizer(sC.getLicensePlate()) + "'";
			int val = stmt.executeUpdate(sentSQL);
			log(Level.INFO, "BD tabla clientes ordinarios eliminada " + val + " fila\t" + sentSQL, null);
			if (val != 1) {  // Se tiene que eliminar 1 - error si no
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
			///              Operaciones con estado de plazas                 ///
			/////////////////////////////////////////////////////////////////////
	
	public static int getNumeroPlazasLibres(Statement stmt) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT (*) FROM estado_plazas WHERE estado_plaza = 0"; // 0 indica que las plazas se encuentran libres
			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null); 
			ResultSet rs = stmt.executeQuery(sentSQL);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return 0;
		}
	}
	
		
	
			/////////////////////////////////////////////////////////////////////
			///              		Operaciones con plantas      	          ///
			/////////////////////////////////////////////////////////////////////
	
	
	public static double getIngresosPlanta(Statement stmt) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT SUM(ingresos_planta) FROM planta GROUP BY nombre_planta";
			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null); 
			ResultSet rs = stmt.executeQuery(sentSQL);
			rs.next();
			return rs.getDouble(1);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return 0;
		}
	}
	

	
	
			/////////////////////////////////////////////////////////////////////
			///    		Operaciones con ingresos por plaza en plantas         ///
			/////////////////////////////////////////////////////////////////////
	
	
			/////////////////////////////////////////////////////////////////////
			///             	 Operaciones por tipo de cliente              ///
			/////////////////////////////////////////////////////////////////////
	
	public static int getClientesPorTipo(Statement stmt) {
		String sentSQL = "";
		try {
			sentSQL = "SELECT COUNT(*) FROM tipo_cliente GROUP BY id_tipo_cliente";
			log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null); 
			ResultSet rs = stmt.executeQuery(sentSQL);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			return 0;
		}
	}
	
	
			/////////////////////////////////////////////////////////////////////
			///             	 Operaciones por tipo de tarifa               ///
			/////////////////////////////////////////////////////////////////////
	
	
			/////////////////////////////////////////////////////////////////////
			///             	 Operaciones con trabajadores                 ///
			/////////////////////////////////////////////////////////////////////
	
	
			/////////////////////////////////////////////////////////////////////
			///    Operaciones de ingresos por plaza de clientes ordinarios   ///
			/////////////////////////////////////////////////////////////////////
	
	
			/////////////////////////////////////////////////////////////////////
			/// Operaciones de ingresos por plaza de clientes suscritos       ///
			/////////////////////////////////////////////////////////////////////
	
	
			/////////////////////////////////////////////////////////////////////
			///              Operaciones por tipo de trabajador               ///
			/////////////////////////////////////////////////////////////////////
	
	
			 /////////////////////////////////////////////////////////////////////
			///                      Metodos privados                          ///
			/////////////////////////////////////////////////////////////////////
	
	/** Volcado de informacion segura en SQL. Mantiene solo los caracteres seguros en español y sustituye ' por ''.
	 * @param  string Palabra que va ser introducida por el usuario
	 * @return Devuelve un String "securizado"
	 */
	private static String securizer(String string) {
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZñÑáéíóúüÁÉÍÓÚÚ.,:;-_(){}[]-+*=<>'\"¿?¡!&%$@#/\\0123456789 ".indexOf(c) >= 0) {
				ret.append(c);
			}
		}
		return ret.toString().replaceAll("'", "''");
	}
	
	
			 /////////////////////////////////////////////////////////////////////
			///                      Logging                                   ///
			/////////////////////////////////////////////////////////////////////

	/** Metodo local para poder loggear. (si no se asigna un logger externo, se asigna uno local)
	 * @param level  Indica la severidad del error
	 * @param message Mensaje que queremos registrar
	 * @param exception  Descripcion muy detallada del estado del programa en el momento que sucede el error
	 */
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
}