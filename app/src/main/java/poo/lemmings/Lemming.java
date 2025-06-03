package poo.lemmings;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Lemming extends ObjetoGrafico implements ObjetoGraficoMovible {

    private boolean vida;
    private  final double velocidad=10;
    private double velocidadY = 10; // velocidad vertical, puede ser usada para saltos o caídas
    private int direccion = 1; // +1 = derecha, -1 = izquierda, 0=parado
    private int x, y;          // posición actual del lemming
    private int ancho = 24;    // ancho en píxeles (ajusta según tu sprite)
    private int alto  = 24;    // alto en píxeles (ajusta según tu sprite)

    
    private int frameWidth = 10;
    private int frameHeight = 10;

    private Rectangle hitbox; 
    private Terreno terreno;

    private Habilidad habilidadActual;

    private double yInicioCaida = -1;

    public Lemming(String filename) {
        super(0, 0); // posición inicial
        this.vida = true;
        this.x = 0;
        this.y = 0;
        this.habilidadActual = null; // no tiene habilidad al inicio
        this.direccion = 1;
        this.hitbox = new Rectangle(x, y, ancho, alto); // Inicializar hitbox
    }

    public void setHabilidad(Habilidad h) {
        this.habilidadActual = h;
    }

    public void morir() {
        if (habilidadActual != null) {
            habilidadActual.suicidar();
        }
        this.vida = false;
    }


    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public void moverse(double delta) {
        if (!vida || terreno == null) return;

        // 1) Caída vertical
        if (!terreno.esSolido(x, y + alto) && !terreno.esSolido(x + ancho - 1, y + alto)) {
            y += velocidadY * delta;
            hitbox.setLocation(x, y);
            return;
        }

        // 2) Movimiento horizontal
        int nextX = (int) (x + velocidad * direccion * delta);
        hitbox.setLocation(nextX, y);

        if (direccion > 0) {
            int pxDerecha = nextX + ancho - 1;
            if (terreno.esSolido(pxDerecha, y) || terreno.esSolido(pxDerecha, y + alto - 1)) {
                girar();
            } else {
                x = nextX;
            }
        } else if (direccion < 0) {
            int pxIzquierda = nextX;
            if (terreno.esSolido(pxIzquierda, y) || terreno.esSolido(pxIzquierda, y + alto - 1)) {
                girar();
            } else {
                x = nextX;
            }
        }
        hitbox.setLocation(x, y);
    }

    @Override
    public void draw(Graphics2D g2) {
        try {
            BufferedImage paradoImg = ImageIO.read(getClass().getResourceAsStream("/imagenes/Lemming/Leming_Parado.png"));
        } catch (Exception e) {
            System.out.println("Error cargando imagen de Lemming parado: " + e);
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void girar() {
        direccion *= -1;
    }
    public int getDireccion(){
        return direccion;
    }

    public boolean estaVivo() {
        return vida;
    }

    public boolean estaEnSuelo(Terreno terreno) {
        // Chequea justo debajo del Lemming (puedes ajustar el +frameHeight según tu sprite)
        int px = (int) x + frameWidth / 2;
        int py = (int) y + frameHeight;
        return terreno.esSolido(px, py);
    }

    public double getVelocidadY() {
       return velocidadY;//valor final
    }

    public void setVelocidadY(double d) {
        velocidadY= d;
    }

    public void setY(double d) {
        this.y = (int) d;
    }

    public void iniciarCaida() {
        if (yInicioCaida == -1) {
            yInicioCaida = this.getY();
        }
    }

    public void aterrizar() {
        yInicioCaida = -1;
    }

    public double getYInicioCaida() {
        return yInicioCaida;
    }

    public void getPersonaje(){
        try {
            BufferedImage personaje = ImageIO.read(getClass().getResourceAsStream("app/src/main/resources/imagenes/Lemming/Leming_Parado.png.png"));

        } catch (Exception e) {
            System.out.println("Hay error en PerLemmings" + e);
        }
    }

   

}
