package poo.lemmings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

import org.example.ObjetoGrafico;

import poo.lemmings.Niveles.Nivel;

public class Entrada extends ObjetoGrafico {
    
    
  

    public Entrada(String filename) {
        super(filename);
    }



    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public double getX() {
        return this.positionX;
    }
    public double getY() {
        return this.positionY;
    }

    /**Genera un nuevo Lemming y lo añade al nivel.
     * No se si ponerlo aca o que el JLemming lo haga
    */
    public void SpawnLemmings(int CantLemmings) {
            for (int i = 0; i < CantLemmings; i++) {
            Lemming lemming = CrearLemming();
            //mostrarEnPantalla(lemming); // Asumiendo que el nivel tiene este método
        }
        
    }

    public Lemming CrearLemming() {
        Lemming nuevo = new Lemming();
       //agregarLemming(nuevo);
    }
    
}
