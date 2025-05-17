package poo.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PantallaInicioPong extends JFrame {
    
    private BufferedImage imagenFondo;

    public PantallaInicioPong(){
        setTitle("Inicio del Juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600); //Con esta linea, la pantalla tendra el mismo tamaño que el juego 
        setLocationRelativeTo(null); //Aqui se centrara la ventana 
        setResizable(false); //Con esto evitamos redimensionamiento

        //Aqui cargamos la imagen utilizando excepciones 
        try{ //con este primer bloque se carga la imagen si todo esta bien 
            imagenFondo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("/ImagenesPong/FondoPong.png"));
        } catch (IOException ex){ //Si ocurre una excepcion pasa a este bloque, lanzando asi el mensaje de la excepcion 
            ex.printStackTrace();
            System.out.println("Error" + ex);
        }
        

        //Creamos un JPanel personalizado con la imagen de fondo 
        JPanel panelFondo = new JPanel(){ //Creamos una clase anonima 
            @Override
            protected void paintComponent(Graphics grafico){
                super.paintComponent(grafico); //Se llama a la implementacion original del Swing 
                if(imagenFondo != null){
                    grafico.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);//imagen que queremos pintar, las coordenadas, y el ancho y alto, el porpio componente
                }
            }
        };

        //configuramos el JPanel
        panelFondo.setLayout(new GridLayout());

        JLabel inicioJuego = new JLabel("Iniciar Juego"); //Creo un label con un texto simple 
        inicioJuego.setHorizontalAlignment(SwingConstants.CENTER); //Con esto alineo el texto en el centro
        inicioJuego.setFont(new Font("Serif",Font.PLAIN,35)); //Con esto seteamos el tamaño y estilo del texto
        inicioJuego.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                iniciarJuego(); //Al hacer click sobre el texto, iniciamos el juego 
            }

            @Override
            public void mouseEntered(MouseEvent e){
                inicioJuego.setForeground(Color.RED); //Al pasar el mouse por encima del texto, el mismo cambia de color 
            }

            @Override
            public void mouseExited(MouseEvent e){
                inicioJuego.setForeground(Color.WHITE);
            }
        });
    }

    private void iniciarJuego(){
        Pong juego = new Pong("Pong",800,600);
        Thread t = new Thread(){
            public void run(){
                juego.run(1.0/60.0);
            }
        };

        t.start();
        dispose();
    }

    public static void main(String[] args) {
        new PantallaInicioPong();
    }

}
