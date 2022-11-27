package jtest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import backend.clases.personal.Empleado;

public class EmpleadoTest {

	private Empleado employee;
	private long fechaComienzo;

	@Before
	public void setUp() {
		employee = new Empleado() {
		};
	}

	@Test
	public void Employee() {

	}

	@Test
	public void testToString() {
//		ZonedDateTime t = ZonedDateTime.now();
//		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		employee.setDni("999");
		employee.setNombre("Pedro");
		employee.setSalario(1500.00);
		employee.setAntiguedad(2);
		employee.setPuesto("empleado");
//		employee.setFechaComienzo(t);
		employee.setApellido("Sanchez");
		employee.setSalario(1500.00);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//		assertEquals("Pedro, Sanchez, 999, " + dateFormatter.format(t) + ", 2, 1500,00", employee.toString());
		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
//        dtf.format(LocalDateTime.now());
        
		assertEquals("Pedro, Sanchez, 999, empleado, " + sdf.format(new Date(fechaComienzo)) + ", 2, 1500,00", employee.toString());
		
	}
}
