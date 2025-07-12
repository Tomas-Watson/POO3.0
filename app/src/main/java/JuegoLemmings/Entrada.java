package JuegoLemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Entrada extends ObjetoGrafico {
    
    private BufferedImage[] framesEntrada;
    private int animFrame = 0;
    private double animTimer = 0.0;
    private final double animInterval = 0.1; 
    private boolean animacionFinalizada = false;

    public Entrada() {
        //Posicion inicial 100 y 200 
        super(100,200);

        try {
            int totalFrames = 10; // Cambia si tienes más o menos imágenes
            framesEntrada = new BufferedImage[totalFrames];

            for (int i = 0; i < totalFrames; i++) {
                framesEntrada[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/Imagenes_Lemmings/Entrada%d.png", i + 1)));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar las imágenes de la entrada", e);
        }
    }

    public void update(double delta) {
        //Si ya finalizó, no hacer nada
        if (animacionFinalizada) return;

        //Sumar tiempo y, cuando supere el intervalo, avanzar frame
        animTimer += delta;//Aumenta segun el delta del gameUpdate
        if (animTimer >= animInterval) {
            animTimer = 0;//reinicio el acumulador
            animFrame++; //avanzo de frame

            //Si llegamos al último frame, mantenemos ahí y marcamos finalizada
            if (animFrame >= framesEntrada.length) {
                animFrame = framesEntrada.length - 1;
                animacionFinalizada = true;
            }
        }
    }

    public void setPosition(double x, double y) {
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
    public void draw(Graphics2D g){
        if (framesEntrada != null && framesEntrada[animFrame] != null) {
            g.drawImage(framesEntrada[animFrame], (int) positionX, (int) positionY, null);
        }
    }
    
}
