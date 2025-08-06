package util;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonido {
    //Cada instancia de sonido contorla su propia musica 
    private Clip musica;

    public void reproducirEfecto(String path) {
        new Thread(() -> {
            try {
                URL url = getClass().getClassLoader().getResource(path);
                if (url == null) {
                    System.err.println("No se encontró el sonido: " + path);
                    return;
                }

                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void reproducirMusica(String archivo) {
        try {
            detenerMusica(); // por si ya había una sonando

            URL url = getClass().getClassLoader().getResource(archivo);
            if (url == null) {
                throw new IllegalArgumentException("Archivo no encontrado: " + archivo);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            musica = AudioSystem.getClip();
            musica.open(audioInputStream);
            musica.loop(Clip.LOOP_CONTINUOUSLY);
            musica.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void detenerMusica() {
        if (musica != null && musica.isRunning()) {
            musica.stop();
            musica.close();
        }
    }
}