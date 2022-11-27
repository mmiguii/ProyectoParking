package backend.clases.personas.clientes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La función de esta clase Usuario es definir un objeto de tipo usuario. Este
 * objeto totalmente abstracto extenderán los dos tipos posibles de usuarios:los
 * clientes ordinarios y los clientes subscritos.
 * 
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public abstract class Usuario {

	/** Atributos genericos de un objeto Usuario */
	private String matricula;
	private String tipoVehiculo;
	private long fechaEntrada;
	private long fechaSalida;
	private double importe;

	/** Constructor vacio de la clase Usuario */
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

	/**
	 * Metodo getter del atributo matricula del usuario.
	 * 
	 * @return matricula: matricula del usuario.
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * Metodo setter del atributo matricula del usuario.
	 * 
	 * @param matricula: matricula del usuario.
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * Metodo getter del atributo tipoVehiculo del usuario.
	 * 
	 * @return tipoVehiculo: tipo de vehiculo del usuario.
	 */
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	/**
	 * Metodo setter del atributo tipoVehiculo del usuario.
	 * 
	 * @param tipoVehiculo: tipo de vehiculo del usuario.
	 */
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	/**
	 * Metodo getter del atributo fechaEntrada del usuario.
	 * 
	 * @return fechaEntrada: fecha de entrada al parking del usuario.
	 */
	public long getFechaEntrada() {
		return fechaEntrada;
	}

	/**
	 * Metodo setter del atributo fechaEntrada del usuario.
	 * 
	 * @param fechaEntrada: fecha de entrada al parking del usuario.
	 */
	public void setFechaEntrada(long fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	/**
	 * Metodo getter del atributo fechaSalida del usuario.
	 * 
	 * @return fechaSalida: fecha de salida del parking del usuario.
	 */
	public long getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * Metodo setter del atributo fechaSalida del usuario.
	 * 
	 * @param fechaSalida: fecha de salida del parking del usuario.
	 */
	public void setFechaSalida(long fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	/**
	 * Metodo getter del atributo importe del usuario.
	 * 
	 * @return importe: importe que debe abonar el usuario.
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * Metodo setter del atributo importe del usuario
	 * 
	 * @param importe: importe que debe abonar el usuario.
	 */
	public void setImporte(double importe) {
		this.importe = importe;
	}

	/** Variable que permite darle formato a las fechas */
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
