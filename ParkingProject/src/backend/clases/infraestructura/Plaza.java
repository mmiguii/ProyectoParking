package backend.clases.infraestructura;

/**
 * La funcion de esta clase Plaza es definir y crear un objeto de tipo plaza.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class Plaza {

	/** Atributos especificos de un objeto Plaza */
	private int numeroPlanta;
	private int numeroPlaza;
	private boolean estadoPlaza;
	private String tipoPlaza;
	private String matricula;

	/** Constructor vacio de la clase Plaza */
	public Plaza() {
		super();
	}

	/**
	 * Constructor de la clase Plaza. Inicializa una nueva instancia de la clase
	 * Plaza con los valores de los argumentos proporcionados. Asigna los valores de
	 * los argumentos a los atributos de la instancia de Plaza correspondientes.
	 */
	public Plaza(int numeroPlanta, int numeroPlaza, boolean estadoPlaza, String tipoPlaza, String matricula) {
		super();
		this.numeroPlanta = numeroPlanta;
		this.numeroPlaza = numeroPlaza;
		this.estadoPlaza = estadoPlaza;
		this.tipoPlaza = tipoPlaza;
		this.matricula = matricula;
	}

	/**
	 * Metodo getter del atributo numeroPlanta de la plaza.
	 * 
	 * @return numeroPlanta: numero de la planta correspondiente.
	 */
	public int getNumeroPlanta() {
		return numeroPlanta;
	}

	/**
	 * Metodo setter del atributo numeroPlanta de la plaza.
	 * 
	 * @param numeroPlanta: numero de la la planta correspondiente.
	 */
	public void setNumeroPlanta(int numeroPlanta) {
		this.numeroPlanta = numeroPlanta;
	}

	/**
	 * Metodo getter del atributo numeroPlaza de la plaza.
	 * 
	 * @return numeroPlaza: numero de la plaza correspondiente.
	 */

	public int getNumeroPlaza() {
		return numeroPlaza;
	}

	/**
	 * Metodo setter del atributo numeroPlaza de la plaza.
	 * 
	 * @param numeroPlaza: numero de la plaza correspondiente.
	 */
	public void setNumeroPlaza(int numeroPlaza) {
		this.numeroPlaza = numeroPlaza;
	}

	/**
	 * Metodo getter del atributo estadoPlaza de la plaza.
	 * 
	 * @return estadoPlaza: true si la plaza se encuentra disponible, false en caso
	 *         constrario.
	 */
	public boolean isEstadoPlaza() {
		return estadoPlaza;
	}

	/**
	 * Metodo setter del atribudo estadoPlaza de la plaza.
	 * 
	 * @param estadoPlaza: estado en el que se encuentra la plaza.
	 */
	public void setEstadoPlaza(boolean estadoPlaza) {
		this.estadoPlaza = estadoPlaza;
	}

	/**
	 * Metodo getter del atributo tipoPlaza de la plaza.
	 * 
	 * @return tipoPlaza: tipo de plaza que corresponde.
	 */
	public String getTipoPlaza() {
		return tipoPlaza;
	}

	/**
	 * Metodo setter del atributo tipoPlaza de la plaza.
	 * 
	 * @param tipoPlaza: tipo de plaza que corresponde.
	 */
	public void setTipoPlaza(String tipoPlaza) {
		this.tipoPlaza = tipoPlaza;
	}

	/**
	 * Metodo getter del atributo matricula de la plaza.
	 * 
	 * @return tipoPlaza: matricula del vehiculo que ocupa la plaza.
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * Metodo setter del atributo matricula de la plaza.
	 * 
	 * @param matricula: matricula del vehiculo que ocupa la plaza.
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * Metodo de instancia de la clase Plaza que sobrescribe el metodo toString() de
	 * la clase Object. Devuelve una cadena que contiene los valores de los
	 * atributos de la instancia de Plaza, formateados de acuerdo con el formato
	 * especificado.
	 */
	@Override
	public String toString() {
		return String.format("%d, %d, %s, %b", numeroPlanta, numeroPlaza, tipoPlaza, estadoPlaza);
	}

}
