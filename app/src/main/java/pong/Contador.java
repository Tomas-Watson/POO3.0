package pong;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.example.ObjetoGrafico;


public class Contador extends ObjetoGrafico{
    private int J1ptos=0;
    private int J2ptos=0;
    private static final int puntaje_ganador = 5;
    private String ganador = null;

    public Contador(int x,int y){
        super(x,y);
        setJ1ptos(x);
        setJ2ptos(y);
    }

    public void setJ1ptos(int J1ptos) {
        this.J1ptos = J1ptos;
    }

    public void setJ2ptos(int J2ptos) {
        this.J2ptos = J2ptos;
    }

    public int getJ1ptos() {
        return this.J1ptos;
    }

    public int getJ2ptos() {
        return this.J2ptos;
    }

    public void sumarPuntoJugador1() {
        J1ptos++;
        if (J1ptos >= puntaje_ganador) {
            ganador = "Jugador 1";
        }
    }
    public void sumarPuntoJugador2() {
        J2ptos++;
        if (J2ptos >= puntaje_ganador) {
            ganador = "Jugador 2";
        }
    }

    public void dibujar(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 40));

        int y1 = 75; // Primer texto
        int y2 = 75; // Segundo Texto

        g.drawString(" " + J1ptos, 150, y1);
        g.drawString(" " + J2ptos, 600, y2);
    }

    public String getGanador() {
        return ganador;
    }
}
