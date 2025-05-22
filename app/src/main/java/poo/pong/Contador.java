package poo.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.example.Fondo;
import org.example.ObjetoGrafico;



public class Contador extends ObjetoGrafico{
    private int J1ptos=0;
    private int J2ptos=0;

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
    
    public void sumarPuntos(int jugador) {
        if (jugador == 1) {
            J1ptos++;
        } else if (jugador == 2) {
            J2ptos++;
        }
    }

    public void dibujar(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));

        FontMetrics fm = g.getFontMetrics();
        int y1 = 75; // Primer texto
        int y2 = y1 + fm.getHeight() - 70; // Segundo texto, debajo del primero

        g.drawString("Jugador 1:" + J1ptos, 10, y1);
        g.drawString("Jugador 2:" + J2ptos, 420, y2);
    }

    public void mostrarGanador(Graphics2D g, Fondo fondo) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        
        String mensajeGanador = (J1ptos > J2ptos) ? "Jugador 1 Gana" : "Jugador 2 Gana";
        FontMetrics metrics = g.getFontMetrics();
        double x = (fondo.getWidth() - metrics.stringWidth(mensajeGanador)) / 2;
        double y = fondo.getHeight() / 2;
        g.drawString(mensajeGanador,(int) x, (int)y);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics2 = g.getFontMetrics();
        String reiniciar = "Para reiniciar presione Enter";
        double xx = (fondo.getWidth() - metrics2.stringWidth(reiniciar)) / 2;
        g.drawString(reiniciar,(int) xx,(int) y + 40);
    }
}
