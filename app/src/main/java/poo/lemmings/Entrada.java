package poo.lemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Entrada extends ObjetoGrafico {
    
    private BufferedImage imagenEntrada;
    public Entrada() {
        //Posicion inicial 100 y 200 
        super(100,100);

        try{
            imagenEntrada = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/Entrada.png"));
        }catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
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

    /**
     * Genera nuevos Lemmings cada 2 segundos y los añade al nivel.
     * Este método inicia un hilo que crea los lemmings con el intervalo deseado.
     */
    public void spawnLemmings(int cantLemmings) {
        new Thread(() -> {
            for (int i = 0; i < cantLemmings; i++) {
                Lemming lemming = crearLemming();
                // mostrarEnPantalla(lemming); // Asumiendo que el nivel tiene este método
                try {
                    Thread.sleep(2000); // Espera 2 segundos antes de crear el siguiente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    public Lemming crearLemming() {
        // Ajusta los argumentos según el constructor definido en la clase Lemming
        return Lemming lemming = new Lemming("imagenes/Lemmings_Puertas_Personajes");
    }

    @Override
    public void draw(Graphics2D g){
        if(imagenEntrada != null){
            g.drawImage(imagenEntrada,(int)positionX, (int)positionY,null);
        }
    }
    
}
