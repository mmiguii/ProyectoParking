package jtest;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import backend.clases.personas.clientes.ClienteOrdinario;

public class ClienteOrdinarioTest {

	private ClienteOrdinario ordinaryCustomer;


	@Before // ese metodo se inicializa antes de cada test creando un nuevo ordinaryCustomer
			// para ser utilizado en los distintos test
	public void setUp() {
		ordinaryCustomer = new ClienteOrdinario(2.3);
	}

	@Test
	public void testGetTarifa() {
		assertEquals(2.3, ordinaryCustomer.getTarifa(), 0.01);

	}

	@Test
	public void testSetTarifa() {
		ordinaryCustomer.setTarifa(2.3);
		assertEquals(2.3, ordinaryCustomer.getTarifa(), 0.01);

	}

	@Test
	public void testToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		ordinaryCustomer.setMatricula("1234AAA");
		ordinaryCustomer.setTipoVehiculo("Normal");
		ordinaryCustomer.setFechaEntrada(1184104800000L);
		ordinaryCustomer.setFechaSalida(1184104800000L);
		ordinaryCustomer.setImporte(2.30);
		assertEquals("1234AAA, Normal, "+sdf.format(new Date(ordinaryCustomer.getFechaEntrada()))+", "+sdf.format(new Date(ordinaryCustomer.getFechaSalida()))+", 2,30, 2,30", ordinaryCustomer.toString());
	}

}