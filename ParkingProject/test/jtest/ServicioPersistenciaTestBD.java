package jtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;
import backend.clases.personas.clientes.Usuario;
import backend.clases.personas.personal.Empleado;
import backend.clases.personas.personal.Manager;
import backend.clases.personas.personal.Trabajador;
import backend.servicios.ServicioPersistenciaBD;

public class ServicioPersistenciaTestBD {

	@Before
	public void setUp() {
		ServicioPersistenciaBD.connect();
	}

	@After
	public void tearDown() {
		ServicioPersistenciaBD.disconnect();
	}

	@Test
	public void testOrdinariosSelect() {
		ArrayList<ClienteOrdinario> p = ServicioPersistenciaBD.ordinariosSelect();
		assertEquals(p, ServicioPersistenciaBD.ordinariosSelect());
	}

	@Test
	public void testOrdinarioSelect() {
		ClienteOrdinario p = ServicioPersistenciaBD.ordinarioSelect("1111AAAA");
		assertEquals("1111AAAA", p.getMatricula());
	}

	@Test
	public void testOrdinarioInsertFalse() {
		ClienteOrdinario c = new ClienteOrdinario(-1.0);
		assertFalse(ServicioPersistenciaBD.ordinarioInsert(c));
	}

	@Test
	public void testOrdinarioInsertTrue() {
		ClienteOrdinario c = new ClienteOrdinario(1.0);
		assertTrue(ServicioPersistenciaBD.ordinarioInsert(c));
	}

	@Test
	public void testOrdinarioDeleteTrue() {
		ClienteOrdinario c = new ClienteOrdinario("1234ABC", "Normal", System.currentTimeMillis(),
				System.currentTimeMillis(), 1.0);
		ServicioPersistenciaBD.ordinarioInsert(c);
		assertTrue(ServicioPersistenciaBD.ordinarioDelete("1234ABC"));
	}

	@Test
	public void testOrdinarioDeleteFalse() {
		ClienteOrdinario c = new ClienteOrdinario("1234ABC", "Normal", System.currentTimeMillis(),
				System.currentTimeMillis(), 1.0);
		ServicioPersistenciaBD.ordinarioInsert(c);
		assertFalse(ServicioPersistenciaBD.ordinarioDelete("4321ABC"));
	}

	@Test
	public void testSubscritosSelect() {
		ArrayList<ClienteSubscrito> p = ServicioPersistenciaBD.subscritosSelect();
		assertEquals(p, ServicioPersistenciaBD.subscritosSelect());
	}

	@Test
	public void testSubscritoSelect() {
		ClienteSubscrito p = ServicioPersistenciaBD.subscritoSelect("1111AAAA");
		assertEquals("1111AAAA", p.getMatricula());
	}

	@Test
	public void testSubscritoInsertFalse() {
		ClienteSubscrito c = new ClienteSubscrito("Mes", -100, new Plaza(1,400, true, "Normal"));
		assertFalse(ServicioPersistenciaBD.subscritoInsert(c));
	}

	@Test
	public void testSubscritoInsertTrue() {
		ClienteSubscrito c = new ClienteSubscrito("Mes", 100, new Plaza(1,400, true, "Normal"));
		assertFalse(ServicioPersistenciaBD.subscritoInsert(c));
	}

	@Test
	public void testSuscritoDeleteTrue() {
		ClienteSubscrito c = new ClienteSubscrito("1234AAA", "Normal", System.currentTimeMillis(),
				System.currentTimeMillis(), 100);
		ServicioPersistenciaBD.subscritoInsert(c);
		assertTrue(ServicioPersistenciaBD.subscritoDelete("1234AAA"));
	}

	@Test
	public void testSuscritoDeleteFalse() {
		ClienteSubscrito c = new ClienteSubscrito("1234AAA", "Normal", System.currentTimeMillis(),
				System.currentTimeMillis(), 100);
		ServicioPersistenciaBD.subscritoInsert(c);
		assertFalse(ServicioPersistenciaBD.subscritoDelete("4321AAA"));
	}

	@Test
	public void testPlazasSelect() {
		ArrayList<Plaza> p = ServicioPersistenciaBD.plazasSelect();
		assertEquals(p, ServicioPersistenciaBD.plazasSelect());
	}

	@Test
	public void testGetPlazasDisponibles() {
		assertTrue(ServicioPersistenciaBD.getPlazasDisponibles() <= 300
				&& ServicioPersistenciaBD.getPlazasDisponibles() >= 0);
	}

	@Test
	public void testGetIngresosPlanta() {
		assertTrue(ServicioPersistenciaBD.getIngresosPlanta() >= 0);
	}

	@Test
	public void testGetClientesPorTipo() {
		assertTrue(ServicioPersistenciaBD.getClientesPorTipo() >= 0);
	}

	@Test
	public void testUsuariosOrds() {
		ArrayList<ClienteOrdinario> c = ServicioPersistenciaBD.usuariosOrds();
		assertEquals(c, ServicioPersistenciaBD.usuariosOrds());
	}

	@Test
	public void testUsuariosSusb() {
		ArrayList<ClienteSubscrito> c = ServicioPersistenciaBD.usuariosSubs();
		assertEquals(c, ServicioPersistenciaBD.usuariosSubs());
	}

	@Test
	public void testUsuarios() {
		ArrayList<Usuario> u = ServicioPersistenciaBD.usuarios();
		assertEquals(u, ServicioPersistenciaBD.usuarios());
	}

	@Test
	public void testUsuario() {
		ServicioPersistenciaBD.ordinarioInsert(
				new ClienteOrdinario("1234AAA", "Normal", System.currentTimeMillis(), System.currentTimeMillis(), 2.0));
		Usuario u = ServicioPersistenciaBD.usuario("1234AAA");
		assertEquals(u, ServicioPersistenciaBD.usuario("1234AAA"));
	}

	@Test
	public void testManagerSelect() {
		Manager m = ServicioPersistenciaBD.managerSelect("9876");
		assertEquals(m, ServicioPersistenciaBD.managerSelect("9876"));
	}

	@Test
	public void testEmpleadoSelect() {
		Empleado e = ServicioPersistenciaBD.empleadoSelect("5432");
		assertEquals(e, ServicioPersistenciaBD.empleadoSelect("5432"));
	}

	@Test
	public void testEmpleadosSelect() {
		ArrayList<Empleado> e = ServicioPersistenciaBD.empleadosSelect();
		assertEquals(e, ServicioPersistenciaBD.empleadosSelect());
	}

	@Test
	public void testManagersSelect() {
		ArrayList<Manager> m = ServicioPersistenciaBD.managersSelect();
		assertEquals(m, ServicioPersistenciaBD.managersSelect());
	}

	@Test
	public void testTrabajadoresSelect() {
		ArrayList<Trabajador> t = ServicioPersistenciaBD.trabajadoresSelect();
		assertEquals(t, ServicioPersistenciaBD.trabajadoresSelect());
	}

}
