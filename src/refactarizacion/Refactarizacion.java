package refactarizacion;

import java.io.*;
import java.util.*;

public class Refactarizacion {

    private static final String FILE_NAME = "alumnos.txt";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        
        while(opcion != 6) {
            mostrarMenu();
            try {
                System.out.print("Elige una opción: ");
                opcion = Integer.parseInt(sc.nextLine());
            } catch(Exception e) {
                System.out.println("Error: Por favor, ingresa un número válido.");
                opcion = 0;
            }
            
            switch(opcion) {
                case 1:
                    agregarAlumno();
                    break;
                case 2:
                    listarAlumnos();
                    break;
                case 3:
                    eliminarAlumnoPorMatricula();
                    break;
                case 4:
                    modificarAlumnoPorMatricula();
                    break;
                case 5:
                    eliminarTodosAlumnos();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida, intenta nuevamente.");
                    break;
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Agregar alumno");
        System.out.println("2. Listar alumnos");
        System.out.println("3. Eliminar alumno por matrícula");
        System.out.println("4. Modificar alumno por matrícula");
        System.out.println("5. Eliminar todos los alumnos");
        System.out.println("6. Salir");
    }

    // Opción 1: Agregar alumno
    private static void agregarAlumno() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);

            System.out.print("Ingresa la matrícula: ");
            String matricula = sc.nextLine();
            System.out.print("Ingresa el nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Ingresa la edad: ");
            String edad = sc.nextLine();
            System.out.print("Ingresa el peso: ");
            String peso = sc.nextLine();

            // Se guarda en formato CSV (separado por comas)
            bw.write(matricula + "," + nombre + "," + edad + "," + peso);
            bw.newLine();
            bw.close();
            fw.close();

            System.out.println("Alumno agregado con éxito.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Opción 2: Listar alumnos
    private static void listarAlumnos() {
        try {
            File file = new File(FILE_NAME);
            if(!file.exists()) {
                System.out.println("No hay alumnos registrados.");
                return;
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String linea;
            System.out.println("\n--- LISTA DE ALUMNOS ---");
            while((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if(datos.length == 4) {
                    System.out.println("Matrícula: " + datos[0] + 
                                       " | Nombre: " + datos[1] + 
                                       " | Edad: " + datos[2] + 
                                       " | Peso: " + datos[3]);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Opción 3: Eliminar alumno por matrícula
    private static void eliminarAlumnoPorMatricula() {
        System.out.print("Ingresa la matrícula del alumno a eliminar: ");
        String matriculaEliminar = sc.nextLine();

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            while((currentLine = reader.readLine()) != null) {
                String[] datos = currentLine.split(",");
                if(datos.length == 4) {
                    String matricula = datos[0];
                    if(matricula.equals(matriculaEliminar)) {
                        found = true;
                        // NO se escribe en el archivo temporal => Se elimina
                        continue;
                    }
                }
                writer.write(currentLine);
                writer.newLine();
            }

            writer.close();
            reader.close();
            
            if(!inputFile.delete()) {
                System.out.println("No se pudo eliminar el archivo original.");
            }
            if(!tempFile.renameTo(inputFile)) {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }

            if(found) {
                System.out.println("Alumno eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún alumno con esa matrícula.");
            }

        } catch (IOException e) {
            System.out.println("Error al eliminar el alumno: " + e.getMessage());
        }
    }

    // Opción 4: Modificar alumno por matrícula
    private static void modificarAlumnoPorMatricula() {
        System.out.print("Ingresa la matrícula del alumno a modificar: ");
        String matriculaModificar = sc.nextLine();

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            while((currentLine = reader.readLine()) != null) {
                String[] datos = currentLine.split(",");
                if(datos.length == 4) {
                    String matricula = datos[0];
                    if(matricula.equals(matriculaModificar)) {
                        found = true;
                        System.out.print("Ingresa el nuevo nombre: ");
                        String nuevoNombre = sc.nextLine();
                        System.out.print("Ingresa la nueva edad: ");
                        String nuevaEdad = sc.nextLine();
                        System.out.print("Ingresa el nuevo peso: ");
                        String nuevoPeso = sc.nextLine();

                        // Escribimos la línea modificada
                        writer.write(matricula + "," + nuevoNombre + "," + nuevaEdad + "," + nuevoPeso);
                        writer.newLine();
                        continue;
                    }
                }
                writer.write(currentLine);
                writer.newLine();
            }

            writer.close();
            reader.close();

            if(!inputFile.delete()) {
                System.out.println("No se pudo eliminar el archivo original.");
            }
            if(!tempFile.renameTo(inputFile)) {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }

            if(found) {
                System.out.println("Alumno modificado con éxito.");
            } else {
                System.out.println("No se encontró ningún alumno con esa matrícula.");
            }

        } catch(IOException e) {
            System.out.println("Error al modificar el alumno: " + e.getMessage());
        }
    }

    // Opción 5: Eliminar todos los alumnos
    private static void eliminarTodosAlumnos() {
        File file = new File(FILE_NAME);
        if(file.exists()) {
            if(file.delete()) {
                System.out.println("Se han eliminado todos los alumnos.");
            } else {
                System.out.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.out.println("No hay archivo existente para eliminar.");
        }
    }
}