package backend.parking;

public class ParkingSpace {

	private int spaceNumber;
	private boolean spaceFull;
	private int vehicleType;

	public ParkingSpace() {
		super();
	}

	public ParkingSpace(int spaceNumber, boolean spaceFull, int vehicleType) {
		super();
		this.spaceNumber = spaceNumber;
		this.spaceFull = spaceFull;
		this.vehicleType = vehicleType;
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}

	public void setSpaceNumber(int spaceNumber) {
		this.spaceNumber = spaceNumber;
	}

	public boolean isSpaceFull() {
		return spaceFull;
	}

	public void setSpaceFull(boolean spaceFull) {
		this.spaceFull = spaceFull;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public String toString() {
		return String.format("%d, %b, %d", spaceNumber, spaceFull, vehicleType);
	}

}
