package backend.customer;

public class SubscriberCustomer extends User {

	private int fee;
	private int feeType;

	public SubscriberCustomer() {
		super();
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getFeeType() {
		return feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

	@Override
	public String toString() {
		return String.format("%s -> Type: %d, %d€", super.toString(), feeType, fee);
	}

}
