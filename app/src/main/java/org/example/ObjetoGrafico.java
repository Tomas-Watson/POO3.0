package org.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjetoGrafico extends Rectangle{
	protected BufferedImage imagen = null;

	protected double positionX = 0;
	protected double positionY = 0;
	protected Color color = Color.WHITE;
	protected int ancho = 0;
	protected int alto = 0;
    
	
	public ObjetoGrafico(String filename) {
    	try {
			imagen= ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));
			
		} catch (IOException e) {
			System.out.println("ZAS! en ObjectoGrafico "+e);
		}
		this.positionX=posX;
		this.positionY=posY;
    }

	//para el  pong

	public ObjetoGrafico(int posX, int posY, int ancho, int alto){
		super(posX, posY, ancho, alto);
	}

	public ObjetoGrafico(int ancho, int alto){
		super(ancho, alto);
	}

	@Override
    public double getWidth(){
		return (double) this.ancho;
	}
	@Override
	public double getHeight(){
		return (double) this.alto;
	}

	public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

   	public void draw(Graphics2D g2) {
		g2.drawImage(imagen,(int) this.positionX,(int) this.positionY,null);
  	}

	@Override
	public double getX(){
		return positionX;
	}
	@Override
	public double getY(){
		return positionY;
	}
}