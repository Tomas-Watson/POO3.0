package JuegoLemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Entrada extends ObjetoGrafico {
    //protected BufferedImage imagenEntrada;
    //protected BufferedImage spritePuertaEntrada;
    
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
                framesEntrada[i] = ImageIO.read(getClass().getResourceAsStream(
                    String.format("/Imagenes_Lemmings/Entrada%d.png", i + 1)));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar las imágenes de la entrada", e);
        }
    }

    public void update(double delta) {
        // 1) Si ya finalizó, no hacer nada
        if (animacionFinalizada) return;

        // 2) Sumar tiempo y, cuando supere el intervalo, avanzar frame
        animTimer += delta;
        if (animTimer >= animInterval) {
            animTimer = 0;
            animFrame++;

            // 3) Si llegamos al último frame, mantenemos ahí y marcamos finalizada
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



    public Personaje CrearLemming() {
        return null;
        //Lemming nuevo = new Lemming("imagenes/Lemmings_Puertas_Personajes",100.0);
        //return nuevo;
    }

    @Override
    public void draw(Graphics2D g){
        if (framesEntrada != null && framesEntrada[animFrame] != null) {
        g.drawImage(framesEntrada[animFrame], (int) positionX, (int) positionY, null);
    }
    }
    
}
