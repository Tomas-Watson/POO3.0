package poo.lemmings;

import org.example.ObjetoGrafico;
import org.example.ObjetoGraficoMovible;

public class Lemming extends ObjetoGrafico implements ObjetoGraficoMovible{
    private final int vida=1;

    public Lemming(String filename) {
        super(filename);
    }

    

    @Override
    public void moverse(double delta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moverse'");
    }
        
}
