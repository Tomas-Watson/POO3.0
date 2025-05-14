package poo.pong;


import org.example.Fondo;
import org.example.ObjetoGraficoMovible;
import org.example.ObjetoGrafico;
import java.awt.event.*; 
import java.util.*;
import java.text.*;
import java.awt.*;
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

    public JuegoPong(String arg0, int arg1, int arg2) {
        super("Pong", 800, 600);
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
        //fondo = new Fondo() adentro del Fondo hay que poner el nombre de la imagen
        pelota= new Pelota(100, 100);
        p1= new Paleta(10, 100);
        p2= new Paleta(10, 100);
    }

    @Override
    public void gameDraw(Graphics2D g) {
        // dibujar el fondo
        fondo.display(g);
        //dibujar raquetas
        //p1.display(g);
        //p2.display(g);
        //dibujar pelota
        /*if(!finJuego){
            g.setColor(Color.WHITE);
            g.fill(pelota.getGrafico());
        }
        */

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
    public void gameUpdate(double arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameUpdate'");
    }

    




}
