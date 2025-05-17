package poo.pong;
import javax.swing.ImageIcon;

import poo.Juego;

public class JuegoPong extends Juego {

    public JuegoPong(){
        setNombre("Pong");
        setVersion("1.0");
        setDesarrolladores("Federico.R & Tomas.W");
        setDescripcion("Ping Pong en 2D");
        setImplementado(true);
        setImgenPortada(new ImageIcon(this.getClass().getResource("ImagenesPong/PortadaPong.png")));

    }

    @Override
    public void run(){
        new PantallaInicioPong();
    }
}
