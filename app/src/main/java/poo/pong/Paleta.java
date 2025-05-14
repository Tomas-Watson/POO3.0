package poo.pong;
import jgame.gradle.ObjetoGraficoMovible;
import jgame.gradle.ObjetoGrafico;

public class Paleta extends ObjetoGrafico implements ObjetoGraficoMovible {
    private int ancho= 10;
    private int alto= 100;
    private int velocidad= 10;

    public Paleta(int ancho, int alto){
        super("paleta.png");
        this.ancho= ancho;
        this.alto= alto;
    }
   
    @Override
    public void moverse(double delta) {
         
    }
}
