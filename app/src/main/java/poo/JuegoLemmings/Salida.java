package JuegoLemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;


public class Salida extends ObjetoGrafico {

    private int contadorRecibidos  = 0;
    private BufferedImage imagenSalida;


    //Personaje lemming;

    public Salida() {
        super(100,100);

        try{
            imagenSalida = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/Salida2.PNG"));
        }catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
        
    }

    public void salvarLemming(Personaje lemming) { 
        contadorRecibidos++;
    }

    public int getContadorRecibidos() {
        return contadorRecibidos;
    }

    public void resetContador(){
        contadorRecibidos = 0;
    }

    @Override
    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public double getX() {
        return this.positionX;
    }

    @Override
    public double getY() {
        return this.positionY;
    }

    @Override
    public void draw(Graphics2D g) {
        if (imagenSalida != null) {
            g.drawImage(imagenSalida, (int) positionX, (int) positionY, null);
        }
    }
}