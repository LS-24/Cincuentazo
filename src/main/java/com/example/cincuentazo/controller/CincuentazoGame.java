package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import com.example.cincuentazo.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CincuentazoGame {

    private CincuentazoController controller;
    private List<Player> jugadores;
    private ArrayList<Carta> mazo;
    public ArrayList<Carta> cartasEnMesa;
    private int turnoJugador;

    /**
     * @param controller
     * @param numJugadores
     */
    public CincuentazoGame(CincuentazoController controller, int numJugadores) {
        if (controller == null) {
            throw new IllegalArgumentException("El controlador no puede ser null");
        }
        this.controller = controller;
        jugadores = new ArrayList<>();
        cartasEnMesa = new ArrayList<>();
        mazo = new MazoController().getCartas();
        turnoJugador = 0;
        iniciarJuego(numJugadores);

        System.out.println("Juego iniciado correctamente.");
        System.out.println("Número de jugadores: " + jugadores.size());
        System.out.println("Número de cartas en el mazo: " + mazo.size());
        System.out.println("Número de cartas en la mesa: " + cartasEnMesa.size());
    }

    /**
     *
     * @param numJugadores
     */
    private void iniciarJuego(int numJugadores) {

        for (int i = 1; i <= numJugadores; i++) {
            ArrayList<Carta> mano = repartirCartas();
            jugadores.add(new Player("Jugador " + i, mano));
        }

        colocarPrimeraCartaEnMesa();

        controller.actualizarCartasJugador(obtenerImagenesCartasJugador(0));

        for (Player jugador : jugadores) {
            System.out.println("Cartas del jugador " + jugador.getName() + ":");
            for (Carta carta : jugador.getMano()) {
                System.out.println(carta.getNombre());
            }
        }
    }

    /**
     *
     */
    private void colocarPrimeraCartaEnMesa() {

        Random random = new Random();
        Carta cartaInicial = mazo.get(random.nextInt(mazo.size()));
        cartasEnMesa.add(cartaInicial);
        mazo.remove(cartaInicial);

        controller.actualizarCartaEnMesa(cartaInicial.getImagen());
        System.out.println("Carta en mesa: " + cartaInicial.getImagen());
    }

    /**
     *
     * @param jugadorIndex
     * @return
     */
    protected String[] obtenerImagenesCartasJugador(int jugadorIndex) {
        Player jugador = jugadores.get(jugadorIndex);
        ArrayList<Carta> mano = jugador.getMano();
        String[] imagenes = new String[mano.size()];

        for (int i = 0; i < mano.size(); i++) {
            imagenes[i] = mano.get(i).getImagen();
        }
        return imagenes;
    }

    /**
     *
     * @return
     */
    public ArrayList<Carta> repartirCartas() {
        ArrayList<Carta> manoJugador = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Carta carta = mazo.remove(0);
            manoJugador.add(carta);
        }
        return manoJugador;
    }

    /**
     *
     * @param cartaIndex
     */
    public void seleccionarCartaJugador(int cartaIndex) {
        Player jugador = jugadores.get(0);
        Carta cartaSeleccionada = (Carta) jugador.getMano().get(cartaIndex);
        System.out.println("El jugador seleccionado es: " + jugador.getName());
        System.out.println("La carta seleccionada es: " + cartaSeleccionada.getNombre());

        cartasEnMesa.add(cartaSeleccionada);
        jugador.borrarCarta(cartaSeleccionada);

        System.out.println("Cartas restantes en la mano de " + jugador.getName() + ":");

        controller.actualizarCartaEnMesa(cartaSeleccionada.getImagen());

        if (mazo.isEmpty()) {
            devolverCartasAlMazo();
        }
    }

    /**
     *
     * @param jugadorIndex
     */
    public void tocarMazo(int jugadorIndex) {
        if (!mazo.isEmpty()) {
            Carta cartaDelMazo = mazo.remove(0);
            Player jugador = jugadores.get(jugadorIndex);
            jugador.agregarCarta(cartaDelMazo);

            controller.actualizarCartasJugador(obtenerImagenesCartasJugador(jugadorIndex));
        }
    }

    /**
     *
     */
    private void devolverCartasAlMazo() {
        if (!cartasEnMesa.isEmpty()) {
            ArrayList<Carta> cartasParaDevolver = new ArrayList<>(cartasEnMesa);
            cartasParaDevolver.remove(cartasEnMesa.size() - 1);
            Collections.shuffle(cartasParaDevolver);

            mazo.addAll(cartasParaDevolver);
            cartasEnMesa.clear();

            Collections.shuffle(mazo);

            System.out.println("El mazo se ha rellenado.");
        }
    }
}