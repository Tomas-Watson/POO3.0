package JuegoLemmings;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Habilidad {

    public enum Tipo {
        MINER, // excava verticalmente hacia abajo
        BUILDER, // construye un escalón delante del lemming
        FLOATER, // (reducir velocidad de caída)
        BLOCKER, // invierte dirección y crea obstáculo (pared temporal)
        CLIMBER, // sube muros (trepa)
        BOMBER, // explota, destruye terreno alrededor y muere
        BASHER, // excava horizontalmente en la dirección actual
        VELOX2, // (velocidad doble)
        NUKE // Mueren TODOS
    }

    private final Tipo tipo;
    private final Personaje lemming; // antes Lemming → ahora Personaje
    private final Terreno terreno;
    private static final int ICON_COUNT = 4;
    // private BufferedImage[] iconos; // Icono de la habilidad, si se necesita
    private BufferedImage[] frames;

    private static BufferedImage[] miner;
    private static BufferedImage[] builder;
    private static BufferedImage[] floater;
    private static BufferedImage[] blocker;
    private static BufferedImage[] climber;
    private static BufferedImage[] bomber;
    private static BufferedImage[] basher;
    private static BufferedImage[] nuke;

    public Habilidad(Tipo tipo, Personaje lemming, Terreno terreno) {
        this.tipo = tipo;
        this.lemming = lemming;
        this.terreno = terreno;
        switch (tipo) {
            case MINER:
                this.frames = miner;
                break;
            case BUILDER:
                this.frames = builder;
                break;
            case FLOATER:
                this.frames = floater;
                break;
            case BLOCKER:
                this.frames = blocker;
                break;
            case CLIMBER:
                this.frames = climber;
                break;
            case BOMBER:
                this.frames = bomber;
                break;
            case BASHER:
                this.frames = basher;
                break;
            case NUKE:
                this.frames = nuke;
                break;
        }

    }

    public static void cargarImagenes() {
        try {

            miner = new BufferedImage[ICON_COUNT];
            builder = new BufferedImage[ICON_COUNT];
            floater = new BufferedImage[ICON_COUNT];
            blocker = new BufferedImage[ICON_COUNT];
            climber = new BufferedImage[ICON_COUNT];
            bomber = new BufferedImage[ICON_COUNT];
            basher = new BufferedImage[ICON_COUNT];
            nuke = new BufferedImage[ICON_COUNT];
            for (int i = 0; i < ICON_COUNT; i++) {
                int index = i + 1; // Para nombrar el archivo desde 1 (miner1.png, etc.)
                miner[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/miner%d.png", index)));
                builder[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/builder%d.png", index)));
                floater[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/floater%d.png", index)));
                blocker[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/blocker%d.png", index)));
                climber[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/climber%d.png", index)));
                bomber[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/bomber%d.png", index)));
                basher[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/basher%d.png", index)));
                nuke[i] = ImageIO.read(Habilidad.class.getResourceAsStream(
                        String.format("/Imagenes_Lemmings/bomber%d.png", index)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar las imágenes de iconos", e);
        }
    }

    
    public void activar() {
        switch (tipo) {
            case MINER:
                // Excava un túnel directamente debajo del lemming
                terreno.miner((int) lemming.getX(), (int) lemming.getY());
                lemming.setEstado(Personaje.EstadoPersonaje.EXCAVANDO);
                break;

            case BUILDER:
                // coordenadas del pie del lemming
                int xPie = (int) lemming.getX();
                int yPie = (int) (lemming.getY() + lemming.getHeight_frame());
                terreno.construirEscalon(xPie, yPie, lemming.getDireccion());
                lemming.setEstado(Personaje.EstadoPersonaje.CAMINANDO);
                break;

            case FLOATER:
                // Mientras caiga, reduce la velocidadVertical (planear):
                lemming.enableFloat();
                if (lemming.getEstado() == Personaje.EstadoPersonaje.CAYENDO) {
                    lemming.setEstado(Personaje.EstadoPersonaje.PLANEANDO);
                }
                break;

            case BLOCKER:
                // Invierte dirección instantáneamente y crea muro en su lugar

                terreno.pararse((int) lemming.getX(), (int) lemming.getY());
                lemming.setEstado(Personaje.EstadoPersonaje.BLOQUEANDO);
                break;

            case CLIMBER: // Habilita el modo trepar
                lemming.enableClimb();
                break;

            case BOMBER:
                // Destruye terreno a su alrededor y luego “muere”
                int xCentro = (int) lemming.getX() + Terreno.baldosa / 2;
                int yCentro = (int) lemming.getY() + Terreno.baldosa / 2;
                ;
                terreno.destruirExplosion(xCentro, yCentro);
                lemming.morir();
                break;

            case BASHER:
                lemming.enableBasher();
                lemming.setEstado(Personaje.EstadoPersonaje.CAMINANDO);
                break;
            case VELOX2:
                // Aumenta la velocidad del lemming
                lemming.setVelocidadX(lemming.getVelocidadX() * 2);
                lemming.setEstado(Personaje.EstadoPersonaje.VELOZ);
                break;

            case NUKE:
                lemming.startNukeCONTADOR(5.0);
                break;
            default:
                lemming.setEstado(Personaje.EstadoPersonaje.CAMINANDO);
                break;
        }
    }

    public BufferedImage[] getFrames() {
        return frames;
    }
}
