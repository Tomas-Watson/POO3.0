package poo.lemmings;

public class Habilidad {
    public enum Tipo {
        MINER, BUILDER, FLOATER, BLOCKER, CLIMBER, BOMBER, BASHER
    }

    private final Tipo tipo;
    private final Lemming lemming;
    private final Terreno terreno;

    public Habilidad(Tipo tipo, Lemming lemming, Terreno terreno) {
        this.tipo     = tipo;
        this.lemming  = lemming;
        this.terreno  = terreno;
    }

   
    public void suicidar() {
        if (tipo == Tipo.BOMBER) {
            terreno.destruirExplosion((int)lemming.getX(), (int)lemming.getY()); //ULTIMO VALOR ES EL RADIO
        }
       
    }

   
    public void activar() {
        switch (tipo) {
            case MINER:
                // excavar un túnel bajo los pies
                terreno.miner((int)lemming.getX(), (int)(lemming.getY()));
                break;

            case BUILDER:
                // construir escalones delante
                terreno.construirEscalon((int)lemming.getX(),(int) lemming.getY(), lemming.getDireccion());
                break;

            case FLOATER:
                // permitir caída sin daño: reducir velocidad vertical
                if (!lemming.estaEnSuelo(terreno)) {
                    lemming.setVelocidadY(lemming.getVelocidadY() * 0.3);
                }
                break;

            case BLOCKER:
                // al activarse, invierte dirección y marca obstáculo
                lemming.girar();
                terreno.pararse((int)lemming.getX(), (int)lemming.getY() );
                break;

            case CLIMBER:
                // permitir trepar paredes: si frente hay pared, subir
                if (terreno.esPared((int)(lemming.getX() + lemming.getDireccion()),(int)lemming.getY())) {
                    lemming.setY(lemming.getY() - 1);
                }
                break;

            case BOMBER:
                suicidar();
                break;
            case BASHER:
                terreno.minerAdelante((int)(lemming.getX(),(int)(lemming.getY()),lemming.getDireccion());
                break;
            default:
            
        }
    }
}
