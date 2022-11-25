package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import backend.clases.personal.Empleado;

public class EmpleadoTest {

	private Empleado employee;

	@Before
	public void setUp() {
		employee = new Empleado() {
		};
	}

	@Test
	public void Employee() {

	}

//	@Test
//	public void testToString() {
//		ZonedDateTime t = ZonedDateTime.now();
//		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
//		employee.setDni("999");
//		employee.setNombre("Pedro");
//		employee.setSalario(1500.00);
//		employee.setAntiguedad(2);
//		employee.setFechaComienzo(t);
//		employee.setApellido("Sanchez");
//		assertEquals("Pedro, Sanchez, 999, " + dateFormatter.format(t) + ", 2, 1500,00", employee.toString());
//	}
}
