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

public class G_Container extends Node {

	public G_Container(int x, int y, int largura, int altura, SVG SVGPath) {
		super(x, y, largura, altura, SVGPath);
		super.texto = new TextBox(0,0,0,0,"");
		this.background = new Cor(0.8,0.8,0.8);
		this.isBackground = true;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.color(this.background.R, this.background.G, this.background.B));
		gc.fillRect(this.getX(), this.getY(), this.getLargura(), this.getAltura());
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
	public void updatePontosAncoragem() {
	}

	@Override
	public void updateElementos() {
	}

	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}

	@Override
	public Node clone() {
		Node node = new G_Container(this.getX(),this.getY(),this.getLargura(),this.getAltura(), this.getSvg());
		node.setBackground(this.getBackground().clone());
		node.setTexto(this.getTexto().clone());
		List<Ponto> pontos = new LinkedList<Ponto>();
		node.setPontosAncoragem(pontos);
		return node;
	}

}
