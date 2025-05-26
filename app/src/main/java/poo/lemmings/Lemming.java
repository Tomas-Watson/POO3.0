package poo.lemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Lemming extends ObjetoGrafico implements ObjetoGraficoMovible {

    private boolean vida;
    private double velocidad;
    private int direccion = 1; // +1 = derecha, -1 = izquierda

    private BufferedImage spriteSheet;
    private BufferedImage[] walkLemming;
    private int currentFrame = 0;
    private double frameTimer = 0;
    private double frameDelay = 0.1; // segundos por frame

    private int frameWidth = 10;
    private int frameHeight = 10;

    private double x, y;

    private Habilidad habilidadActual;

    public Lemming(String filename, double velocidadInicial) {
        super(filename, 0, 0); // posición inicial
        this.vida = true;
        this.velocidad = velocidadInicial;
        this.x = 0;
        this.y = 0;

        try {
            spriteSheet = ImageIO.read(new File(filename));

            int cols = spriteSheet.getWidth() / frameWidth;
            walkLemming = new BufferedImage[cols];

            for (int i = 0; i < cols; i++) {
                walkLemming[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setHabilidad(Habilidad h) {
        this.habilidadActual = h;
    }

    public void morir() {
        if (habilidadActual != null) {
            habilidadActual.suicidar();
        }
        this.vida = false;
    }

    @Override
    public void moverse(double delta) {
        x += velocidad * direccion * delta;

        // Actualizar animación
        frameTimer += delta;
        if (frameTimer >= frameDelay) {
            frameTimer -= frameDelay;
            currentFrame = (currentFrame + 1) % walkLemming.length;
        }

        // Activar habilidad si existe
        if (habilidadActual != null) {
            habilidadActual.activar(null);
        }

        // Chequear vida
        if (!vida) {
            morir();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage frame = walkLemming[currentFrame];
        int fw = frame.getWidth();
        int fh = frame.getHeight();

        if (direccion >= 0) {
            // Normal (derecha)
            g2.drawImage(frame, (int) x, (int) y, fw, fh, null);
        } else {
            // Reflejado horizontalmente (izquierda)
            g2.drawImage(frame, (int) x + fw, (int) y, -fw, fh, null);
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void girar() {
        direccion *= -1;
    }

    public boolean estaVivo() {
        return vida;
    }
}
