package JuegoLemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;


public class Personaje extends ObjetoGrafico implements ObjetoGraficoMovible {
    public enum EstadoPersonaje {CAYENDO,CAMINANDO,EXCAVANDO,BLOQUEANDO,PLANEANDO,TREPANDO,MURIENDO,SALVADO, VELOZ, NUCLEAR}
    
    private double POS_X;
    private double POS_Y;
    private double velocidadY;
    private double velocidadX = 30.0;

    private int x;
    private int y; 

    private int direccion = 1;

    private BufferedImage Dr1, Dr2, Izq1, Izq2;

    private boolean vida;
    private double nukeTimer = -1;

    public BufferedImage[] framesDr;   // Esto me permitira que el personaje camine hacia la derecha
    public BufferedImage[] framesIzq;    // Esto me permitira que el personaje camine hacia la izquierda

    public int animFrame = 0; 
    public double animTimer = 0.0; //Es un acumulador de tiempo para pasar al siguiente frame
    public final double animInterval = 0.2; // Cada dos segundos se cambiara de frame 

    private Terreno terreno;
    private EstadoPersonaje estadoActual = EstadoPersonaje.CAMINANDO;
    private boolean puedeTrepar = false;
    public int treparHastaY = -1;
    private int treparBlockX;
    private int treparDir;
    private final double velocidadTrepar=40.0; 
    private final double GRAVEDAD= 200.0;
    private boolean puedePlanear = false;
    private boolean basherActive = false;
    private final double velocidadMaxPlaneo = 150.0;
    private double caidaY = -1;
    private Habilidad habilidadPendiente;

    public Personaje(int startX, int startY, Terreno terreno) { 
        super(startX, startY);
        this.positionX = startX;
        this.positionY = startY;
        this.terreno=terreno;
        this.vida = true; 
        //Carga las imagenes para la animacion
        try{
            Dr1 = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/boy_right_1.png"));
            Dr2 = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/boy_right_2.png"));
            Izq1 = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/boy_left_1.png"));
            Izq2 = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/boy_left_2.png"));

            framesDr = new BufferedImage[] {Dr1,Dr2}; //Ya cargo el arreglo con las imagenes
            framesIzq = new BufferedImage[] {Izq1,Izq2};

        }catch (IOException e){
            throw new RuntimeException("Error al cargar las imagenes para los personajes", e);
        }
    }
    
    
    @Override
    public void moverse(double delta) { 

    if (estadoActual == EstadoPersonaje.BLOQUEANDO) {
        return;
    }
    if (nukeTimer >= 0) {
        nukeTimer -= delta;
        if (nukeTimer <= 0) {
            terreno.destruirExplosion(
                (int)(positionX + Terreno.baldosa/2),
                (int)(positionY + Terreno.baldosa/2)
            );
            morir();
        }
        return;  
    }
    if (habilidadPendiente != null) {
        habilidadPendiente.activar();
        habilidadPendiente = null;
    }

    // 1) Animación
    animTimer += delta;
    if (animTimer >= animInterval) {
        animFrame = (animFrame + 1) % framesDr.length;
        animTimer -= animInterval;
    }

    // 1.1) TREPAR
    if (estadoActual == EstadoPersonaje.TREPANDO) {
        positionY -= velocidadTrepar * delta;
        if (positionY <= treparHastaY) {
            positionY = treparHastaY;
            int w = getWidth_frame();
            if (treparDir > 0) {
                positionX = treparBlockX;
            } else {
                positionX = treparBlockX + Terreno.baldosa - w;
            }
            velocidadY    = 0;
            estadoActual  = EstadoPersonaje.CAMINANDO;
            puedeTrepar   = false;
        }
        return;
    }

    // 2) GRAVEDAD & PLANEADO

    // <<< RETO DE CAÍDA >>>: arrancamos a contar cuando entramos en CAYENDO
    if (estadoActual != EstadoPersonaje.CAYENDO && terreno.esSolido((int)positionX, (int)(positionY + getHeight_frame())) == false) {
        // iniciamos la cuenta de caída
        caidaY = positionY;
    }

    // planear
    if (puedePlanear && estadoActual == EstadoPersonaje.CAYENDO) {
        velocidadY = Math.min(velocidadY, velocidadMaxPlaneo);
        estadoActual = Personaje.EstadoPersonaje.PLANEANDO;
        puedePlanear = false;
    } else {
        velocidadY += GRAVEDAD * delta;
    }

    double newY   = positionY + velocidadY * delta;
    int spriteH   = framesDr[animFrame].getHeight();
    int footX     = (int)positionX;
    int footY     = (int)(newY + spriteH);

    if (terreno.esSolido(footX, footY)) {
        // <<< RETO DE CAÍDA >>>: al aterrizar, medimos cuántos píxeles hemos caído
        int fila     = footY / Terreno.baldosa;
        double landY = fila * Terreno.baldosa - spriteH;
        double caidaDist = landY - caidaY;
        if (caidaY >= 0 && caidaDist >= Terreno.baldosa * 3) {
            morir();
            return;
        }
        // Aterriza justo encima
        positionY = landY;
        velocidadY = 0;
        if (estadoActual == EstadoPersonaje.CAYENDO || estadoActual == EstadoPersonaje.PLANEANDO) {
            estadoActual = EstadoPersonaje.CAMINANDO;
        }
        // reseteamos contador de caída
        caidaY = -1;
    } else {
        positionY = newY;
        if (estadoActual != EstadoPersonaje.PLANEANDO) {
            estadoActual = EstadoPersonaje.CAYENDO;
        }
    }

    // 3) MOVIMIENTO HORIZONTAL
    if (estadoActual == EstadoPersonaje.CAMINANDO) {
        double newX = positionX + velocidadX * delta * direccion;
        int w = getWidth_frame();
        int h = getHeight_frame();
        int sideX = direccion > 0 ? (int)(newX + w) : (int)newX;
        int sideY = (int)(positionY + h - 1);

        if (terreno.esSolido(sideX, sideY)) {
            if (basherActive) {
                int colBlock = sideX / Terreno.baldosa;
                int filaBlock = sideY / Terreno.baldosa;
                terreno.mapNivel[colBlock][filaBlock] = 0;
                basherActive = false;
                return;  
            }
            if (puedeTrepar) {
                int col    = sideX / Terreno.baldosa;
                int row    = sideY / Terreno.baldosa;
                int height = 0;
                // 1) Contar bloques contiguos comenzando en 'row'
                while (row - height >= 0 && terreno.mapNivel[col][row - height] != 0) {
                    height++;
                }
                // 'topRow' es la fila del bloque superior
                int topRow = row - height + 1;

                // 2) Comprobar que justo encima de la cima no haya bloque
                if (!(topRow > 0 && terreno.mapNivel[col][topRow - 1] != 0)) {
                    // 3) Calcular la Y final en píxeles para posicionar encima de la cima
                    treparHastaY = topRow * Terreno.baldosa - h;
                    System.out.printf("Subiendo muro de %d bloques, hasta Y=%d%n", height, treparHastaY);

                    // 4) Ajustar estado y posición X lateral para empezar la animación de trepar
                    estadoActual  = EstadoPersonaje.TREPANDO;
                    treparBlockX  = col * Terreno.baldosa;
                    treparDir     = direccion;
                    positionX     = (direccion > 0)
                                      ? treparBlockX - w
                                      : treparBlockX + Terreno.baldosa;
                    return;
                }
            }
            direccion *= -1;
        } else {
            positionX = newX;
        }
    }
}




    

    @Override
    public void draw(Graphics2D g) {
        BufferedImage currentFrame;
        if (direccion == 1) {          // si camina a la derecha
            currentFrame = framesDr[animFrame];
        } else {                        // si camina a la izquierda
            currentFrame = framesIzq[animFrame];
        }

        if (currentFrame != null) {
            g.drawImage(currentFrame, (int) positionX, (int) positionY, null);
        }
    }

    public EstadoPersonaje getEstado() { 
        return estadoActual; 
    }

    public void setEstado(EstadoPersonaje e) { 
        estadoActual = e; 
    }

    public void setY(int y){
        this.POS_Y=y;
    }

    public void setX(int x){
        this.POS_X=x;
    }

    @Override
    public double getX() {
        return positionX;
    }

    @Override
    public double getY() {
        return positionY;
    }

    public double getVelocidadY() {
       return velocidadY;
    }

    public double getVelocidadX() { 
        return velocidadX; 
    }

    public void setVelocidadY(double d) {
        velocidadY = d;
    }

    public void setVelocidadX(double d) {
        velocidadX = velocidadX * d;
    }

    public boolean estaVivo(){
        return vida;
    }

    public void morir() {
        this.vida = false;
        this.estadoActual = EstadoPersonaje.MURIENDO;
    }

    public int getDireccion(){
        return direccion;
    }

    public void setDireccion(int nuevaDireccion) {
        if (nuevaDireccion != 0) {
            this.direccion = (nuevaDireccion > 0 ? +1 : -1);
        }
    }

    public void setHabilidadPendiente(Habilidad h) {
        this.habilidadPendiente = h;
    }
    public Habilidad getHabilidadPendiente() {
        return habilidadPendiente;
    }
    
    public void startNukeCONTADOR(double segundos) {
        this.nukeTimer    = segundos;
        this.estadoActual = EstadoPersonaje.NUCLEAR;
    }

    public void enableClimb() {
        this.puedeTrepar = true;
    }

    public void settreparHastaY(int y) {
        this.treparHastaY = y;
    }

    public int getWidth_frame() {
        return  framesDr[animFrame].getWidth();
    }
    public int getHeight_frame() {
        return  framesDr[animFrame].getHeight();
    }

    public void enableFloat() {
        this.puedePlanear = true;
    }
    public void enableBasher() {
        this.basherActive = true;
    }

}
