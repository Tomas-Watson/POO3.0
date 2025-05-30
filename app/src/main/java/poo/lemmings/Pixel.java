package poo.lemmings;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Pixel {
    public BufferedImage imagen;
    public boolean esSolido; // true si el lemming no puede pasar
    public String tipo;

    public Pixel(BufferedImage imagen, boolean esSolido, String tipo) {
        this.imagen = imagen;
        this.esSolido = esSolido;
        this.tipo = tipo;
    }

    public Image getImage() {
        return imagen;
    }

    public boolean esSolido() {
        return this.esSolido;
    }
}
