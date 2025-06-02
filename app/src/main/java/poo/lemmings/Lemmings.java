package poo.lemmings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;

public class Lemmings extends JGame{

    //atributos del tipo date
    Date dInit = new Date();
    Date dAhora;

    //atributos del tipo int 
    private final static int ANCHO_PANTALLA = 800;
    private final static int ALTO_PANTALLA = 600;

    //Atributos del tipo 
    private final boolean finJuego = false;
    private boolean enPausa = false;
    private boolean pPresionado = false;

    //Atributos de otras clase 
    private Terreno terreno;
    
    //Atributos del tipo imagen
    private BufferedImage imgFondo = null;

    public Lemmings() {
        super("Lemmings",ANCHO_PANTALLA,ALTO_PANTALLA);
    }

    @Override
    public void gameDraw(Graphics2D g) {

        //Dibujar el fondo del juego
        g.setColor(Color.BLUE);
        g.fillRect(0,0,ANCHO_PANTALLA,ALTO_PANTALLA);

        g.drawImage(imgFondo,320,240,null);
        g.setColor(Color.red);
        g.drawString("Hola",320,240);
    }

    @Override
    public void gameStartup() {

        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
        //terreno = new Terreno(50,50);
        //terreno.cargarImagen("Imagenes_Lemmings/grass01.png");
        try {
            imgFondo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Imagenes_Lemmings/grass01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gameUpdate(double arg0) {
        if(!finJuego){
            Mouse mouse = this.getMouse();

            Keyboard keyboard = this.getKeyboard();

            // Pausar/reanudar el juego con 'P'
            if (keyboard.isKeyPressed(KeyEvent.VK_P)) {
                if (!pPresionado) {
                    enPausa = !enPausa;
                    pPresionado = true;
                }
            } else {
                pPresionado = false;
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)){
                System.exit(0);
            }


            
        }
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }
}
