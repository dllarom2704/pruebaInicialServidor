package es.iessoterohernandez.gestion_nominas.laboral;

public class Persona {
	public String nombre, dni;
	public char sexo;

	/**
	 * Constructor del objeto Persona, en este caso se le pasa como parámetros el nombre, el dni, sexo
	 * @param nombre El nombre de la persona
	 * @param dni El dni de la persona
	 * @param sexo El sexo de la persona
	 * */
	public Persona(String nombre, String dni, char sexo) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.sexo = sexo;
	}

	/**
	 * Constructor del objeto Persona, en este caso se le pasa como parámetros el nombre y el sexo
	 * @param nombre El nombre de la persona
	 * @param sexo El sexo de la persona
	 */
	public Persona(String nombre, char sexo) {
		super();
		this.nombre = nombre;
		this.sexo = sexo;
	}

	/**
	 * Es el setter de la propiedad dni del objeto persona
	 * @param dni El dni de la persona
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	/**
	 * Método que imprime el nombre y el dni de la persona
	 */
	public void imprime() {
		System.out.println("Nombre: " + nombre + "\nDni: " + dni);
	}

}
