package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteSubscrito;

public class ClienteSubscritoTest {

	private ClienteSubscrito sC;
	private Plaza p = new Plaza(400, false, "Normal");

	@Before
	public void setUp() {

		sC = new ClienteSubscrito("Semanal", 10.00, p);
	}

	@Test
	public void testGetTipoCuota() {
		assertEquals("Semanal", sC.getTipoCuota());
	}

	@Test
	public void testSetTipoCuota() {
		sC.setTipoCuota("Mensual");
		assertEquals("Mensual", sC.getTipoCuota());
	}

	@Test
	public void testGetPrecioCuota() {
		assertEquals(10.00, sC.getPrecioCuota(), 0.001);
	}

	@Test
	public void testSetPrecioCuota() {
		sC.setPrecioCuota(5.00);
		assertEquals(5.00, sC.getPrecioCuota(), 0.001);
	}

	@Test
	public void testGetPlazaOcupada() {
		assertEquals(p, sC.getPlazaOcupada());
	}

	@Test
	public void testSetPlazaOcupada() {
		sC.setPlazaOcupada(p);
		assertEquals(p, sC.getPlazaOcupada());
	}

}