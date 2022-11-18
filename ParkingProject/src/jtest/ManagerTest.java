package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import backend.worker.Manager;

public class ManagerTest {

	private Manager manager;

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
		manager.setId(999);
		manager.setName("Pedro");
		manager.setSalary(1500.00);
		manager.setSeniority(2);
		manager.setStartDate(t);
		manager.setSurname("Sanchez");
		assertEquals("Pedro, Sanchez, 999, " + dateFormatter.format(t) + ", 2, 1500,00", manager.toString());
	}
}
