package poo.lemmings;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {
        public static Clip clip;

    public static void iniciar(String nombre){
        try {
            InputStream inputStream = Sonido.class.getResourceAsStream("/musica/musicLemming/" + nombre + ".wav");

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(bufferedInputStream);
            
            clip = AudioSystem.getClip();

            clip.open(audioInput);

            clip.start();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void parar(){
        clip.stop();
    }

    public static void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void cambiarCancion(String ruta){
        parar();
        iniciar(ruta);
        loop();
    }
}
