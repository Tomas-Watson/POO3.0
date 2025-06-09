package JuegoLemmings;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;
    
public class BarraHabilidad extends ObjetoGrafico {
    private final int iconCount;
    private final BufferedImage[] iconos;
    private final Rectangle[] botones;
    private int seleccion = -1;

    private static final int MARGEN_X = 10;
    private static final int ESPACIADO = 5;
    private static final int MARGEN_Y = 10;

    public BarraHabilidad(int pantallaWidth, int pantallaHeight) {
        // Llama explícitamente al constructor de ObjetoGrafico
        super(pantallaHeight, pantallaHeight); 
        this.iconCount = Habilidad.Tipo.values().length;
        iconos = new BufferedImage[iconCount];
        botones = new Rectangle[iconCount];
        // Carga cada icono y define su rectángulo
        try{
            for (int i = 0; i < iconCount; i++) {
                iconos[i] = ImageIO.read(getClass().getResourceAsStream( String.format("/Imagenes_Lemmings/BOTONES%d.png", i + 1) ));
                int w = iconos[i].getWidth();
                int h = iconos[i].getHeight();
                int x = MARGEN_X + i * (w + ESPACIADO);
                int y = pantallaHeight - h - MARGEN_Y;
                botones[i] = new Rectangle(x, y, w, h);
            }
        }catch (IOException e) {
            throw new RuntimeException("Error al cargar los iconos de habilidades", e);
            
            
        }
    }

    /**
     * Dibuja todos los iconos y resalta el seleccionado
     */
    public void draw(Graphics2D g) {
        for (int i = 0; i < iconCount; i++) {
            g.drawImage(iconos[i], botones[i].x, botones[i].y, null);
            if (i == seleccion) {
                g.setColor(Color.YELLOW);
                g.draw(botones[i]);
            }
        }
    }

    
    public int manejarClic(int mouseX, int mouseY) {
        for (int i = 0; i < iconCount; i++) {
            if (botones[i].contains(mouseX, mouseY)) {
                seleccion = i;
                return i;
            }
        }
        return -1;
    }

    
    public int getSeleccion() {
        return seleccion;
    }
}



