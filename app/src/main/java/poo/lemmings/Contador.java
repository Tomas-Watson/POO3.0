package poo.lemmings;

import java.awt.*;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import org.example.ObjetoGrafico;

// Clase que lleva el conteo de lemmings y el temporizador
public class Contador extends ObjetoGrafico {
    private int contadorSalvados;
    private int contadorUsados;
    private int contadorPerdidos;
    private int tiempo;  // tiempo restante en segundos
    private DecimalFormat twoDigits = new DecimalFormat("00");

    public Contador(int x, int y, int tiempoInicial) {
        super(x, y);
        this.contadorSalvados = 0;
        this.contadorUsados = 0;
        this.contadorPerdidos = 0;
        this.tiempo = tiempoInicial;
    }

    public void incrementarSalvados() {
        this.contadorSalvados++;
    }

    
    public void incrementarUsados() {
        this.contadorUsados++;
    }

    
    public void incrementarPerdidos() {
        this.contadorPerdidos++;
    }

    public int getContadorSalvados() { return contadorSalvados; }
    public int getContadorUsados()   { return contadorUsados; }
    public int getContadorPerdidos() { return contadorPerdidos; }
    public int getTiempoRestante()   { return tiempo; }

    
    public double getPorcentajeExito() {
        if (contadorUsados == 0) return 0.0;
        return (contadorSalvados / (double)contadorUsados) * 100.0;
    }

   
    public void tick() {
        if (tiempo > 0) {
            tiempo--;
        }
    }

    // Formato tiempo en mm:ss
    private String formatTime() {
        int min = tiempo / 60;
        int sec = tiempo % 60;
        return twoDigits.format(min) + ":" + twoDigits.format(sec);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (imagen != null) {
            g2.drawImage(imagen, (int)positionX, (int)positionY, null);
        }
        // Texto en color blanco, fuente notable
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        // Mostrar cantidad de salvados
        g2.drawString("Salvados: " + contadorSalvados, (int)positionX + 10, (int)positionY + 25);
        // Mostrar cantidad de perdidos
        g2.drawString("Perdidos: " + contadorPerdidos, (int)positionX + 10, (int)positionY + 50);
        // Mostrar porcentaje de éxito formateado (ej: 75.0%)
        String porcentajeTexto = String.format("Éxito: %.1f%%", getPorcentajeExito());
        g2.drawString(porcentajeTexto, (int)positionX + 10, (int)positionY + 75);
        // Mostrar tiempo restante
        g2.drawString("Tiempo: " + formatTime(), (int)positionX + 180, (int)positionY + 25);
    }
}


    

