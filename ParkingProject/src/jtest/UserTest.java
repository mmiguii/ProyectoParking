package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.customer.User;

public class UserTest {

	private User user;

	@Before
	public void setUp() {
		user = new User("1234AAA", 1) {
		};
	}

	@Test
	public void testGetLicensePlate() {
		assertEquals("1234AAA", user.getLicensePlate());
	}

	@Test
	public void testSetLicensePlate() {
		user.setLicensePlate("2345ABC");
		assertEquals("2345ABC", user.getLicensePlate());
	}

	@Test
	public void testGetVehicleType() {
		assertEquals(1, user.getVehicleType());
	}

	@Test
	public void testSetVehicleType() {
		user.setVehicleType(2);
		assertEquals(2, user.getVehicleType());
	}

	@Test
	public void testToString() {
		assertEquals("1, 1234AAA", user.toString());
	}
}
