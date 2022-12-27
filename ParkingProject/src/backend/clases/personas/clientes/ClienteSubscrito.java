package backend.clases.personas.clientes;

import backend.clases.infraestructura.Plaza;

/**
 * La funcion de esta clase ClienteSubscrito es definir y crear un objeto de
 * tipo subscrito que desciende de un objeto usuario.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class ClienteSubscrito extends Usuario {

	/** Atributos especificos de un objeto ClienteSubscrito */
	private String tipoCuota;
	private double precioCuota;
	private Plaza plazaOcupada;

	/** Constructor vacio de la clase ClienteSubscrito */
	public ClienteSubscrito() {
		super();
	}

	/**
	 * Constuctor super del objeto Usuario (con todos los atributos).
	 * 
	 * @param matricula:    Matricula del cliente.
	 * @param tipoVehiculo: tipo de vehiculo dl cliente.
	 * @param fechaEntrada: fecha de entrada al parking del cliente.
	 * @param fechaSalida:  fecha de salida del parking del cliente.
	 * @param importe:      importe a abonar del cliente.
	 */
	public ClienteSubscrito(String matricula, String tipoVehiculo, long fechaEntrada, long fechaSalida,
			double importe) {
		super(matricula, tipoVehiculo, fechaEntrada, fechaSalida, importe);
	}

	/**
	 * Constructor de la clase CLienteSubscrito. Inicializa una nueva instancia de
	 * la clase ClienteSubscrito con los valores de los argumentos proporcionados.
	 * Asigna los valores de los argumentos a los atributos de la instancia de
	 * ClienteSubscrito correspondientes.
	 */
	public ClienteSubscrito(String tipoCuota, double precioCuota, Plaza plazaOcupada) {
		super();
		this.tipoCuota = tipoCuota;
		this.precioCuota = precioCuota;
		this.plazaOcupada = plazaOcupada;
	}

	/**
	 * Metodo getter del atributo tipoCuota del cliente subscrito.
	 * 
	 * @return tipoCuota: tipo de cuota correspondiente al cliente subscrito.
	 */
	public String getTipoCuota() {
		return tipoCuota;
	}

	/**
	 * Metodo setter del atributo tipoCuota del cliente subscrito.
	 * 
	 * @param tipoCuota: tipo de cuota correspondiente al cliente subscrito.
	 */
	public void setTipoCuota(String tipoCuota) {
		this.tipoCuota = tipoCuota;
	}

	/**
	 * Metodo getter del atributo precioCuota del cliente subscrito.
	 * 
	 * @return precioCuota: precio de la cuota correspondiente al cliente subscrito.
	 */
	public double getPrecioCuota() {
		return precioCuota;
	}

	/**
	 * Metodo setter del atributo precioCuota del cliente subscrito.
	 * 
	 * @param precioCuota: precio de la cuota correspondiente al cliente subscrito.
	 */
	public void setPrecioCuota(double precioCuota) {
		this.precioCuota = precioCuota;
	}

	/**
	 * Metodo getter del atributo plazaOcupada del cliente subscrito.
	 * 
	 * @return plazaOcupada: objeto de tipo plaza de la plaza ocupada por el cliente
	 *         subscrito
	 */
	public Plaza getPlazaOcupada() {
		return plazaOcupada;
	}

	/**
	 * Metodo setter del atributo plazaOcupada del cliente subscrito.
	 * 
	 * @param plazaOcupada: objeto de tipo plaza de la plaza ocupada por el cliente
	 *                      subscrito
	 */
	public void setPlazaOcupada(Plaza plazaOcupada) {
		this.plazaOcupada = plazaOcupada;
	}

	/**
	 * Metodo de instancia de la clase ClienteSubscrito que sobrescribe el metodo
	 * toString() de la clase Object. Devuelve una cadena que contiene los valores
	 * de los atributos de la instancia de ClienteSubscrito, formateados de acuerdo
	 * con el formato especificado.
	 */
	@Override
	public String toString() {
		return String.format("%s, %s, %.2f, %d", super.toString(), tipoCuota, precioCuota,
				plazaOcupada.getNumeroPlaza());
	}

}
