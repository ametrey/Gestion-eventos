# Sistema de Gestión de Eventos

Este proyecto es una aplicación de consola en Java para la gestión de eventos, incluyendo registración de usuarios, creación de eventos y compra de entradas. Utiliza MySQL como base de datos y está construido con Maven.

## Requisitos

- Java 8 o superior
- Maven 3.6.0 o superior
- MySQL 5.7 o superior

## Instalación

1. Clona el repositorio del proyecto:

2. Configura la base de datos MySQL. Ejecuta el script SQL proporcionado para crear las tablas necesarias. El script se encuentra en el archivo db/schema.sql:

3. Configura el archivo dbconfig.properties con los detalles de tu base de datos:

db.url=jdbc:mysql://localhost:3306/tu_base_de_datos
db.user=tu_usuario
db.password=tu_contraseña

4. Compila y ejecuta el proyecto utilizando Maven:

mvn clean install
mvn exec:java -Dexec.mainClass="com.gestioneventos.TicketManagementApp"

## Uso

Al ejecutar la aplicación, verás un menú con las siguientes opciones:

Registrar usuario
Iniciar sesión
Salir

### Funcionalidades del Comprador

Ver eventos disponibles: Muestra una lista de todos los eventos disponibles.
Comprar ticket: Permite comprar tickets para un evento especificando el ID del evento y la cantidad de tickets.
Ver compras realizadas: Muestra una lista de todas las compras realizadas por el comprador.
Cerrar sesión: Cierra la sesión actual y vuelve al menú principal.

### Funcionalidades del Organizador

Ver eventos creados: Muestra una lista de todos los eventos creados por el organizador.
Crear evento: Permite crear un nuevo evento proporcionando el nombre, descripción, fecha, cantidad de tickets y precio por ticket.
Modificar evento: Permite modificar un evento existente especificando el ID del evento.
Eliminar evento: Permite eliminar un evento existente especificando el ID del evento.
Cerrar sesión: Cierra la sesión actual y vuelve al menú principal.
