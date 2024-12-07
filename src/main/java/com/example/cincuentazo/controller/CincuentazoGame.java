package com.example.cincuentazo.controller;

import com.example.cincuentazo.model.Carta;
import com.example.cincuentazo.model.Player;
import com.example.cincuentazo.model.alert.AlertBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
     * Cambia el turno al jugador siguiente
     */
    public void siguienteTurno() {
        turnoJugador = (turnoJugador + 1) % jugadores.size();

        if (turnoJugador > 0 && jugadores.size() > 1 && esMaquina.get(turnoJugador)) {
            esTurnoDeMaquina = true;

            PauseTransition pausa = new PauseTransition(Duration.seconds(2 + (int)(Math.random() * 2)));
            pausa.setOnFinished(event -> {
                jugarTurnoDeLaMaquina();
            });
            pausa.play();

        } else {
            esTurnoDeMaquina = false;
            controller.cartasJugador1HBox.setDisable(false);
            controller.mazoImageView.setDisable(false);
            controller.actualizarInterfazDeTurno();
        }
        System.out.println("Turno del jugador: " + turnoJugador);
        System.out.println("Cartas restantes en el mazo: " + mazo.size());
        System.out.println("Cartas jugadas en la mesa: " + cartasEnMesa.size());
        actualizarSumaMesa();
    }

    /**
     * Gestiona el turno de la maquina
     */
    public void jugarTurnoDeLaMaquina() {
        Player jugadorMaquina = jugadores.get(turnoJugador);
        ArrayList<Carta> manoMaquina = jugadorMaquina.getMano();

        if (!validarCartasParaJugar(jugadorMaquina)) {
            System.out.println("EL jugador no tiene cartas válidas para jugar y ha sido eliminado.");
            jugadores.remove(jugadorMaquina);

            verificarGanador();

            siguienteTurno();
            return;
        }

        Random random = new Random();
        int cartaIndex = random.nextInt(manoMaquina.size());
        Carta cartaSeleccionada = manoMaquina.get(cartaIndex);

        while (sumaDelJuego + cartaSeleccionada.getValor() > 50) {
            System.out.println("La máquina no puede jugar la carta " + cartaSeleccionada.getNombre() + " porque excede la suma de 50.");
            cartaIndex = random.nextInt(manoMaquina.size());
            cartaSeleccionada = manoMaquina.get(cartaIndex);
        }

        System.out.println("La máquina ha jugado: " + cartaSeleccionada.getNombre());
        posibleJugarCarta(jugadorMaquina, cartaSeleccionada);

        if (jugadorMaquina.getMano().size() < 4 && !mazo.isEmpty()) {
            Carta cartaDelMazo = mazo.remove(mazo.size() - 1);
            jugadorMaquina.agregarCarta(cartaDelMazo);

            System.out.println("La máquina ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
        }

        if (mazo.isEmpty()) {
            devolverCartasAlMazo();
        }

        siguienteTurno();
    }

    /**
     * Metodo para que inicie el juego
     * @param numJugadores
     */
    private void iniciarJuego(int numJugadores) {

        controller.cartasJugador1HBox.setVisible(false);
        controller.cartasJugador2HBox.setVisible(false);
        controller.cartasJugador3VBox.setVisible(false);
        controller.cartasJugador4VBox.setVisible(false);

        for (int i = 1; i <= numJugadores; i++) {
            boolean esMaquinaJugador = (i > 1);
            esMaquina.add(esMaquinaJugador);

            ArrayList<Carta> mano = repartirCartas();
            jugadores.add(new Player("Jugador " + i, mano));
        }

        actualizarVisibilidadJugadores();

        colocarPrimeraCartaEnMesa();

        controller.actualizarCartasJugador(obtenerImagenesCartasJugador(0));
    }

    /**
     * Muestra visualmente los jugadores
     */
    protected void actualizarVisibilidadJugadores() {

        switch (jugadores.size()) {
            case 1:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(false);
                controller.cartasJugador3VBox.setVisible(false);
                controller.cartasJugador4VBox.setVisible(false);
                break;
            case 2:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(true);
                controller.cartasJugador3VBox.setVisible(false);
                controller.cartasJugador4VBox.setVisible(false);
                break;
            case 3:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(true);
                controller.cartasJugador3VBox.setVisible(true);
                controller.cartasJugador4VBox.setVisible(false);
                break;
            case 4:
                controller.cartasJugador1HBox.setVisible(true);
                controller.cartasJugador2HBox.setVisible(true);
                controller.cartasJugador3VBox.setVisible(true);
                controller.cartasJugador4VBox.setVisible(true);
                break;
            default:
                break;
        }
    }

    /**
     *  Oculta visualmente los jugadores eliminados
     * @param jugador
     */
    private void ocultarContenedorDeJugador(Player jugador) {
        int index = jugadores.indexOf(jugador);

        switch (index) {
            case 0:
                controller.cartasJugador1HBox.setVisible(false);
                break;
            case 1:
                controller.cartasJugador2HBox.setVisible(false);
                break;
            case 2:
                controller.cartasJugador3VBox.setVisible(false);
                break;
            case 3:
                controller.cartasJugador4VBox.setVisible(false);
                break;
            default:
                break;
        }
    }

    /**
     * Coloca la carta inicial
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
     * Obtiene las imagenes de las cartas del jugador
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
     * Reparte las cartas
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
     * Metodo para que el jugador seleccione una carta
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

        if (sumaDelJuego + cartaSeleccionada.getValor() > 50) {
            new AlertBox().showAlert("ERROR", "no puede jugar esta carta porque excede la suma de 50.");
            controller.mazoImageView.setDisable(true);
        }

        posibleJugarCarta(jugador, cartaSeleccionada);

        System.out.println("Cartas restantes en la mano de " + jugador.getName() + ":");
        jugador.getMano().forEach(c -> System.out.println(c.getNombre()));

    }

    /**
     * permite al jugador tomar una carta del mazo
     * @param jugadorIndex
     */
    public void tocarMazo(int jugadorIndex) {
        Player jugador = jugadores.get(jugadorIndex);

        if (jugador.getMano().size() < 4) {
            if (!mazo.isEmpty()) {
                Carta cartaDelMazo = mazo.remove(mazo.size() - 1);
                jugador.agregarCarta(cartaDelMazo);

                controller.actualizarCartasJugador(obtenerImagenesCartasJugador(jugadorIndex));
                System.out.println("El jugador " + jugador.getName() + " ha tomado una carta del mazo: " + cartaDelMazo.getNombre());
            } else {
                devolverCartasAlMazo();
            }
        } else {
            System.out.println("El jugador " + jugador.getName() + " no puede tomar del mazo porque ya tiene 4 cartas.");
        }
    }

    /**
     * Regresa las cartas al mazo cuando se queda vacio.
     */
    private void devolverCartasAlMazo() {
        if (!cartasEnMesa.isEmpty()) {
            ArrayList<Carta> cartasParaDevolver = new ArrayList<>(cartasEnMesa);
            Carta ultimaCarta = cartasParaDevolver.remove(cartasParaDevolver.size() - 1);

            mazo.addAll(cartasParaDevolver);
            cartasEnMesa.clear();

            cartasEnMesa.add(ultimaCarta);

            Collections.shuffle(mazo);

            System.out.println("El mazo se ha rellenado.");
        }
    }

    /**
     *  Actualiza la suma de las cartas
     */
    public void actualizarSumaMesa() {
        sumaDelJuego = 0;
        for (Carta carta : cartasEnMesa) {
            sumaDelJuego += carta.getValor();
        }
        System.out.println("Suma de la mesa: " + sumaDelJuego);
    }

    /**
     *  Verifica si el jugador tiene cartas que pueda jugar
      * @param jugador
     * @return
     */
    private boolean validarCartasParaJugar(Player jugador) {
        ArrayList<Carta> manoJugador = jugador.getMano();

        for (Carta carta : manoJugador) {
            if (sumaDelJuego + carta.getValor() <= 50) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Verifica si se puede jugar una carta
     * @param jugador
     * @param cartaSeleccionada
     */
    public void posibleJugarCarta(Player jugador, Carta cartaSeleccionada) {
        if (sumaDelJuego + cartaSeleccionada.getValor() <= 50) {

            cartasEnMesa.add(cartaSeleccionada);
            jugador.borrarCarta(cartaSeleccionada);
            controller.actualizarCartaEnMesa(cartaSeleccionada.getImagen());
            actualizarSumaMesa();

            if (!validarCartasParaJugar(jugador)) {
                System.out.println(jugador.getName() + " no tiene cartas válidas para jugar y ha sido eliminado.");
                jugadores.remove(jugador);

                ocultarContenedorDeJugador(jugador);

                verificarGanador();
            }
        } else {
            System.out.println(jugador.getName() + " no puede jugar esta carta porque excede la suma de 50.");
        }

    }

    /**
     *  Verifica si hay un ganador
     */
    private void verificarGanador() {
        if (jugadores.size() == 1) {
            Player ganador = jugadores.get(0);
            declararGanador(ganador);
        }
    }

    /**
     *  Indica al ganador
     * @param jugador
     */
    private void declararGanador(Player jugador) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ganador");
            alert.setHeaderText("¡Felicidades!");
            alert.setContentText("¡El jugador ha ganado "+ jugador.getName() +"!");

            alert.showAndWait();
        });
    }
}