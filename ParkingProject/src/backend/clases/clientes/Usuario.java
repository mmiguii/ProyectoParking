package backend.clases.clientes;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Usuario {

	private String matricula;
	private String tipoVehiculo;
	private long fechaEntrada;
	private long fechaSalida;
	private double importe;

	/**
	 * Constructor de Usuario Vacio
	 * 
	 */
	public Usuario() {
		super();
	}

	public Usuario(String matricula, String tipoVehiculo, long fechaEntrada, long fechaSalida, double importe) {
		super();
		this.matricula = matricula;
		this.tipoVehiculo = tipoVehiculo;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.importe = importe;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public long getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(long fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public long getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(long fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %.2f", matricula, tipoVehiculo, sdf.format(new Date(fechaEntrada)),
				sdf.format(new Date(fechaSalida)), importe);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			return matricula == ((Usuario) obj).matricula;
		} else {
			return false;
		}
	}

}
