package jtest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import backend.clases.personas.personal.Manager;

public class ManagerTest {

	private backend.clases.personas.personal.Manager manager;
	private long fechaComienzo;

	@Before
	public void setUp() {
		manager = new Manager();
	}

	@Test
	public void testManager() {

	}

	@Test
	public void testToString() {
		ZonedDateTime t = ZonedDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		manager.setNombre("Pedro");
		manager.setApellido("Sanchez");
		manager.setDni("999");
		manager.setEmail("pedrosanchez9@gmail.com");
		manager.setPuesto("manager");
		manager.setSalario(1500.00);
		manager.setAntiguedad(2);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

		assertEquals("Pedro, Sanchez, pedrosanchez9@gmail.com, 999, manager, " + sdf.format(new Date(fechaComienzo)) + ", 2, 1500,00", manager.toString());
	}
}
