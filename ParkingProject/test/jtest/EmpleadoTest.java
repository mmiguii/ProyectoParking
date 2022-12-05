package jtest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import backend.clases.personas.personal.Empleado;

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
		employee.setNombreUsuario("Pedro");
		employee.setSalario(1500.00);
		employee.setAntiguedad(2);
		employee.setEmail("pedrosanchez9@gmail.com");
		employee.setPuesto("empleado");
//		employee.setFechaComienzo(t);
		employee.setPassword("Sanchez");
		employee.setSalario(1500.00);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//		assertEquals("Pedro, Sanchez, 999, " + dateFormatter.format(t) + ", 2, 1500,00", employee.toString());

//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
//        dtf.format(LocalDateTime.now());

		assertEquals("Pedro, Sanchez, 999, pedrosanchez9@gmail.com, empleado, " + sdf.format(new Date(fechaComienzo))
				+ ", 2, 1500,00", employee.toString());

	}
}
