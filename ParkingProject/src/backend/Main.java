package backend;

import java.util.ArrayList;

import backend.customer.OrdinaryCustomer;

public class Main {

	public static void main(String[] args) {

		// Prueba
		OrdinaryCustomer o = new OrdinaryCustomer();
		o.setLicensePlate("4433ABC");
		o.setVehicleType(1);
		o.setFare(7.56);
		System.out.println(o);
		
		
		ReaderWriter rw = new ReaderWriter();
		ArrayList<OrdinaryCustomer> prueba =  rw.ordinaryCustomerReader();
		for(OrdinaryCustomer oc : prueba) {
			System.out.println(oc);
		}
		
	}

}
