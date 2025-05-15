package poo.pong;
import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Pelota extends ObjetoGrafico implements ObjetoGraficoMovible {
    private final int RADIO = 10;
    private int dx=5 ;
    private int dy=5 ;

    public Pelota(String filename, int x, int y){
        super(filename);
        this.positionX = x;
        this.positionY = y;
    }

    public void rebotar(){
        dx = -dx;
    }

    public int getRadio(){
        return RADIO;
    }

    @Override
    public void moverse(double delta) {
        this.positionX += dx * delta;
        this.positionY += dy * delta;
    }

    @Override
    public double getX() {
       return this.positionX;
    }

    @Override
    public double getY() {
        return this.positionY;
    }

}
