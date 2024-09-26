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
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CalculaNominas {

	public static Nomina nomina = new Nomina();
	private static final String URL = "jdbc:mysql://localhost:3306/gestion_nominas";
	private static final String USER = "root";
	private static final String PASSWORD = "123456";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection connection = null;
		Statement statement = null;

		Scanner sc = new Scanner(System.in);

		try {
			// BASE DE DATOS
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("\nConexión establecida con éxito\n");

//			List<Empleado> empleados = new ArrayList<>();
//
//			FileWriter writer;
//			writer = new FileWriter("empleados.txt");
//
//			statement = connection.createStatement();
//
//			ResultSet empleadosFromDatabase = statement
//					.executeQuery("SELECT nombre, dni, sexo, categoria, anyos FROM empleados");
//			while (empleadosFromDatabase.next()) {
//				String nombre = empleadosFromDatabase.getString("nombre");
//				String dni = empleadosFromDatabase.getString("dni");
//				char sexo = empleadosFromDatabase.getString("sexo").charAt(0);
//				Integer categoria = empleadosFromDatabase.getInt("categoria");
//				Integer anyos = empleadosFromDatabase.getInt("anyos");
//
//				Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
//				empleados.add(empleado);
//				writer.write(empleado.nombre + ", " + empleado.dni + ", " + empleado.sexo + ", "
//						+ empleado.getCategoria() + ", " + empleado.anyos + "\n");
//
//				statement = connection.createStatement();
//				statement.executeUpdate("INSERT INTO nominas (dni, sueldo) VALUES ('" + empleado.dni + "', "
//						+ nomina.sueldo(empleado) + ")");
//			}
//			writer.close();
//
//			statement = connection.createStatement();
//			ResultSet dniSueldos = statement.executeQuery("SELECT dni, sueldo FROM nominas");
//			writer = new FileWriter("salarios.dat");
//			while (dniSueldos.next()) {
//				writer.write(dniSueldos.getString("dni") + ", " + dniSueldos.getInt("sueldo"));
//			}
//
//			System.out.println();
//			escribe(empleados.get(0));
//			System.out.println("----------------------");
//			escribe(empleados.get(1));
//
//			empleados.get(0).incrAnyo();
//			empleados.get(1).setCategoria(9);
//
//			statement = connection.createStatement();
//
//			String updateEmpleadosAnyos = "UPDATE empleados SET anyos = " + empleados.get(0).anyos + " WHERE dni = '"
//					+ empleados.get(0).dni + "'";
//			statement.executeUpdate(updateEmpleadosAnyos);
//
//			String updateEmpleadosCategoria = "UPDATE empleados SET categoria = " + empleados.get(1).getCategoria()
//					+ " WHERE dni = '" + empleados.get(1).dni + "'";
//
//			statement = connection.createStatement();
//			statement.executeUpdate(updateEmpleadosCategoria);
//
//			statement = connection.createStatement();
//			ResultSet empleadosFromQuery = statement
//					.executeQuery("SELECT nombre, dni, sexo, categoria, anyos FROM empleados");
//
//			FileReader file = new FileReader("empleados.txt");
//			BufferedReader buffer = new BufferedReader(file);
//
//			String line = buffer.readLine();
//
//			List<String> data = new ArrayList<>();
//
//			int count = 0;
//			while (line != null) {
//				if (count <= 1) {
//					line = buffer.readLine();
//				}
//				line = buffer.readLine();
//				data.add(line);
//				count++;
//			}
//
//			String text = "";
//
//			for (String datum : data) {
//				text += datum + "\n";
//			}
//
//			while (empleadosFromQuery.next()) {
//				text += empleadosFromQuery.getString("nombre") + ", " + empleadosFromQuery.getString("dni") + ", "
//						+ empleadosFromQuery.getString("sexo").charAt(0) + ", " + empleadosFromQuery.getInt("categoria")
//						+ empleadosFromQuery.getInt("anyos") + "\n";
//			}
//			writer = new FileWriter("empleados.txt");
//			writer.write(text);
//			writer.close();
//
//			System.out.println("\nDatos actualizados correctamente\n");
//
//			Map<String, Integer> salariosEmpleados = new HashMap<>();
//			for (Empleado e : empleados) {
//				salariosEmpleados.put(e.dni, nomina.sueldo(e));
//			}
//
//			for (Map.Entry<String, Integer> se : salariosEmpleados.entrySet()) {
//				String key = se.getKey();
//				Integer value = se.getValue();
//
//				writer = new FileWriter("salarios.dat");
//				writer.write(key + ", " + value + "\n");
//			}
//			writer.close();

			// MENU
			Integer opcion = null;
			do {
				System.out.println(
						"\nMENU:\n---------------\n  1. Mostrar todos los empleados\n  2. Mostrar el salario de un empleado por su dni\n  3. Gestionar empleados (submenú)\n  4. Pulsa 0 para salir\nSelecciona una opción: ");
				opcion = sc.nextInt();
				sc.nextLine();

				switch (opcion) {
				case 1:
					statement = connection.createStatement();
					ResultSet empleados = statement
							.executeQuery("SELECT nombre, dni, sexo, categoria, anyos FROM empleados");
					System.out.println("\nEMPLEADOS\n---------------\n");
					while (empleados.next()) {
						System.out.println(
								"  Nombre: " + empleados.getString("nombre") + "\n  Dni: " + empleados.getString("dni")
										+ "\n  Sexo: " + empleados.getString("sexo").charAt(0) + "\n  Categoria: "
										+ empleados.getInt("categoria") + "\n  Años: " + empleados.getInt("anyos"));
						System.out.println();
					}
					break;
				case 2:
					System.out.println("\nIntroduzca el dni del empleado: ");
					String dni = sc.nextLine();

					statement = connection.createStatement();
					ResultSet sueldoEmpleado = statement
							.executeQuery("SELECT sueldo FROM nominas WHERE dni = '" + dni + "'");
					while (sueldoEmpleado.next()) {
						System.out.println("\nSueldo: " + sueldoEmpleado.getInt("sueldo"));
					}
					break;
				case 3:
					System.out.println(
							"\nSUBMENÚ\n---------------\n  1. Modificar datos (menos sueldo)\n  2. Recalcular y actualizar el sueldo\n  3. Recalcular y actualizar los sueldos de todos\n  4. Realizar una copia de seguirdad de la base de datos en ficheros\nSelecciona una opción: ");
					Integer opcion2 = sc.nextInt();
					sc.nextLine();

					switch (opcion2) {
					case 1:
						System.out.println("\nIntroduzca el dni del empleado: ");
						String dni2 = sc.nextLine();

						System.out.println(
								"\n¿Qué quieres actualizar?\n  1. Nombre\n  2. Dni\n  3. Sexo\n  4. Categoria\n  5. Años\nSelecciona una opción: ");
						Integer opcion3 = sc.nextInt();
						sc.nextLine();
						switch (opcion3) {
						case 1:
							System.out.println("\nIntroduzca el nuevo nombre: ");
							String nombre = sc.nextLine();

							statement = connection.createStatement();
							statement.executeUpdate(
									"UPDATE empleados SET nombre = '" + nombre + "' WHERE dni = '" + dni2 + "'");

							System.out.println("\nNombre actualizado");
							break;
						case 2:
							System.out.println("\nIntroduzca el nuevo dni: ");
							String dniToChange = sc.nextLine();

							statement = connection.createStatement();
							statement.executeUpdate("DELETE FROM nominas WHERE dni = '" + dni2 + "'");

							statement = connection.createStatement();
							statement.executeUpdate(
									"UPDATE empleados SET dni = '" + dniToChange + "' WHERE dni = '" + dni2 + "'");

							statement = connection.createStatement();
							ResultSet empleado = statement.executeQuery(
									"SELECT nombre, dni, sexo, categoria, anyos FROM empleados WHERE dni = '"
											+ dniToChange + "'");

							while (empleado.next()) {
								Integer sueldo = nomina.sueldo(new Empleado(empleado.getString("nombre"),
										empleado.getString("dni"), empleado.getString("sexo").charAt(0),
										empleado.getInt("categoria"), empleado.getInt("anyos")));

								statement = connection.createStatement();
								statement.executeUpdate("INSERT INTO nominas (dni, sueldo) VALUES ('" + dniToChange
										+ "', " + sueldo + ")");
							}

							System.out.println("\nDni actualizado");
							break;
						case 3:
							System.out.println("\nIntroduzca el nuevo sexo: ");
							String sexo = sc.nextLine();

							statement = connection.createStatement();
							statement.executeUpdate(
									"UPDATE empleados SET sexo = '" + sexo.charAt(0) + "' WHERE dni = '" + dni2 + "'");

							System.out.println("\nSexo actualizado");
							break;
						case 4:
							System.out.println("\nIntroduzca el nuevo número de categoría: ");
							Integer categoria = sc.nextInt();

							statement = connection.createStatement();
							statement.executeUpdate(
									"UPDATE empleados SET categoria = " + categoria + " WHERE dni = '" + dni2 + "'");

							System.out.println("\nCategoría actualizada");
							break;
						case 5:
							System.out.println("\nIntroduzca el nuevo número de años: ");
							Integer anyos = sc.nextInt();

							statement = connection.createStatement();
							statement.executeUpdate(
									"UPDATE empleados SET anyos = " + anyos + " WHERE dni = '" + dni2 + "'");

							System.out.println("\nAños actualizados");
							break;
						default:
							break;
						}
						break;
					case 2:
						System.out.println("\nIntroduzca el dni del empleado: ");
						String dni3 = sc.nextLine();

						statement = connection.createStatement();
						ResultSet empleado = statement.executeQuery(
								"SELECT nombre, dni, sexo, categoria, anyos FROM empleados WHERE dni = '" + dni3 + "'");

						while (empleado.next()) {
							Integer sueldo = nomina.sueldo(new Empleado(empleado.getString("nombre"),
									empleado.getString("dni"), empleado.getString("sexo").charAt(0),
									empleado.getInt("categoria"), empleado.getInt("anyos")));

							statement = connection.createStatement();
							statement.executeUpdate(
									"UPDATE nominas SET sueldo = " + sueldo + " WHERE dni = '" + dni3 + "'");
						}

						System.out.println("\nSueldo actualizado");
						break;
					case 3:
						statement = connection.createStatement();
						ResultSet empleadosFromQuery = statement
								.executeQuery("SELECT nombre, dni, sexo, categoria, anyos FROM empleados");
						while (empleadosFromQuery.next()) {
							Integer sueldo = nomina.sueldo(new Empleado(empleadosFromQuery.getString("nombre"),
									empleadosFromQuery.getString("dni"), empleadosFromQuery.getString("sexo").charAt(0),
									empleadosFromQuery.getInt("categoria"), empleadosFromQuery.getInt("anyos")));

							statement = connection.createStatement();
							statement.executeUpdate("UPDATE nominas SET sueldo = " + sueldo + " WHERE dni = '"
									+ empleadosFromQuery.getString("dni") + "'");

							System.out.println("\nSueldos actualizados");
						}
						break;
					case 4:
						statement = connection.createStatement();
						ResultSet empleadosFromQuery2 = statement
								.executeQuery("SELECT nombre, dni, sexo, categoria, anyos FROM empleados");
						FileWriter writer = new FileWriter("copiaSeguridad.txt");

						String text = "EMPLEADOS\n---------------";
						while (empleadosFromQuery2.next()) {
							text += "\n" + empleadosFromQuery2.getString("nombre") + ", "
									+ empleadosFromQuery2.getString("dni") + ", "
									+ empleadosFromQuery2.getString("sexo").charAt(0) + ", "
									+ empleadosFromQuery2.getInt("categoria") + ", "
									+ empleadosFromQuery2.getInt("anyos") + "\n";
						}

						text += "\nNOMINAS\n---------------";
						ResultSet nominas = statement.executeQuery("SELECT dni, sueldo FROM nominas");
						while(nominas.next()) {
							text += "\n" + nominas.getString("dni") + ", " + nominas.getInt("sueldo") + "\n";
						}

						writer.write(text);
						writer.close();

						System.out.println("\nBackup completado");
						break;
					default:
						if (opcion2 != 0) {
							System.out.println("Selecciona una opción válida");
							break;
						}

						System.out.println("Saliendo...");
						break;
					}
					break;
				default:
					if (opcion != 0) {
						System.out.println("Selecciona una opción válida");
						break;
					}

					System.out.println("Saliendo...");
					break;
				}
			} while (!(opcion == 0));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (DatosNoCorrectosException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DatosNoCorrectosException e) {
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

			String insertSalarioEmpleado = "INSERT INTO nominas (dni, sueldo) VALUES ('" + empleado.dni + "', " + sueldo
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

	/**
	 * @param fichero
	 */
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

			for (Empleado empleado : empleados) {
				text += "\n" + empleado.nombre + ", " + empleado.dni + ", " + empleado.sexo + ", "
						+ empleado.getCategoria() + ", " + empleado.anyos;

				String insertEmpleado = "INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES ('"
						+ empleado.nombre + "', '" + empleado.dni + "', '" + empleado.sexo + "', "
						+ empleado.getCategoria() + ", " + empleado.anyos + ");";

				statement = connection.createStatement();
				statement.executeUpdate(insertEmpleado);

				Integer sueldo = nomina.sueldo(empleado);

				String insertSalarioEmpleado = "INSERT INTO nominas (dni, sueldo) VALUES ('" + empleado.dni + "', "
						+ sueldo + ");";

				statement = connection.createStatement();
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
