package model.estrutura;

import java.io.Serializable;

public class Rect implements Serializable{
	Ponto coordenada = new Ponto(0,0,0,0);
	int largura;
	int altura;
	
	public Rect(int x, int y, int l, int a) {
		coordenada.setX(x);
		coordenada.setY(y);
		this.largura = l;
		this.altura = a;
	}
	
	public int getX() {
		return coordenada.getXAbsoluto();
	}
	public int getY() {
		return coordenada.getYAbsoluto();
	}
	
	public int getLargura() {
		return largura;
	}
	
	public int getAltura() {
		return altura;
	}

	public void setX(int x) {
		this.coordenada.setX(x);
	}

	public void setY(int y) {
		coordenada.setY(y);
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	public boolean rectIntersect(int x, int y, int largura, int altura) {
		
		if( ((this.getX() >= x && this.getX() <= x + largura) ||
			(this.getX() + this.getLargura() >= x && this.getX() + this.getLargura() <= x + largura)) &&
			((this.getY() >= y && this.getY() <= y + altura) ||
			(this.getY() + this.getLargura() >= y && this.getY() + this.getLargura() <= y + altura))) {
			return true;
		}
		return false;
	}
	
	public Ponto getCoordenada() {
		return coordenada;
	}
	
	

}
