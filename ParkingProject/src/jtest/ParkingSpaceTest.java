package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.parking.ParkingSpace;

public class ParkingSpaceTest {

	private ParkingSpace parkingSpace;

	@Before
	public void setUp() {
		parkingSpace = new ParkingSpace(100, false, 1);
	}

	@Test
	public void testGetSpaceNumber() {
		assertEquals(100, parkingSpace.getSpaceNumber());
	}

	@Test
	public void testSetSpaceNumber() {
		parkingSpace.setSpaceNumber(101);
		assertEquals(101, parkingSpace.getSpaceNumber());
	}

	@Test
	public void testIsSpaceFull() {
		assertEquals(false, parkingSpace.isSpaceFull());
	}

	@Test
	public void testSetSpaceFull() {
		parkingSpace.setSpaceFull(true);
		assertEquals(true, parkingSpace.isSpaceFull());
	}

	@Test
	public void testGetVehicleType() {
		assertEquals(1, parkingSpace.getVehicleType());
	}

	@Test
	public void testSetVehicleType() {
		parkingSpace.setVehicleType(2);
		assertEquals(2, parkingSpace.getVehicleType());
	}

	@Test
	public void testToString() {
		assertEquals("100, false, 1", parkingSpace.toString());
	}
}
