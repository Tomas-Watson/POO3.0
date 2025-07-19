package util;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Configuracion {
    private static Configuracion instancia;

    public boolean sonidoActivado;
    public boolean musicaActivada;
    public String skinSeleccionado;
    public String pistaMusical;
    public String pistaMusical2;
    public Map<String, Integer> teclas;

    private Configuracion() {
        cargarPredeterminadas();
    }

    public static Configuracion get() {
        if (instancia == null) {
            instancia = new Configuracion();
        }
        return instancia;
    }

    public void cargarPredeterminadas() {
        sonidoActivado = true;
        musicaActivada = true;
        skinSeleccionado = "default";
        pistaMusical = "Mizu"; // archivo: resources/musica/tema_original.wav
        pistaMusical2 = "Zelda";

        teclas = new HashMap<>();
        teclas.put("EFECTO", KeyEvent.VK_Q);
        teclas.put("MUSICA", KeyEvent.VK_E);
        teclas.put("PAUSA", KeyEvent.VK_SPACE);
    }
}