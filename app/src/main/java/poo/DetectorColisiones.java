package jgame.gradle.CircusCharlie.ObjetosGraficos.Obstaculos;
import jgame.gradle.CircusCharlie.Charlie;
import jgame.gradle.Pong.Ball;
import jgame.gradle.Pong.Raqueta;
import jgame.gradle.Pong.Sonido;
import java.awt.Rectangle;

public class DetectorColisiones extends Rectangle {
        // Metodos de colisiones del juego Pong
        public static void colisionPelotaContraBordesSupInf(Ball pelotita, jgame.gradle.CircusCharlie.Fondo fondo){
        // Colisión de la pelota con los bordes
                if (pelotita.getX() < 0 || pelotita.getX() + pelotita.getRadio() * 2 > fondo.getWidth()) {
                        pelotita.rebotarHorizontal();
                }
                if (pelotita.getY() < 30 || pelotita.getY() + pelotita.getRadio() * 2 > fondo.getHeight()) {
                        Sonido.iniciar("wall");
                        pelotita.rebotarVertical();
                }
        }

        public static void colisionPelotaRaqueta(Ball pelotita, Raqueta raquetazo){
                Rectangle raquetazoBounds = new Rectangle((int) raquetazo.getX(), (int) raquetazo.getY(), raquetazo.getWidth(), raquetazo.getHeight());
                Rectangle pelotitaBounds = new Rectangle((int) pelotita.getX(), (int) pelotita.getY(), pelotita.getRadio() * 2, pelotita.getRadio() * 2);
                if (raquetazoBounds.intersects(pelotitaBounds)) {
                        Sonido.iniciar("Paddle");
                        pelotita.rebotarHorizontal();
                }   
        }

        public static boolean colisionPelotaContraLateralIzquierda(Ball pelotita){
                boolean band = false;
                if(pelotita.getX() <= 0){
                        Sonido.iniciar("point");
                        band = true;
                }
                return band;
        }

        public static boolean colisionPelotaContraLateralDerecha(Ball pelotita, int tamanoFondo){
                boolean band = false;
                if(pelotita.getX() >= tamanoFondo - 15){// - pelotita.getWidth()){
                        Sonido.iniciar("point");
                        band = true;
                }
                return band;
        }
        // Metodos de colisiones del juego CircusCharlie
        // Colisiones 1Er nivel
        // Detectar si colisiona con un aro
        public static boolean detectarAro(Aro aro, Charlie charlie) {
                // Obtener las coordenadas y dimensiones de los rectángulos que rodean a los
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                Rectangle rectAroQuarter = new Rectangle();
                int aroQuarterHeight = aro.getHeight() / 4;
                rectAroQuarter = new Rectangle((int) aro.getX(), (int) (aro.getY() + aroQuarterHeight * 3), aro.getWidth(), aroQuarterHeight);
                return rectAroQuarter.intersects(rectCharlie);
        }

        // Detectar si el objeto pasa por el medio del aro
        public static boolean detectarMedioAro(Aro aro, Charlie charlie) {
                // Obtener las coordenadas y dimensiones de los rectángulos que rodean a los
                // objetos
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                Rectangle rectMedioAro = new Rectangle();
                // Calcular las dimensiones y posición del rectángulo imaginario que representa el "medio" del aro
                int aroMedioWidth = aro.getWidth() / 2;
                int aroMedioHeight = aro.getHeight() / 2;
                int aroMedioX = (int) (aro.getX() + aro.getWidth() / 4); // El "medio" del aro comienza desde un cuarto del ancho del aro
                int aroMedioY = (int) (aro.getY() + aro.getHeight() / 4); // El "medio" del aro comienza desde un cuarto de la altura del aro
                // Crear el rectángulo imaginario que representa el "medio" del aro
                rectMedioAro.setBounds(aroMedioX, aroMedioY, aroMedioWidth, aroMedioHeight);
                // Verificar si el objeto está dentro del rectángulo imaginario que representa
                // el "medio" del aro
                return rectMedioAro.intersects(rectCharlie);
        }

        // Detectar si colisiona con un caldero de fuego
        public static boolean detectarCalderoDeFuego(CalderoDeFuego calderito, Charlie charlie) {
                // Obtener las coordenadas y dimensiones de los rectángulos que rodean a los
                // objetos
                Rectangle recCaldero = new Rectangle((int) calderito.getX(), (int) calderito.getY(), calderito.getWidth(), calderito.getHeight());
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                // Verificar si los rectángulos se superponen
                return recCaldero.intersects(rectCharlie);
        }

        public static boolean detectarArribaCalderoDeFuego(CalderoDeFuego calderito, Charlie charlie) {
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al caldero
                // Definir un área en la parte superior del caldero
                int areaArriba = 100; // Altura del área superior (ajústala según tus necesidades)
                Rectangle areaSuperior = new Rectangle((int) calderito.getX(), (int) calderito.getY() - areaArriba,calderito.getWidth(), areaArriba);
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al personaje
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                // Verificar si los rectángulos se superponen
                return areaSuperior.intersects(rectCharlie);
        }

        public static boolean detectarBolsita(Money bolsita, Charlie charlie){
                Rectangle rectBolsita = new Rectangle((int) bolsita.getX(), (int)bolsita.getY(), bolsita.getWidth(), bolsita.getHeight());
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                return rectBolsita.intersects(rectCharlie);
        }

        // Colisiones 2Do nivel
        public static boolean detectarMonoNormal(MonoMarron monito, Charlie charlie) {
                Rectangle rectMonito = new Rectangle((int) monito.getX(), (int) monito.getY(), monito.getWidth(),
                        monito.getHeight());
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(),
                        charlie.getHeight());
                return rectMonito.intersects(rectCharlie);
        }

        public static boolean detectarMonoAzul(MonoAzul monito, Charlie charlie) {
                Rectangle rectMonito = new Rectangle((int) monito.getX(), (int) monito.getY(), monito.getWidth(),
                        monito.getHeight());
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(),
                        charlie.getHeight());
                return rectMonito.intersects(rectCharlie);
        }

        public static boolean detectarEntreMonos(MonoMarron monitoMarron, MonoAzul monitoazul) {
                Rectangle rectMonitoMarron = new Rectangle((int) monitoMarron.getX(), (int) monitoMarron.getY(),
                        monitoMarron.getWidth(), monitoMarron.getHeight());
                Rectangle rectMonitoAzul = new Rectangle((int) monitoazul.getX(), (int) monitoazul.getY(),
                        monitoazul.getWidth(), monitoazul.getHeight());
                return rectMonitoMarron.intersects(rectMonitoAzul);
        }

        public static boolean detectarArribaMonoMarron(MonoMarron monoMarron, Charlie charlie) {
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al caldero
                // Definir un área en la parte superior del caldero
                int areaArriba = 100; // Altura del área superior (ajústala según tus necesidades)
                Rectangle areaSuperior = new Rectangle((int) monoMarron.getX(), (int) monoMarron.getY() - areaArriba,monoMarron.getWidth(), areaArriba);
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al personaje
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                // Verificar si los rectángulos se superponen
                return areaSuperior.intersects(rectCharlie);
        }

        public static boolean detectarArribaMonoAzul(MonoAzul monoAzul, Charlie charlie) {
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al caldero
                // Definir un área en la parte superior del caldero
                int areaArriba = 100; // Altura del área superior (ajústala según tus necesidades)
                Rectangle areaSuperior = new Rectangle((int) monoAzul.getX(), (int) monoAzul.getY() - areaArriba,monoAzul.getWidth(), areaArriba);
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al personaje
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                // Verificar si los rectángulos se superponen
                return areaSuperior.intersects(rectCharlie);
        }

        // Colisiones 3Er nivel
        public static boolean detectarCharlieParadoSobrePelota(Pelota p, Charlie charlie) {
                // Obtener las coordenadas y dimensiones del rectángulo que rodea al personaje
                Rectangle rectCharlie = new Rectangle((int) charlie.getX(), (int) charlie.getY(), charlie.getWidth(), charlie.getHeight());
                // Definir un área en la parte superior del rectángulo de la pelota
                int areaArriba = 5; // Aumentar el área superior para mejorar la detección de colisiones
                Rectangle areaSuperior = new Rectangle((int) p.getX(), (int) p.getY() - areaArriba, p.getWidth(), areaArriba);
                // Verificar si el rectángulo de Charlie intersecta con el área superior de la pelota
                boolean intersection = areaSuperior.intersects(rectCharlie);
                return intersection;
        }
        
        public static boolean detectarEntrePelotas(Pelota p1, Pelota p2){
                Rectangle rectPelota1 = new Rectangle((int) p1.getX(), (int) p1.getY(), p1.getWidth(), p1.getHeight());
                Rectangle rectPelota2 = new Rectangle((int) p2.getX(), (int) p2.getY(), p2.getWidth(), p2.getHeight());
                return rectPelota1.intersects(rectPelota2); 
        }
}