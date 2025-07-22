package JuegoLemmings;

import java.io.Serializable;

public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private long tiempoSegundos;
    private int lemmingsSalvados;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tiempoSegundos = 0;
        this.lemmingsSalvados = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTiempoSegundos() {
        return tiempoSegundos;
    }

    public int getLemmingsSalvados() {
        return lemmingsSalvados;
    }

    public void setTiempoSegundos(long tiempoTotal) {
        this.tiempoSegundos = tiempoTotal;
    }

    public void setLemmingsSalvados(int lemmingsSalvados2) {
        this.lemmingsSalvados = lemmingsSalvados2;
    }
}
