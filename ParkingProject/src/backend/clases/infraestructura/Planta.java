package backend.clases.infraestructura;

/**
 * La funcion de esta clase Planta es definir y crear un objeto de tipo planta.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class Planta {

	/** Atributos especificos de un objeto Planta */
	private int numeroPlanta;
	private int cantidadPlazasNormales;
	private int cantidadPlazasElectricas;
	private int cantidadPlazasDiscapacitados;

	/** Constructor vacio de la clase Planta */
	public Planta() {
		super();
	}

	/**
	 * Este es el constructor de la clase Planta. Inicializa una nueva instancia de
	 * la clase Planta con los valores de los argumentos proporcionados. Asigna los
	 * valores de los argumentos a los atributos de la instancia de Planta
	 * correspondientes.
	 */
	public Planta(int numeroPlanta, int cantidadPlazasNormales, int cantidadPlazasElectricas,
			int cantidadPlazasDiscapacitados) {
		super();
		this.numeroPlanta = numeroPlanta;
		this.cantidadPlazasNormales = cantidadPlazasNormales;
		this.cantidadPlazasElectricas = cantidadPlazasElectricas;
		this.cantidadPlazasDiscapacitados = cantidadPlazasDiscapacitados;
	}

	/**
	 * Metodo getter del atributo numeroPlanta de la planta.
	 * 
	 * @return numeroPlanta: numero de la planta a la que corresponde la planta.
	 */
	public int getNumeroPlanta() {
		return numeroPlanta;
	}

	/**
	 * Metodo setter del atributo numeroPlanta de la planta.
	 * 
	 * @param numeroPlanta: numero de la planta a la que corresponde la planta.
	 */
	public void setNumeroPlanta(int numeroPlanta) {
		this.numeroPlanta = numeroPlanta;
	}

	/**
	 * Metodo getter del atributo cantidadPlazasNormales de la planta.
	 * 
	 * @return cantidadPlazasNormales: cantidad de plazas normales en la planta.
	 */
	public int getCantidadPlazasNormales() {
		return cantidadPlazasNormales;
	}

	/**
	 * Metodo setter del atributo cantidadPlazasNormales de la planta.
	 * 
	 * @param cantidadPlazasNormales: cantidad de plazas normales en la planta.
	 * 
	 */
	public void setCantidadPlazasNormales(int cantidadPlazasNormales) {
		this.cantidadPlazasNormales = cantidadPlazasNormales;
	}

	/**
	 * Metodo getter del atributo cantidadPlazasElectricas de la planta.
	 * 
	 * @return cantidadPlazasElectricas: cantidad de plazas electricas en la planta.
	 */
	public int getCantidadPlazasElectricas() {
		return cantidadPlazasElectricas;
	}

	/**
	 * Metodo setter del atributo cantidadPlazasElectricas de la planta.
	 * 
	 * @param cantidadPlazasElectricas: cantidad de plazas electricas en la planta.
	 * 
	 */
	public void setCantidadPlazasElectricas(int cantidadPlazasElectricas) {
		this.cantidadPlazasElectricas = cantidadPlazasElectricas;
	}

	/**
	 * Metodo getter del atributo cantidadPlazasDiscapacitados de la planta.
	 * 
	 * @return cantidadPlazasDiscapacitados: cantidad de plazas para discapacitados
	 *         en la planta.
	 */
	public int getCantidadPlazasDiscapacitados() {
		return cantidadPlazasDiscapacitados;
	}

	/**
	 * Metodo setter del atributo cantidadPlazasDiscapacitados de la planta.
	 * 
	 * @param cantidadPlazasDiscapacitados: cantidad de plazas para discapacitados
	 *                                      en la planta.
	 */
	public void setCantidadPlazasDiscapacitados(int cantidadPlazasDiscapacitados) {
		this.cantidadPlazasDiscapacitados = cantidadPlazasDiscapacitados;
	}

	/**
	 * Metodo de instancia de la clase Planta que sobrescribe el metodo toString()
	 * de la clase Object. Devuelve una cadena que contiene los valores de los
	 * atributos de la instancia de Planta, formateados de acuerdo con el formato
	 * especificado.
	 */
	@Override
	public String toString() {
		return String.format("%d, %d, %d, %d", numeroPlanta, cantidadPlazasNormales, cantidadPlazasElectricas,
				cantidadPlazasDiscapacitados);
	}

}
