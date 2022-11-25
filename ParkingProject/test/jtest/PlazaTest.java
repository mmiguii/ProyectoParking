package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Plaza;

public class PlazaTest {

	private Plaza parkingSpace;

	@Before
	public void setUp() {
		parkingSpace = new Plaza(100, false, "Normal");
	}

	@Test
	public void testGetSpaceNumber() {
		assertEquals(100, parkingSpace.getNumeroPlaza());
	}

	@Test
	public void testSetSpaceNumber() {
		parkingSpace.setNumeroPlaza(101);
		assertEquals(101, parkingSpace.getNumeroPlaza());
	}

	@Test
	public void testIsSpaceFull() {
		assertEquals(false, parkingSpace.isEstadoPlaza());
	}

	@Test
	public void testSetSpaceFull() {
		parkingSpace.setEstadoPlaza(true);
		assertEquals(true, parkingSpace.isEstadoPlaza());
	}

	@Test
	public void testGetVehicleType() {
		assertEquals("Normal", parkingSpace.getTipoPlaza());
	}

	@Test
	public void testSetVehicleType() {
		parkingSpace.setTipoPlaza("Minusvalidos");
		assertEquals("Minusvalidos", parkingSpace.getTipoPlaza());
	}

	@Test
	public void testToString() {
		assertEquals("100, false, Normal", parkingSpace.toString());
	}
}
