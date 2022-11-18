package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import backend.worker.Employee;

public class EmployeeTest {

	private Employee employee;

	@Before
	public void setUp() {
		employee = new Employee() {
		};
	}

	@Test
	public void Employee() {

	}

	@Test
	public void testToString() {
		ZonedDateTime t = ZonedDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		employee.setId(999);
		employee.setName("Pedro");
		employee.setSalary(1500.00);
		employee.setSeniority(2);
		employee.setStartDate(t);
		employee.setSurname("Sanchez");
		assertEquals("Pedro, Sanchez, 999, " + dateFormatter.format(t) + ", 2, 1500,00", employee.toString());
	}
}
