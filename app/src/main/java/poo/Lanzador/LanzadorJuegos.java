package poo.Lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import poo.pong.JuegoPong;

public class LanzadorJuegos extends JFrame{
    public LanzadorJuegos(){
        this.setVisible(true); //cuando se crea la ventana se hace visible 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Cuando se presiona la "x" se cierra la ventana
        this.setBackground(Color.BLACK);//Seteamos el color del fondo 

        JPanel panelTitulo = new JPanel(); //Se crea un panel 
        panelTitulo.setBackground(Color.BLACK);//Se setea el color del fondo del panel 

        JLabel titulo = new JLabel("Lanzador de Juegos");//Se crea un label que dice "Lanzador de Juegos"
        titulo.setBackground(Color.WHITE);//Se setea el color del LABEL
        titulo.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 30));//Se setea el tipo y tamaño de la fuente
        panelTitulo.add(titulo);//El LABEL se agrega al panel 

        JPanel juegos = new JPanel();
        juegos.setLayout(new FlowLayout());//Esto se utiliza para que los recuadros de juego se coloquen por defecto uno tras otro

        juegos.add(new RecuadroJuego(new JuegoPong(),this));//el this es la propia instancia "LanzadorJuegos"

        this.add(juegos, BorderLayout.CENTER); //En el centro se seteara el panel de juegos. 
        this.add(panelTitulo, BorderLayout.NORTH); //En la parte norte se seteara el panel del titulo

        this.pack(); //Ajusta automaticamente el tamaño de la ventana
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());//Fuerza a la ventana a ocupar todo el ancho de la pantalla del monitor
    }
}
