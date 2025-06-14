package poo.Lanzador;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import poo.lemmings.Lemmings;
import poo.pong.Pong;

public class SistemaJuegos extends JPanel implements ActionListener {
    Pong juego;
    Lemmings juego2;

    Thread t;
    Thread t1;

    public SistemaJuegos(){
        int filas = 0;
        int columnas = 1;
        int separacion = 10; 

        this.setLayout(new GridLayout(filas ,columnas,separacion,separacion));
        setVisible(true);
        String[] arrEtiquetas = {"JuegoPong","Lemmings"};

        JButton boton;

        for(String etiqueta:arrEtiquetas){
            boton=new JButton(etiqueta);
            boton.addActionListener(this);
            this.add(boton);
            
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("JuegoPong")){
            juego = new Pong();

            t = new Thread() {
			    public void run() {
					juego.run(1.0 / 60.0);
				}
			};

			t.start();
        } 

        if(e.getActionCommand().equals("Lemmings")){
            juego2 = new Lemmings();
            t1 = new Thread(() -> juego2.run(1.0 / 60.0));
            t1.start();
        } 

    }

    public static void main(String...z){
		JFrame f=new JFrame("Lanzador");

		f.add(new SistemaJuegos());
		WindowListener l=new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            };
        };

        f.addWindowListener(l);
        f.pack();
        f.setVisible(true);
	 	f.setLocationRelativeTo(null); // PAra que la pantalla este al medio cuando se inicie
	}

}
