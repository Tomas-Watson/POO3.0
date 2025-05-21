package poo.lemmings;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Terreno extends ObjetoGrafico {
    private BufferedImage terrenoImg; // Imagen editable del terreno
    private boolean obstaculo;
    //Hay que hacer una clase relieve
    private final List<Relieve> relieves;

   public Terreno(String filename) {
        super(filename);
        try {
            BufferedImage mapa = ImageIO.read(new File(filename));
            // Asegura canal alfa para poder borrar píxeles
            terrenoImg = new BufferedImage(mapa.getWidth(), mapa.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = terrenoImg.createGraphics();
            g.drawImage(mapa, 0, 0, null);
            g.dispose();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Marca el terreno como destruido (sin obstáculo).
     */
    public void destruccion() {
        this.obstaculo = false;
        this.relieves.clear();
        
    }

    // Agrega un relieve (detalle visual) al terreno.
    public void agregarRelieve(Relieve relieve) {
        this.relieves.add(relieve);
        
    }

    /* Quita un relieve del terreno.
     */
    public void sacarRelieve(Relieve relieve) {
        this.relieves.remove(relieve);
      
    }

    // Método para excavar un círculo en el terreno (por ejemplo, cuando un Lemming excava)
    public void excavar(int cx, int cy, int radio) {
        Graphics2D g2 = terrenoImg.createGraphics();
        g2.setComposite(AlphaComposite.Clear); // Modo borrador
        g2.fillOval(cx - radio, cy - radio, radio * 2, radio * 2);
        g2.dispose();
    }

    // Dibuja el terreno en pantalla
    @Override
    public void display(Graphics2D g) {
        g.drawImage(terrenoImg, 0, 0, null);
    }

    /*@Override
    public void draw(GraphicsContext gc) {
        // Dibuja base del terreno
        super.draw(gc);
        // Si es obstáculo, pinta zona bloqueada
        if (obstaculo) {
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        // Dibuja cada relieve encima
        for (Relieve r : relieves) {
            r.draw(gc);
        }
    }

    private void actualizarGrafico() {
        // Recarga o invalida el sprite/textura para refrescar la vista
        // implementación dependiente de ObjetoGrafico
    }
    
    ESTOS METODOS VAN EN VLemmings*/

    
}
