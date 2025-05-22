package poo.pong;

import java.awt.Graphics2D;

import org.example.ObjetoGrafico;

public class Paleta extends ObjetoGrafico{
    private int ancho= 20;
    private int alto= 110;

    public Paleta(int x, int y){
        super(x, y);
        setX(x);
        setY(y);
        System.out.println("Creo paleta 1 ");
    }

    public void display(Graphics2D g){
        g.setColor(color.RED);
        g.fillRect((int)getX(),(int)getY() ,getAncho(), getAlto());
    }

    public void setX(double x){
        this.positionX=x;
    }

    public void setY(double y){
        this.positionY=y;
    }

    @Override
    public double getX(){
        return this.positionX;
    }

    @Override
    public double getY(){
        return this.positionY;
    }
    
    public int getAlto(){
        return alto;
    }

    public int getAncho(){
        return ancho;
    }
}
