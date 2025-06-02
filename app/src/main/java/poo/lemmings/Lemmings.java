package poo.lemmings;


import java.awt.*;

import com.entropyinteractive.JGame;

public class Lemmings extends JGame {
   
    public final static int HEIGHT = 600;
    public final static int WIDTH = 800;
    private static final Object Lemming = null;
    private boolean enPausa = false;
    private boolean finJuego = false;
    private boolean pPresionado = false;
    private boolean enterPresionado = false;
    
    public static void main(String[] args) {
        Lemmings game = new Lemmings();
        game.run(1.0 / 60.0);
        System.exit(0);
    }

    public Lemmings() {
        
        super("Lemmings ", WIDTH, HEIGHT);

    }

    @Override
    public void gameDraw(Graphics2D g) {
        
    }

    @Override
    public void gameShutdown() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameShutdown'");
    }

    @Override
    public void gameStartup() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameStartup'");
    }

    @Override
    public void gameUpdate(double arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gameUpdate'");
    }

    public Object getLemming() {
        return Lemming;
    }
    
}
