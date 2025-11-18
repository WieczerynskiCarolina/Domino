package ar.edu.unlu.poo.Domino.Vista;

import ar.edu.unlu.poo.Domino.Modelo.Ficha;

import java.util.ArrayList;
import java.util.Map;
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

    public void mostrarEstadoJuego(String nombreJugador, ArrayList<Ficha> mano, String mesa, boolean pozoVacio) {
        System.out.println("\n\n\n==================================================");
        System.out.println("Turno de: " + nombreJugador);
        System.out.println("--------------------------------------------------");

        System.out.println(mesa);
        System.out.println("--------------------------------------------------");

        String estadoPozo = pozoVacio ? "VACÍO" : "Fichas disponibles";
        System.out.println("Pozo: " + estadoPozo);

        StringBuilder sbMano = new StringBuilder();
        sbMano.append("Tu mano: ");

        if (mano.isEmpty()) {
            sbMano.append("(Sin fichas)");
        } else {
            for (int i = 0; i < mano.size(); i++) {
                sbMano.append(i + 1).append(". ");
                sbMano.append(mano.get(i).toString());
                sbMano.append("  ");
            }
        }
        System.out.println(sbMano.toString().trim());
        System.out.println("==================================================");
    }

    public void mostrarFinRonda(String mensajeFinal) {
        System.out.println("\n==================================================");
        System.out.println("¡RONDA TERMINADA!");
        System.out.println(mensajeFinal);
        System.out.println("==================================================");
    }

    public void mostrarPuntajesTotales(Map<String, Integer> puntajes, int numeroRonda, int puntajeLimite){
        System.out.println("\n--- Resumen tras Ronda " + numeroRonda + " (Objetivo: " + puntajeLimite + ") ---");
        for(Map.Entry<String, Integer> entry: puntajes.entrySet()){
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " puntos");
        }
        System.out.println("-------------------------------------");
    }

    public void mostrarGanadorJuego(String nombreGanador){
        System.out.println("¡FELICIDADES " + nombreGanador.toUpperCase() + "!");
        System.out.println("¡Has ganado el juego alcanzando el límite de puntos!");
    }


    public int pedirIndiceFicha() {
        System.out.println("Elige el número de la ficha: ");
        while(!scanner.hasNextInt()){
            mostrarMensaje("Error: Debe ingresar un número.");
            scanner.next();
            System.out.println("Elige el número de la ficha: ");
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

    public void esperarEnter() {
        scanner.nextLine();
    }

    public int pedirPuntajeLimite() {
        System.out.println("\nIngrese el puntaje límite para ganar el juego (Enter para 100): ");
        String entrada = scanner.nextLine();

        if(entrada.isEmpty()){
            mostrarMensaje("Se usará el puntaje por defecto: 100.");
            return 100;
        }

        try{
            int puntaje = Integer.parseInt(entrada);
            if(puntaje <= 0){
                mostrarMensaje("Error: El puntaje debe ser positivo. Se usará 100.");
                return 100;
            }

            mostrarMensaje("Se jugará hasta " + puntaje + " puntos.");
            return puntaje;
        } catch(NumberFormatException e){
            mostrarMensaje("Error: Entrada no válida. Se usará 100.");
            return 100;
        }
    }
}
