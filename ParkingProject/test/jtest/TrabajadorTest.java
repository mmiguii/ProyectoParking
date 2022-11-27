package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import backend.clases.personal.Trabajador;

public class TrabajadorTest {

	private Trabajador w;

	@Before
	public void setUp() {
		w = new Trabajador("Pedro", "Sanchez", "999", "empleado", System.currentTimeMillis(), 2, 1500.0) {
		};
	}

	@Test
	public void testGetName() {
		assertEquals("Pedro", w.getNombre());
	}

	@Test
	public void testSetName() {
		w.setNombre("Pablo");
		assertEquals("Pablo", w.getNombre());
	}

	@Test
	public void testGetSurname() {
		assertEquals("Sanchez", w.getApellido());
	}

	@Test
	public void testSetSurname() {
		w.setApellido("Iglesias");
		assertEquals("Iglesias", w.getApellido());
	}

	@Test
	public void testGetId() {
		assertEquals("999", w.getDni());
	}

	@Test
	public void testSetId() {
		w.setDni("1000");
		assertEquals("1000", w.getDni());
	}
	
	@Test
	public void testGetPuesto() {
		assertEquals("empleado", w.getPuesto());
	}
	
	@Test
	public void testSetPuesto() {
		w.setPuesto("Manager");
		assertEquals("Manager", w.getPuesto());
	}

	@Test
	public void testGetStartDate() {
		assertEquals(System.currentTimeMillis(), w.getFechaComienzo());
	}

	@Test
	public void testSetStartDate() {
		w.setFechaComienzo(1184104800000L);
		assertEquals(1184104800000L, w.getFechaComienzo());
	}

	@Test
	public void testGetSeniority() {
		assertEquals(2, w.getAntiguedad());
	}

	@Test
	public void testSetSeniority() {
		w.setAntiguedad(1);
		assertEquals(1, w.getAntiguedad());
	}

	@Test
	public void testGetSalary() {
		assertEquals(1500.0, w.getSalario(), 0.01);
	}

	@Test
	public void testSetSalary() {
		w.setSalario(1000.0);
		assertEquals(1000, w.getSalario(), 0.01);
	}

	@Test
	public void testToString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YY HH:mm:ss");
		ZonedDateTime t = ZonedDateTime.now();
		assertEquals("Pedro, Sanchez, 999, empleado, " + dateFormatter.format(t) + ", 2, 1500,00", w.toString());
	}

}
