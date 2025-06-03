package poo.lemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Entrada extends ObjetoGrafico {
    protected BufferedImage spriteSheet;
    protected BufferedImage spritePuertaEntrada;
    public Entrada() {
        //Posicion inicial 100 y 200 
        super(100,200);

        try{
            //De esta manera extraigo los sprites desde los recursos
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream("app\\src\\main\\resources\\imagenes\\Lemmings_Puertas_Decoraciones.png"));

            //Extraer un sprite especifico, en este caso una puerta para que salgan los lemmings
            //En vez de 86 y 450 puede ser 168 y 500 
            spritePuertaEntrada = spriteSheet.getSubimage(86, 450, 82, 50);


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

    /**Genera un nuevo Lemming y lo añade al nivel.
     * No se si ponerlo aca o que el JLemming lo haga
    */
    public void SpawnLemmings(int CantLemmings) {
            for (int i = 0; i < CantLemmings; i++) {
            Lemming lemming = CrearLemming();
            //mostrarEnPantalla(lemming); // Asumiendo que el nivel tiene este método
        }
        
    }

    public Lemming CrearLemming() {
        Lemming nuevo = new Lemming("imagenes/Lemmings_Puertas_Personajes",100.0);
        return nuevo;
    }

    @Override
    public void draw(Graphics2D g){
        if(spritePuertaEntrada != null){
            g.drawImage(spritePuertaEntrada,(int)positionX, (int)positionY,null);
        }
    }
    
}
