package jtest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import backend.clases.clientes.Usuario;

public class UsuarioTest {

	private Usuario user;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	@Before
	public void setUp() {


		user = new Usuario("1234AAA", "Normal", System.currentTimeMillis(), System.currentTimeMillis(), 2.00) {

		};
	}

	@Test
	public void testGetMatricula() {
		assertEquals("1234AAA", user.getMatricula());
	}

	@Test
	public void testSetMatricula() {
		user.setMatricula("2345ABC");
		assertEquals("2345ABC", user.getMatricula());
	}

	@Test
	public void testGetTipeVehiculo() {
		assertEquals("Normal", user.getTipoVehiculo());
	}

	@Test
	public void testSetTipoVehiculo() {
		user.setTipoVehiculo("Electrico");
		assertEquals("Electrico", user.getTipoVehiculo());
	}

	@Test
	public void testGetFechaEntrada() {
		assertEquals(System.currentTimeMillis(), user.getFechaEntrada());
	}

	@Test
	public void testSetFechaEntrada() {
		user.setFechaEntrada(1234567);
		assertEquals(1234567, user.getFechaEntrada());
	}

	@Test
	public void testGetFechaSalida() {
		assertEquals(System.currentTimeMillis(), user.getFechaSalida());
	}

	@Test
	public void testSetFechaSalida() {
		user.setFechaSalida(1234567);
		assertEquals(1234567, user.getFechaSalida());
	}

	@Test
	public void testGetImporte() {
		assertEquals(2.00, user.getImporte(), 0.001);
	}

	@Test
	public void testSetImporte() {
		user.setImporte(3.00);
		assertEquals(3.00, user.getImporte(), 0.001);
	}

	@Test
	public void testToString() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        dtf.format(LocalDateTime.now());

		assertEquals("1234AAA, Normal, "+ dtf.format(LocalDateTime.now())+ ", " +dtf.format(LocalDateTime.now())+ ", 2,00", user.toString());
		
//		assertEquals("1234AAA, Normal, 26/11/22 15:24:46, 25/11/22 15:24:46, 2,00", user.toString());
	}

}
