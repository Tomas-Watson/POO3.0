/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.example;


import java.awt.Color;  //jgame
import java.awt.Graphics2D;
import java.awt.event.KeyEvent; //eventos
import java.awt.image.BufferedImage;  //imagenes
import java.text.SimpleDateFormat; //imagenes
import java.util.Date;
import java.util.LinkedList; //Point2d

import javax.imageio.ImageIO;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

 


public class App extends JGame {
 

    Date dInit = new Date();
    Date dAhora;
    SimpleDateFormat ft = new SimpleDateFormat ("mm:ss");
    final double NAVE_DESPLAZAMIENTO=150.0;

    BufferedImage img_fondo = null;
    
    Personaje ovni=new Personaje();

    public static void main(String[] args) {

        App game = new App();
        game.run(1.0 / 60.0);
        System.exit(0);
    }



    public App() {

        super("App", 800, 600);

        System.out.println(appProperties.stringPropertyNames());

    }


       public String getGreeting() {
        return "Hello World!";
    }

    public void gameStartup() {
        System.out.println("gameStartup");
        try{
            
            FXPlayer.init();
            FXPlayer.volume = FXPlayer.Volume.LOW;
            
             
            //ovni.setImagen(ImageIO.read(getClass().getResource("imagenes/ufo.png")));
            
            img_fondo= ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/fondo.jpg"));
            
            ovni.setImagen(ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/mario.png")));
            
            ovni.setPosicion(getWidth() / 2,getHeight() / 2 );
        }
        catch(Exception e){
            System.out.println(e);
        }
       
    }

public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
         
        // Procesar teclas de direccion
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
            ovni.setY( ovni.getY() - NAVE_DESPLAZAMIENTO * delta);
            //shipY -= NAVE_DESPLAZAMIENTO * delta;
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            //shipY += NAVE_DESPLAZAMIENTO * delta;
            ovni.setY( ovni.getY() + NAVE_DESPLAZAMIENTO * delta);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
            ///shipX -= NAVE_DESPLAZAMIENTO * delta;
            ovni.setX( ovni.getX() - NAVE_DESPLAZAMIENTO * delta);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
            //shipX += NAVE_DESPLAZAMIENTO * delta;
            ovni.setX( ovni.getX() + NAVE_DESPLAZAMIENTO * delta);
        }
         

        if (keyboard.isKeyPressed(KeyEvent.VK_M)){
            FXPlayer.TEMA1.loop();
        }

        // Esc fin del juego
        LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for (KeyEvent event: keyEvents) {
            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                stop();
            }
        }


        ovni.update(delta);

    }

    public void gameDraw(Graphics2D g) {

        dAhora= new Date( );
        long dateDiff = dAhora.getTime() - dInit.getTime();
        long diffSeconds = dateDiff / 1000 % 60;
        long diffMinutes = dateDiff / (60 * 1000) % 60;

        g.drawImage(img_fondo,0,0,null);// imagen de fondo

        g.setColor(Color.black);
        g.drawString("Tiempo de Juego: "+diffMinutes+":"+diffSeconds,12,42);
        g.drawString("Tecla ESC = Fin del Juego ",592,42);

        g.setColor(Color.white);
        g.drawString("Tiempo de Juego: "+diffMinutes+":"+diffSeconds,10,40);
        g.drawString("Tecla ESC = Fin del Juego ",590,40);

        ovni.draw(g);

    }

    public void gameShutdown() {
       Log.info(getClass().getSimpleName(), "Shutting down game");
    }
}
