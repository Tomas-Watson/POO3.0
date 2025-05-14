package jgame.gradle;
import jgame.gradle.ObjetoGraficoMovible;
import jgame.gradle.ObjetoGrafico;

public class Pelota implements ObjetoGraficoMovible {
    private final int RADIO = 10;
    private int dx = 5;
    private int dy = 5;

    public Pelota(int x, int y){
        this.dx = x;
        this.dy = y;
    }

    public void moverse() {
        this.dx += dx;
        this.dy += dy;
    }

    public void rebotar(){
        dx = -dx;
    }

    public int getRadio(){
        return RADIO;
    }

    @Override
    public void moverse(double delta) {
        //
    }

    @Override
    public double getX() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getX'");
    }

    @Override
    public double getY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getY'");
    }

}
