package backend.clases.infraestructura;

public class Plaza {

	private int numeroPlaza;
	private boolean estadoPlaza; // vacio (false) o ocupado (true)
	private String tipoPlaza; // Corresponde al tipo de vehiculo que vaya a aparcar: normal (1), electrico  (2), ...

	public Plaza() {
		super();
	}

	public Plaza(int numeroPlaza, boolean estadoPlaza, String tipoPlaza) {
		super();
		this.numeroPlaza = numeroPlaza;
		this.estadoPlaza = estadoPlaza;
		this.tipoPlaza = tipoPlaza;
	}

	public int getNumeroPlaza() {
		return numeroPlaza;
	}

	public void setNumeroPlaza(int numeroPlaza) {
		this.numeroPlaza = numeroPlaza;
	}

	public boolean isEstadoPlaza() {
		return estadoPlaza;
	}

	public void setEstadoPlaza(boolean estadoPlaza) {
		this.estadoPlaza = estadoPlaza;
	}

	public String getTipoPlaza() {
		return tipoPlaza;
	}

	public void setTipoPlaza(String tipoPlaza) {
		this.tipoPlaza = tipoPlaza;
	}

	@Override
	public String toString() {
		return String.format("%d, %b, %s", numeroPlaza, estadoPlaza, tipoPlaza);
	}

}
