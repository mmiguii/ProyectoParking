package backend.parking;

public class Floor {

	private int floorNumber;
	private int ordinaryParkingSpace;
	private int electricParkingSpace;
	private int disabledParkingSpace;

	public Floor() {
		super();
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getOrdinaryParkingSpace() {
		return ordinaryParkingSpace;
	}

	public void setOrdinaryParkingSpace(int ordinaryParkingSpace) {
		this.ordinaryParkingSpace = ordinaryParkingSpace;
	}

	public int getElectricParkingSpace() {
		return electricParkingSpace;
	}

	public void setElectricParkingSpace(int electricParkingSpace) {
		this.electricParkingSpace = electricParkingSpace;
	}

	public int getDisabledParkingSpace() {
		return disabledParkingSpace;
	}

	public void setDisabledParkingSpace(int disabledParkingSpace) {
		this.disabledParkingSpace = disabledParkingSpace;
	}

	@Override
	public String toString() {
		return String.format("%d, %d, %d, %d", floorNumber, ordinaryParkingSpace, electricParkingSpace, disabledParkingSpace);
	}

}
