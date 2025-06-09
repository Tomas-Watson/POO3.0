package JuegoLemmings;

import java.awt.Color;
import java.awt.Graphics2D;

public class Temporizador {
    private double tiempoRestante; // en segundos

    public Temporizador(double tiempoInicialSegundos) {
        this.tiempoRestante = tiempoInicialSegundos;
    }

    public void actualizar(double delta) {
        if (tiempoRestante > 0) {
            tiempoRestante -= delta;
        }
    }

    public int getMinutos() {
        return (int)(tiempoRestante / 60);
    }

    public int getSegundos() {
        return (int)(tiempoRestante % 60);
    }

    public boolean termino() {
        return tiempoRestante <= 0;
    }

    public void dibujar(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawString("Tiempo: " + getMinutos() + ":" + String.format("%02d", getSegundos()), 576, 550);
    }
}
