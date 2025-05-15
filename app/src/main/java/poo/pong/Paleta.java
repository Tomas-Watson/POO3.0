package poo.pong;
import org.example.ObjetoGraficoMovible;
import org.example.ObjetoGrafico;

import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;


import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

public class Paleta extends ObjetoGrafico implements ObjetoGraficoMovible {
    private int ancho= 10;
    private int alto= 100;
    private int velocidad= 10;
    private Keyboard keyboard;

    public Paleta(double x, double y, Color color, Keyboard keyboard){
        super("paleta.png");
        this.positionX = x;
        this.positionY = y;
        this.setHeight(100);
        this.setWidth();
        this.imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = this.imagen.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, ancho, alto);
        g2d.dispose();
        this.keyboard = keyboard;
    }
   
    @Override
    public void moverse(double delta) {
        if(keyboard.isKeyPressed(KeyEvent.VK_UP)){
            this.positionY -= velocidad * delta;
        }else if(keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            this.positionY += velocidad * delta;
        }
    }
}
