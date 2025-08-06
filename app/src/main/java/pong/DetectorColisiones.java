package pong;
import java.awt.Rectangle;

import util.Sonido;

public class DetectorColisiones extends Rectangle {
        // Metodos de colisiones del juego Pong
        private final static int ANCHO_PANTALLA = 800;
        private final static int ALTO_PANTALLA = 600;

        private final Sonido sonido;
        private final ConfiguracionPong config;

        public DetectorColisiones(Sonido sonido,ConfiguracionPong config) {
                this.sonido = sonido;
                this.config = config;
        }

        public void colisionPelotaContraBordesSupInf(Pelota pelotita){
        // Colisión de la pelota con los bordes
                if (pelotita.getX() < 0 || pelotita.getX() + pelotita.getRadio() * 2 > ANCHO_PANTALLA) {
                        pelotita.rebotarHorizontal();
                        if (config.isSonidoActivado()) {
                                sonido.reproducirEfecto("musica/Rebote.wav");
                        }
                }
                if (pelotita.getY() < 30 || pelotita.getY() + pelotita.getRadio() * 2 > ALTO_PANTALLA) {
                        pelotita.rebotarVertical();
                        if (config.isSonidoActivado()) {
                                sonido.reproducirEfecto("musica/Rebote.wav");
                        }
                }
        }

        public void colisionPelotaRaqueta(Pelota pelotita, Paleta raqueta){
                Rectangle raquetazoBounds = new Rectangle((int) raqueta.getX(), (int) raqueta.getY(), (int) raqueta.getAncho(), (int) raqueta.getAlto());
                Rectangle pelotitaBounds = new Rectangle((int) pelotita.getX(), (int) pelotita.getY(), pelotita.getRadio() * 2, pelotita.getRadio() * 2);
                if (raquetazoBounds.intersects(pelotitaBounds)) {
                        // Calcula el punto de impacto relativo
                        double centroPelota = pelotita.getY() + pelotita.getRadio();
                        double centroRaqueta = raqueta.getY() + raqueta.getAlto() / 2.0;
                        double distancia = (centroPelota - centroRaqueta) / (raqueta.getAlto() / 2.0); // entre -1 y 1

                        // Ajusta el ángulo de rebote según la distancia
                        pelotita.rebotarHorizontal();
                        pelotita.setDY((int)(pelotita.getVelocidadMax() * distancia));
                        if (config.isSonidoActivado()) {
                                sonido.reproducirEfecto("musica/Rebote.wav");
                        }               
                }   
        }

        public boolean colisionPelotaContraLateralIzquierda(Pelota pelotita){
                boolean band = false;
                if(pelotita.getX() <= 0){
                      band = true;
                }
                return band;
        }

        public boolean colisionPelotaContraLateralDerecha(Pelota pelotita, int tamanoFondo){
                boolean band = false;
                if(pelotita.getX() + pelotita.getRadio() * 2 >= ANCHO_PANTALLA){
                       band = true;
                }
                return band;
        }
}