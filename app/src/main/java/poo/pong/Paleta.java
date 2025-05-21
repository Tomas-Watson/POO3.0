package poo.pong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImageFilter;

import org.example.ObjetoGrafico; //eventos

public class Paleta extends ObjetoGrafico{
    private int ancho= 10;
    private int alto= 100;
    private Color color;

    

    public Paleta(Color color, double x, double y){
        super(x, y); // Call the appropriate ObjetoGrafico constructor
        this.color = color;
       
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



    public void draw(Graphics2D g) {
        Graphics2D g2d = this.imagen.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, ancho, alto);
        g2d.dispose();
    }


}
