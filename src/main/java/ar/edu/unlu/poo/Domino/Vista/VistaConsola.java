package ar.edu.unlu.poo.Domino.Vista;

import java.util.Scanner;

public class VistaConsola{
    private Scanner scanner = new Scanner(System.in);

    public void mostrarBienvenida(){
        System.out.println("=== DOMINO ===");
    }

    public void mostrarMenu(){
        System.out.println("\n¿Qué hacer?");
        System.out.println("1. Jugar");
        System.out.println("2. Pasar");
        System.out.println("Seleccione una opción: ");
    }


    public int obtenerOpcion(){
        while (!scanner.hasNextInt()) {
            mostrarMensaje("Error: Debe ingresar un NÚMERO (1 o 2).");
            scanner.next();
            mostrarMenu();
        }

        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    public int pedirCantidadJugadores(){
        System.out.println("Ingrese la cantidad de jugadores (2-4): ");

        while(!scanner.hasNextInt()){
            mostrarMensaje("Error: Debe ingresar un número");
            scanner.next();
            System.out.println("Ingrese la cantidad de jugadores (2-4): ");
        }

        int cantidad = scanner.nextInt();
        scanner.nextLine();
        return cantidad;
    }

    public String pedirNombreJugador(int numeroJugador){
        System.out.println("Ingrese el nombre del Jugador " + numeroJugador + ": ");
        return scanner.nextLine();
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }

    public void mostrarEstadoJuego(String nombreJugador, String mano, String mesa, boolean pozoVacio) {
        System.out.println("==================================================");
        System.out.println("Turno de: " + nombreJugador);
        System.out.println("--------------------------------------------------");

        System.out.println(mesa);
        System.out.println("--------------------------------------------------");

        String estadoPozo = pozoVacio ? "VACÍO" : "Fichas disponibles";
        System.out.println("Pozo: " + estadoPozo);

        System.out.println("Tu mano: " + mano);
        System.out.println("==================================================");
    }

    public void mostrarFinPartida(String mensajeFinal) {
        System.out.println("\n=====================================");
        System.out.println("¡PARTIDA TERMINADA!");
        System.out.println(mensajeFinal);
        System.out.println("=====================================");
    }


    public int pedirIndiceFicha() {
        System.out.println("Elige el índice de la ficha (0, 1, 2,...): ");
        while(!scanner.hasNextInt()){
            mostrarMensaje("Error: Debe ingresar un número.");
            scanner.next();
            System.out.println("Elige el índice de la ficha (0, 1, 2,...): ");
        }

        int idx = scanner.nextInt();
        scanner.nextLine();
        return idx;
    }

    public int pedirLado() {
        int opcionLado = 0;

        do{
            System.out.println("Elige el lado (1: Izquierda, 2: Derecha): ");

            while(!scanner.hasNextInt()){
                mostrarMensaje("Error: Debe ingresar un número");
                scanner.next();
                System.out.println("Elige el lado (1: Izquierda, 2: Derecha): ");
            }

            opcionLado = scanner.nextInt();
            scanner.nextLine();

            if(opcionLado != 1 && opcionLado != 2){
                mostrarMensaje("Error: Opción no válida. Ingrese 1 o 2.");
            }
        } while(opcionLado != 1 && opcionLado != 2);

        return opcionLado;
    }
}
