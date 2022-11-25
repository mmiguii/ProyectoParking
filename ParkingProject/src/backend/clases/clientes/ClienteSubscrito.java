package backend.clases.clientes;

import backend.clases.infraestructura.Plaza;

public class ClienteSubscrito extends Usuario {

	private String tipoCuota;
	private double precioCuota;
	private Plaza plazaOcupada;

	public ClienteSubscrito() {
		super();
	}

	public ClienteSubscrito(String matricula, String tipoVehiculo, long fechaEntrada, long fechaSalida,
			double importe) {
		super(matricula, tipoVehiculo, fechaEntrada, fechaSalida, importe);
	}

	public ClienteSubscrito(String tipoCuota, double precioCuota, Plaza plazaOcupada) {
		super();
		this.tipoCuota = tipoCuota;
		this.precioCuota = precioCuota;
		this.plazaOcupada = plazaOcupada;
	}

	public String getTipoCuota() {
		return tipoCuota;
	}

	public void setTipoCuota(String tipoCuota) {
		this.tipoCuota = tipoCuota;
	}

	public double getPrecioCuota() {
		return precioCuota;
	}

	public void setPrecioCuota(double precioCuota) {
		this.precioCuota = precioCuota;
	}

	public Plaza getPlazaOcupada() {
		return plazaOcupada;
	}

	public void setPlazaOcupada(Plaza plazaOcupada) {
		this.plazaOcupada = plazaOcupada;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %.2f, %d,", super.toString(), tipoCuota, precioCuota,
				plazaOcupada.getNumeroPlaza());
	}

}
