package poo.JuegoLemmings;

public class Jugador {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTiempoSegundos'");
    }

    public void setLemmingsSalvados(int lemmingsSalvados2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLemmingsSalvados'");
    }
}
