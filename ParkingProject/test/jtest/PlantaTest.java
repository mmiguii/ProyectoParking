package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Planta;

public class PlantaTest {

	private Planta floor;

	@Before
	public void setUp() {
		floor = new Planta(1, 70, 20, 10);
	}

	@Test
	public void testFloor() {

	}

	@Test
	public void testGetFloorNumber() {
		assertEquals(1, floor.getNumeroPlanta());
	}

	@Test
	public void testSetFloorNumber() {
		floor.setNumeroPlanta(2);
		assertEquals(2, floor.getNumeroPlanta());
	}

	@Test
	public void testGetOrdinaryParkingSpace() {
		assertEquals(70, floor.getCantidadPlazasNormales());
	}

	@Test
	public void testSetOrdinaryParkingSpace() {
		floor.setCantidadPlazasNormales(60);
		assertEquals(60, floor.getCantidadPlazasNormales());
		;
	}

	@Test
	public void testGetElectricParkingSpace() {
		assertEquals(20, floor.getCantidadPlazasElectricas());
	}

	@Test
	public void testSetElectricParkingSpace() {
		floor.setCantidadPlazasElectricas(10);
		assertEquals(10, floor.getCantidadPlazasElectricas());
	}

	@Test
	public void testGetDisabledParkingSpace() {
		assertEquals(10, floor.getCantidadPlazasDiscapacitados());
	}

	@Test
	public void testSetDisabledParkingSpace() {
		floor.setCantidadPlazasDiscapacitados(5);
		assertEquals(5, floor.getCantidadPlazasDiscapacitados());
	}

	@Test
	public void testToString() {
		Planta floor = new Planta(1, 2, 3, 4);
		assertEquals("1, 2, 3, 4", floor.toString());
	}
}
