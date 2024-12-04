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
    protected boolean esTurnoDeMaquina;

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
        esTurnoDeMaquina = false;
        iniciarJuego(numJugadores);

        System.out.println("Juego iniciado correctamente.");
        System.out.println("Número de jugadores: " + jugadores.size());
        System.out.println("Número de cartas en el mazo: " + mazo.size());
        System.out.println("Número de cartas en la mesa: " + cartasEnMesa.size());
    }

    /**
     *
     */
    public void siguienteTurno() {

        turnoJugador = (turnoJugador + 1) % jugadores.size();

        if (turnoJugador == 1) {
            esTurnoDeMaquina = true;
            jugarTurnoDeLaMaquina();
        } else {
            esTurnoDeMaquina = false;
            controller.actualizarInterfazDeTurno();
        }
    }

    /**
     *
     */
    public void jugarTurnoDeLaMaquina() {
        Player jugadorMaquina = jugadores.get(1);
        ArrayList<Carta> manoMaquina = jugadorMaquina.getMano();

        Random random = new Random();
        int cartaIndex = random.nextInt(manoMaquina.size());

        Carta cartaSeleccionada = manoMaquina.get(cartaIndex);
        System.out.println("La máquina ha jugado: " + cartaSeleccionada.getNombre());

        cartasEnMesa.add(cartaSeleccionada);
        jugadorMaquina.borrarCarta(cartaSeleccionada);

        controller.actualizarCartaEnMesa(cartaSeleccionada.getImagen());

        if (!mazo.isEmpty()) {
            Carta cartaDelMazo = mazo.remove(0);
            jugadorMaquina.agregarCarta(cartaDelMazo);
            System.out.println("La máquina ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
        }

        siguienteTurno();
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
        if (cartaIndex < 0 || cartaIndex >= jugadores.get(0).getMano().size()) {
            System.out.println("Índice de carta inválido: " + cartaIndex);
            return;
        }

        Player jugador = jugadores.get(turnoJugador);
        Carta cartaSeleccionada = jugador.getMano().get(cartaIndex);
        System.out.println("El jugador " + jugador.getName() + " ha jugado: " + cartaSeleccionada.getNombre());

        System.out.println("Jugador seleccionado: " + jugador.getName());
        System.out.println("Carta seleccionada: " + cartaSeleccionada.getNombre());

        cartasEnMesa.add(cartaSeleccionada);
        jugador.borrarCarta(cartaSeleccionada);

        System.out.println("Cartas restantes en la mano de " + jugador.getName() + ":");
        jugador.getMano().forEach(c -> System.out.println(c.getNombre()));

        //controller.actualizarCartaEnMesa(cartaSeleccionada.getImagen());

        if (!mazo.isEmpty()) {
            Carta cartaDelMazo = mazo.remove(0);
            jugador.agregarCarta(cartaDelMazo);
            System.out.println("El jugador " + jugador.getName() + " ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
        }

        siguienteTurno();
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
            System.out.println("El jugador " + jugador.getName() + " ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
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