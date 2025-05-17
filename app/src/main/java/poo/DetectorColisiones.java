package poo;

import poo.pong.*;

import java.awt.Rectangle;

import org.example.Fondo;

public class DetectorColisiones extends Rectangle {
        // Metodos de colisiones del juego Pong
        public static void colisionPelotaContraBordesSupInf(Pelota pelotita, Fondo fondo){
        // Colisión de la pelota con los bordes
                if (pelotita.getX() < 0 || pelotita.getX() + pelotita.getRadio() * 2 > fondo.getWidth()) {
                        pelotita.rebotarHorizontal();
                }
                if (pelotita.getY() < 30 || pelotita.getY() + pelotita.getRadio() * 2 > fondo.getHeight()) {
                        pelotita.rebotarVertical();
                }
        }

        public static void colisionPelotaRaqueta(Pelota pelotita, Paleta raqueta){
                Rectangle raquetazoBounds = new Rectangle((int) raqueta.getX(), (int) raqueta.getY(), raqueta.getWidth(), raqueta.getHeight());
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
                if(pelotita.getX() >= tamanoFondo - 15){// - pelotita.getWidth()){
                       band = true;
                }
                return band;
        }
        // Metodos de colisiones del juego Lemmings
        // Colisiones 1Er nivel
       
        // Colisiones 2Do nivel
       

        // Colisiones 3Er nivel
        
}