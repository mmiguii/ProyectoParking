package backend.clases.infraestructura;

public class Planta {

	private int numeroPlanta;
	private int cantidadPlazasNormales;
	private int cantidadPlazasElectricas;
	private int cantidadPlazasDiscapacitados;

	public Planta() {
		super();
	}

	public Planta(int numeroPlanta, int cantidadPlazasNormales, int cantidadPlazasElectricas,
			int cantidadPlazasDiscapacitados) {
		super();
		this.numeroPlanta = numeroPlanta;
		this.cantidadPlazasNormales = cantidadPlazasNormales;
		this.cantidadPlazasElectricas = cantidadPlazasElectricas;
		this.cantidadPlazasDiscapacitados = cantidadPlazasDiscapacitados;
	}

	public int getNumeroPlanta() {
		return numeroPlanta;
	}

	public void setNumeroPlanta(int numeroPlanta) {
		this.numeroPlanta = numeroPlanta;
	}

	public int getCantidadPlazasNormales() {
		return cantidadPlazasNormales;
	}

	public void setCantidadPlazasNormales(int cantidadPlazasNormales) {
		this.cantidadPlazasNormales = cantidadPlazasNormales;
	}

	public int getCantidadPlazasElectricas() {
		return cantidadPlazasElectricas;
	}

	public void setCantidadPlazasElectricas(int cantidadPlazasElectricas) {
		this.cantidadPlazasElectricas = cantidadPlazasElectricas;
	}

	public int getCantidadPlazasDiscapacitados() {
		return cantidadPlazasDiscapacitados;
	}

	public void setCantidadPlazasDiscapacitados(int cantidadPlazasDiscapacitados) {
		this.cantidadPlazasDiscapacitados = cantidadPlazasDiscapacitados;
	}

	@Override
	public String toString() {
		return String.format("%d, %d, %d, %d", numeroPlanta, cantidadPlazasNormales, cantidadPlazasElectricas,
				cantidadPlazasDiscapacitados);
	}

}
