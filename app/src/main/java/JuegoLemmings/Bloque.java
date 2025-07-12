package JuegoLemmings;

import java.awt.image.BufferedImage;

public class Bloque {
    private BufferedImage imagen;
    private boolean esSolido; // true si el lemming no puede pasar
    
    public void setesSolido(boolean esSolido) {
        this.esSolido = esSolido;
    }
    
    public boolean esSolido() {
        return esSolido;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public Object getImagen() {
        return imagen;
    }


}
