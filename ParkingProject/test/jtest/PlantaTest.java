package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Planta;

public class PlantaTest {

	private Planta p;

	@Before
	public void setUp() {
		p = new Planta(1, 85, 5, 15);
	}

	@Test
	public void testGetNumeroPlanta() {
		assertEquals(1, p.getNumeroPlanta());
	}

	@Test
	public void testSetNumeroPlanta() {
		p.setNumeroPlanta(2);
		assertEquals(2, p.getNumeroPlanta());
	}

	@Test
	public void testGetCantidadPlazasNormales() {
		assertEquals(85, p.getCantidadPlazasNormales());
	}

	@Test
	public void testSetCantidadPlazasNormales() {
		p.setCantidadPlazasNormales(100);
		assertEquals(100, p.getCantidadPlazasNormales());
	}

	@Test
	public void testGetCantidadPlazasElectricas() {
		assertEquals(5, p.getCantidadPlazasElectricas());
	}

	@Test
	public void testSetCantidadPlazasElectricas() {
		p.setCantidadPlazasElectricas(10);
		assertEquals(10, p.getCantidadPlazasElectricas());
	}

	@Test
	public void testGetCantidadPlazasDiscapacitados() {
		assertEquals(15, p.getCantidadPlazasDiscapacitados());
	}

	@Test
	public void testSetCantidadPlazasDiscapacitados() {
		p.setCantidadPlazasDiscapacitados(10);
		assertEquals(10, p.getCantidadPlazasDiscapacitados());
	}

	@Test
	public void testToString() {
		assertEquals("1, 85, 5, 15", p.toString());
	}
}
