package backend.clases.personas.personal;

/**
 * La funcion de esta clase Manager es definir y crear un objeto de tipo manager
 * que desciende de un objeto Trabajador.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class Manager extends Trabajador {

	/** Constructor vacio de la clase Manager */
	public Manager() {
		super();
	}

	/**
	 * Constuctor super del objeto Trabajador (con todos los atributos).
	 * 
	 * @param nombreUsuario: nombre de usuario del manager.
	 * @param password:      password del manager.
	 * @param dni:           dni del manager.
	 * @param email:         email del manager.
	 * @param puesto:        puesto que ocupa. (Manager en este caso).
	 * @param fechaComienzo: fecha de comienzo en el parking.
	 * @param antiguedad:    anos que lleva en el parking.
	 * @param salario:       salario mensual del manager.
	 */
	public Manager(String nombreUsuario, String password, String dni, String email, String puesto, long fechaComienzo,
			int antiguedad, double salario) {
		super(nombreUsuario, password, dni, email, puesto, fechaComienzo, antiguedad, salario);
	}

	/**
	 * Sobrescribe el metodo toString de la superclase y devuelve una representacion
	 * en forma de cadena del objeto actual.
	 * 
	 */
	@Override
	public String toString() {
		return super.toString();
	}
}
