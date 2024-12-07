package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import com.example.cincuentazo.model.Player;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CincuentazoGame {

    private CincuentazoController controller;
    private ArrayList<Player> jugadores;
    protected ArrayList<Carta> mazo;
    public ArrayList<Carta> cartasEnMesa;
    protected ArrayList<Boolean> esMaquina;
    public int turnoJugador;
    protected boolean esTurnoDeMaquina;
    protected int sumaDelJuego;

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
        esMaquina = new ArrayList<Boolean>();
        mazo = new MazoController().getCartas();
        turnoJugador = 0;
        esTurnoDeMaquina = false;
        sumaDelJuego = 0;
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

        if (turnoJugador > 0 && jugadores.size() > 1) {
            esTurnoDeMaquina = true;

            PauseTransition pausa = new PauseTransition(Duration.seconds(5));
            pausa.setOnFinished(event -> {
                jugarTurnoDeLaMaquina();
            });
            pausa.play();

        } else {
            esTurnoDeMaquina = false;
            controller.cartasJugador1HBox.setDisable(false);;
            controller.actualizarInterfazDeTurno();
        }
        System.out.println("Turno del jugador: " + turnoJugador);
        System.out.println("Cartas restantes en el mazo: " + mazo.size());
        System.out.println("Cartas jugadas en la mesa: " + cartasEnMesa.size());
        actualizarSumaMesa();
    }


    /**
     *
     */
    public void jugarTurnoDeLaMaquina() {
        Player jugadorMaquina = jugadores.get(turnoJugador);
        ArrayList<Carta> manoMaquina = jugadorMaquina.getMano();

        if (!validarCartasParaJugar(jugadorMaquina)) {
            System.out.println("EL jugador no tiene cartas válidas para jugar y ha sido eliminado.");
            jugadores.remove(jugadorMaquina);

            if (jugadores.size() == 1) {
                declararGanador(jugadores.get(0));
            }

            siguienteTurno();
            return;
        }

        Random random = new Random();
        int cartaIndex = random.nextInt(manoMaquina.size());
        Carta cartaSeleccionada = manoMaquina.get(cartaIndex);

        System.out.println("La máquina ha jugado: " + cartaSeleccionada.getNombre());
        posibleJugarCarta(jugadorMaquina, cartaSeleccionada);

//        cartasEnMesa.add(cartaSeleccionada);
//        jugadorMaquina.borrarCarta(cartaSeleccionada);
//
//        controller.actualizarCartaEnMesa(cartaSeleccionada.getImagen());
//
//        if (!mazo.isEmpty()) {
//            Carta cartaDelMazo = mazo.remove(0);
//            jugadorMaquina.agregarCarta(cartaDelMazo);
//            System.out.println("La máquina ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
//        }

        siguienteTurno();
    }


    /**
     *
     * @param numJugadores
     */
    private void iniciarJuego(int numJugadores) {

        for (int i = 1; i <= numJugadores; i++) {
            boolean esMaquinaJugador = (i > 1);
            esMaquina.add(esMaquinaJugador);

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
        actualizarSumaMesa();
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
     * @return
     */
    public ArrayList<Player> getJugadores() {
        return jugadores;
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

//        cartasEnMesa.add(cartaSeleccionada);
//        jugador.borrarCarta(cartaSeleccionada);

        posibleJugarCarta(jugador, cartaSeleccionada);

        System.out.println("Cartas restantes en la mano de " + jugador.getName() + ":");
        jugador.getMano().forEach(c -> System.out.println(c.getNombre()));

    }

    /**
     *
     * @param jugadorIndex
     */
    public void tocarMazo(int jugadorIndex) {
        if (!mazo.isEmpty()) {
            Carta cartaDelMazo = mazo.remove(mazo.size() - 1);
            Player jugador = jugadores.get(jugadorIndex);
            jugador.agregarCarta(cartaDelMazo);

            controller.actualizarCartasJugador(obtenerImagenesCartasJugador(jugadorIndex));
            System.out.println("El jugador " + jugador.getName() + " ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
        }else{
            devolverCartasAlMazo();
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

    public void actualizarSumaMesa() {
        sumaDelJuego = 0;
        for (Carta carta : cartasEnMesa) {
            sumaDelJuego += carta.getValor();
        }
        System.out.println("Suma de la mesa: " + sumaDelJuego);
    }

    private boolean validarCartasParaJugar(Player jugador) {
        ArrayList<Carta> manoJugador = jugador.getMano();

        for (Carta carta : manoJugador) {
            if (sumaDelJuego + carta.getValor() <= 50) {
                return true;
            }
        }

        return false;
    }

    public void posibleJugarCarta(Player jugador, Carta cartaSeleccionada) {

        if (sumaDelJuego + cartaSeleccionada.getValor() <= 50) {

            cartasEnMesa.add(cartaSeleccionada);
            jugador.borrarCarta(cartaSeleccionada);
            controller.actualizarCartaEnMesa(cartaSeleccionada.getImagen());

            if (!validarCartasParaJugar(jugador)) {
                System.out.println(jugador.getName() + " no tiene cartas válidas para jugar y ha sido eliminado.");
                jugadores.remove(jugador);

                if (jugadores.size() == 1) {
                    declararGanador(jugadores.get(0));
                }
            }
        } else {
            System.out.println(jugador.getName() + " no puede jugar esta carta porque excede la suma de 50.");
        }
    }

    private void declararGanador(Player jugador) {
        System.out.println("¡El ganador es " + jugador.getName() + "!");
    }
}