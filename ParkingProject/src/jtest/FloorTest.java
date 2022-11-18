package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.parking.Floor;

public class FloorTest {

	private Floor floor;

	@Before
	public void setUp() {
		floor = new Floor(1, 70, 20, 10);
	}

	@Test
	public void testFloor() {

	}

	@Test
	public void testGetFloorNumber() {
		assertEquals(1, floor.getFloorNumber());
	}

	@Test
	public void testSetFloorNumber() {
		floor.setFloorNumber(2);
		assertEquals(2, floor.getFloorNumber());
	}

	@Test
	public void testGetOrdinaryParkingSpace() {
		assertEquals(70, floor.getOrdinaryParkingSpace());
	}

	@Test
	public void testSetOrdinaryParkingSpace() {
		floor.setOrdinaryParkingSpace(60);
		assertEquals(60, floor.getOrdinaryParkingSpace());
		;
	}

	@Test
	public void testGetElectricParkingSpace() {
		assertEquals(20, floor.getElectricParkingSpace());
	}

	@Test
	public void testSetElectricParkingSpace() {
		floor.setElectricParkingSpace(10);
		assertEquals(10, floor.getElectricParkingSpace());
	}

	@Test
	public void testGetDisabledParkingSpace() {
		assertEquals(10, floor.getDisabledParkingSpace());
	}

	@Test
	public void testSetDisabledParkingSpace() {
		floor.setDisabledParkingSpace(5);
		assertEquals(5, floor.getDisabledParkingSpace());
	}

	@Test
	public void testToString() {
		Floor floor = new Floor(1, 2, 3, 4);
		assertEquals("1, 2, 3, 4", floor.toString());
	}
}
