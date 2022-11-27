package backend.clases.personas.personal;

/**
 * La función de esta clase Manager es definir y crear un objeto de tipo manager
 * que desciende de un objeto Trabajador.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class Manager extends Trabajador {

	/** Constructor vacio de la clase ClienteSubscrito */
	public Manager() {
		super();
	}

	/**
	 * 
	 * @param nombre:        nombre del manager.
	 * @param apellido:      apellido del manager.
	 * @param dni:           dni del manager.
	 * @param email:         email del manager.
	 * @param puesto:        puesto que ocupa. (Manager en este caso).
	 * @param fechaComienzo: fecha de comienzo en el parking.
	 * @param antiguedad:    anos que lleva en el parking.
	 * @param salario:       salario mensual del manager.
	 */
	public Manager(String nombre, String apellido, String dni, String email, String puesto, long fechaComienzo,
			int antiguedad, double salario) {
		super(nombre, apellido, dni, email, puesto, fechaComienzo, antiguedad, salario);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
