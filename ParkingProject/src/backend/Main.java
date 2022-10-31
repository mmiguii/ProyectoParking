package backend;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import backend.customer.OrdinaryCustomer;
import frontend.OrdinaryCustomerWindow;

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
		
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//			new OrdinaryCustomerWindow();
//			}
//		});
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrdinaryCustomerWindow frame = new OrdinaryCustomerWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
	}

}
