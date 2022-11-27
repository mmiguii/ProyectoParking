package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import backend.clases.clientes.ClienteOrdinario;

public class ClienteOrdinarioTest {

	private ClienteOrdinario clienteOrdinario;

	@Before // ese metodo se inicializa antes de cada test creando un nuevo ordinaryCustomer
			// para ser utilizado en los distintos test
	public void setUp() {
		clienteOrdinario = new ClienteOrdinario(2.3);

	}

	@Test
	public void testClienteOrdinario() {

	}

	@Test
	public void testGetTarifa() {
		assertEquals(2.3, clienteOrdinario.getTarifa(), 0.01);

	}

	@Test
	public void testSetFare() {
		clienteOrdinario.setTarifa(2.3);
		assertEquals(2.3, clienteOrdinario.getTarifa(), 0.01);

	}

	@Test
	public void testToString() {
		clienteOrdinario.setMatricula("1234AAA");
		clienteOrdinario.setTipoVehiculo("Normal");
		assertEquals("1234AAA, Normal, 01/01/70 01:00:00, 01/01/70 01:00:00, 0,00, 2,30", clienteOrdinario.toString());
//		assertEquals("Normal, 1234AAA, 2,30€", clienteOrdinario.toString());
	}

}