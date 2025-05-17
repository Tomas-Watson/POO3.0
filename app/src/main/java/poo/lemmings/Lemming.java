package poo.lemmings;

import java.awt.Graphics2D;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Lemming extends ObjetoGrafico implements ObjetoGraficoMovible{
   
    private boolean vida;
    private double velocidad;          
    private int direccion = 1; 
    // +1 = derecha, -1 = izquierda        


    private Habilidad habilidadActual;

 
    public Lemming(String spriteFilename, double x, double y, double velocidad) {
        super(spriteFilename);
        setPosition((int)x, (int)y);
        this.velocidad = velocidad;
        this.vida = true;
        this.habilidadActual = null;  
    }

    //Asigna una nueva habilidad al lemming 
    public void setHabilidad(Habilidad h) {
        this.habilidadActual = h;
    }

    /** Mata al lemming antes de tiempo llamando a suicidar() */
    public void morir() {
        if (habilidadActual != null) {
            habilidadActual.suicidar();
        }
        this.vida = false;
    }

    
    @Override
    public void moverse(double delta) {
        double dx = velocidad * delta * direccion;
        this.positionX += dx;

        
        if (habilidadActual != null) {
            habilidadActual.activar();
        }

        
        if (!vida) {
            morir();
        }
    }

    /** Getter X de la interface */
    @Override
    public double getX() {
        return super.getX();
    }

    /** Getter Y de la interface */
    @Override
    public double getY() {
        return super.getY();
    }

    /** Dibuja el lemming y opcionalmente info de debug */
    @Override
    public void display(Graphics2D g2) {
        super.display(g2);

        // Ejemplo: dibujar la vida restante sobre el sprite
        g2.setColor(java.awt.Color.YELLOW);
        g2.drawString("♥ " + vida, (int)getX(), (int)getY() - 5);
    }

    /** Cambia de dirección (cuando choca con un muro, por ejemplo) */
    public void girar() {
        direccion *= -1;
    }

    public boolean estaVivo() {
        return vida;
    }
        
}
