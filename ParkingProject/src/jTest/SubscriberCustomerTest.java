package jTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.customer.SubscriberCustomer;

public class SubscriberCustomerTest {

	private SubscriberCustomer sC;
	 
	@Before
	public void setUp() {
		sC = new SubscriberCustomer(2, 1);
	}
	
	@Test
	public void testGetFee() {
		assertEquals(2, sC.getFee());
	}
	
	@Test
	public void testSetFee() {
		sC.setFee(1);
		assertEquals(1, sC.getFee());
	}
	
	@Test
	public void testGetFeeType() {
		assertEquals(1, sC.getFeeType());
	}
	
	@Test
	public void testSetFeeType() {
		sC.setFeeType(2);
		assertEquals(2, sC.getFeeType());
	}
	
	@Test
	public void testToString() {
		assertEquals("1, 1234AAA -> Type: 2, 1€", sC.toString());
	}
	
	
	
}
