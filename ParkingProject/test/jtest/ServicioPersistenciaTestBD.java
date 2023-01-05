package jtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.clientes.Usuario;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;

public class ServicioPersistenciaTestBD {

	private Connection conn;

	@Before
	public void setUp() throws SQLException {
		ServicioPersistenciaBD.getInstance().connect("ParkingTest.db");
		conn = DriverManager.getConnection("jdbc:sqlite:ParkingTest.db");
	}

	@After
	public void tearDown() {
		ServicioPersistenciaBD.getInstance().disconnect();
	}

	@Test
	public void testOrdinariosSelect() {
		// Llamar al método ordinariosSelect
		Map<String, ClienteOrdinario> ordinarios = ServicioPersistenciaBD.getInstance().ordinariosSelect();
		// Verificar que el resultado es correcto
		assertNotNull(ordinarios);
		assertEquals(3, ordinarios.size()); // Suponiendo que hay 3 clientes ordinarios en la base de datos de prueba
		assertTrue(ordinarios.containsKey("1111BBB"));
		assertTrue(ordinarios.containsKey("1111CCC"));
		assertTrue(ordinarios.containsKey("1111DDD"));
		// Verificamos un cliente ordinario
		ClienteOrdinario ordinario1 = ordinarios.get("1111BBB");
		assertEquals("1111BBB", ordinario1.getMatricula());
		assertEquals("Ordinario", ordinario1.getTipoVehiculo());
		assertEquals(0.5, ordinario1.getTarifa(), 0.001);
		assertEquals(1671897760000L, ordinario1.getFechaEntrada());
	}

	@Test
	public void testOrdinarioCargar() {
		Vector<String> cabeceras = new Vector<>(
				Arrays.asList("Matricula", "Tipo Vehiculo", "Tarifa", "Fecha de Entrada"));
		DefaultTableModel model = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceras);
		ServicioPersistenciaBD.getInstance().ordinarioCargar(model);
		assertEquals(4, model.getColumnCount());
		assertEquals(1, model.getRowCount());
		assertEquals("1111BBB", model.getValueAt(0, 0));
		assertEquals("Ordinario", model.getValueAt(0, 1));
		assertEquals(0.5, model.getValueAt(0, 2));
		assertEquals(1671897760000L, model.getValueAt(0, 3));
	}

	@Test
	public void testOrdinarioSelect() {
		// Llamar al método ordinarioSelect
		ClienteOrdinario ordinario1 = ServicioPersistenciaBD.getInstance().ordinarioSelect("1111BBB");
		// Verificar que el resultado es correcto
		assertNotNull(ordinario1);
		assertEquals("1111BBB", ordinario1.getMatricula());
		assertEquals("Ordinario", ordinario1.getTipoVehiculo());
		assertEquals(0.5, ordinario1.getTarifa(), 0.001);
		assertEquals(1671897760000L, ordinario1.getFechaEntrada());
	}

	@Test
	public void testInsertOrdinario() {
		// Crear un nuevo cliente ordinario
		ClienteOrdinario ordinario = new ClienteOrdinario();
		ordinario.setMatricula("1111FFF");
		ordinario.setTipoVehiculo("Ordinario");
		ordinario.setTarifa(0.5);
		ordinario.setFechaEntrada(1671897760000L);

		// Insertar el cliente ordinario en la base de datos
		boolean result = ServicioPersistenciaBD.getInstance().ordinarioInsert(ordinario);

		// Verificar que la inserción se realizó correctamente
		assertTrue(result);

		// Llamar al método ordinariosSelect para obtener el cliente ordinario recién
		// insertado
		Map<String, ClienteOrdinario> ordinarios = ServicioPersistenciaBD.getInstance().ordinariosSelect();

		// Verificar que el resultado es correcto
		assertNotNull(ordinarios);
		assertTrue(ordinarios.containsKey("1111FFF"));
		ClienteOrdinario ordinario1 = ordinarios.get("1111FFF");
		assertEquals("1111FFF", ordinario1.getMatricula());
		assertEquals("Ordinario", ordinario1.getTipoVehiculo());
		assertEquals(0.5, ordinario1.getTarifa(), 0.001);
		assertEquals(1671897760000L, ordinario1.getFechaEntrada());

	}

	@Test
	public void testOrdinariodelete() {
		Map<String, ClienteOrdinario> ordinariosPrev = ServicioPersistenciaBD.getInstance().ordinariosSelect();
		ServicioPersistenciaBD.getInstance().ordinarioDelete("1111FFF");
		Map<String, ClienteOrdinario> ordinariosPost = ServicioPersistenciaBD.getInstance().ordinariosSelect();
		assertEquals(1, ordinariosPrev.size() - ordinariosPost.size());
	}

	@Test
	public void testSubscritosSelect() {
		// Llamar al método ordinariosSelect
		Map<String, ClienteSubscrito> subscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();
		// Verificar que el resultado es correcto
		assertNotNull(subscritos);
		assertEquals(4, subscritos.size()); // Suponiendo que hay 3 clientes subscritos en la base de datos de prueba
		assertTrue(subscritos.containsKey("1111SSS"));
		assertTrue(subscritos.containsKey("1111XXX"));
		assertTrue(subscritos.containsKey("1111ZZZ"));
		// Verificamos un cliente ordinario
		ClienteSubscrito subscrito1 = subscritos.get("1111SSS");
		assertEquals("1111SSS", subscrito1.getMatricula());
		assertEquals("Ordinario", subscrito1.getTipoVehiculo());
		assertEquals("0.5", subscrito1.getTipoCuota());
		assertEquals(0.0, subscrito1.getPrecioCuota(), 0.001);
		assertEquals(65, subscrito1.getPlazaOcupada().getNumeroPlaza() + 1);
		assertEquals(1671972962000L, subscrito1.getFechaEntrada());
		assertEquals(1672577762000L, subscrito1.getFechaSalida());
	}

	@Test
	public void testSubscritoSelect() {
		// Llamar al método subscritoSelect
		ClienteSubscrito subscrito1 = ServicioPersistenciaBD.getInstance().subscritoSelect("1111SSS");
		// Verificar que el resultado es correcto
		assertEquals("1111SSS", subscrito1.getMatricula());
		assertEquals("Ordinario", subscrito1.getTipoVehiculo());
		assertEquals("0.5", subscrito1.getTipoCuota());
		assertEquals(0.0, subscrito1.getPrecioCuota(), 0.001);
		assertEquals(65, subscrito1.getPlazaOcupada().getNumeroPlaza() + 1);
		assertEquals(1671972962000L, subscrito1.getFechaEntrada());
		assertEquals(1672577762000L, subscrito1.getFechaSalida());
	}

	@Test
	public void testSubscritoInsert() {
		// Crear un nuevo cliente ordinario
		ClienteSubscrito subscrito = new ClienteSubscrito();
		subscrito.setMatricula("1111YYY");
		subscrito.setTipoVehiculo("Ordinario");
		subscrito.setTipoCuota("0.5");
		subscrito.setPrecioCuota(0.0);
		Map<Integer, Plaza> plazas = ServicioPersistenciaBD.getInstance().plazasSelect();
		Plaza p = plazas.get(68);
		subscrito.setPlazaOcupada(p);
		subscrito.setFechaEntrada(1671972962000L);
		subscrito.setFechaSalida(1672577762000L);
		// Insertar el cliente ordinario en la base de datos
		boolean result = ServicioPersistenciaBD.getInstance().subscritoInsert(subscrito);

		// Verificar que la inserción se realizó correctamente
		assertTrue(result);

		// Llamar al método ordinariosSelect para obtener el cliente ordinario recién
		// insertado
		Map<String, ClienteSubscrito> subscritos = ServicioPersistenciaBD.getInstance().subscritosSelect();

		// Verificar que el resultado es correcto
		assertNotNull(subscritos);
		assertTrue(subscritos.containsKey("1111YYY"));
		ClienteSubscrito subscrito1 = subscritos.get("1111YYY");
		// Verificar que el resultado es correcto
		assertEquals("1111YYY", subscrito1.getMatricula());
		assertEquals("Ordinario", subscrito1.getTipoVehiculo());
		assertEquals("0.5", subscrito1.getTipoCuota());
		assertEquals(0.0, subscrito1.getPrecioCuota(), 0.001);
		assertEquals(68, subscrito1.getPlazaOcupada().getNumeroPlaza() + 1);
		assertEquals(1671972962000L, subscrito1.getFechaEntrada());
		assertEquals(1672577762000L, subscrito1.getFechaSalida());

	}

	@Test
	public void testSubscritoDelete() {
		Map<String, ClienteSubscrito> subscritosPrev = ServicioPersistenciaBD.getInstance().subscritosSelect();
		ServicioPersistenciaBD.getInstance().subscritoDelete("1111YYY");
		Map<String, ClienteSubscrito> subscritosPost = ServicioPersistenciaBD.getInstance().subscritosSelect();
		assertEquals(1, subscritosPrev.size() - subscritosPost.size());

	}

	@Test
	public void testSubscritoDeleteDes() {
		// Cerrar la conexión a la base de datos
		ServicioPersistenciaBD.getInstance().disconnect();
		try {
			// Invocar al método subscritoDelete con una conexión cerrada
			ServicioPersistenciaBD.getInstance().subscritoDelete("123456");
		} catch (Exception e) {
			// Verificar que se haya lanzado una excepción de tipo SQLException
			assertEquals(SQLException.class, e.getClass());
		}
	}

	@Test
	public void testGetAllUsuarios() {
		Map<String, Usuario> usuarios = ServicioPersistenciaBD.getInstance().getAllUsuarios();
		assertEquals(7, usuarios.size());
	}

	@Test
	public void testGetUsuario() {
		// Arrange
		Usuario usuario1 = new ClienteOrdinario();
		usuario1.setMatricula("123456");
		usuario1.setTipoVehiculo("Ordinario");
		usuario1.setFechaEntrada(1671897760000L);

		Usuario usuario2 = new ClienteSubscrito();
		usuario2.setMatricula("123457");
		usuario2.setTipoVehiculo("Ordinario");
		Map<String, Usuario> usuarios = new HashMap<>();
		usuarios.put(usuario1.getMatricula(), usuario1);
		usuarios.put(usuario2.getMatricula(), usuario2);

		// Act
		Usuario result1 = usuarios.get("123456");
		Usuario result2 = usuarios.get("123457");
		Usuario result3 = usuarios.get("111111");

		// Assert
		assertEquals(usuario1, result1);
		assertEquals(usuario2, result2);
		assertNull(result3);
	}

	@Test
	public void testTrabajadoresSelect() {
		Map<String, Trabajador> trabajadores = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
		List<Trabajador> trabajadoresList = new ArrayList<>(trabajadores.values());
		assertEquals(3, trabajadoresList.size());
		assertEquals("unaguil", trabajadoresList.get(0).getNombreUsuario());
		assertEquals("aroztegiMiguel", trabajadoresList.get(1).getNombreUsuario());
		assertEquals("sanjurjoEduardo", trabajadoresList.get(2).getNombreUsuario());
	}

	@Test
	public void testManagersSelect() {
		Map<String, Trabajador> trabajadores = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
		List<Trabajador> trabajadoresList = new ArrayList<>(trabajadores.values());
		int managers = 0;
		for (Trabajador t : trabajadoresList) {
			if (t.getDni().equals("12345678A")) {
				managers++;
			}
		}
		assertEquals(1, managers);

	}

	@Test
	public void testManagersSelectDes() {
		// Cerrar la conexión a la base de datos
		ServicioPersistenciaBD.getInstance().disconnect();

		try {
			// Invocar al método subscritoDelete con una conexión cerrada
			@SuppressWarnings("unused")
			Map<String, Trabajador> trabajadores = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
		} catch (Exception e) {
			// Verificar que se haya lanzado una excepción de tipo SQLException
			assertEquals(NullPointerException.class, e.getClass());
		}
	}

	@Test
	public void testEmpleadosSelect() {
		Map<String, Trabajador> trabajadores = ServicioPersistenciaBD.getInstance().trabajadoresSelect();
		List<Trabajador> trabajadoresList = new ArrayList<>(trabajadores.values());
		int empleados = 0;
		for (Trabajador t : trabajadoresList) {
			if (!t.getDni().equals("12345678A")) {
				empleados++;
			}
		}
		assertEquals(2, empleados);
	}

	@Test
	public void testUpdatePlaza() {
		// Crear instancia de Plaza con número de plaza 1
		Plaza plaza = new Plaza();

		// Establecer estado y matrícula para actualizar la plaza
		String estado = "Ocupado";
		String matricula = "ABC123";

		plaza.setEstadoPlaza(false);
		plaza.setMatricula(matricula);

		// Llamar al método updatePlaza
		ServicioPersistenciaBD.getInstance().updatePlaza(plaza, estado, matricula);

		// Verificar que la plaza se haya actualizado correctamente en la base de datos
		String sentSQL = "SELECT estado_plaza, matricula FROM plazas WHERE numero_plaza = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setInt(1, plaza.getNumeroPlaza());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String estadoObtenido = rs.getString("estado_plaza");
				String matriculaObtenida = rs.getString("matricula");
				assertEquals(estado, estadoObtenido);
				assertEquals(matricula, matriculaObtenida);
			} else {
				fail("No se ha encontrado la plaza en la base de datos");
			}
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	@Test
	public void testGetPlazasDisponibles() {
		// Llamar al método getPlazasDisponibles
		int plazasDisponibles = ServicioPersistenciaBD.getInstance().getPlazasDisponibles();

		// Verificar que el método haya devuelto el número correcto de plazas
		// disponibles
		String sentSQL = "SELECT COUNT(*) FROM plazas WHERE estado_plaza = 'DISPONIBLE'";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sentSQL)) {
			rs.next();
			int plazasDisponiblesEsperadas = rs.getInt(1);
			assertEquals(plazasDisponiblesEsperadas, plazasDisponibles);
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	@Test
	public void testPlazasSelect() {
		int numeroPlanta = 1;
		String tipoPlaza = "Electrico";
		List<Plaza> plazas = ServicioPersistenciaBD.getInstance().plazasSelect(numeroPlanta, tipoPlaza);
		assertTrue(plazas.size() > 0);
		for (Plaza p : plazas) {
			assertEquals(numeroPlanta, p.getNumeroPlanta());
			assertEquals(tipoPlaza, p.getTipoPlaza());
		}
	}

	@Test
	public void testIngresosPlanta() {
		// Escogemos de la base de datos una matricula existente con un importe
		// aleatorio con el fin de hacer la prueba
		String matricula = "ABC123";
		double importe = 130;
		double ingresosPasados = 0;

		// Para obtener la asercion de ingresos correcta, se debe obtener el importe de
		// ingreso anterior
		// y posterior al Update. Por ello se realiza el proceso dos veces, una antes y
		// otra tras el Update
		// 1- en la primera hayamos el numero de planta por matricula en la tabla de
		// plazas
		// 2- en la segunda comprobamos los ingresos con el numero de planta anterior
		String sentSQL = "SELECT ingresos_planta FROM plantas WHERE numero_planta = "
				+ "(SELECT numero_planta FROM plazas WHERE matricula = ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			stmt.setString(1, matricula);
			try (ResultSet rs = stmt.executeQuery()) {
				ingresosPasados = rs.getDouble("ingresos_planta");
			}
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}

		ServicioPersistenciaBD.getInstance().ingresosPlanta(matricula, importe);

		// Comprobamos que se ha actualizado la tabla "plantas" correctamente con una
		// doble consulta,
		// 1- en la primera hayamos el numero de planta por matricula en la tabla de
		// plazas
		// 2- en la segunda comprobamos los ingresos con el numero de planta anterior
		String sentSQL2 = "SELECT ingresos_planta FROM plantas WHERE numero_planta = "
				+ "(SELECT numero_planta FROM plazas WHERE matricula = ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL2)) {
			stmt.setString(1, matricula);
			try (ResultSet rs = stmt.executeQuery()) {
				double ingresosPlanta = rs.getDouble("ingresos_planta");
				assertEquals(ingresosPasados + importe, ingresosPlanta, 0.0);
			}
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	@Test
	public void testIngresosTotales() {
		// Usando el metodo anterior comprobaremos su utilidad
		String matricula = "ABC123";
		double importe = 130;
		double importeAnterior = 0;
		// A continuacion se comprueban los ingresos. Recordemos que el aserto refleja
		// el ingreso final
		// Es decir, el importe mas el valor anterior. Para ello, realizamos una
		// consulta sabiendo
		// el numero de planta hayando el valor anterior, y sumando el siguiente.
		String sentSQL = "SELECT ingresos_planta FROM plantas WHERE numero_planta = 1";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			importeAnterior = rs.getDouble("ingresos_planta");
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}
		ServicioPersistenciaBD.getInstance().ingresosPlanta(matricula, importe);

		List<Double> ingresos = ServicioPersistenciaBD.getInstance().ingresosTotales();
		// Al haber 3 plantas, el rango es 3
		assertEquals(3, ingresos.size());
		assertEquals(importeAnterior + importe, ingresos.get(0), 0.0);
	}

	@Test
	public void testOcupacionPlazas() {
		List<Integer> estadoPlazas = ServicioPersistenciaBD.getInstance().ocupacionPlazas();
		assertEquals(2, estadoPlazas.size());
		// Plazas disponibles
		assertEquals(90, estadoPlazas.get(0), 0.0);
		// Plazas ocupadas
		assertEquals(1, estadoPlazas.get(1), 0.0);
	}

	@Test
	public void testNumeroUsuarios() {
		List<Integer> usuarios = ServicioPersistenciaBD.getInstance().numeroUsuarios();
		assertEquals(3, usuarios.size());
		// Al solo haber una plaza ocupada en tabla plazas de la BD de Test, el test
		// sale asi
		assertEquals(1, usuarios.get(0), 0);
		assertEquals(1, usuarios.get(1), 0);
		assertEquals(0, usuarios.get(2), 0);
	}

	@Test
	public void testNumeroUsuariosPorTipoYVehiculo() {
		Map<String, List<Integer>> mapaUsuariosTipoVehiculo = ServicioPersistenciaBD.getInstance()
				.numeroUsuariosPorTipoYVehiculo();
		// Al solo haber una plaza ocupada en tabla plazas de la BD de Test, el test
		// sale asi
		assertEquals(0, mapaUsuariosTipoVehiculo.get("Ordinarios").get(0), 0);
		assertEquals(1, mapaUsuariosTipoVehiculo.get("Ordinarios").get(1), 0);
		assertEquals(0, mapaUsuariosTipoVehiculo.get("Ordinarios").get(2), 0);
		assertEquals(0, mapaUsuariosTipoVehiculo.get("Subscritos").get(0), 0);
		assertEquals(0, mapaUsuariosTipoVehiculo.get("Subscritos").get(1), 0);
		assertEquals(0, mapaUsuariosTipoVehiculo.get("Subscritos").get(2), 0);
	}

	@Test
	public void testNumeroUsuariosPorVehiculo() {
		List<Integer> usuariosVehiculo = ServicioPersistenciaBD.getInstance().numeroUsuariosPorVehiculo();
		assertEquals(3, usuariosVehiculo.size());
		// Al solo haber una plaza ocupada en tabla plazas de la BD de Test, el test
		// sale asi
		assertEquals(0, usuariosVehiculo.get(0), 0);
		assertEquals(1, usuariosVehiculo.get(1), 0);
		assertEquals(0, usuariosVehiculo.get(2), 0);
	}

	@Test
	public void testGetUsuarioPorMatricula() {
		// Obtenemos matricula por una matricula dada en la tabla clientes ordinarios
		ClienteOrdinario u = (ClienteOrdinario) ServicioPersistenciaBD.getInstance().getUsuario("1111BBB");
		String sentSQL = "SELECT matricula, tarifa FROM clientes_ordinarios WHERE matricula = '1111BBB'";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			assertEquals(rs.getString("matricula"), u.getMatricula());
			assertEquals(rs.getDouble("tarifa"), u.getTarifa(), 0.0);
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}

		// Obtenemos matricula por una matricula dada en la tabla clientes subscritos
		ClienteSubscrito u2 = (ClienteSubscrito) ServicioPersistenciaBD.getInstance().getUsuario("1111XXX");
		String sentSQL2 = "SELECT matricula, tipo_vehiculo FROM clientes_subscritos WHERE matricula = '1111XXX'";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL2)) {
			ResultSet rs = stmt.executeQuery();
			assertEquals(rs.getString("matricula"), u2.getMatricula());
			assertEquals(rs.getString("tipo_vehiculo"), u2.getTipoVehiculo());
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	@Test
	public void testSalarioSelect() {
		double d = ServicioPersistenciaBD.getInstance().salarioSelect();
		// Se realiza la suma de los salarios de trabajadores manualmente para comprobar
		// si es correcto
		assertEquals(5000, d, 0);
	}

	@Test
	public void testTrabajadoresUpdate() {
		// Introducimos el DNI de un trabajador ya existente y cambiamos la clave.
		String nuevoPassword = ServicioPersistenciaBD.getInstance().trabajadoresUpdate("12345678C");
		assertEquals(10, nuevoPassword.length());
		// Hallaremos la nueva clave con una consulta SQL
		String sentSQL = "SELECT password FROM trabajadores WHERE dni = '12345678C'";
		try (PreparedStatement stmt = conn.prepareStatement(sentSQL)) {
			ResultSet rs = stmt.executeQuery();
			assertEquals(rs.getString("password"), nuevoPassword);
		} catch (SQLException e) {
			fail("Error al acceder a la base de datos: " + e.getMessage());
		}
	}
}
