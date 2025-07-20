package JuegoLemmings;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracionLemmings {
    
    private static ConfiguracionLemmings instancia;

    private boolean sonidoActivado;
    private boolean musicaActivada;
    private String pistaMusical;
    
    private Map<String, Integer> teclas;

    private ConfiguracionLemmings() {
        cargarPredeterminadas();
    }

    public static ConfiguracionLemmings get() {
        if (instancia == null) {
            instancia = new ConfiguracionLemmings();
        }
        return instancia;
    }

    public void cargarPredeterminadas() {
        sonidoActivado = true;
        musicaActivada = true;
        pistaMusical = "Zelda";
        

        teclas = new HashMap<>();
        teclas.put("EFECTO", KeyEvent.VK_Q);
        teclas.put("MUSICA", KeyEvent.VK_E);
        teclas.put("PAUSA", KeyEvent.VK_SPACE);
        teclas.put("J1_UP", KeyEvent.VK_UP);
        teclas.put("J1_DOWN", KeyEvent.VK_DOWN);
        teclas.put("J2_UP", KeyEvent.VK_W);
        teclas.put("J2_DOWN", KeyEvent.VK_S);
    }

    // === GETTERS y SETTERS ===
    public boolean isSonidoActivado() {
        return sonidoActivado;
    }

    public void setSonidoActivado(boolean sonidoActivado) {
        this.sonidoActivado = sonidoActivado;
    }

    public boolean isMusicaActivada() {
        return musicaActivada;
    }

    public void setMusicaActivada(boolean musicaActivada) {
        this.musicaActivada = musicaActivada;
    }

    public String getPistaMusical() {
        return pistaMusical;
    }

    public void setPistaMusical(String pistaMusical) {
        this.pistaMusical = pistaMusical;
    }

    public Map<String, Integer> getTeclas() {
        return teclas;
    }

    public void setTeclas(Map<String, Integer> teclas) {
        this.teclas = teclas;
    }
}
