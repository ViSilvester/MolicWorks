package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import model.Node;
import model.SVG;
import model.TextBox;
import model.estrutura.Cor;
import model.estrutura.Ponto;

public class DI_Abertura extends Node {

	public DI_Abertura(int posx, int posy, int largura, int altura, SVG SVGPath) {
		super(posx, posy, largura, altura, SVGPath);
		super.texto = new TextBox(posx,posy,0,0,"");
		super.background = new Cor(0,0,0);
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, 0));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, altura));
		super.pontosAncoragem.add(new Ponto(posx, posy, 0, altura/2));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura, altura/2));
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
		
		DI_Abertura node = new DI_Abertura(this.getX(), this.getY(), this.getLargura(), this.getAltura(), this.svg);
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
