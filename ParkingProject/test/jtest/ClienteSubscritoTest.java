package jtest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import backend.clases.clientes.ClienteSubscrito;
import backend.clases.infraestructura.Plaza;

public class ClienteSubscritoTest {

	private ClienteSubscrito clienteSubscrito;

	@Before
	public void setUp() {

		Plaza plaza = new Plaza();
		clienteSubscrito = new ClienteSubscrito("Semanal", 10.00, plaza);
		ClienteSubscrito otroCliente = new ClienteSubscrito("1111AAA", "ordinario", System.currentTimeMillis(), System.currentTimeMillis(), 2.00);
		ClienteSubscrito cs = new ClienteSubscrito();
	}

	@Test
	public void testGetTipoCuota() {
		assertEquals("Semanal", clienteSubscrito.getTipoCuota());
	}

	@Test
	public void testSetTipoCuota() {
		clienteSubscrito.setTipoCuota("Mensual");
		assertEquals("Mensual", clienteSubscrito.getTipoCuota());
	}

	@Test
	public void testGetPrecioCuota() {
		assertEquals(10.00, clienteSubscrito.getPrecioCuota(), 0.001);
	}

	@Test
	public void testSetPrecioCuota() {
		clienteSubscrito.setPrecioCuota(5.00);
		assertEquals(5.00, clienteSubscrito.getPrecioCuota(), 0.001);
	}

	@Test
	public void testGetPlazaOcupada() {
		Plaza plaza = new Plaza();
		clienteSubscrito.setPlazaOcupada(plaza);
		assertEquals(plaza, clienteSubscrito.getPlazaOcupada());
	}

	@Test
	public void testSetPlazaOcupada() {
		Plaza p = new Plaza();
		clienteSubscrito.setPlazaOcupada(p);
		assertEquals(p, clienteSubscrito.getPlazaOcupada());
	}
	
	@Test
	public void testToString() {
		clienteSubscrito.setMatricula("1234AAA");
		clienteSubscrito.setTipoVehiculo("Subnormal");
		Plaza p = new Plaza();
		assertEquals("1234AAA, Subnormal, 01/01/70 01:00:00, 01/01/70 01:00:00, 0,00, Semanal, 10,00, " + p.getNumeroPlaza()+",", clienteSubscrito.toString());
	}
	

}
