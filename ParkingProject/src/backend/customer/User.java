package backend.customer;

public abstract class User {

	private String licensePlate;
	private int vehicleType;

	public User() {
		super();
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public String toString() {
		return String.format("%d, %s", vehicleType, licensePlate);
	}

}
