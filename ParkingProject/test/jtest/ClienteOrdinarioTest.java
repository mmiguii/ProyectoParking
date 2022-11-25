package jtest;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;

import backend.clases.clientes.ClienteOrdinario;

public class ClienteOrdinarioTest {

	private ClienteOrdinario ordinaryCustomer;

	@Before // ese metodo se inicializa antes de cada test creando un nuevo ordinaryCustomer
			// para ser utilizado en los distintos test
	public void setUp() {
		ordinaryCustomer = new ClienteOrdinario(2.3);

	}

	@Test
	public void testOrdinaryCustomer() {

	}

	@Test
	public void testGetFare() {
		assertEquals(2.3, ordinaryCustomer.getTarifa(), 0.01);

	}

	@Test
	public void testSetFare() {
		ordinaryCustomer.setTarifa(2.3);
		assertEquals(2.3, ordinaryCustomer.getTarifa(), 0.01);

	}

	@Test
	public void testToString() {
		ordinaryCustomer.setMatricula("1234AAA");
		ordinaryCustomer.setTipoVehiculo("Normal");
		assertEquals("Normal, 1234AAA, 2,30€", ordinaryCustomer.toString());
	}

}
