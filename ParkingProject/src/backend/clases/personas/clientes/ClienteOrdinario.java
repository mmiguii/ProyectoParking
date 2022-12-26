package backend.clases.personas.clientes;

/**
 * La funcion de esta clase ClienteOrdinario es definir y crear un objeto de
 * tipo ordinario que desciende de un objeto usuario.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */
public class ClienteOrdinario extends Usuario {

	/** Atributos especificos de un objeto ClienteOrdinario */
	private double tarifa;

	/** Constructor vacio de la clase ClienteOrdinario */
	public ClienteOrdinario() {
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
	public ClienteOrdinario(String matricula, String tipoVehiculo, long fechaEntrada, long fechaSalida,
			double importe) {
		super(matricula, tipoVehiculo, fechaEntrada, fechaSalida, importe);
	}

	/**
	 * Constructor que se utiliza para crear una nueva instancia de ClienteOrdinario
	 * con una tarifa específica y para inicializar los campos de la instancia de
	 * ClienteOrdinario y de su superclase.
	 */
	public ClienteOrdinario(double tarifa) {
		super();
		this.tarifa = tarifa;
	}

	/**
	 * Metodo getter del atributo tarifa del cliente ordinario.
	 * 
	 * @return tarifa: tarifa correspondiente al cliente ordinario.
	 */
	public double getTarifa() {
		return tarifa;
	}

	/**
	 * Metodo setter del atributo tarifa del cliente ordinario.
	 * 
	 * @param tarifa: tarifa correspondiente al cliente ordinario.
	 */
	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Metodo de instancia de la clase ClienteOrdinario que sobrescribe el metodo
	 * toString() de la clase Object. Devuelve una cadena que contiene los valores
	 * de los atributos de la instancia de ClienteOrdinario, formateados de acuerdo
	 * con el formato especificado.
	 */
	@Override
	public String toString() {
		return String.format("%s, %.2f", super.toString(), tarifa);
	}

}
