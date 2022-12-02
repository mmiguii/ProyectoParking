package jtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Plaza;

public class PlazaTest {

	private Plaza p;
	
	@Before
	public void setUp() {
		p = new Plaza(200, false, "Normal");
	}
	
	@Test
	public void testGetNumeroPlaza() {
		assertEquals(200, p.getNumeroPlaza());
	}
	
	@Test 
	public void testSetNumeroPlaza() {
		p.setNumeroPlaza(201);
		assertEquals(201, p.getNumeroPlaza());
	}
	
	@Test
	public void testIsEstadoPlaza() {
		assertFalse(p.isEstadoPlaza());
		p.setEstadoPlaza(true);
		assertTrue(p.isEstadoPlaza());
	}
	
	@Test
	public void testSetEstadoPlaza() {
		p.setEstadoPlaza(true);
		assertTrue(p.isEstadoPlaza());
		p.setEstadoPlaza(false);
		assertFalse(p.isEstadoPlaza());
	}
	
	@Test
	public void testGetTipoPlaza() {
		assertEquals("Normal", p.getTipoPlaza());
	}
	
	@Test
	public void testSetTipoPlaza() {
		p.setTipoPlaza("Electrico");
		assertEquals("Electrico", p.getTipoPlaza());
	}
	
	@Test
	public void testToString() {
		assertEquals("200, false, Normal", p.toString());
	}

}
