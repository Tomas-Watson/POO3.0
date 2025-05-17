package poo.lemmings;

import org.example.ObjetoGrafico;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Contador extends ObjetoGrafico {
    private int contadorSalvados;
    private int contadorUsados;
    private int tiempo;
    private DecimalFormat twoDigits = new DecimalFormat("00");

    
    public Contador(int x, int y) {
        super(x, y);
        this.contadorSalvados = 0;
        this.contadorUsados = 0;
    }

    public void incrementarSalvados() {
        this.contadorSalvados++;
    }

    public void incrementarUsados() {
        this.contadorUsados++;
    }

    public int getContadorSalvados() {
        return contadorSalvados;
    }

    public int getContadorUsados() {
        return contadorUsados;
    }

        /** Llama a este método cada segundo para descontar tiempo */
    public void tick() {
        if (tiempo > 0) {
            tiempo--;
        }
    }

    /** Formatea el tiempo en mm:ss */
    private String formatTime() {
        int min = tiempo / 60;
        int sec = tiempo % 60;
        return twoDigits.format(min) + ":" + twoDigits.format(sec);
    }

    public void display(Graphics2D g2) {
        // Si tiene una imagen de fondo para el contador, se puede dibujar primero.
        if (imagen != null) {
            g2.drawImage(imagen, (int)positionX, (int)positionY, null);
        }
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        
        
        String texto = "Salvados: " + contadorSalvados;
        g2.drawString(texto, (int)positionX + 10, (int)positionY + 25);

        g2.drawString("TIME " + formatTime(), (int)positionX + 160, (int)positionY + 25);
    }
}


    

