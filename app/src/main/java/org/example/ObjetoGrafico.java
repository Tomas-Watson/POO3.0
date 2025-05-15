package org.example;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjetoGrafico {
	protected BufferedImage imagen = null;

	protected double positionX = 0;
	protected double positionY = 0;
	
    public ObjetoGrafico(String filename) {
    		try {
				imagen= ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));

			} catch (IOException e) {
				System.out.println("ZAS! en ObjectoGrafico "+e);
			}
    }

	public int getWidth(){
		return imagen.getWidth();
	}
	public int getHeight(){

		return imagen.getHeight();
	}

	public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

   	public void display(Graphics2D g2) {
		g2.drawImage(imagen,(int) this.positionX,(int) this.positionY,null);
  	}

	public double getX(){
		return positionX;
	}

	public double getY(){
		return positionY;
	}
}