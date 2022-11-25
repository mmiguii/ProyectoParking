package backend.clases.clientes;

public class ClienteOrdinario extends Usuario {

	private double tarifa;

	public ClienteOrdinario() {
		super();
	}

	public ClienteOrdinario(String matricula, String tipoVehiculo, long fechaEntrada, long fechaSalida,
			double importe) {
		super(matricula, tipoVehiculo, fechaEntrada, fechaSalida, importe);
	}

	public ClienteOrdinario(double tarifa) {
		super();
		this.tarifa = tarifa;
	}

	public double getTarifa() {
		return tarifa;
	}

	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}

	@Override
	public String toString() {
		return String.format("%s, %.2f", super.toString(), tarifa);
	}

}
