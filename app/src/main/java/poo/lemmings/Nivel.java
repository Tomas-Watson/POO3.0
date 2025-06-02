package poo.lemmings;

import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.example.FXPlayer;

public abstract class Nivel {


    public void animacionSalida(double delta){
        private Lemmings Lemmings;
        Timer temporizador = new Timer();
        Date dInit = new Date();
        Date dReloj;
        Date dAhora;
        if (Nivel1.llegoAMeta() || Nivel2.llegoAMeta() || Nivel3.llegoAMeta()) {
            if (dReloj == null) {
                dReloj = new Date();
            }
            dAhora = new Date();
            Lemmings.getLemming().LlegadaMeta(delta);
        }
    }
    
    protected void choqueDelPersonaje(Charlie charlie) {
        FXPlayer.DERROTA.playOnce();
        charlie.setPISO(charlie.getY());
        charlie.setPosition(charlie.getX(), charlie.getPISO());
        charlie.setImagen("imagenes/JuegoCircusCharlie/Generales/charlieDerrota.png");
        Timer tempo = new Timer();

        tempo.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!accion) {
                    if(!restar){
                        charlie.restarVida(1);
                        restar = true;
                    }
                    reiniciarJuegoXColisiones(charlie.getX(), charlie);
                    accion = true;
                }
            }
        }, 4000);
    }

    // Cuando detecta una colision, reiniciamos el juego en ese punto
    protected void reiniciarJuegoXColisiones(double x1, Charlie charlie) {
        // Busca el checkpoint más cercano a la posición x
        int[] checkpointsEjeX = { 201, 990, 1814, 2654, 3451, 4259, 5066, 5869, 6668, 7433 };
        int pos = 0, i;
        for (i = 1; i < checkpointsEjeX.length; i++) {
            if (checkpointsEjeX[i] < x1) {
                pos = i - 1;
            }
        }
        // Reinicia el juego en el checkpoint más cercano
        int newX = checkpointsEjeX[pos];
        mostrarNivel = true;
        CircusCharlie.inicioNivel(false);
        reiniciarJuego(newX, charlie);
    }

    // Método para reiniciar el juego en una posición específica
    protected void reiniciarJuego(double x, Charlie charlie) {
        charlie.setPISO(412);
        charlie.setPosition(x + 31, charlie.getPISO());
        llegoAMeta = false;
        colisiono = false;
        FXPlayer.DERROTA.stop();
        charlie.setImagen("imagenes/JuegoCircusCharlie/Generales/charlie.png");
        charlie.reiniciarDescuento();
    }

}
