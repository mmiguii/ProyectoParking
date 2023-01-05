package backend.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;

import backend.clases.email.PasswordGenerator;
import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.clientes.Usuario;
import backend.clases.personas.personal.Empleado;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;

/**
 * La función de esta clase ServicioPersistenciaBD es la de declarar metodos
 * relacionados a la DB para su futuro uso.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */
public class ServicioPersistenciaBD {

	private static Logger logger = null;
	private static Exception lastError = null;
	private static ServicioPersistenciaBD instance;
	private Connection conn;

	/**
	 * Este código forma parte de un bloque estático, que se ejecuta una única vez
	 * cuando se carga la clase. En este caso, lo que hace es cargar la clase
	 * org.sqlite.JDBC, que es necesaria para establecer la conexión con una base de
	 * datos SQLite.
	 */
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			log(Level.SEVERE, "Error en conexion de base de datos Parking.db", e);
		}
	}

	/**
	 * Comprueba si la variable instance, que es un atributo de la clase y es
	 * utilizada para almacenar la instancia del objeto, es null. Si es así,
	 * significa que aún no se ha creado ninguna instancia de la clase, por lo que
	 * se crea una nueva instancia y se asigna a la variable instance. En cualquier
	 * caso, el método devuelve el valor de la variable instance.
	 * 
	 * @return Devuelve la unica instancia de la clase ServicioPersistenciaBD que
	 *         existe.
	 */
	public static ServicioPersistenciaBD getInstance() {
		if (instance == null) {
			instance = new ServicioPersistenciaBD();
		}
		return instance;
	}

	/**
	 * Metodo que permite obtener una conexión a una base de datos desde otras
	 * partes de la aplicación.
	 * 
	 * @return Devuelve una conexión a una base de datos.
	 */
	protected Connection getConnection() {
		return conn;
	}

	/**
	 * Establece una conexión a una base de datos y almacena la conexión en una
	 * variable de instancia de la clase para su posterior uso. También proporciona
	 * una forma de manejar errores que puedan ocurrir al intentar establecer la
	 * conexión.
	 */
	public void connect(String database) {
		try {
			log(Level.INFO, "Lanzada consulta a la base de datos: " + database, null);
			conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", database));
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en conexion de base de datos Parking.db", e);
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la base de datos abierta
	 */
	public void disconnect() {
		try {
			log(Level.INFO, "Cerrando conexion con la base de datos: ", null);
			conn.close();
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en cierre de base de datos", e);
			e.printStackTrace();
		}
	}

	/**
	 * Este método elimina las tablas "clientes_ordinarios", "clientes_subscritos",
	 * "trabajadores", "planta" y "plazas" de la base de datos, si existen, y luego
	 * crea estas tablas nuevamente con los campos y tipos de datos especificados
	 */
	public void createTables() {
		try (Statement stmt = conn.createStatement()) {
			stmt.setQueryTimeout(30); // poner timeout 30 msg

			stmt.executeUpdate("DROP TABLE IF EXISTS clientes_ordinarios");
			stmt.executeUpdate("DROP TABLE IF EXISTS clientes_subscritos");
			stmt.executeUpdate("DROP TABLE IF EXISTS trabajadores");
			stmt.executeUpdate("DROP TABLE IF EXISTS planta");
			stmt.executeUpdate("DROP TABLE IF EXISTS plazas");

			stmt.executeUpdate(
					"CREATE TABLE clientes_ordinarios (matricula TEXT, tipo_vehiculo TEXT, fecha_entrada LONG, fecha_salida LONG, tarifa DOUBLE, importe DOUBLE)");
			stmt.executeUpdate(
					"CREATE TABLE clientes_subscritos (matricula TEXT, tipo_vehiculo TEXT, tipo_cuota TEXT, precio_cuota DOUBLE, plaza_ocupada INTEGER,  fecha_comienzo LONG, fecha_final LONG)");
			stmt.executeUpdate(
					"CREATE TABLE trabajadores (id_trabajador INTEGER, dni TEXT, nombre TEXT, apellido TEXT, email TEXT, puesto TEXT, fecha_comienzo LONG, antiguedad INTEGER, salario_mensual DOUBLE)");
			stmt.executeUpdate(
					"CREATE TABLE plantas (numero_planta INTEGER, cantidad_plazas_normales INTEGER, cantidad_plazas_electricos INTEGER, cantidad_plazas_minusvalidos INTEGER, cantidad_plazas_ocupadas INTEGER)");
			stmt.executeUpdate(
					"CREATE TABLE plazas (numero_plaza INTEGER, numero_planta INTEGER, estado_plaza TEXT, tipo_plaza TEXT)");

			log(Level.INFO, "Reiniciada la base de datos", null);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en creacion de base de datos", e);
			e.printStackTrace();
		}

	}

	/**
	 * Se trata de un método de clase (static) que permite acceder a la variable
	 * lastError sin necesidad de tener una instancia de la clase. La variable
	 * lastError es un atributo de la clase y se utiliza para almacenar el último
	 * error ocurrido en la base de datos, en caso de que haya uno.
	 * 
	 * @return Devuelve el último error ocurrido en la base de datos, si es que hay
	 *         alguno.
	 */
	public static Exception getLastError() {
		return lastError;
	}

	/**
	 * Realiza una consulta a una base de datos y obtiene un conjunto de filas de la
	 * tabla "clientes_ordinarios". Luego, para cada fila obtenida, crea una
	 * instancia de la clase ClienteOrdinario y establece sus atributos con los
	 * valores obtenidos de la fila. Finalmente, agrega cada instancia de
	 * ClienteOrdinario a un mapa utilizando la matrícula del cliente como clave.
	 * 
	 * @return Devuelve el mapa ordinariosSelect() con los valores del tipo
	 *         ClienteOrdinario asociados a las claves String (matriculas)
	 *         correspondientes
	 */
	public Map<String, ClienteOrdinario> ordinariosSelect() {
		Map<String, ClienteOrdinario> ordinarios = new HashMap<>();
		String sentSQL = "SELECT matricula, tipo_vehiculo, tarifa, fecha_entrada FROM clientes_ordinarios";
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sentSQL)) {
				log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
				while (rs.next()) {
					ClienteOrdinario ordinario = new ClienteOrdinario();
					String matricula = rs.getString("matricula");
					ordinario.setMatricula(matricula);
					ordinario.setTipoVehiculo(rs.getString("tipo_vehiculo"));
					ordinario.setTarifa(rs.getDouble("tarifa"));
					ordinario.setFechaEntrada(rs.getLong("fecha_entrada"));

					ordinarios.put(matricula, ordinario);
				}
			}
			return ordinarios;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Este metodo ejecuta una consulta SQL en la base de datos para obtener datos
	 * de la tabla "clientes_ordinarios", y luego añade esos datos a un modelo de
	 * tabla en la aplicación Java.
	 */
	public void ordinarioCargar(DefaultTableModel modelo) {
		String sentSQL = "SELECT matricula, tipo_vehiculo, tarifa, fecha_entrada FROM clientes_ordinarios ";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					@SuppressWarnings("unused")
					ClienteOrdinario ordinario = new ClienteOrdinario();
					modelo.addRow(new Object[] { rs.getString("matricula"), rs.getString("tipo_vehiculo"),
							rs.getDouble("tarifa"), rs.getLong("fecha_entrada") });
				}
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
	}

	/**
	 * Realiza una consulta a una base de datos para obtener una fila de la tabla
	 * "clientes_ordinarios" cuyo valor de la columna "matricula" sea igual al
	 * argumento proporcionado. Luego, crea una instancia de la clase
	 * ClienteOrdinario y establece sus atributos con los valores obtenidos de la
	 * fila.
	 * 
	 * @param matricula Atributo de la clase ClienteOrdinario
	 * @return Devuelve la instancia de ClienteOrdinario. Si no se encuentra ninguna
	 *         fila que cumpla con la condición de la consulta, devuelve null.
	 */
	public ClienteOrdinario ordinarioSelect(String matricula) {
		String sentSQL = "SELECT matricula, tipo_vehiculo, tarifa, fecha_entrada FROM clientes_ordinarios WHERE matricula = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, matricula);
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ClienteOrdinario ordinario = new ClienteOrdinario();
					ordinario.setMatricula(rs.getString("matricula"));
					ordinario.setTipoVehiculo(rs.getString("tipo_vehiculo"));
					ordinario.setTarifa(rs.getDouble("tarifa"));
					ordinario.setFechaEntrada(rs.getLong("fecha_entrada"));

					return ordinario;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Inserta un nuevo registro en una tabla de la base de datos llamada
	 * "clientes_ordinarios". El método toma como parámetro
	 * 
	 * @param ordinario Objeto "ClienteOrdinario" que contiene los datos que se van
	 *                  a insertar en la tabla.
	 * @return Devuelve un valor booleano que indica si la operación tuvo éxito.
	 *         También registra cualquier error que pueda ocurrir durante el
	 *         proceso.
	 */
	public boolean ordinarioInsert(ClienteOrdinario ordinario) {
		String sentSQL = "INSERT INTO clientes_ordinarios (matricula, tipo_vehiculo, tarifa, fecha_entrada) VALUES ( ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, ordinario.getMatricula());
			stmt.setString(2, ordinario.getTipoVehiculo());
			stmt.setDouble(3, ordinario.getTarifa());
			stmt.setLong(4, ordinario.getFechaEntrada());

			if (ordinario.getTarifa() < 0) {
				log(Level.WARNING, "Error en insert de base de datos por tarifa negativa\t" + sentSQL, null);
				return false;
			}
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = stmt.executeUpdate();
			if (val != 1) {
				log(Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null);
				return false;
			} else {
				log(Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null);
				return true;
			}
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina una fila de la tabla "clientes_ordinarios" en una base de datos,
	 * utilizando el "matricula" proporcionado como condición.
	 * 
	 * @param matricula Atributo de la clase ClienteOrdinario
	 * @return Devuelve un valor booleano que indica si la operación tuvo éxito.
	 *         También registra cualquier error que pueda ocurrir durante el
	 *         proceso.
	 */
	public boolean ordinarioDelete(String matricula) {
		String sentSQL = "DELETE FROM clientes_ordinarios WHERE matricula = ? ";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, matricula);
			int val = stmt.executeUpdate();
			if (val != 1) { // Se tiene que eliminar 1 - error si no
				log(Level.SEVERE, "Error en delete de BD\t" + sentSQL, null);
				return false;
			}
			log(Level.INFO, "BD tabla clientes ordinarios eliminada " + val + " fila\t" + sentSQL, null);
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Selecciona todas las filas de la tabla "clientes_subscritos" de la base de
	 * datos y las almacena en un mapa que asigna a cada "matricula" un objeto
	 * "ClienteSubscrito" con los demás valores obtenidos de la fila.
	 * 
	 * @return Devuelve el mapa resultante o null en caso de error. También registra
	 *         cualquier error que pueda ocurrir durante el proceso.
	 */
	public Map<String, ClienteSubscrito> subscritosSelect() {
		Map<String, ClienteSubscrito> subscritos = new HashMap<>();
		Map<Integer, Plaza> plazas = plazasSelect();
		String sentSQL = "SELECT matricula, tipo_vehiculo, tipo_cuota, precio_cuota, numero_plaza_ocupada, fecha_comienzo, fecha_final FROM clientes_subscritos";
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sentSQL)) {
				log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
				while (rs.next()) {
					ClienteSubscrito subscrito = new ClienteSubscrito();
					String matricula = rs.getString("matricula");
					subscrito.setMatricula(matricula);
					subscrito.setTipoVehiculo(rs.getString("tipo_vehiculo"));
					subscrito.setTipoCuota(rs.getString("tipo_cuota"));
					subscrito.setPrecioCuota(rs.getDouble("precio_cuota"));
					int numPlaza = rs.getInt("numero_plaza_ocupada");
					Plaza p = plazas.get(numPlaza - 1);
					subscrito.setPlazaOcupada(p);
					subscrito.setFechaEntrada(rs.getLong("fecha_comienzo"));
					subscrito.setFechaSalida(rs.getLong("fecha_final"));

					subscritos.put(matricula, subscrito);
				}
			}
			return subscritos;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Realiza una consulta a la base de datos para obtener información sobre un
	 * cliente suscrito con una matrícula específica
	 * 
	 * @param matricula Atributo de la clase ClienteSubscrito
	 * @return Crea un objeto ClienteSubscrito con la información obtenida y la
	 *         devuelve. Si no se encuentra ningún cliente suscrito con esa
	 *         matrícula, se devuelve null.
	 */
	public ClienteSubscrito subscritoSelect(String matricula) {
		String sentSQL = "SELECT matricula, tipo_vehiculo, tipo_cuota, precio_cuota, numero_plaza_ocupada, fecha_comienzo, fecha_final FROM clientes_subscritos WHERE matricula = ?";
		// Crear un Map para almacenar las plazas
		Map<Integer, Plaza> plazas = plazasSelect();
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, matricula);
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ClienteSubscrito subscrito = new ClienteSubscrito();
					subscrito.setMatricula(rs.getString("matricula"));
					subscrito.setTipoVehiculo(rs.getString("tipo_vehiculo"));
					subscrito.setTipoCuota(rs.getString("tipo_cuota"));
					subscrito.setPrecioCuota(rs.getDouble("precio_cuota"));
					// Almacenar el resultado de rs.getInt("numero_plaza") en una variable local
					int numPlaza = rs.getInt("numero_plaza_ocupada");
					// Obtener la plaza del Map utilizando la variable local como índice
					Plaza p = plazas.get(numPlaza - 1);
					subscrito.setPlazaOcupada(p);
					subscrito.setFechaEntrada(rs.getLong("fecha_comienzo"));
					subscrito.setFechaSalida(rs.getLong("fecha_final"));
					return subscrito;
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Inserta un nuevo cliente suscrito en la base de datos con la información
	 * especificada en el objeto ClienteSubscrito proporcionado.
	 * 
	 * @param subscrito Objeto "CLienteSubscrito" que contiene los datos que se van
	 *                  a insertar en la tabla.
	 * @return Devuelve true si se ha podido realizar la inserción correctamente, y
	 *         false en caso contrario.
	 */
	public boolean subscritoInsert(ClienteSubscrito subscrito) {
		String sentSQL = "INSERT INTO clientes_subscritos (matricula, tipo_vehiculo, tipo_cuota, precio_cuota, numero_plaza_ocupada, fecha_comienzo, fecha_final) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, subscrito.getMatricula());
			stmt.setString(2, subscrito.getTipoVehiculo());
			stmt.setString(3, subscrito.getTipoCuota());
			stmt.setDouble(4, subscrito.getPrecioCuota());
			stmt.setInt(5, subscrito.getPlazaOcupada().getNumeroPlaza());
			stmt.setLong(6, subscrito.getFechaEntrada());
			stmt.setLong(7, subscrito.getFechaSalida());
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = stmt.executeUpdate();
			if (val != 1) {
				log(Level.WARNING, "Error en insert de base de datos\t" + sentSQL, null);
				return false;
			}
			log(Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL, null);
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en inserción de base de datos\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Elimina de la base de datos la fila que contenga la información del cliente
	 * suscrito con la matrícula especificada.
	 * 
	 * @param matricula Atributo de la clase Usuario
	 * @return Devuelve true si se ha podido realizar la eliminación correctamente,
	 *         y false en caso contrario.
	 */
	public boolean subscritoDelete(String matricula) {
		String sentSQL = "DELETE FROM clientes_subscritos WHERE matricula = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, matricula);
			log(Level.INFO, "Lanzada actualización a base de datos: " + sentSQL, null);
			int val = stmt.executeUpdate();
			if (val != 1) {
				log(Level.WARNING, "Error en delete de base de datos\t" + sentSQL, null);
				return false;
			} else {
				log(Level.INFO, "Eliminada " + val + " fila a base de datos\t" + sentSQL, null);
				return true;
			}
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Realiza una consulta a la base de datos para obtener información sobre todas
	 * las plazas disponibles y crea un objeto Plaza para cada una de ellas. Luego,
	 * almacena cada objeto Plaza en un Map con su número de plaza como clave.
	 * 
	 * @return Devuelve el Map completo. Si hay algún error al realizar la consulta
	 *         a la base de datos, devuelve null.
	 */
	public Map<Integer, Plaza> plazasSelect() {
		Map<Integer, Plaza> plazas = new HashMap<>();
		String sentSQL = "SELECT * FROM plazas";
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sentSQL)) {
				log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
				while (rs.next()) {
					Plaza plaza = new Plaza();
					plaza.setNumeroPlanta(rs.getInt("numero_planta"));
					plaza.setNumeroPlaza(rs.getInt("numero_plaza"));
					boolean disp = rs.getString("estado_plaza").equals("DISPONIBLE") ? true : false;
					plaza.setEstadoPlaza(disp);
					plaza.setTipoPlaza(rs.getString("tipo_plaza"));
					plaza.setMatricula(rs.getString("matricula"));

					plazas.put(plaza.getNumeroPlaza(), plaza);
				}
			}
			return plazas;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Este método realiza una consulta a la base de datos para obtener información
	 * sobre las plazas que se encuentran en una planta específica y son del tipo
	 * especificado. Crea un objeto Plaza para cada una de ellas y los almacena en
	 * una lista, que luego devuelve. Si hay algún error al realizar la consulta a
	 * la base de datos, devuelve null.
	 */

	public List<Plaza> plazasSelect(int numeroPlanta, String tipoPlaza) {
		String sentSQL = "SELECT numero_planta, numero_plaza, tipo_plaza, estado_plaza FROM plazas WHERE numero_planta = ?  AND tipo_plaza = ? ";
		List<Plaza> ret = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setInt(1, numeroPlanta);
			stmt.setString(2, tipoPlaza);
			log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Plaza plaza = new Plaza();
				plaza.setNumeroPlanta(rs.getInt("numero_planta"));
				plaza.setNumeroPlaza(rs.getInt("numero_plaza"));
				plaza.setTipoPlaza(rs.getString("tipo_plaza"));
				boolean estado = rs.getString("estado_plaza").equals("OCUPADO") ? false : true;

				plaza.setEstadoPlaza(estado);
				ret.add(plaza);
			}
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Este método actualiza la información de una plaza específica en la base de
	 * datos. Recibe como parámetros un objeto Plaza que representa la plaza que se
	 * quiere actualizar, el estado actual de la plaza y la matrícula del vehículo
	 * que ocupa la plaza (si es que hay alguno). Actualiza el estado y la matrícula
	 * de la plaza en la base de datos y no devuelve ningún valor. Si hay algún
	 * error al realizar la actualización en la base de datos, se registra un error.
	 */
	public void updatePlaza(Plaza plaza, String estado, String matricula) {
		String sentSQL = "UPDATE plazas SET estado_plaza = ?, matricula = ? WHERE numero_plaza = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
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

	/**
	 * Este metodo obtiene el numero de plazas disponibles en una tabla de la base
	 * de datos mediante la ejecución de una consulta SQL que cuenta el numero de
	 * filas que tienen el estado "DISPONIBLE". Devuelve el resultado como un
	 * entero.
	 */
	public int getPlazasDisponibles() {
		String sentSQL = "SELECT COUNT(*) FROM plazas WHERE estado_plaza = ? ";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, "DISPONIBLE");
			try (ResultSet rs = stmt.executeQuery()) {
				log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Este metodo añade en la tabla "plantas" los ingresos del parking. Estos
	 * ocurren cuando un cliente ordinario paga en la caja del parking o un cliente
	 * subscrito compra su abono. Para el calculo de este metodo se realizan dos
	 * consulta, y se han necesitado la matricula del coche del cliente y el valor
	 * de su tarifa para ello: 1- La obtencion del numero de planta para añadir el
	 * ingreso de dicha planta. 2- La actualizacion de los ingresos del parking.
	 */

	public void ingresosPlanta(String matricula, double importe) {
		int numeroPlanta = 0;
		String sentSQL = "SELECT numero_planta FROM plazas WHERE matricula = ? ";
		String sentSQL2 = "UPDATE plantas SET ingresos_planta = COALESCE(ingresos_planta, 0) + ? WHERE numero_planta = ?";
		log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, matricula);
			try (ResultSet rs = stmt.executeQuery()) {
				rs.next();
				numeroPlanta = rs.getInt("numero_planta");
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
		log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL2, null);
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL2)) {
			stmt.setDouble(1, importe);
			stmt.setInt(2, numeroPlanta);
			stmt.executeUpdate();
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL2, e);
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo obtiene los ingresos por cada planta en una lista. Primero, se
	 * realiza una consulta para ello, y posteriormente se almacenan en una lista.
	 * Estos ingresos de acuerdo a la contabilidad son los correspondientes a los
	 * del parking.
	 */
	public List<Double> ingresosTotales() {
		List<Double> ingresosPlanta = new ArrayList<>();
		String sentSQL = "SELECT ingresos_planta FROM plantas";
		log(Level.INFO, "Lanzada consulta a base de datos: " + sentSQL, null);
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ingresosPlanta.add(rs.getDouble("ingresos_planta"));
			}
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
		return ingresosPlanta;
	}

	/**
	 * Este metodo calculara la ocupación total del parking. El resultado se
	 * reflejara en una lista de enteros que, segun la ocupacion de las plazas,
	 * determinaremos la cantidad de espacios ocupados y libres. Para ello, se hara
	 * una doble consulta a la tabla plazas para determinar esa disponibilidad.
	 */

	public List<Integer> ocupacionPlazas() {
		List<Integer> plazas = new ArrayList<>();
		String sentSQL = "SELECT COUNT(*) AS disponibles, "
				+ "(SELECT COUNT(*) FROM plazas WHERE estado_plaza != 'DISPONIBLE') AS ocupadas " + "FROM plazas"
				+ " WHERE estado_plaza = 'DISPONIBLE'";
		log(Level.INFO, "Lanzada la consulta a base de datos: " + sentSQL, null);
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			plazas.add(0, rs.getInt("disponibles"));
			plazas.add(1, rs.getInt("ocupadas"));
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
		return plazas;
	}

	/**
	 * Este metodo obtiene una lista en la que se recoge la cantidad de clientes
	 * total que hay en el parking, la cantidad de clientes ordinarios y la cantidad
	 * de clientes subscritos. Para ello, se recurre a esta consulta triple que por
	 * la tabla de plazas se relacionan todos los clientes, y mediante las distintas
	 * condiciones y denominaciones, se pueden hallar los calculos. Sabiendo que los
	 * clientes ocupan plazas, y que los subscritos estan todos en la tercera
	 * planta, ya se pueden realizar los calculos.
	 */

	public List<Integer> numeroUsuarios() {
		List<Integer> tiposUsuarios = new ArrayList<>();
		String sentSQL = "SELECT COUNT(*) AS usuarios, "
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta != 3 AND estado_plaza != 'DISPONIBLE') AS ordinarios, "
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta = 3 AND estado_plaza != 'DISPONIBLE') AS subscritos "
				+ "FROM plazas " + "WHERE estado_plaza != 'DISPONIBLE'";
		log(Level.INFO, "Lanzada la consulta a base de datos: " + sentSQL, null);
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			tiposUsuarios.add(0, rs.getInt("usuarios"));
			tiposUsuarios.add(1, rs.getInt("ordinarios"));
			tiposUsuarios.add(2, rs.getInt("subscritos"));
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
		return tiposUsuarios;
	}

	/**
	 * Este metodo obtiene mediante un mapa, la cantidad de usuarios del parking
	 * segun el tipo de usuario que es, y el tipo de vehiculo del usuario. Para
	 * ello, se utiliza los datos proporcionados de la tabla plaza, tanto como
	 * numero_planta, estado_planta y tipo_plaza que nos permiten hacer la
	 * clasificacion. Sabiendo que los clientes ocupan plazas, que los subscritos
	 * estan todos en la tercera planta y que ademas se recogen los tipos de
	 * vehiculo, ya se pueden realizar los calculos. Se recogen los datos en un mapa
	 * cuyas claves son el tipo de usuario, y valor una lista que contiene la
	 * cantidad de usuarios por vehiculo.
	 */
	public Map<String, List<Integer>> numeroUsuariosPorTipoYVehiculo() {
		Map<String, List<Integer>> mapaUsuariosTipoVehiculo = new HashMap<>();
		String sentSQL = "SELECT COUNT(*) AS ordinariosOrdinarios, "
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta != 3 AND estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Electrico') AS ordinariosElectricos, "
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta != 3 AND estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Minusvalido') AS ordinariosMinusvalidos,"
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta = 3 AND estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Ordinario') AS subscritosOrdinarios,"
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta = 3 AND estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Electrico') AS subscritosElectricos,"
				+ "(SELECT COUNT(*) FROM plazas WHERE numero_planta = 3 AND estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Minusvalido') AS subscritosMinusvalidos "
				+ "FROM plazas "
				+ "WHERE numero_planta != 3 AND estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Ordinario'";
		log(Level.INFO, "Lanzada la consulta a base de datos: " + sentSQL, null);
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			mapaUsuariosTipoVehiculo.put("Ordinarios", Arrays.asList(rs.getInt("ordinariosOrdinarios"),
					rs.getInt("ordinariosElectricos"), rs.getInt("ordinariosMinusvalidos")));
			mapaUsuariosTipoVehiculo.put("Subscritos", Arrays.asList(rs.getInt("subscritosOrdinarios"),
					rs.getInt("subscritosElectricos"), rs.getInt("subscritosMinusvalidos")));
		} catch (SQLException e) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");

			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
		return mapaUsuariosTipoVehiculo;
	}

	/**
	 * Este metodo obtenemos los usuarios por tipo de vehiculo en su conjunto.
	 * Hayamos los datos segun tipo_plaza y estado_plaza dentro de la tabla de
	 * plazas. Sabiendo que los clientes ocupan plazas, y que se recogen los tipos
	 * de vehiculo, ya se pueden realizar los calculos. Estas cantidades se añaden a
	 * una lista de enteros
	 */

	public List<Integer> numeroUsuariosPorVehiculo() {
		List<Integer> usuariosVehiculo = new ArrayList<>();
		String sentSQL = "SELECT COUNT(*) AS ordinarios, "
				+ "(SELECT COUNT(*) FROM plazas WHERE estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Electrico') AS electricos, "
				+ "(SELECT COUNT(*) FROM plazas WHERE estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Minusvalido') AS minusvalidos "
				+ "FROM plazas " + "WHERE estado_plaza != 'DISPONIBLE' AND tipo_plaza = 'Ordinario'";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			usuariosVehiculo.add(0, rs.getInt("ordinarios"));
			usuariosVehiculo.add(1, rs.getInt("electricos"));
			usuariosVehiculo.add(2, rs.getInt("minusvalidos"));
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
		}
		return usuariosVehiculo;
	}

	/**
	 * Este metodo obtiene y devuelve un mapa de usuarios de la base de datos.
	 * Primero, se obtienen los usuarios ordinarios y los usuarios suscritos de la
	 * base de datos y se añaden a un mapa de usuarios. Luego, se ordena el mapa por
	 * fecha de entrada de los usuarios y se devuelve el mapa ordenado.
	 */
	public Map<String, Usuario> getAllUsuarios() {
		Map<String, Usuario> usuarios = new HashMap<>();
		usuarios.putAll(ordinariosSelect());
		usuarios.putAll(subscritosSelect());
		log(Level.INFO, "Carga de todos los usuarios de la base de datos realizada", null);
		Map<String, Usuario> sortedMap = usuarios.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.comparingLong(Usuario::getFechaEntrada)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortedMap;
	}

	/**
	 * Este método obtiene y devuelve un usuario de la base de datos con la
	 * matrícula especificada. Primero, obtiene todos los usuarios de la base de
	 * datos y los almacena en un mapa. Luego, itera a través de los valores del
	 * mapa y, si encuentra un usuario con la matrícula especificada, devuelve ese
	 * usuario. Si no se encuentra ningún usuario con la matrícula especificada,
	 * devuelve null.
	 */
	public Usuario getUsuario(String matricula) {
		log(Level.INFO, "Carga de todos los usuarios de la base de datos realizada", null);
		Map<String, Usuario> usuarios = getAllUsuarios();
		for (Usuario usuario : usuarios.values()) {
			if (usuario.getMatricula().equals(matricula)) {
				if (usuario instanceof ClienteOrdinario) {
					Usuario ordinario = (ClienteOrdinario) usuario;
					return ordinario;
				} else {
					Usuario subscrito = (ClienteSubscrito) usuario;
					return subscrito;
				}
			}
		}
		return null;
	}

	/**
	 * Este metodo obtiene y devuelve un mapa de empleados de la base de datos
	 * mediante la ejecucion de una consulta SQL. Crea un mapa de empleados y luego
	 * itera a traves de los resultados de la consulta, creando una nueva instancia
	 * de Empleado para cada fila y agregandola al mapa con su DNI como clave.
	 * Devuelve el mapa de empleados.
	 */
	public Map<String, Empleado> empleadosSelect() {
		Map<String, Empleado> empleados = new HashMap<>();
		String sentSQL = "SELECT dni, nombre_usuario, password, email, puesto, antiguedad, salario_mes FROM trabajadores";
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sentSQL)) {
				log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
				while (rs.next()) {
					Empleado empleado = new Empleado();
					String dni = rs.getString("dni");
					empleado.setDni(dni);
					empleado.setNombreUsuario(rs.getString("nombre_usuario"));
					empleado.setPassword(rs.getString("password"));
					empleado.setEmail(rs.getString("email"));
					empleado.setPuesto(rs.getString("puesto"));
					empleado.setAntiguedad(rs.getInt("antiguedad"));
					empleado.setSalario(rs.getDouble("salario_mes"));
					empleados.put(dni, empleado);
				}
			}
			return empleados;
		} catch (NullPointerException | SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Este método obtiene y devuelve un mapa de managers de la base de datos
	 * mediante la ejecución de una consulta SQL. Crea un mapa de managers y luego
	 * itera a través de los resultados de la consulta, creando una nueva instancia
	 * de Manager para cada fila y agregándola al mapa con su DNI como clave.
	 * Devuelve el mapa de managers.
	 */
	public Map<String, Manager> managersSelect() {
		Map<String, Manager> managers = new HashMap<>();
		String sentSQL = "SELECT dni, nombre_usuario, password, email, puesto, antiguedad, salario_mes FROM trabajadores";
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sentSQL)) {
				log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
				while (rs.next()) {
					Manager manager = new Manager();
					String dni = rs.getString("dni");
					manager.setDni(dni);
					manager.setNombreUsuario(rs.getString("nombre_usuario"));
					manager.setPassword(rs.getString("password"));
					manager.setEmail(rs.getString("email"));
					manager.setPuesto(rs.getString("puesto"));
					manager.setSalario(rs.getDouble("salario_mes"));
					managers.put(dni, manager);
				}
			}
			return managers;
		} catch (NullPointerException | SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Este metodo ejecuta una consulta SQL en la base de datos para obtener los
	 * salarios de los trabajadores de la tabla "trabajadores", luego suma todos los
	 * salarios y devuelve el total como un valor double. Para la contabilidad del
	 * parking, estos suponen los gastos principales.
	 */
	public Double salarioSelect() {
		double d = 0;
		String sentSQL = "SELECT sum(salario_mes) AS salarios FROM trabajadores";
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sentSQL)) {
				log(Level.INFO, "Lanzada consulta a la base de datos: " + sentSQL, null);
				d = rs.getDouble("salarios");
			}
			return d;
		} catch (NullPointerException | SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en la busqueda de base de datos: " + sentSQL, e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Este método obtiene y devuelve un mapa de trabajadores de la base de datos
	 * que incluye tanto a los managers como a los empleados. Primero, obtiene los
	 * managers y los empleados de la base de datos y los agrega a un mapa de
	 * trabajadores. Luego, ordena el mapa por puesto de los trabajadores de manera
	 * descendente y devuelve el mapa ordenado.
	 */
	public Map<String, Trabajador> trabajadoresSelect() {
		Map<String, Trabajador> trabajadores = new TreeMap<>();
		trabajadores.putAll(managersSelect());
		trabajadores.putAll(empleadosSelect());
		log(Level.INFO, "Carga de todos los trabajadores de la base de datos realizada", null);
		Map<String, Trabajador> sortedMap = trabajadores.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.comparing(Trabajador::getPuesto).reversed()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sortedMap;
	}

	/**
	 * Este metodo se conecta a una base de datos y tiene como objetivo cambiar la
	 * contraseña de un trabajador en la tabla "trabajadores" de la base de datos.
	 */
	public String trabajadoresUpdate(String dni) {
		String sentSQL = "UPDATE trabajadores SET password = ? WHERE dni = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			PasswordGenerator pg = new PasswordGenerator();
			String nuevoPass = pg.generate(10, 3, 3);
			stmt.setString(1, nuevoPass);
			stmt.setString(2, dni);
			stmt.executeUpdate();
			return nuevoPass;

		} catch (SQLException e) {
			log(Level.SEVERE, "Error en la busqueda de base de datos: ", e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

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
				e.printStackTrace();
			}
		}
		if (exception == null) {
			logger.log(level, message);
		} else {
			logger.log(level, message, exception);
		}
	}

}
