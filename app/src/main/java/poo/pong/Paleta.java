package poo.pong;
import org.example.ObjetoGraficoMovible;
import org.example.ObjetoGrafico;

public class Paleta extends ObjetoGrafico implements ObjetoGraficoMovible {
    private int ancho= 10;
    private int alto= 100;
    private int velocidad= 10;

    public Paleta(int x, int y, Color color){
        super("paleta.png");
        this.positionX = x;
        this.positiony = y;
        this.setHeight(100);
        this.setWidth(10);
        this.imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = this.imagen.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, ancho, alto);
        g2d.dispose();
    }
   
    @Override
    public void moverse(double delta) {
        if(Keyboard.iskeyDown(Keyboard.KEY_UP)){
            this.positionY -= velocidad * delta;
        }else if(Keyboard.iskeyDown(Keyboard.KEY_DOWN)){
            this.positionY += velocidad * delta;
        }
    }
}
