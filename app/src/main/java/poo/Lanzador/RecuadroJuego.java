package poo.Lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import poo.Juego;

public class RecuadroJuego extends JPanel implements ActionListener {
    private JButton boton;
    private Juego juego;
    private LanzadorJuegos lanzador;

    public RecuadroJuego(Juego juego,LanzadorJuegos lanzador){
        this.juego = juego;
        this.lanzador = lanzador;

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5,true));
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(400,500));

        //No hace falta explicar esto, ya que se explica en la clase lanzador
        JPanel tituloPanel = new JPanel();
        JLabel titulo = new JLabel(juego.getNombre());
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        tituloPanel.add(titulo);

        //Se crea un Panel donde se agrega la descripcion, desarrolladores y version del juego en ejecucion
        JPanel abajo = new JPanel();
        abajo.setLayout(new BoxLayout(abajo, BoxLayout.Y_AXIS));
        abajo.add(new JLabel("Descripcion " + juego.getDescripcion()));
        abajo.add(new JLabel("Desarrolladores "+ juego.getDesarrolladores()));
        abajo.add(new JLabel("Version "+ juego.getVersion()));

        JPanel foto = new JPanel();
        boton = new JButton(juego.getImagenPortada());
        boton.addActionListener(this); //Cuando se hace click en la imagen 
        foto.add(boton);

        this.add(tituloPanel, BorderLayout.PAGE_START);
        this.add(foto, BorderLayout.CENTER);
        this.add(abajo, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent Ae){
        if (juego.isImplementado()) { //Comprueba si el juego esta listo, se oculta la ventana, sino la vuelve a mostrar
            lanzador.setVisible(false);
        } else {
            lanzador.setVisible(true);
        }

        if(!Ae.getSource().equals(boton))return; juego.run(); //Lo de adentro del if verifica que reaccionamos solo cuando se pulsa el boton
        //juego.run lanzara la logica del juego

    }
}
