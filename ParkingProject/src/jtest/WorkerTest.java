package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import backend.worker.Worker;

public class WorkerTest {

	private Worker w;

	@Before
	public void setUp() {
		ZonedDateTime time = ZonedDateTime.now();
		w = new Worker("Pedro", "Sanchez", 999, time, 2, 1500.0) {
		};
	}

	@Test
	public void testGetName() {
		assertEquals("Pedro", w.getName());
	}

	@Test
	public void testSetName() {
		w.setName("Pablo");
		assertEquals("Pablo", w.getName());
	}

	@Test
	public void testGetSurname() {
		assertEquals("Sanchez", w.getSurname());
	}

	@Test
	public void testSetSurname() {
		w.setSurname("Iglesias");
		assertEquals("Iglesias", w.getSurname());
	}

	@Test
	public void testGetId() {
		assertEquals(999, w.getId());
	}

	@Test
	public void testSetId() {
		w.setId(1000);
		assertEquals(1000, w.getId());
	}

	@Test
	public void testGetStartDate() {
		ZonedDateTime t = ZonedDateTime.now();
		assertEquals(t, w.getStartDate());
	}

	@Test
	public void testSetStartDate() {
		ZonedDateTime time = ZonedDateTime.now();
		w.setStartDate(time);
		assertEquals(time, w.getStartDate());
	}

	@Test
	public void testGetSeniority() {
		assertEquals(2, w.getSeniority());
	}

	@Test
	public void testSetSeniority() {
		w.setSeniority(1);
		assertEquals(1, w.getSeniority());
	}

	@Test
	public void testGetSalary() {
		assertEquals(1500.0, w.getSalary(), 0.01);
	}

	@Test
	public void testSetSalary() {
		w.setSalary(1000.0);
		assertEquals(1000, w.getSalary(), 0.01);
	}
	
	@Test
	public void testToString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		ZonedDateTime t = ZonedDateTime.now();
		assertEquals("Pedro, Sanchez, 999, "+ dateFormatter.format(t)+", 2, 1500,00", w.toString());
	}
	

}
