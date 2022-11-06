package jtest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.swing.text.DateFormatter;

import org.junit.Before;
import org.junit.Test;

import backend.customer.OrdinaryCustomer;
import backend.customer.User;

public class OrdinaryCustomerTest {

	private OrdinaryCustomer ordinaryCustomer;
	
	@Before //ese metodo se inicializa antes de cada test creando un nuevo ordinaryCustomer para ser utilizado en los distintos test
	public void setUp() {
		ZonedDateTime time = ZonedDateTime.now();
		ordinaryCustomer = new OrdinaryCustomer(2.3 ,time);
		
	}
	
	@Test
	public void testOrdinaryCustomer() {
		
	}
	
	
	@Test
	public void testGetFare() {
		assertEquals(2.3, ordinaryCustomer.getFare(), 0.01);
		
	}
	
	@Test
	public void testSetFare() {
		ordinaryCustomer.setFare(2.3);
		assertEquals(2.3, ordinaryCustomer.getFare(), 0.01);
		
	}
	
	@Test
	public void testGetHoraDeEntrada() {
		ZonedDateTime time = ZonedDateTime.now();
		assertEquals(time, ordinaryCustomer.getHoraDeEntrada());
	}
	
	@Test
	public void testSetHoraDeEntrada() {
		ZonedDateTime time = ZonedDateTime.now();
		ordinaryCustomer.setHoraDeEntrada(time);
		assertEquals(time, ordinaryCustomer.getHoraDeEntrada());
	}
		
	@Test 
	public void testToString() {
		ordinaryCustomer.setLicensePlate("1234AAA");
		ordinaryCustomer.setVehicleType(1);
		assertEquals("1, 1234AAA, 2,30€", ordinaryCustomer.toString());
	}
	
}
