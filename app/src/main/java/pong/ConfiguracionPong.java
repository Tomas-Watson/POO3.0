package pong;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracionPong {

    private static ConfiguracionPong instancia;

    public boolean sonidoActivado;
    public boolean musicaActivada;
    public String skinSeleccionado;
    public String pistaMusical;
    public String pistaMusical2;
    public Map<String, Integer> teclas;

    private ConfiguracionPong() {
        cargarPredeterminadas();
    }

    public static ConfiguracionPong get() {
        if (instancia == null) {
            instancia = new ConfiguracionPong();
        }
        return instancia;
    }

    public void cargarPredeterminadas() {
        sonidoActivado = true;
        musicaActivada = true;
        skinSeleccionado = "default";
        pistaMusical = "Mizu";

        teclas = new HashMap<>();
        teclas.put("EFECTO", KeyEvent.VK_Q);
        teclas.put("MUSICA", KeyEvent.VK_E);
        teclas.put("PAUSA", KeyEvent.VK_SPACE);

        teclas.put("J1_UP", KeyEvent.VK_UP);
        teclas.put("J1_DOWN", KeyEvent.VK_DOWN);
        teclas.put("J2_UP", KeyEvent.VK_W);
        teclas.put("J2_DOWN", KeyEvent.VK_S);
    }
}
