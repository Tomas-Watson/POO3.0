package poo.lemmings;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Salida extends ObjetoGrafico {

    private int contadorRecibidos;
    protected BufferedImage spriteSheet; 
    protected BufferedImage spritePuertaSalida;

    public Salida() {
        super("imagenes/Lemmings_Puertas_Decoraciones",010,200);
        try {
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings_Puertas_Decoraciones"));
            spritePuertaSalida = spriteSheet.getSubimage(1218, 0, 60, 52);
        } catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen :(",e);
        }
    }

    //Con este metodo cuento los lemmings que se salvaron
    public void salvarLemming(Lemming lemming) {
       //removerLemming(lemming); 
        contadorRecibidos++;
    }

    public int getContadorRecibidos() {
        return contadorRecibidos;
    }
}
