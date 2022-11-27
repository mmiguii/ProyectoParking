package backend.clases.infraestructura;

/**
 * La función de esta clase Plaza es definir y crear un objeto de tipo plaza.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class Plaza {

	/** Atributos especificos de un objeto Plaza */
	private int numeroPlaza;
	private boolean estadoPlaza; // vacio (false) o ocupado (true)
	private String tipoPlaza; // Corresponde al tipo de vehiculo que vaya a aparcar: normal (1), electrico
								// (2), ...

	/** Constructor vacio de la clase Planta */
	public Plaza() {
		super();
	}

	public Plaza(int numeroPlaza, boolean estadoPlaza, String tipoPlaza) {
		super();
		this.numeroPlaza = numeroPlaza;
		this.estadoPlaza = estadoPlaza;
		this.tipoPlaza = tipoPlaza;
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

	@Override
	public String toString() {
		return String.format("%d, %b, %s", numeroPlaza, estadoPlaza, tipoPlaza);
	}

}
