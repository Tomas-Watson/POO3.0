package poo.pong;


import org.example.Fondo;
import org.example.ObjetoGraficoMovible;
import org.example.ObjetoGrafico;
import java.awt.event.*; 
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.io.*;

import poo.Videojuego;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

public class JuegoPong extends JGame {

    private Fondo fondo;
    private Pelota pelota;
    private Paleta p1;
    private Paleta p2;
    private Contador contador;
    private boolean enPausa = false;
    private boolean finJuego = false;
    private boolean pPresionado = false;
    private boolean enterPresionado = false;

    final double velocidad = 150.0;

    public JuegoPong(String arg0, int arg1, int arg2) {
        super("Pong", 800, 600);
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
        //fondo = new Fondo() adentro del Fondo hay que poner el nombre de la imagen
        pelota= new Pelota(100, 100);
        p1= new Paleta();
        p2= new Paleta();
    }

    @Override
    public void gameDraw(Graphics2D g) {
        // dibujar el fondo
        fondo.display(g);
        //dibujar raquetas
        p1.display(g);
        p2.display(g);
        //dibujar pelota
        if(!finJuego){
            g.setColor(Color.WHITE);
            g.fill(pelota.getGrafico());
        }
        

        //dibujar contador
        contador.dibujar(g, fondo);
        if (enPausa) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g.getFontMetrics();
            int x = ((int) fondo.getWidth() - metrics.stringWidth("Pausa")) / 2;
            int y = (int) fondo.getHeight() / 2;
            g.drawString("Pausa", x, y);
        } else if (finJuego) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            FontMetrics metrics = g.getFontMetrics();
            String mensajeFin = contador.getJ1ptos() > contador.getJ2ptos() ? "Gana Jugador 1" : "Gana Jugador 2";
            int x = ((int) fondo.getWidth() - metrics.stringWidth(mensajeFin)) / 2;
            int y = (int) fondo.getHeight() / 2;
            g.drawString(mensajeFin, x, y);
            String reiniciarMensaje = "Presione Enter para reiniciar";
            x = ((int) fondo.getWidth() - metrics.stringWidth(reiniciarMensaje)) / 2;
            y += 50;
            g.drawString(reiniciarMensaje, x, y);
        }
    }
    

    @Override
    public void gameShutdown() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameShutdown'");
    }

   

    @Override
    public void gameUpdate(double delta) {
       if(!finJuego){
        Keyboard keyboard = this.getKeyboard();
        // Verificar si se presiona 'P' para pausar/reanudar el juego
        if(keyboard.isKeyPressed(KeyEvent.VK_P)){
            if(!pPresionado && !finJuego){
                enPausa = !enPausa;
                pPresionado = true;
            }else{
                pPresionado = false;
            }
        }
       //Verificar si se presiona 'Enter' para reiniciar el juego
       if(keyboard.isKeyPressed(KeyEvent.VK_ENTER)){
            if(!pPresionado && !finJuego){
                reiniciarJuego();
                enterPresionado = true;
            }else{
                enterPresionado = false;
            }
       }
       //movimiento de las paletas

        if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
            p1.setY(p1.getY() - velocidad * delta);
            //shipY -= NAVE_DESPLAZAMIENTO * delta;
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            //shipY += NAVE_DESPLAZAMIENTO * delta;
            p1.setY(p1.getY() + velocidad * delta);
        }

       if(!enPausa){
            p1.moverse(arg0);
       }
       // Mover la pelota
       // Colisión de la pelota con los bordes
       // Colisión de la pelota con las paletas
       //colision de la pelota en los laterales (GOL)
        }
    }

    
    public void reiniciarJuego() {
        contador.setJ1ptos(0);
        contador.setJ2ptos(0);
        finJuego = false;
        enPausa = false;
    } 
    
    public void moverse(){

    }


}
