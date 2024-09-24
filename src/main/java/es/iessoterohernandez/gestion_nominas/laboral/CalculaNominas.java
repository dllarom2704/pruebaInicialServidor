package es.iessoterohernandez.gestion_nominas.laboral;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CalculaNominas {

	public static Nomina nomina = new Nomina();
	private static final String URL = "jdbc:mysql://localhost:3306/gestion_nominas";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection connection = null;
		Statement statement = null;

		try {
			FileReader file = null;

			file = new FileReader("empleados.txt");

			BufferedReader buffer = new BufferedReader(file);

			String line = buffer.readLine();
			String[] data = null;

			List<Empleado> empleados = new ArrayList<>();

			while (line != null) {
				data = line.split(", ");
				line = buffer.readLine();

				String nombre = data[0];
				String dni = data[1];
				char sexo = data[2].charAt(0);
				;

				Empleado empleado;
				if (data.length == 5) {
					Integer categoria = Integer.parseInt(data[3]);
					Integer anyos = Integer.parseInt(data[4]);

					empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
					empleados.add(empleado);
				}

				if (data.length == 3) {
					empleado = new Empleado(nombre, dni, sexo);
					empleados.add(empleado);
				}
			}
			buffer.close();

			escribe(empleados.get(0));
			System.out.println("----------------------");
			escribe(empleados.get(1));

			empleados.get(1).incrAnyo();
			empleados.get(0).setCategoria(9);

			FileWriter writer;

			writer = new FileWriter("empleados.txt");
			writer.write(empleados.get(0).nombre + ", " + empleados.get(0).dni + ", " + empleados.get(0).sexo + ", "
					+ empleados.get(0).getCategoria() + ", " + empleados.get(0).anyos + "\n" + empleados.get(1).nombre
					+ ", " + empleados.get(1).dni + ", " + empleados.get(1).sexo + ", "
					+ empleados.get(1).getCategoria() + ", " + empleados.get(1).anyos);

			writer.close();

			System.out.println();
			escribe(empleados.get(0));
			System.out.println("----------------------");
			escribe(empleados.get(1));

			Map<String, Integer> salariosEmpleados = new HashMap<>();
			for (Empleado e : empleados) {
				salariosEmpleados.put(e.dni, nomina.sueldo(e));
			}
			writer = new FileWriter("salarios.dat");
			for (Map.Entry<String, Integer> se : salariosEmpleados.entrySet()) {
				String key = se.getKey();
				Integer value = se.getValue();

				writer.write(key + ", " + value + "\n");
			}
			writer.close();

			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("\nConexión establecida con éxito");

			statement = connection.createStatement();

			String updateEmpleadosAnyos = "UPDATE empleados SET anyos = " + empleados.get(1).anyos + " WHERE dni = '"
					+ empleados.get(1).dni + "'";
			String updateEmpleadosCategoria = "UPDATE empleados SET categoria = " + empleados.get(0).getCategoria()
					+ " WHERE dni = '" + empleados.get(0).dni + "'";

			statement.executeUpdate(updateEmpleadosAnyos);
			statement.executeUpdate(updateEmpleadosCategoria);

			System.out.println("Datos actualizados correctamente\n");

			file = new FileReader("salarios.dat");

			buffer = new BufferedReader(file);

			line = buffer.readLine();
			data = null;

			while (line != null) {
				data = line.split(", ");
				line = buffer.readLine();

				String dni = data[0];
				String salario = data[1];

				String insertSalarioEmpleados = "INSERT INTO nominas (dni, sueldo) VALUES ('" + dni + "', " + salario
						+ ");";

				statement.executeUpdate(insertSalarioEmpleados);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void escribe(Empleado empleado) {
		empleado.imprime();
		System.out.println(nomina.sueldo(empleado));
	}

	private static void altaEmpleado(Empleado empleado) {

		Connection connection = null;
		Statement statement = null;

		try {
			FileReader file = new FileReader("empleados.txt");
			BufferedReader buffer = new BufferedReader(file);

			String line = buffer.readLine();

			List<String> data = new ArrayList<>();

			while (line != null) {
				data.add(line);
				line = buffer.readLine();
			}
			buffer.close();

			String text = "";

			for (String datum : data) {
				text += "\n" + datum;
			}

			if (empleado.getCategoria() == 1 && empleado.anyos == 0) {
				text += "\n" + empleado.nombre + ", " + empleado.dni + ", " + empleado.sexo;
			}

			text += "\n" + empleado.nombre + ", " + empleado.dni + ", " + empleado.sexo + ", " + empleado.getCategoria()
					+ ", " + empleado.anyos;

			FileWriter writer = new FileWriter("empleados.txt");
			writer.write(text);

			writer.close();

			Integer sueldo = nomina.sueldo(empleado);

			file = new FileReader("salarios.dat");
			buffer = new BufferedReader(file);

			line = buffer.readLine();

			data = new ArrayList<>();

			while (line != null) {
				data.add(line);
				line = buffer.readLine();
			}

			buffer.close();

			text = "";

			for (String datum : data) {
				text += "\n" + datum;
			}

			text += "\n" + empleado.dni + ", " + sueldo;

			writer = new FileWriter("empleados.txt");
			writer.write(text);

			writer.close();

			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("\nConexión establecida con éxito");

			statement = connection.createStatement();

			String insertEmpleado = "INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES ('"
					+ empleado.nombre + "', '" + empleado.dni + "', '" + empleado.sexo + "', " + empleado.getCategoria()
					+ ", " + empleado.anyos + ");";

			statement.executeUpdate(insertEmpleado);

			String insertSalarioEmpleado = "INSERT INTO nomina (dni, sueldo) VALUES ('" + empleado.dni + "', " + sueldo
					+ ");";

			statement.executeUpdate(insertSalarioEmpleado);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void altaEmpleado(String fichero) {

		Connection connection = null;
		Statement statement = null;

		try {
			FileReader file = new FileReader(fichero);

			BufferedReader buffer = new BufferedReader(file);

			String line = buffer.readLine();

			String[] data = null;

			List<Empleado> empleados = new ArrayList<>();

			while (line != null) {
				data = line.split(", ");
				line = buffer.readLine();

				String nombre = data[0];
				String dni = data[1];
				char sexo = data[2].charAt(0);
				;

				Empleado empleado;
				if (data.length == 5) {
					Integer categoria = Integer.parseInt(data[3]);
					Integer anyos = Integer.parseInt(data[4]);

					empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
					empleados.add(empleado);
				}

				if (data.length == 3) {
					empleado = new Empleado(nombre, dni, sexo);
					empleados.add(empleado);
				}
			}
			buffer.close();

			file = new FileReader("empleados.txt");
			buffer = new BufferedReader(file);

			line = buffer.readLine();

			List<String> dataEmpleadosTxt = new ArrayList<>();

			while (line != null) {
				dataEmpleadosTxt.add(line);
				line = buffer.readLine();
			}
			buffer.close();

			String text = "";

			for (String datum : dataEmpleadosTxt) {
				text += "\n" + datum;
			}

			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("\nConexión establecida con éxito");

			statement = connection.createStatement();

			for (Empleado empleado : empleados) {
				text += "\n" + empleado.nombre + ", " + empleado.dni + ", " + empleado.sexo + ", "
						+ empleado.getCategoria() + ", " + empleado.anyos;

				String insertEmpleado = "INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES ('"
						+ empleado.nombre + "', '" + empleado.dni + "', '" + empleado.sexo + "', "
						+ empleado.getCategoria() + ", " + empleado.anyos + ");";

				statement.executeUpdate(insertEmpleado);
				
				Integer sueldo = nomina.sueldo(empleado);
				
				String insertSalarioEmpleado = "INSERT INTO nomina (dni, sueldo) VALUES ('" + empleado.dni + "', " + sueldo
						+ ");";

				statement.executeUpdate(insertSalarioEmpleado);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
