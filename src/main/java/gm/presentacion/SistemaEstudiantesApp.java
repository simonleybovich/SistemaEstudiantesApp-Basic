package gm.presentacion;

import gm.datos.EstudianteDAO;
import gm.dominio.Estudiante;

import java.util.Scanner;

public class SistemaEstudiantesApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);
        // se crea una instancia de la clase servicio
        var estudianteDao = new EstudianteDAO();
        while (!salir){
            try {
                mostrarMenu();
                salir = ejecutarOpciones(consola, estudianteDao);
            }catch (Exception e){
                System.out.println("Error al ejecutar operacion: " + e.getMessage());
            }
            System.out.println();
        }//fin while
    }
    private static void mostrarMenu(){
        System.out.print("""
                **** Sistema de Estudiantes ****
                1. Listar Estudiantes
                2. Buscar Estudiante
                3. Agregar Estudiante
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                Elige una opcion: 
                """);
    }
    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch (opcion){
            case 1:// Listar estudiantes
                System.out.println("Listado de estudiantes...");
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);
                break;
            case 2:// Buscar Estudiante
                System.out.println("Introduce el id del estudiante a buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudianteABuscar = new Estudiante(idEstudiante);
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudianteABuscar);
                if (encontrado){
                    System.out.println("Estudiante encontrado: " + estudianteABuscar);
                } else {
                    System.out.println("Estudiante no encontrado: " + estudianteABuscar);
                }
                break;
            case 3:// Agregar Estudiante
                System.out.println("Agregar Estudiante: ");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();
                //crear objeto estudiante (sin id)
                var estudianteAAgregar = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudianteAAgregar);
                if (agregado){
                    System.out.println("Estudiante agregado: " + estudianteAAgregar);
                } else {
                    System.out.println("Estudiante NO agregado: " + estudianteAAgregar);
                }
                break;
            case 4:// Modificar Estudiante
                System.out.println("Modificar estudiante: ");
                System.out.println("Id estudiante: ");
                var idEstudianteAModificar = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombreAModificar = consola.nextLine();
                System.out.print("Apellido: ");
                var apellidoAModificar = consola.nextLine();
                System.out.print("telefono: ");
                var telefonoAModificar = consola.nextLine();
                System.out.print("Email: ");
                var emailAModificar = consola.nextLine();
                //crear objeto estudiante a modificar
                var estudianteAModificar = new Estudiante
                        (idEstudianteAModificar, nombreAModificar, apellidoAModificar, telefonoAModificar, emailAModificar);
                var modificado = estudianteDAO.modificarEstudiante(estudianteAModificar);
                if (modificado){
                    System.out.println("Estudiante modificado: " + estudianteAModificar);
                } else{
                    System.out.println("Estudiante NO modificado: " + estudianteAModificar);
                }
                break;
            case 5:// Eliminar Estudiante
                System.out.println("Eliminar estudiante: ");
                System.out.println("Id estudiante: ");
                var idEstudianteAEliminar = Integer.parseInt(consola.nextLine());
                var estudianteAEliminar = new Estudiante(idEstudianteAEliminar);
                var eliminado = estudianteDAO.eliminarEstudiante(estudianteAEliminar);
                if (eliminado){
                    System.out.println("Estudiante eliminado: " + estudianteAEliminar);
                } else{
                    System.out.println("Estudiante NO eliminado: " + estudianteAEliminar);
                }
                break;
            case 6:// SALIR
                System.out.println("Hasta pronto...");
                salir = true;
                break;
            default:
                System.out.println("Opcion no reconocida...");
                break;
        }
        return salir;
    }
}