package JuegoLemmings;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracionLemmings {
    
    private static ConfiguracionLemmings instancia;

    private boolean sonidoActivado;
    private boolean musicaActivada;
    private String pistaMusical;
    
    //Declara una varaible llamada teclas que es un mapa, que asocia un String con un Integer
    //Es como un diccionario: cada clave tiene asociado un valor 
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
        
        /*El HashMap es una implementacion concreta de la interfaz Map
         * Usa una tabla Hash interna para guardar y acceder rapidamente a los elementos
         * Aqui no importa el orden de insercion, ya que no se mantiene
         */
        teclas = new HashMap<>();
        teclas.put("EFECTO", KeyEvent.VK_Q);
        teclas.put("MUSICA", KeyEvent.VK_E);
        teclas.put("PAUSA", KeyEvent.VK_SPACE);
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
