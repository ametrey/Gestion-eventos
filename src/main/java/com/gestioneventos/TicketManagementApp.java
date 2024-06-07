package com.gestioneventos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.gestioneventos.compras.Compra;
import com.gestioneventos.compras.DetalleCompra;
import com.gestioneventos.eventos.Evento;
import com.gestioneventos.usuarios.Comprador;
import com.gestioneventos.usuarios.Organizador;
import com.gestioneventos.usuarios.Usuario;

public class TicketManagementApp {

    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual;

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
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
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

    private static void registrarUsuario() {
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

        if (tipoUsuario == 1) {
            Comprador comprador = new Comprador(0, usuario, password, nombre, apellido, dni);
            comprador.registrarse();
        } else if (tipoUsuario == 2) {
            Organizador organizador = new Organizador(0, usuario, password, nombre, apellido, dni);
            organizador.registrarse();
        } else {
            System.out.println("Tipo de usuario no válido.");
        }
    }

    private static void iniciarSesion() {
        System.out.println("Ingrese su usuario:");
        String usuario = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String password = scanner.nextLine();

        usuarioActual = Usuario.iniciarSesion(usuario, password);

        if (usuarioActual != null) {
            if (usuarioActual instanceof Comprador) {
                mostrarMenuComprador();
            } else if (usuarioActual instanceof Organizador) {
                mostrarMenuOrganizador();
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    private static void mostrarMenuComprador() {
        Comprador comprador = (Comprador) usuarioActual;
        comprador.cargarComprasRealizadas(); // Cargar compras realizadas

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
                    comprador.listarEventos();
                    break;
                case 2:
                    System.out.println("Ingrese el ID del evento:");
                    int eventoId = scanner.nextInt();
                    System.out.println("Ingrese la cantidad de tickets:");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea
                    comprador.comprarTicket(eventoId, cantidad);
                    break;
                case 3:
                    mostrarComprasRealizadas(comprador);
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

    private static void mostrarComprasRealizadas(Comprador comprador) {
        List<Compra> compras = comprador.getComprasRealizadas();

        if (compras.isEmpty()) {
            System.out.println("No hay compras realizadas.");
            return;
        }

        for (Compra compra : compras) {
            System.out.printf("Compra ID: %d, Fecha: %s, Total: %.2f, Estado: %s%n",
                    compra.getId(), compra.getFecha().toString(), compra.getTotal(), compra.getEstado());

            for (DetalleCompra detalle : compra.getDetalles()) {
                System.out.printf("\tEvento: %s, Cantidad: %d, Precio por entrada: %.2f%n",
                        detalle.getNombreEvento(), detalle.getCantidad(), detalle.getPrecioUnitario());
            }
        }
    }

    private static void mostrarMenuOrganizador() {
        Organizador organizador = (Organizador) usuarioActual;
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
                    organizador.listarEventos();
                    break;
                case 2:
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
                    try {
                        Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaStr);
                        Evento evento = new Evento(nombre, descripcion, fecha, cantidadTickets, precioTicket);
                        organizador.crearEvento(evento);
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;
                case 3:
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
                    try {
                        Date nuevaFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nuevaFechaStr);
                        Evento eventoModificado = new Evento(nuevoNombre, nuevaDescripcion, nuevaFecha,
                                nuevaCantidadTickets, nuevoPrecioTicket);
                        eventoModificado.setId(eventoIdModificar); // Asignar ID al evento modificado
                        organizador.modificarEvento(eventoModificado);
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha incorrecto.");
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el ID del evento a eliminar:");
                    int eventoIdEliminar = scanner.nextInt();
                    organizador.eliminarEvento(eventoIdEliminar);
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
}
