package poo.lemmings;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;


public class Salida extends ObjetoGrafico {

    private int contadorRecibidos;


    public Salida() {
        super(100,100);

        try{
            BufferedImage imagenSalida = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/Salida.png"));
        }catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen del caldero", e);
        }
    }


    public void salvarLemming(Lemming lemming) {
       //removerLemming(lemming); 
        contadorRecibidos++;
    }

    public int getContadorRecibidos() {
        return contadorRecibidos;
    }
}
