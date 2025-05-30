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
    private final int maxScreenColumnas = 16;
    private final int maxScreenFilas = 12;
    private final int screenAncho = baldosa * maxScreenColumnas;
    private final int screenAlto = baldosa * maxScreenFilas;
    private int[][] mapNivel;
    private Pixel[] pixeles; // Array de tipos de Pixel

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
            return false; // Fuera del mapa, consideramos que no es sólido
        }

        int tipoPixel = mapNivel[col][fila];
        Pixel pixel = pixeles[tipoPixel];
        // Suponemos que Pixel tiene un método esSolido()
        return pixel != null && pixel.esSolido();
    }
}
