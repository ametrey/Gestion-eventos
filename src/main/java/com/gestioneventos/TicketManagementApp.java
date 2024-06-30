package com.gestioneventos;

import java.util.Scanner;

import com.gestioneventos.controller.TicketManagementController;
import com.gestioneventos.model.eventos.Evento;

import java.util.List;

public class TicketManagementApp {
    private static Scanner scanner = new Scanner(System.in);
    private static TicketManagementController controller = new TicketManagementController();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    mostrarRegistroUsuario();
                    break;
                case 2:
                    mostrarInicioSesion();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private static void mostrarRegistroUsuario() {
        System.out.println("Seleccione el tipo de usuario:");
        System.out.println("1. Comprador");
        System.out.println("2. Organizador");
        int tipoUsuario = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese su nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese su apellido:");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese su usuario:");
        String usuario = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String password = scanner.nextLine();
        System.out.println("Ingrese su DNI:");
        String dni = scanner.nextLine();

        controller.registrarUsuario(nombre, apellido, usuario, password, dni, tipoUsuario);
        System.out.println("Usuario registrado con éxito.");
    }

    private static void mostrarInicioSesion() {
        System.out.println("Ingrese su usuario:");
        String usuario = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String password = scanner.nextLine();

        if (controller.iniciarSesion(usuario, password)) {
            System.out.println("Inicio de sesión exitoso");
            if (controller.esComprador()) {
                mostrarMenuComprador();
            } else if (controller.esOrganizador()) {
                mostrarMenuOrganizador();
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    private static void mostrarMenuComprador() {
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ver eventos disponibles");
            System.out.println("2. Comprar ticket");
            System.out.println("3. Ver compras realizadas");
            System.out.println("4. Cerrar sesión");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    listarEventos();
                    break;
                case 2:
                    comprarTicket();
                    break;
                case 3:
                    mostrarComprasRealizadas();
                    break;
                case 4:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private static void listarEventos() {
        List<Evento> eventos = controller.listarEventos();
        if (eventos == null || eventos.isEmpty()) {
            System.out.println("No hay eventos disponibles.");
        } else {
            for (Evento evento : eventos) {
                System.out.printf(
                        "ID: %d, Nombre: %s, Descripción: %s, Fecha: %s, Tickets Disponibles: %d, Precio por Ticket: %.2f%n",
                        evento.getId(), evento.getNombre(), evento.getDescripcion(), evento.getFecha().toString(),
                        evento.getCantidadTickets(), evento.getPrecioTicket());
            }
        }
    }

    private static void comprarTicket() {
        System.out.println("Ingrese el ID del evento:");
        int eventoId = scanner.nextInt();
        System.out.println("Ingrese la cantidad de tickets:");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (controller.comprarTicket(eventoId, cantidad)) {
            System.out.println("Compra realizada con éxito.");
        } else {
            System.out.println("Error al realizar la compra.");
        }
    }

    private static void mostrarComprasRealizadas() {
        String detallesCompras = controller.obtenerDetallesCompra();
        System.out.println(detallesCompras);
    }

    private static void mostrarMenuOrganizador() {
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ver eventos creados");
            System.out.println("2. Crear evento");
            System.out.println("3. Modificar evento");
            System.out.println("4. Eliminar evento");
            System.out.println("5. Cerrar sesión");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    listarEventos();
                    break;
                case 2:
                    mostrarCrearEvento();
                    break;
                case 3:
                    mostrarModificarEvento();
                    break;
                case 4:
                    mostrarEliminarEvento();
                    break;
                case 5:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private static void mostrarCrearEvento() {
        System.out.println("Ingrese el nombre del evento:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese la descripción del evento:");
        String descripcion = scanner.nextLine();
        System.out.println("Ingrese la fecha del evento (YYYY-MM-DD HH:MM:SS):");
        String fechaStr = scanner.nextLine();
        System.out.println("Ingrese la cantidad de tickets:");
        int cantidadTickets = scanner.nextInt();
        System.out.println("Ingrese el precio de cada ticket:");
        double precioTicket = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        String resultado = controller.crearEvento(nombre, descripcion, fechaStr, cantidadTickets, precioTicket);
        System.out.println(resultado);
    }

    private static void mostrarModificarEvento() {
        System.out.println("Ingrese el ID del evento a modificar:");
        int eventoIdModificar = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.println("Ingrese el nuevo nombre del evento:");
        String nuevoNombre = scanner.nextLine();
        System.out.println("Ingrese la nueva descripción del evento:");
        String nuevaDescripcion = scanner.nextLine();
        System.out.println("Ingrese la nueva fecha del evento (YYYY-MM-DD HH:MM:SS):");
        String nuevaFechaStr = scanner.nextLine();
        System.out.println("Ingrese la nueva cantidad de tickets:");
        int nuevaCantidadTickets = scanner.nextInt();
        System.out.println("Ingrese el nuevo precio de cada ticket:");
        double nuevoPrecioTicket = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        String resultado = controller.modificarEvento(eventoIdModificar, nuevoNombre, nuevaDescripcion, nuevaFechaStr,
                nuevaCantidadTickets, nuevoPrecioTicket);
        System.out.println(resultado);
    }

    private static void mostrarEliminarEvento() {
        System.out.println("Ingrese el ID del evento a eliminar:");
        int eventoIdEliminar = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        String resultado = controller.eliminarEvento(eventoIdEliminar);
        System.out.println(resultado);
    }
}
