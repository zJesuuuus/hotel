import java.util.Scanner;

public class HotelReservationSystem {
    // Constantes del sistema
    private static final int numHabs = 5;
    private static final int numPisos = 3;
    private static final int habsPorPiso = 3;
    
    // Arrays para almacenar la informacion de las reservas
    private static String[] nombresClientes = new String[numHabs];
    private static int[] numerosHabitaciones = new int[numHabs];
    private static int[] nochesReservadas = new int[numHabs];
    private static double[] preciosPorNoche = new double[numHabs];
    private static boolean[] reservasActivas = new boolean[numHabs];
    
    // Matriz para representar las habitaciones del hotel (pisos x habitaciones)
    private static String[][] estadoHabitaciones = new String[numPisos][habsPorPiso];
    
    private static Scanner input = new Scanner(System.in);
    private static int reservasRegistradas = 0;

    public static void main(String[] args) {
        inicializarHotel();
        mostrarMenuPrincipal();
    }
    
    // Inicializa el estado de todas las habitaciones como disponibles

    private static void inicializarHotel() {
        for (int piso = 0; piso < numPisos; piso++) {
            for (int hab = 0; hab < habsPorPiso; hab++) {
                estadoHabitaciones[piso][hab] = "Disponible";
            }
        }
    }
    
    //Muestra el menu principal y maneja las opciones del usuario

    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE RESERVAS DEL HOTEL ===");
            System.out.println("1. Registrar nueva reserva.");
            System.out.println("2. Cancelar reserva.");
            System.out.println("3. Mostrar reporte de habitaciones.");
            System.out.println("4. Calcular total a pagar");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            
            opcion = input.nextInt();
            input.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    registrarReserva();
                    break;
                case 2:
                    cancelarReserva();
                    break;
                case 3:
                    generarReporte();
                    break;
                case 4:
                    calcularTotalPagar();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }
    
    // Registra una nueva reserva en el sistema

    private static void registrarReserva() {
        if (reservasRegistradas >= numHabs) {
            System.out.println("No hay habitaciones disponibles. Hotel completo.");
            return;
        }
        
        System.out.println("\n--- NUEVA RESERVA ---");
        
        // Obtener datos del cliente
        System.out.print("Nombre del cliente: ");
        String nombre = input.nextLine();
        
        // Mostrar habitaciones disponibles
        System.out.println("\nHabitaciones disponibles:");
        mostrarHabitacionesDisponibles();
        
        System.out.print("Numero de habitacion (1-" + (numPisos * habsPorPiso) + "): ");
        int numeroHabitacion = input.nextInt();
        
        // Validar numero de habitacion
        if (numeroHabitacion < 1 || numeroHabitacion > numPisos * habsPorPiso) {
            System.out.println("Numero de habitacion no valido.");
            return;
        }
        
        // Verificar si la habitacion esta disponible
        int piso = (numeroHabitacion - 1) / habsPorPiso;
        int hab = (numeroHabitacion - 1) % habsPorPiso;
        
        if (!estadoHabitaciones[piso][hab].equals("Disponible")) {
            System.out.println("La habitacion seleccionada no esta disponible.");
            return;
        }
        
        System.out.print("Numero de noches: ");
        int noches = input.nextInt();
        
        System.out.print("Precio por noche: ");
        double precioNoche = input.nextDouble();
        
        // Almacenar la reserva
        nombresClientes[reservasRegistradas] = nombre;
        numerosHabitaciones[reservasRegistradas] = numeroHabitacion;
        nochesReservadas[reservasRegistradas] = noches;
        preciosPorNoche[reservasRegistradas] = precioNoche;
        reservasActivas[reservasRegistradas] = true;
        
        // Actualizar estado de la habitacion
        estadoHabitaciones[piso][hab] = "Ocupada por " + nombre;
        reservasRegistradas++;
        
        System.out.println("Reserva registrada exitosamente!");
    }
    
    // Muestra las habitaciones disponibles

    private static void mostrarHabitacionesDisponibles() {
        for (int piso = 0; piso < numPisos; piso++) {
            for (int hab = 0; hab < habsPorPiso; hab++) {
                if (estadoHabitaciones[piso][hab].equals("Disponible")) {
                    int numHab = piso * habsPorPiso + hab + 1;
                    System.out.println("Habitacion " + numHab + " - Piso " + (piso + 1));
                }
            }
        }
    }
    
    //Cancela una reserva existente

    private static void cancelarReserva() {
        System.out.println("\n--- CANCELAR RESERVA ---");
        System.out.print("Ingrese el numero de habitacion a cancelar: ");
        int numeroHabitacion = input.nextInt();
        
        // Buscar la reserva
        int indiceReserva = -1;
        for (int i = 0; i < reservasRegistradas; i++) {
            if (numerosHabitaciones[i] == numeroHabitacion && reservasActivas[i]) {
                indiceReserva = i;
                break;
            }
        }
        
        if (indiceReserva == -1) {
            System.out.println("No se encontro una reserva activa para esa habitacion.");
            return;
        }
        
        // Liberar la habitacion
        int piso = (numeroHabitacion - 1) / habsPorPiso;
        int hab = (numeroHabitacion - 1) % habsPorPiso;
        estadoHabitaciones[piso][hab] = "Disponible";
        
        // Marcar reserva como cancelada
        reservasActivas[indiceReserva] = false;
        
        System.out.println("Reserva cancelada exitosamente para la habitacion " + numeroHabitacion);
    }
    
    // Genera un reporte del estado del hotel
    private static void generarReporte() {
        System.out.println("\n--- REPORTE DEL HOTEL ---");
        
        // Mostrar estado de cada habitacion
        for (int piso = 0; piso < numPisos; piso++) {
            System.out.println("\nPiso " + (piso + 1) + ":");
            for (int hab = 0; hab < habsPorPiso; hab++) {
                int numHab = piso * habsPorPiso + hab + 1;
                System.out.println("Habitacion " + numHab + ": " + estadoHabitaciones[piso][hab]);
            }
        }
        
        // Calcular estadisticas
        int habitacionesOcupadas = 0;
        for (int i = 0; i < reservasRegistradas; i++) {
            if (reservasActivas[i]) {
                habitacionesOcupadas++;
            }
        }
        
        int totalHabitaciones = numPisos * habsPorPiso;
        int habitacionesDisponibles = totalHabitaciones - habitacionesOcupadas;
        
        System.out.println("\nResumen:");
        System.out.println("Habitaciones ocupadas: " + habitacionesOcupadas);
        System.out.println("Habitaciones disponibles: " + habitacionesDisponibles);
        System.out.println("Total habitaciones: " + totalHabitaciones);
    }
    
    // Calcula el total a pagar para una reserva especifica
    private static void calcularTotalPagar() {
        System.out.println("\n--- CALCULAR TOTAL A PAGAR ---");
        System.out.print("Ingrese el numero de habitacion: ");
        int numeroHabitacion = input.nextInt();
        
        // Buscar la reserva
        int indiceReserva = -1;
        for (int i = 0; i < reservasRegistradas; i++) {
            if (numerosHabitaciones[i] == numeroHabitacion && reservasActivas[i]) {
                indiceReserva = i;
                break;
            }
        }
        
        if (indiceReserva == -1) {
            System.out.println("No se encontro una reserva activa para esa habitacion.");
            return;
        }
        
        double total = nochesReservadas[indiceReserva] * preciosPorNoche[indiceReserva];
        
        System.out.println("\nDetalle de la reserva:");
        System.out.println("Cliente: " + nombresClientes[indiceReserva]);
        System.out.println("Habitacion: " + numerosHabitaciones[indiceReserva]);
        System.out.println("Noches reservadas: " + nochesReservadas[indiceReserva]);
        System.out.println("Precio por noche: $" + preciosPorNoche[indiceReserva]);
        System.out.println("Total a pagar: $" + total);
    }
}