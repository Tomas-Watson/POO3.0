package poo.lemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Lemming extends ObjetoGrafico implements ObjetoGraficoMovible{
   
    private boolean vida;
    private double velocidad;          
    private int direccion = 1; 
    // +1 = derecha, -1 = izquierda
    
    //Para la imagen del personaje
    private BufferedImage spriteSheet;
    private BufferedImage[] walkLemming;
    private int currentFrame = 0;
    private int x = 0;  // Posición horizontal del Lemming

    private int frameWidth = 10, frameHeight = 10;  // Tamaño aproximado de cada fotograma

    private Habilidad habilidadActual;

 
    public Lemming(String Filename, double velocidad) {
        super(Filename, 0,0);
        this.vida = true;
        this.velocidad = velocidad;
        try {
            // Cargo la hoja de sprites 
            spriteSheet = ImageIO.read(new File("Amiga Amiga CD32 - Lemmings - Lemming.png"));
            // Calculo el nro de frames en la primera fila
            int cols = spriteSheet.getWidth() / frameWidth;
            walkLemming = new BufferedImage[cols];
            // Extraer cada frame de la primera fila de la imagen
            for (int i = 0; i < cols; i++) {
                walkLemming[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Asigna una nueva habilidad al lemming 
    public void setHabilidad(Habilidad h) {
        this.habilidadActual = h;
    }

    /** Mata al lemming antes de tiempo llamando a suicidar() */
    public void morir() {
        if (habilidadActual != null) {
            habilidadActual.suicidar();
        }
        this.vida = false;
    }

    
    @Override
    public void moverse(double delta) {
        double dx = velocidad * delta * direccion;
        this.positionX += dx;

        
        if (habilidadActual != null) {
            habilidadActual.activar(null);
        }

        
        if (!vida) {
            morir();
        }
    }

    /** Getter X de la interface */
    @Override
    public double getX() {
        return super.getX();
    }

    /** Getter Y de la interface */
    @Override
    public double getY() {
        return super.getY();
    }

    /** Dibuja el lemming y opcionalmente info de debug */
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        // Ejemplo: dibujar la vida restante sobre el sprite
        g2.setColor(java.awt.Color.YELLOW);
        g2.drawString("♥" + vida, (int)getX(), (int)getY() - 5);
    }

    /** Cambia de dirección (cuando choca con un muro, por ejemplo) */
    public void girar() {
        direccion *= -1;
    }

    public boolean estaVivo() {
        return vida;
    }
        
}
