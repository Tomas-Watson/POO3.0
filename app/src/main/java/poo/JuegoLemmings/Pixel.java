package JuegoLemmings;

import java.awt.image.BufferedImage;

public class Pixel {
    public BufferedImage imagen;
    public boolean esSolido; // true si el lemming no puede pasar
    public String tipo;
    public boolean esSolido() {
        return esSolido;
    }
    public Object getImage() {
        return imagen;
    }


}
