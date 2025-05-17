package poo.lemmings;

import org.example.ObjetoGrafico;


public class Salida extends ObjetoGrafico {

    private int contadorRecibidos;


    public Salida(String filename) {
        super(filename);
        
    }


    public void salvarLemming(Lemming lemming) {
       //removerLemming(lemming); 
        contadorRecibidos++;
    }

    public int getContadorRecibidos() {
        return contadorRecibidos;
    }
}
