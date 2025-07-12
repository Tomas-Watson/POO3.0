package JuegoLemmings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {

    private List<Jugador> jugadores;

    public Ranking() {
        jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        ordenar();
        if (jugadores.size() > 5) {
            jugadores = jugadores.subList(0, 5); // mantener top 5
        }
    }

    public List<Jugador> getTop5() {
        return new ArrayList<>(jugadores); // devuelve una copia para no modificar el original
    }

    public void ordenar() {
        jugadores.sort(
                Comparator
                        .comparingInt(Jugador::getLemmingsSalvados).reversed()
                        .thenComparingLong(Jugador::getTiempoSegundos));
    }

    public void setJugadores(List<Jugador> nuevosJugadores) {
        this.jugadores = nuevosJugadores;
        ordenar();
    }

    public List<Jugador> getTodos() {
        return jugadores;
    }

    public void draw(Graphics2D g, int panelWidth) {
        // Fondo semi-transparente
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, panelWidth, 300);

        // Título
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("🏆 RANKING TOP 5 🏆", panelWidth / 2 - 100, 40);

        // Cabecera de la tabla
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        String header = String.format("%-5s %-12s %-10s %-10s", "#", "Jugador", "Tiempo", "Salvados");
        g.drawString(header, 50, 80);

        // Línea divisoria
        g.drawLine(50, 85, panelWidth - 50, 85);

        // Contenido del top 5
        g.setFont(new Font("Monospaced", Font.PLAIN, 16));
        int y = 110;
        int pos = 1;

        for (Jugador j : getTop5()) {
            long totalSegundos = j.getTiempoSegundos();
            long min = totalSegundos / 60;
            long seg = totalSegundos % 60;

            String linea = String.format(
                    "%-5d %-12s %02d:%02d      %-10d",
                    pos,
                    j.getNombre(),
                    min,
                    seg,
                    j.getLemmingsSalvados());

            g.drawString(linea, 50, y);
            y += 25;
            pos++;
        }
    }
}
