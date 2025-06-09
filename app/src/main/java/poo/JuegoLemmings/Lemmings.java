package JuegoLemmings;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;

public class Lemmings extends JGame{

    public static final int originalTileSize = 16;
    public static final int escala = 3;

    public static final int tileSize = originalTileSize * escala;
    private static final int maxColumnas = 16;
    private static final int maxFilas = 12;
    private static final int ANCHO_PANTALLA = tileSize * maxColumnas; //768 / 32  =24
    private static final int ALTO_PANTALLA = tileSize * maxFilas; //576 / 32 = 18 

    private boolean finJuego = false;
    private boolean enPausa = false;
    private boolean pPresionado = false;
    private boolean rPresionado = false;
    private boolean nivelCompletado = false;
    private boolean gameOver = false;
    
    private boolean turbo = false;        
    private double timerapido = 1.0; 

    private Pixel pixeles[];
    private List<Personaje> lemming;
    private Personaje personajeSeleccionado;
    private Terreno terreno;
    private Entrada entrada;
    private Salida salida;
    
    //Variables para la creacion de lemmings
    private int lemmingsPorSpawn;
    private int lemmingsSpawned = 0;
    private int lemmingsNecesario;
    private double tiempoDesdeUltimo = 0.0;
    private final double spawnInterval = 1.5;

    //Variables para el HUD
    private static final int PANEL_ALTURA = 50;
    private ContadorLemmings contador;

    private int nivelActual = 0;
    private final String[] archivosNivel = {"/archivos_txt/nivel1.txt","/archivos_txt/nivel2.txt","/archivos_txt/nivel3.txt"};
    //Un point representa una coordenada 2D
    private final Point[] salidaPosiciones = {new Point(13* tileSize, 8 * tileSize),new Point(10 * tileSize, 8 * tileSize),new Point(12 * tileSize, 8 * tileSize)};
    
    //Rango minimo y maximo de personajes que aparecen
    private static final int MIN_LEMMINGS = 4;
    private static final int MAX_LEMMINGS = 10;
    private final Random random = new Random();

    private BarraHabilidad barra;
    private int habilidadActual = - 1;

    private Temporizador temp;

    public Lemmings() {
        super("Lemmings", ANCHO_PANTALLA, ALTO_PANTALLA);
    }

    @Override
    public void gameDraw(Graphics2D g) {
        //Seteo el color del fondo
        g.setColor(Color.black);

        //Cargo el terreno
        terreno.draw(g);

        //Aqui dibujo la Entrada
        entrada.draw(g);

        //Aqui dibujo la Salida
        salida.draw(g);

        //Aqui dibujo a los Personajes
        for (Personaje p : lemming) {
            p.draw(g);

            if (p == personajeSeleccionado) {
                // obtén dimensiones del sprite actual
                int w = p.framesDr[p.animFrame].getWidth(); //Obtiene el ancho de la imagen
                int h = p.framesDr[p.animFrame].getHeight(); //Obtiene el alto de la imagen

                g.setColor(Color.yellow);
                g.drawRect((int)p.getX(), (int)p.getY(), w, h);
                g.setColor(Color.white);
            }
        }

        //Dibujo el contador
        contador.draw(g);

        //Dibujo el temporizador
        temp.dibujar(g);

        if (enPausa) {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));

            FontMetrics metrics = g.getFontMetrics();
            int x = (ANCHO_PANTALLA - metrics.stringWidth("¿Por qué pausas tremendo juegazo?")) / 2;
            int y = (ALTO_PANTALLA / 2) - 70;

            
            g.drawString("¿Por qué pausas tremendo juegazo?", x, y); 
        }

        barra.draw(g);

        if(nivelCompletado){
            g.setColor(new Color(0,0,0,150)); //Esto es un fondo semitransparente
            g.fillRect(0,0,ANCHO_PANTALLA,ALTO_PANTALLA);

            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,24));

            String mensaje = "¡Nivel completado - Muy bien chinchulin!";
            FontMetrics fm = g.getFontMetrics();

            int x = (ANCHO_PANTALLA - fm.stringWidth(mensaje)) / 2;
            int y = ALTO_PANTALLA / 2 - 20;

            g.drawString(mensaje,x,y);

            String instruccion = "Presiona ENTER para avanzar de nivel";
            int x2 = (ANCHO_PANTALLA - fm.stringWidth(instruccion)) / 2;
            g.drawString(instruccion, x2, y + 40);
        } 

        if(finJuego){
            g.setColor( new Color(0,0,0,200));
            g.fillRect(0,0,ANCHO_PANTALLA,ALTO_PANTALLA);

            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,24));

            String linea1 = "¡Terminaste el juego!";
            String linea2 = "¡Felicitaciones!";
            FontMetrics fm = g.getFontMetrics();

            int x1 = (ANCHO_PANTALLA - fm.stringWidth(linea1)) / 2;
            int y1 = ALTO_PANTALLA / 2 - fm.getHeight();
            g.drawString(linea1, x1, y1);

            int x2 = (ANCHO_PANTALLA - fm.stringWidth(linea2)) / 2;
            int y2 = y1 + fm.getHeight() + 10;
            g.drawString(linea2, x2, y2);

            String instruccion = "Presiona ESC para salir";
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            int xi = (ANCHO_PANTALLA - g.getFontMetrics().stringWidth(instruccion)) / 2;
            g.drawString(instruccion, xi, y2 + 40);
        }

        if (gameOver) { 
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, ANCHO_PANTALLA, ALTO_PANTALLA);

            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            String msg1 = "¡Como vas a perder en un juego así!";
            FontMetrics fm = g.getFontMetrics();
            int x1 = (ANCHO_PANTALLA - fm.stringWidth(msg1)) / 2;
            int y1 = ALTO_PANTALLA / 2 - fm.getHeight();
            g.drawString(msg1, x1, y1);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String msg2 = "Presiona R para reintentar o ESC para salir";
            int x2 = (ANCHO_PANTALLA - fm.stringWidth(msg2)) / 2;
            g.drawString(msg2, x2, y1 + 40);
            return; //no dibuja nada
        }
        
    }

    @Override
    public void gameShutdown() {
        
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    @Override
    public void gameStartup() {
        Log.info(getClass().getSimpleName(), "Ejecutando el juego");
        
        int colEntrada = 2;
        int filaEntrada = 1;

        //Aqui cargo el terreno
        pixeles = new Pixel[4];

        //Aqui cargo la entrada
        entrada = new Entrada();
        entrada.setPosition(colEntrada * tileSize,filaEntrada * tileSize);
        
        //Aqui cargo a los lemmings
        lemming = new ArrayList<>();

        //Cargo nivel actual, y ademas se carga el terreno
        cargarNivel(nivelActual);

        //Cargo el temporizador
        temp = new Temporizador(60); // 2 minutos

        //Cargo las habilidades
        Habilidad[] habilidad = new Habilidad[Habilidad.Tipo.values().length];
        for (int i = 0; i < habilidad.length; i++) {
            habilidad[i] = new Habilidad(Habilidad.Tipo.values()[i], null, terreno);
        }

        //Cargo la barra de habilidades
        barra = new BarraHabilidad(ANCHO_PANTALLA, ALTO_PANTALLA);

    }

    @Override
    public void gameUpdate(double delta) {

        Mouse mouse = this.getMouse();
        Keyboard keyboard = this.getKeyboard();
        double EscalaDelta = delta * timerapido;
        
        if(!finJuego){
            
            // Pausar/reanudar el juego con 'P'
            if (keyboard.isKeyPressed(KeyEvent.VK_P)) {
                if (!pPresionado) {
                    enPausa = !enPausa;
                    pPresionado = true;
                }
            } else {
                pPresionado = false;
            }

            if(keyboard.isKeyPressed(KeyEvent.VK_R)){
                if(!rPresionado){
                    cargarNivel(nivelActual);
                    nivelCompletado = false;
                    enPausa = false;
                    finJuego = false;
                }
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)){
                System.exit(0);
            }

            if (!enPausa) {

                temp.actualizar(EscalaDelta);
                Iterator<Personaje> it = lemming.iterator();

                while (it.hasNext()) {

                    Personaje p = it.next();
                    p.moverse(EscalaDelta);

                    if (!p.estaVivo()) {
                        it.remove();
                        continue;
                    }

                    // Si alcanza la salida:
                    int px = (int) p.getX(); //Tomo la posicion en X del personaje
                    int py = (int) p.getY(); //Tomo la posicion en Y del personaje

                    int anchoSprite = p.framesDr[p.animFrame].getWidth();//Tomo el ancho de la imagen del personaje
                    int altoSprite  = p.framesDr[p.animFrame].getHeight();//Tomo el alto de la imagen del personaje

                    int sx = (int) salida.getX(); //Tomo la posicion en X de la salida
                    int sy = (int) salida.getY(); //Tomo la posicion en Y de la salida

                    boolean colisionSalida = px + anchoSprite > sx && px < sx + tileSize && py + altoSprite > sy && py < sy + tileSize;
                    //El boolean colisionSalida por defecto retorna TRUE
                    if (colisionSalida) {
                        if (contador.getTotalSalvados() < lemmingsNecesario) {
                            contador.incrementar();
                        }
                        it.remove();

                        // 3) Si justo acabo de llegar al necesario, se muestra el cartel del fin de nivel
                        if (contador.getTotalSalvados() >= lemmingsNecesario) {
                            nivelCompletado = true;
                            enPausa = true;
                        }
                    }

                    //Si el personaje se sale de los limites de la pantalla, lo remuevo y muestro un cartel de perdiste
                    if (px + anchoSprite < 0 || px > ANCHO_PANTALLA|| py + altoSprite < 0 || py > ALTO_PANTALLA) {
                        it.remove();
                        continue;  // pasamos al siguiente lemming
                    }
                }

                //Controla la aparicion de los lemmings
                if (lemmingsSpawned < lemmingsPorSpawn) {

                    tiempoDesdeUltimo += EscalaDelta;
                    if (tiempoDesdeUltimo >= spawnInterval) {

                        tiempoDesdeUltimo = 0.0;
                        spawnNuevoLemming();
                        lemmingsSpawned++;
                    }
                }

                //Le doy movimiento a la entrada
                entrada.update(EscalaDelta);

                // Detectar clic izquierdo
                if (mouse.isLeftButtonPressed()) {
                    int mx = mouse.getX() - 2;
                    int my = mouse.getY() + 23;

                    // Comprueba si clicaste sobre la barra de habilidades
                    int sel = barra.manejarClic(mx, my);
                    if (sel >= 0) {

                        habilidadActual = sel;
                        personajeSeleccionado = null;   // deselecciona visualmente
                        Habilidad.Tipo tipoSel = Habilidad.Tipo.values()[sel];

                        if (tipoSel == Habilidad.Tipo.VELOX2) {
                            turbo = !turbo;
                            timerapido = turbo ? 2.0 : 1.0;
            
                            habilidadActual = -1;
                            personajeSeleccionado = null;
                            return;   
                        }
                        if (tipoSel == Habilidad.Tipo.NUKE) {
                            for (Personaje p : lemming) {
                                p.setHabilidadPendiente(new Habilidad(Habilidad.Tipo.NUKE, p, terreno));
                                
                            }
                            // limpiamos selección para no volver a activarlo
                            habilidadActual = -1;
                            personajeSeleccionado = null;
                        }
                    } else {
                        // Verifica si se hizo click sobre un personaje
                        Personaje clicado = null;
                        final int PAD = 8;
                        for (Personaje p : lemming) {
                            int px = (int)p.getX();
                            int py = (int)p.getY();

                            int w  = p.framesDr[p.animFrame].getWidth();
                            int h  = p.framesDr[p.animFrame].getHeight();

                            Rectangle r = new Rectangle(px - PAD, py - PAD, w + PAD*2, h + PAD*2);
                            if (r.contains(mx, my)) {
                                clicado = p;
                                break;
                            }
                            personajeSeleccionado = clicado;
                        }

                        if (clicado != null && habilidadActual >= 0) {
                            // asignar sólo al lemming clicado
                            Habilidad hab = new Habilidad(Habilidad.Tipo.values()[habilidadActual], clicado, terreno);
                            clicado.setHabilidadPendiente(hab);
                            habilidadActual = -1;
                            personajeSeleccionado = null;
                        }
                    }
                }

            }

        }
        
        //Si el nivel fue completado y presiono la tecla enter paso al siguiente nivel
        if (nivelCompletado && keyboard.isKeyPressed(KeyEvent.VK_ENTER)) {
            nivelCompletado = false;
            enPausa = false;
            
            //Si no es el ultimo nivel 
            if (nivelActual < archivosNivel.length - 1) {
                nivelActual++;
                cargarNivel(nivelActual);
            } else {
                finJuego = true;
            }
        }

        //Si se llego al ultimo nivel y se logra pasarlo con exito, debo presionar ENTER y se deberia mostrar un cartel 
        if (finJuego) {
            if (keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                System.exit(0);
            }
            return;
        }

        /*Si el nivel no se completo, no es fin del juego y no es gameOver
         * detecto la derrota
         */
        if (!nivelCompletado && !finJuego && !gameOver) {
            boolean todosSpawneados = lemmingsSpawned >= lemmingsPorSpawn;
            boolean listaVacia = lemming.isEmpty();
            boolean noAlcanzaste = contador.getTotalSalvados() < lemmingsNecesario;

            if (temp.termino() || (todosSpawneados && listaVacia && noAlcanzaste)) {
                gameOver = true;
                enPausa = true;
            }
        }

        //Tras una derrota, le doy la opcion al usuario de reiniciar con R o salir con ESC
        if (gameOver) {
            if (getKeyboard().isKeyPressed(KeyEvent.VK_R)) {
                // reiniciar mismo nivel
                gameOver = false;
                enPausa = false;
                cargarNivel(nivelActual);
            } else if (getKeyboard().isKeyPressed(KeyEvent.VK_ESCAPE)) {
                System.exit(0);
            }
            return; //NO se procesa mas 
        }
    }

    private void spawnNuevoLemming() {
        //Posición de la entrada 
        int xEntrada = (int) entrada.getX() + 40;
        int yEntrada = (int) entrada.getY();

        //Primero creo al Personaje asumiendo que "yEntrada" es la base
        Personaje p = new Personaje(xEntrada, yEntrada, terreno);

        int col = (xEntrada + 2) / tileSize;
        int filaGround = -1;

        int filaEntrada = yEntrada / tileSize;
        for (int f = filaEntrada + 1; f < maxFilas; f++) {
            if (terreno.mapNivel[col][f] != 0) {
                filaGround = f;
                break;
            }
        }

        //Si encontramos un ground válido, calculamos startY según su alto de sprite:
        int altoSprite = p.framesDr[0].getHeight();
        int spawnY;
        if (filaGround >= 0) {
            // Colocamos al personaje un sprite por encima de la baldosa de suelo
            spawnY = filaGround * tileSize - altoSprite;
        } else {
            // Si no hay suelo abajo (caso extraño), lo dejamos justo en la entrada:
            spawnY = yEntrada - altoSprite;
        }
        p.setY(spawnY);

        //Finalmente agregamos el nuevo lemming a la lista
        lemming.add(p);
    }

    private void cargarNivel(int nivel) {

        //Creo una cantidad random de lemmings con un minimo y un maximo
        lemmingsPorSpawn = random.nextInt(MAX_LEMMINGS - MIN_LEMMINGS + 1) + MIN_LEMMINGS;
        lemmingsNecesario = Math.max(1,lemmingsPorSpawn - 2);

        //Genero el contador y le paso la cantidad de Lemmings Necesario y la cantidad que se generar en ese nivel
        if(contador == null){
            contador = new ContadorLemmings(576, 480, ANCHO_PANTALLA/4, PANEL_ALTURA, lemmingsNecesario);
            contador.setLemmingsTotales( lemmingsPorSpawn);

        } else {
            contador.setNecesarios(lemmingsPorSpawn);
            contador = new ContadorLemmings(576, 480, ANCHO_PANTALLA/4, PANEL_ALTURA, lemmingsNecesario);
            contador.setLemmingsTotales(lemmingsPorSpawn);
        }

        //Al pasar de nivel se resetea el contador
        if (salida != null){
            salida.resetContador();
        }

        //Cargo el terreno
        terreno = new Terreno(pixeles);
        terreno.getImg();
        terreno.CargarTerreno(archivosNivel[nivel]);

        //Coloco la entrada 
        entrada.setPosition(2 * tileSize, 1 * tileSize);

        // Creo la misma salida en la posición del nivel
        // Si ya existía, cambio su posición
        if (salida == null) {
            salida = new Salida();            
        }
        Point pos = salidaPosiciones[nivel];
        salida.setPosition(pos.x, pos.y);

        //Reinicio el temporizador en 60segundos, con el paso de niveles
        temp = new Temporizador(60.0);

        //Reset de lemmings y contador
        lemming.clear();
        lemmingsSpawned = 0;
        finJuego     = false;
    }
}