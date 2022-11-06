package backend.customer;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class OrdinaryCustomer extends User {

	private double fare;
	private ZonedDateTime horaDeEntrada;

	public OrdinaryCustomer() {
		super();
	}

	
	
	public OrdinaryCustomer(double fare, ZonedDateTime horaDeEntrada) {
		super();
		this.fare = fare;
		this.horaDeEntrada = horaDeEntrada;
	}



	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public ZonedDateTime getHoraDeEntrada() {
		return horaDeEntrada;
	}

	public void setHoraDeEntrada(ZonedDateTime horaDeEntrada) {
		this.horaDeEntrada = horaDeEntrada;
	}

	@Override
	public String toString() {
		return String.format("%s -> %.2f€", super.toString(), fare);
	}

}
