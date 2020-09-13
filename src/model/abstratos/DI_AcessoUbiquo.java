package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import model.Node;
import model.SVG;
import model.TextBox;
import model.estrutura.Cor;
import model.estrutura.Ponto;

public class DI_AcessoUbiquo extends Node {

	public DI_AcessoUbiquo(int posx, int posy, int largura, int altura, SVG SVGPath) {
		super(posx, posy, largura, altura, SVGPath);
		super.background = new Cor(0.8,0.8,0.8);
		super.texto = new TextBox(posx, posy, largura, altura,"");
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, altura));
	}
	
	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}
	
	public void updatePontosAncoragem() {
		super.pontosAncoragem.get(0).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura()/2, this.getAltura()));
	}
	
	public void updateElementos() {
		super.texto.setxRelativo(this.getX());
		super.texto.setyRelativo(this.getY());
	}
	
	@Override
	public Node clone() {
		
		DI_AcessoUbiquo node = new DI_AcessoUbiquo(this.getX(), this.getY(), this.getLargura(), this.getAltura(), this.svg);
		node.setBackground(this.getBackground().clone());
		node.setSvg(this.getSvg());
		node.setTexto(this.getTexto().clone());
		List<Ponto> pontos = new LinkedList<Ponto>();
		pontos.add(this.getPontosAncoragem().get(0).clone());
		node.setPontosAncoragem(pontos);
		
		return node;
	}
}
