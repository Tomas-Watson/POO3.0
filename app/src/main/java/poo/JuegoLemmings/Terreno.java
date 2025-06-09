package JuegoLemmings;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.example.ObjetoGrafico;

public class Terreno extends ObjetoGrafico {
    private static final int tamBaldosaOriginal = 16;
    private static final int escala = 3;
    public static final int baldosa = tamBaldosaOriginal * escala;
    public static final int maxScreenColumnas = 16;
    public static final int maxScreenFilas = 12;
    private static final int screenAncho = baldosa * maxScreenColumnas;
    private static final int screenAlto = baldosa * maxScreenFilas;
    public int[][] mapNivel;
    private Pixel[] pixeles; // Array de tipos de Pixel

    private final int r =20; //para explosiones

    public Terreno(Pixel[] pixeles) {
        super(screenAncho,screenAlto);
        this.pixeles = pixeles; // Recibe el array de tipos de Pixel
        mapNivel = new int[maxScreenColumnas][maxScreenFilas];
    }

    public void CargarTerreno(String filename) {
        try {
        InputStream is = getClass().getResourceAsStream(filename);
        if (is == null) {
            // Si esto ocurre, Java no encontró el recurso en el classpath.
            System.err.println("ERROR: No se encontró el archivo de mapa: " + filename);
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        // LEYENDO POR FILAS (16 columnas x 12 filas)
        for (int fila = 0; fila < maxScreenFilas; fila++) {
            String linea = reader.readLine();
            if (linea == null) {
                System.err.println("ERROR: El archivo de mapa tiene menos de " + maxScreenFilas + " líneas.");
                break;
            }
            String[] numeros = linea.trim().split("\\s+");
            for (int col = 0; col < maxScreenColumnas; col++) {
                if (col < numeros.length) {
                    int nro = Integer.parseInt(numeros[col]);
                    mapNivel[col][fila] = nro;
                } else {
                    mapNivel[col][fila] = 0;
                }
            }
        }

        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void getImg(){
        //completar innicializando los numeros con imagenes
        try {
           pixeles[0] = new Pixel();
           pixeles[0].imagen = ImageIO.read(getClass().getResourceAsStream(" "));
           pixeles[0].esSolido=false;
            

            pixeles[1] = new Pixel();
            pixeles[1].imagen = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/tierra.PNG"));
            pixeles[1].esSolido= true;
            


            pixeles[2] = new Pixel();
            pixeles[2].imagen = ImageIO.read(getClass().getResourceAsStream("/Imagenes_Lemmings/grass01.png"));
            pixeles[2].esSolido=true;

            pixeles[3] = new Pixel();//para el BLOCKER
            pixeles[3].esSolido= true;

        } catch (Exception e) {
            System.out.println("Hay error acaaa" + e);
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

    public void construirEscalon(int x, int y, int direccion) {
        int colStart = x / baldosa;
        int filaStart = y / baldosa;

        // a cada paso subimos una fila y avanzamos una columna
        for (int i = 1; ; i++) {
            int col = colStart + i * direccion;
            int fila = filaStart - i;

            // si salimos de los límites, detenemos
            if (col < 0 || col >= maxScreenColumnas || fila < 0 || fila >= maxScreenFilas) {
                break;
            }
            // si topamos con algo sólido, no ponemos ese bloque y paramos
            if (pixeles[ mapNivel[col][fila] ].esSolido()) {
                break;
            }
            // pintamos el bloque “grass” (tile 2)
            mapNivel[col][fila] = 2;
        } 
    }

    public void pararse(int x, int y) {
        // Convierte la posición de píxel a columna y fila de baldosa
        int col = x / baldosa;
        int fila = y / baldosa;

        // Verifica límites del mapa
        if (col < 0 || col >= maxScreenColumnas || fila < 0 || fila >= maxScreenFilas) {
            return;
        }
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

        return (pixelActual != null && pixelActual.esSolido()) && (pixelArriba != null && pixelArriba.esSolido());
    }
}
