package poo.lemmings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.example.ObjetoGrafico;

public class Terreno extends ObjetoGrafico {
    private final int tamBaldosaOriginal = 16;
    private final int escala = 3;
    public final int baldosa = tamBaldosaOriginal * escala;
    public final int maxScreenColumnas = 16;
    public final int maxScreenFilas = 12;
    private final int screenAncho = baldosa * maxScreenColumnas;
    private final int screenAlto = baldosa * maxScreenFilas;
    private int[][] mapNivel;
    private Pixel[] pixeles; // Array de tipos de Pixel

    private final int r =20; //para explosiones

    public Terreno(Pixel[] pixeles) {
        super(0, 0);
        this.pixeles = pixeles; // Recibe el array de tipos de Pixel
        mapNivel = new int[maxScreenColumnas][maxScreenFilas];
    }

    public void CargarTerreno(String filename) {
        try {
            InputStream is = getClass().getResourceAsStream(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            for (int col = 0; col < maxScreenColumnas; col++) {
                String linea = reader.readLine();
                String[] numeros = linea.split(" ");
                for (int fila = 0; fila < maxScreenFilas; fila++) {
                    int nro = Integer.parseInt(numeros[fila]);
                    mapNivel[col][fila] = nro;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        for (int col = 0; col < maxScreenColumnas; col++) {
            for (int fila = 0; fila < maxScreenFilas; fila++) {
                int tipoPixel = mapNivel[col][fila];
                Pixel pixel = pixeles[tipoPixel];
                if (pixel != null && pixel.getImage() != null) {
                    g2.drawImage((java.awt.Image) pixel.getImage(), col * baldosa, fila * baldosa, baldosa, baldosa, null);
                }
            }
        }
    }

    public boolean esSolido(int px, int py) {
        // Convertimos coordenadas de píxel a columna y fila de baldosa
        int col = px / baldosa;
        int fila = py / baldosa;

        // Verificamos que esté dentro de los límites del mapa
        if (col < 0 || col >= maxScreenColumnas || fila < 0 || fila >= maxScreenFilas) {
            return false; 
        }

        int tipoPixel = mapNivel[col][fila];
        Pixel pixel = pixeles[tipoPixel];
        return pixel != null && pixel.esSolido();
    }

    public void destruirExplosion(int x, int y) {
        int radioCuadrado = r * r;
        for (int col = 0; col < maxScreenColumnas; col++) {
            for (int fila = 0; fila < maxScreenFilas; fila++) {
                // Centro de la baldosa
                int centroX = col * baldosa + baldosa / 2;
                int centroY = fila * baldosa + baldosa / 2;
                int dx = centroX - x;
                int dy = centroY - y;
                if (dx * dx + dy * dy <= radioCuadrado) {
                    // Cambia el tipo de pixel a "aire" (por ejemplo, tipo 0)
                    mapNivel[col][fila] = 0;
                }
            }
        }
    }

    public void minerAdelante(int x, int y, int direccion)  {
        int col = x / baldosa;
        int fila = y / baldosa;

        // El miner destruye bloques en la fila actual hacia la dirección indicada
        // dirección: 1 = derecha, -1 = izquierda
        int nextCol = col + direccion;

        // Verifica límites del mapa
        while (nextCol >= 0 && nextCol < maxScreenColumnas) {
            // Si el siguiente bloque es aire, termina de minar
            if (mapNivel[nextCol][fila] == 0) {
            break;
            }

            // Destruye el bloque (lo convierte en aire)
            mapNivel[nextCol][fila] = 0;

            // Avanza en la dirección indicada
            nextCol += direccion;
        }
    }

    public void miner(int x, int y)  {
        int col = x / baldosa;
        int fila = y / baldosa;

        // El miner destruye hacia abajo en la misma columna
        while (fila + 1 < maxScreenFilas) {
            int nextFila = fila + 1;

            // Si el siguiente bloque es aire, termina de minar
            if (mapNivel[col][nextFila] == 0) {
                break;
            }

            // Destruye el bloque (lo convierte en aire)
            mapNivel[col][nextFila] = 0;

            // Avanza hacia abajo
            fila = nextFila;
        }
    }

    public boolean construirEscalon(int x, int y, int direccion) {
        // dirección: 1 = derecha, -1 = izquierda
        // Calcula la posición del nuevo escalón (un poco arriba y adelante del lemming)
        int col = (x / baldosa) + direccion;
        int fila = (y / baldosa) - 1; // Un bloque arriba del lemming

        // Verifica límites del mapa
        if (col < 0 || col >= maxScreenColumnas || fila < 0 || fila >= maxScreenFilas) {
            return false; // No se puede construir fuera del mapa
        }

        // Si ya hay un bloque sólido, no se puede construir (pared o techo)
        int tipoPixel = mapNivel[col][fila];
        Pixel pixel = pixeles[tipoPixel];
        if (pixel != null && pixel.esSolido()) {
            return false; // Hay pared o techo, no se puede construir
        }

        // Construye el escalón (2 es escalón)
        mapNivel[col][fila] = 2;
        return true; // Construcción exitosa
    }

    public void pararse(int x, int y) {
        // Convierte la posición de píxel a columna y fila de baldosa
        int col = x / baldosa;
        int fila = y / baldosa;

        // Verifica límites del mapa
        if (col < 0 || col >= maxScreenColumnas || fila < 0 || fila >= maxScreenFilas) {
            return;
        }

        // Cambia el tipo de pixel a uno especial de "pared temporal" (por ejemplo, tipo 3)
        mapNivel[col][fila] = 3; // Asegúrate que pixeles[3] sea sólido y tenga imagen de pared o invisible
    }

    public boolean esPared(int x, int y) {
        int col = x / baldosa;
        int fila = y / baldosa;

        // Verifica límites del mapa
        if (col < 0 || col >= maxScreenColumnas || fila <= 0 || fila >= maxScreenFilas) {
            return false;
        }

        // Chequea si hay bloque sólido en la posición actual y justo encima
        int tipoPixelActual = mapNivel[col][fila];
        int tipoPixelArriba = mapNivel[col][fila - 1];
        Pixel pixelActual = pixeles[tipoPixelActual];
        Pixel pixelArriba = pixeles[tipoPixelArriba];

        return (pixelActual != null && pixelActual.esSolido()) &&
               (pixelArriba != null && pixelArriba.esSolido());
    }
}
