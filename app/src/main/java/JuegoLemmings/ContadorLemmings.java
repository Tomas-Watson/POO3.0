package JuegoLemmings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.example.ObjetoGrafico;

public class ContadorLemmings extends ObjetoGrafico {
    private int totalSalvados = 0;
    private int necesarios = 0;
    private int lemmingsGenerados = 0;

    public ContadorLemmings(int posX, int posY, int ancho, int alto, int necesarios) {
        super(posX, posY, ancho, alto);
        this.positionX = posX;
        this.positionY = posY;
        this.ancho = ancho;
        this.alto = alto;
        this.necesarios = necesarios;
    }

    public void incrementar() {
        totalSalvados++;
    }

    public int getTotalSalvados() {
        return totalSalvados;
    }

    public void setNecesarios(int necesarios){
        this.necesarios = necesarios;
    }

    public void setLemmingsTotales(int lemmingsGenerados){
        this.lemmingsGenerados = lemmingsGenerados;
    }

    @Override
    public void draw(Graphics2D g){
        Color first = g.getColor();
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)positionX, (int)positionY,ancho,alto);

        Font fuente = new Font("SansSerif", Font.BOLD, 18);
        g.setFont(fuente);

        g.setColor(Color.WHITE);
        g.drawString("Salvados: " + totalSalvados + " / " + necesarios, x + 10, y + 20);
        g.drawString("Generados: " + lemmingsGenerados , x + 10, y + 40);

        g.setColor(first);
    }
}