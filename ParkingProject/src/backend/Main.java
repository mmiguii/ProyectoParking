package backend;

import backend.customer.OrdinaryCustomer;

public class Main {

	public static void main(String[] args) {

		// Prueba
		OrdinaryCustomer o = new OrdinaryCustomer();
		o.setLicensePlate("4433ABC");
		o.setVehicleType(1);
		o.setFare(7.56);
		System.out.println(o);
	}

}
