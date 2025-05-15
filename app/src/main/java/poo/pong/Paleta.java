package poo.pong;

import java.awt.geom.Point2D;

import org.example.ObjetoGrafico; //eventos

public class Paleta extends ObjetoGrafico{
    private int ancho= 10;
    private int alto= 100;
    private int velocidad= 10;
    private Keyboard keyboard;

    public Paleta(double x, double y, Color color, Keyboard keyboard){
        super("paleta.png");
        this.positionX = x;
        this.positionY = y;
        this.setHeight(100);
        this.setWidth();
        this.imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = this.imagen.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, ancho, alto);
        g2d.dispose();
        this.keyboard = keyboard;
    }
   
    @Override
    public void moverse(double delta) {
        if(keyboard.isKeyPressed(KeyEvent.VK_UP)){
            this.positionY -= velocidad * delta;
        }else if(keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            this.positionY += velocidad * delta;
        }

    

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