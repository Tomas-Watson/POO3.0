package poo;

import java.awt.Rectangle;

import poo.pong.Paleta;
import poo.pong.Pelota;

public class DetectorColisiones extends Rectangle {
        // Metodos de colisiones del juego Pong
        private final static int ANCHO_PANTALLA = 800;
        private final static int ALTO_PANTALLA = 600;
        public static void colisionPelotaContraBordesSupInf(Pelota pelotita){
        // Colisión de la pelota con los bordes
                if (pelotita.getX() < 0 || pelotita.getX() + pelotita.getRadio() * 2 > ANCHO_PANTALLA) {
                        pelotita.rebotarHorizontal();
                }
                if (pelotita.getY() < 30 || pelotita.getY() + pelotita.getRadio() * 2 > ALTO_PANTALLA) {
                        pelotita.rebotarVertical();
                }
        }

        public static void colisionPelotaRaqueta(Pelota pelotita, Paleta raqueta){
                Rectangle raquetazoBounds = new Rectangle((int) raqueta.getX(), (int) raqueta.getY(), (int) raqueta.getAncho(), (int) raqueta.getAlto());
                Rectangle pelotitaBounds = new Rectangle((int) pelotita.getX(), (int) pelotita.getY(), pelotita.getRadio() * 2, pelotita.getRadio() * 2);
                if (raquetazoBounds.intersects(pelotitaBounds)) {
                        pelotita.rebotarHorizontal();
                }   
        }

        public static boolean colisionPelotaContraLateralIzquierda(Pelota pelotita){
                boolean band = false;
                if(pelotita.getX() <= 0){
                      band = true;
                }
                return band;
        }

        public static boolean colisionPelotaContraLateralDerecha(Pelota pelotita, int tamanoFondo){
                boolean band = false;
                if(pelotita.getX() + pelotita.getRadio() * 2 >= ANCHO_PANTALLA){
                       band = true;
                }
                return band;
        }
        
        // Metodos de colisiones del juego Lemmings
        // Colisiones 1Er nivel
       
        // Colisiones 2Do nivel
       

        // Colisiones 3Er nivel
        
}