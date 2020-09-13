package model.estrutura;

import java.io.Serializable;

public class Ponto implements Serializable {
	
	private int relativoX;
	private int relativoY;
	private int x;
	private int y;
	public Ponto(int relativoX, int relativoY, int x, int y) {
		super();
		this.relativoX = relativoX;
		this.relativoY = relativoY;
		this.x = x;
		this.y = y;
	}
	
	public int getXAbsoluto() {
		return x + relativoX;
	}
	public int getYAbsoluto() {
		return y + relativoY;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRelativoX() {
		return relativoX;
	}
	public int getRelativoY() {
		return relativoY;
	}
	public void setRelativoX(int relativoX) {
		this.relativoX = relativoX;
	}
	
	public void setRelativoY(int relativoY) {
		this.relativoY = relativoY;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void replicarPonto(Ponto p) {
		this.relativoX = p.relativoX;
		this.relativoY = p.relativoY;
		this.x = p.x;
		this.y = p.y;
	}
	
	public Ponto clone() {
		return new Ponto(relativoX,relativoY,x,y);
	}
	
}
