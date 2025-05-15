package poo.pong;

import java.awt.geom.Point2D;

import org.example.ObjetoGrafico; //eventos

public class Paleta extends ObjetoGrafico{
    private int ancho= 10;
    private int alto= 100;
    

    private Point2D.Double posicion  = new Point2D.Double();

    public Paleta(String filename){
        super("paleta.png");
    }

    public void setPosicion(double x, double y){
        posicion.setLocation(x,y);
    }

    public void setX(double x){
        posicion.x=x;
    }

    public void setY(double y){
        posicion.y=y;
    }

    @Override
    public double getX(){
        return posicion.getX();
    }

    @Override
    public double getY(){
        return posicion.getY();
    }
    
    public int getAlto(){
        return alto;
    }

    public int getAncho(){
        return ancho;
    }


}
