package poo.lemmings;

import java.util.List;

import org.example.ObjetoGrafico;

public class Terreno extends ObjetoGrafico {
    private boolean obstaculo;
    //Hay que hacer una clase relieve
    private final List<Relieve> relieves = null;

   public Terreno(String filename) {
        super(filename);
        //TODO Auto-generated constructor stub
    }


    /**
     * Marca el terreno como destruido (sin obstáculo).
     */
    public void destruccion() {
        this.obstaculo = false;
        this.relieves.clear();
        
    }

    // Agrega un relieve (detalle visual) al terreno.
    public void agregarRelieve(Relieve relieve) {
        this.relieves.add(relieve);
        
    }

    /* Quita un relieve del terreno.
     */
    public void sacarRelieve(Relieve relieve) {
        this.relieves.remove(relieve);
      
    }

    /*@Override
    public void draw(GraphicsContext gc) {
        // Dibuja base del terreno
        super.draw(gc);
        // Si es obstáculo, pinta zona bloqueada
        if (obstaculo) {
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        // Dibuja cada relieve encima
        for (Relieve r : relieves) {
            r.draw(gc);
        }
    }

    private void actualizarGrafico() {
        // Recarga o invalida el sprite/textura para refrescar la vista
        // implementación dependiente de ObjetoGrafico
    }
    
    ESTOS METODOS VAN EN VLemmings*/

    
}
