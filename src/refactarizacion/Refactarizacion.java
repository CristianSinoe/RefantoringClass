package refactarizacion;

import java.io.*;
import java.util.*;

public class Refactarizacion {

public static void main(String[] args) {
    
    Scanner sc = new Scanner(System.in);
        AlumnoService service = new AlumnoService(new AlumnoFileRepository());
        int opcion;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. AGREGAR ALUMNO");
            System.out.println("2. LISTAR ALUMNOS");
            System.out.println("3. ELIMINAR ALUMNO POR MATRICULA");
            System.out.println("4. MODIFICAR ALUMNO POR MATRICULA");
            System.out.println("5. ELIMINAR TODOS LOS ALUMNOS");
            System.out.println("6. SALIR");
            System.out.print("ELIGE UNA OPCION: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("MATRICULA: ");
                    String matricula = sc.nextLine();
                    System.out.print("NOMBRE: ");
                    String nombre = sc.nextLine();
                    System.out.print("EDAD: ");
                    int edad = sc.nextInt();
                    System.out.print("PESO: ");
                    double peso = sc.nextDouble();
                    service.agregarAlumno(matricula, nombre, edad, peso);
                }
                case 2 -> service.listarAlumnos();
                case 3 -> {
                    System.out.print("MATRICULA A ELIMINAR: ");
                    service.eliminarAlumno(sc.nextLine());
                }
                case 4 -> {
                    System.out.print("MATRICULA A MODIFICAR: ");
                    String matricula = sc.nextLine();
                    System.out.print("NUEVO NOMBRE: ");
                    String nombre = sc.nextLine();
                    System.out.print("NUEVA EDAD: ");
                    int edad = sc.nextInt();
                    System.out.print("NUEVO PESO: ");
                    double peso = sc.nextDouble();
                    service.modificarAlumno(matricula, nombre, edad, peso);
                }
                case 5 -> service.eliminarTodos();
            }
        } while (opcion != 6);
    }
}
