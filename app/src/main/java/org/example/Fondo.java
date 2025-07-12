package org.example;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fondo extends ObjetoGrafico {
	private BufferedImage imagenFondo;
	private static String filename;
	
	public Fondo(String filename,int posX, int posY) {
		super(filename,posX,posY);
		try{
			this.imagenFondo=ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void update(double delta){}

	public void draw(Graphics2D g){
		super.draw(g);
	}
}
