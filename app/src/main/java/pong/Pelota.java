package pong;
import java.awt.Color;
import java.awt.Graphics2D;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Pelota extends ObjetoGrafico implements ObjetoGraficoMovible {
    private final int RADIO = 15;
    private int dx=5 ;
    private int dy=5 ;

    public Pelota(int x, int y){
        super(x, y);
        setX(x);
        setY(y);
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.BLUE);
		g.fillOval((int)positionX, (int)positionY, 15, 15);
    }

    public void rebotarHorizontal(){
        dx = -dx;
        aumentarVelocidad(1.15);
    }

    public void rebotarVertical() {
       dy = -dy;
       aumentarVelocidad(1.2);
    }


    public int getRadio(){
        return RADIO;
    }

    @Override
    public void moverse(double velocidad) {
        this.positionX += dx * velocidad;
        this.positionY += dy * velocidad;
    }

    public void aumentarVelocidad(double factor){
        dx *= factor;
        dy *= factor;

        double maxVel = 20;
        if (Math.abs(dx) > maxVel) dx = (int) (Math.signum(dx) * maxVel);
        if (Math.abs(dy) > maxVel) dy = (int) (Math.signum(dy) * maxVel);
    }

    @Override
    public double getX() {
       return this.positionX;
    }

    @Override
    public double getY() {
        return this.positionY;
    }

    public void setX(double x) {
        this.positionX = x;
    }

    public void setY(double y) {
        this.positionY = y;
    }

    public void reiniciarVelocidad() {
        dx = 5;
        dy = 5;
    }

    public void setDY(int nuevoDY) {
        this.dy = nuevoDY;
    }

    public int getDY() {
        return this.dy;
    }

    public int getDX() {
        return this.dx;
    }

    public int getVelocidadMax() {
        return 20; // igual que tu maxVel en aumentarVelocidad
    }
}
