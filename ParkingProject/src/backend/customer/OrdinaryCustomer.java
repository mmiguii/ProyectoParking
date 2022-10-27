package backend.customer;

public class OrdinaryCustomer extends User {

	private double fare;

	public OrdinaryCustomer() {
		super();
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		return String.format("%s -> %.2f€", super.toString(), fare);
	}

}
