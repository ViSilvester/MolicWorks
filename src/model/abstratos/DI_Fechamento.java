package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Node;
import model.SVG;
import model.TextBox;
import model.estrutura.Cor;
import model.estrutura.Ponto;

public class DI_Fechamento extends Node {

	SVG graficoExterno;
	public DI_Fechamento(int posx, int posy, int largura, int altura, SVG SVGPath) {
		super(posx, posy, largura, altura, SVGPath);
		super.texto = new TextBox(posx, posy, 0,0,"");
		String svg = "M 7 0 L 7 0 C 14 0 14 10 7 10 L 7 10 C 0 10 0 0 7 0 M 7 2 C 3 2 3 8 7 8 C 11 8 11 2 7 2";
		graficoExterno = new SVG(svg, 0, 0,20, 20);
		super.background = new Cor(0,0,0);
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, 0));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, altura));
		super.pontosAncoragem.add(new Ponto(posx, posy, 0, altura/2));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura, altura/2));
		
	}
	
	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		this.graficoExterno.setPosition(super.getX(), super.getY());
		this.graficoExterno.setScale(super.getLargura(), super.getAltura());
		graficoExterno.render(gc);
		
		if(selected) {
        	gc.setLineDashes(5);
        	gc.setFill(Color.rgb(200, 200, 255, 0.2));
        	gc.setStroke(Color.AQUA);
        	gc.fillRect(this.getX()-4, this.getY()-4, this.getLargura()+8, this.getAltura()+8);
        	gc.strokeRect(this.getX()-4, this.getY()-4, this.getLargura()+8, this.getAltura()+8);
        	gc.strokeRect(this.getX()-4, this.getY()-4, this.getLargura()+8, this.getAltura()+8);
        	gc.setLineDashes(0);
        	
        }
	}
	
	@Override
	public void setXY(int x, int y) {
		super.setXY(x, y);
	}
	
	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}
	
	public void updatePontosAncoragem() {
		super.pontosAncoragem.get(0).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura()/2, 0));
		super.pontosAncoragem.get(1).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura()/2, this.getAltura()));
		super.pontosAncoragem.get(2).replicarPonto(new Ponto(this.getX(), this.getY(), 0, this.getAltura()/2));
		super.pontosAncoragem.get(3).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura(), this.getAltura()/2));
	}
	
	public void updateElementos() {
		super.texto.setxRelativo(this.getX());
		super.texto.setyRelativo(this.getY());
	}
	
	@Override
	public Node clone() {
		
		DI_Fechamento node = new DI_Fechamento(this.getX(), this.getY(), this.getLargura(), this.getAltura(), this.svg);
		node.setBackground(this.getBackground().clone());
		node.setSvg(this.getSvg());
		node.setTexto(this.getTexto().clone());
		List<Ponto> pontos = new LinkedList<Ponto>();
		pontos.add(this.getPontosAncoragem().get(0).clone());
		pontos.add(this.getPontosAncoragem().get(1).clone());
		pontos.add(this.getPontosAncoragem().get(2).clone());
		pontos.add(this.getPontosAncoragem().get(3).clone());
		node.setPontosAncoragem(pontos);
		
		return node;
	}

}
