package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.clases.clientes.ClienteSubscrito;
import backend.clases.infraestructura.Plaza;

public class ClienteSubscritoTest {

	private ClienteSubscrito sC;

	@Before
	public void setUp() {

		sC = new ClienteSubscrito("Semanal", 10.00, new Plaza());
	}

	@Test
	public void testGetTipoCuota() {
		assertEquals("Semanal", sC.getTipoCuota());
	}

	@Test
	public void testSetFee() {
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
		assertEquals(new Plaza(), sC.getPlazaOcupada());
	}

	@Test
	public void testSetPlazaOcupada() {
		sC.setPlazaOcupada(new Plaza());
		assertEquals(5.00, sC.getPlazaOcupada());
	}

}
