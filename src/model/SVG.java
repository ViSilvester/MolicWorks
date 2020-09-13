package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class SVG implements Serializable{
	
	private String[] SVG_Origem;
	private int larguraOrigem;
	private int alturaOrigem;
	
	private String[] SVG_Final;
	private int largura;
	private int altura;
	private int x;
	private int y;
	
	
	public SVG(String SVG_Path, int x , int y, int largura, int altura) {
		
		SVG_Origem = SVG_Path.split(" ");
		SVG_Final = SVG_Origem.clone();
		this.setPosition(x, y);
		
		int maiorX = 0;
		int menorX = 10000;
		int maiorY = 0;
		int menorY = 10000;
		boolean xy = false;
		
		for(int i=0;i <SVG_Final.length; i++) {
			
			
			if(SVG_Final[i].matches("[0-9]+")) {
				int numero = Integer.parseInt(SVG_Final[i]);
				if(xy == false) {
					if (numero > maiorX) {
						maiorX = numero;
					}
					if(numero < menorX) {
						menorX = numero;
					}
				}
				else{
					if (numero > maiorY) {
						maiorY = numero;
					}
					if(numero < menorY) {
						menorY = numero;
					}
				}
				xy = !xy;
			}
			else {
				xy = false;
			}
		}
		
		this.largura = maiorX - menorX;
		this.altura = maiorY - menorY;
		this.larguraOrigem = maiorX - menorX;
		this.alturaOrigem = maiorY - menorY;
		
		//System.out.println("Maior X=" + maiorX + ", MaiorY = "+ maiorY);
		//System.out.println("largura ="+this.largura+", altura= "+this.altura);
		
		this.setScale(largura, altura);
	
	}
	
	public String getSVG() {
		String str= "";
		for(int i=0; i<SVG_Final.length; i++) {
			str = str+ SVG_Final[i]+" ";
		}
		return str;
	}
	
	public void setScale(int largura, int altura) {
		
		this.setPosition(this.x, this.y);
		
		boolean xy = false;
		
		for(int i=0;i <SVG_Final.length; i++) {
			
			
			if(SVG_Origem[i].matches("[0-9]+")) {
				int numero = Integer.parseInt(SVG_Final[i]);
				if(xy == false) {
					double escalar = (double)largura /(double)this.larguraOrigem;
					int novoPonto = (int) (((numero - this.x) * escalar)+this.x);
					SVG_Final[i] = ""+ novoPonto;
				}
				else{
					double escalar = (double)altura /(double)this.alturaOrigem;
					int novoPonto = (int) (((numero - this.y) * escalar)+this.y);
					SVG_Final[i] = ""+ novoPonto;
				}
				xy = !xy;
			}
			else {
				xy = false;
			}
		}
		
		this.largura = largura;
		this.altura = altura;
		
	}
	
	public void setPosition(int x, int y) {
		boolean xy = false;
		for(int i=0; i<SVG_Origem.length; i++) {
			if(SVG_Origem[i].matches("[0-9]+")) {
				int numero = Integer.parseInt(SVG_Origem[i]);
				if(xy == false) {
					SVG_Final[i] = ""+(numero+ x);
				}
				else {
					SVG_Final[i] = ""+(numero+ y);
				}
				xy = !xy;
			}
			else {
				xy = false;
			}
		}
		
		this.x = x;
		this.y = y;
		
	}
	
	public void render(GraphicsContext gc){
		
		gc.setLineWidth(1);
		gc.beginPath();
		gc.appendSVGPath(this.getSVG());
		gc.closePath();
		gc.fill();
		gc.stroke();
	}
	

}
