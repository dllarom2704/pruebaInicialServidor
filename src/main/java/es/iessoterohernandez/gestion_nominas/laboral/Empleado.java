package es.iessoterohernandez.gestion_nominas.laboral;

public class Empleado extends Persona {
	private Integer categoria;
	public Integer anyos;

	public Empleado(String nombre, char sexo) {
		super(nombre, sexo);
	}

	public Empleado(String nombre,  String dni, char sexo) {
		super(nombre, dni, sexo);
		categoria = 1;
		anyos = 0;
	}

	public Empleado(String nombre, String dni, char sexo, Integer categoria, Integer anyos) throws DatosNoCorrectosException {
		super(nombre, dni, sexo);
		if (!(categoria >= 1 && categoria <= 10 || !(anyos >= 0))) {
			throw new DatosNoCorrectosException("Datos no correctos");
		}
		
		this.categoria = categoria;
		this.anyos = anyos;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public void incrAnyo() {
		anyos++;
	}

	public void imprime() {
		System.out.println("Nombre: " + nombre + "\nDni: " + dni + "\nSexo: " + sexo + "\nCategoría: " + categoria
				+ "\nAños trabajados: " + anyos);
	}

}
