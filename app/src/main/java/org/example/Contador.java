package jgame.gradle;
import jgame.gradle.ObjetoGraficoMovible;
import jgame.gradle.ObjetoGrafico;
import java.awt.*;


public class Contador extends ObjetoGrafico{
    private int J1ptos=0;
    private int J2ptos=0;

    public Contador(int J1ptos, int J2ptos){
        
        this.J1ptos= J1ptos;
        this.J2ptos= J2ptos;
    }

    public void setJ1ptos(int J1ptos) {
        this.J1ptos = J1ptos;
    }

    public void setJ2ptos(int J2ptos) {
        this.J1ptos = J2ptos;
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

    public void dibujar(Graphics2D g, Fondo fondo) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        g.drawString("Jugador 1: " + J1ptos, 10, 20);
        g.drawString("Jugador 2: " + J2ptos, 10, 40);
    }

    public void Moatrarganador(Graphics2D g, Fondo fondo){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = g.getFontMetrics();
        String ganador = "Ganador";
        int x = (fondo.getWidth() - metrics.stringWidth(ganador)) / 2;
        int y = fondo.getHeight() / 2;
        if (J1ptos > J2ptos){
            g.drawString("Jugador 1 Gana", x, y);
        }else{
            g.drawString("Jugador 2 Gana", x, y);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics2 = g.getFontMetrics();
        int xx = (fondo.getWidth() - metrics2.stringWidth("Para reiniciar presione Enter")) / 2;
        int yy = fondo.getHeight() / 2;
        g.drawString("Para reiniciar presione Enter", xx, yy + 30);
    }
}
