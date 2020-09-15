package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Diagrama;
import model.Node;
import model.SVG;
import model.TextBox;
import model.estrutura.Ponto;

public class DI_Cena extends Node{
	
	TextBox descricao;
	
	public DI_Cena(int posx, int posy, int largura, int altura, SVG SVGPath) {
		super(posx, posy, largura, altura, SVGPath);
		texto = new TextBox(posx, posy,largura/2, altura/10, "Cena sem nome");
		descricao = new TextBox(posx, posy, largura/2, altura/2, "Descricao");
		super.pontosAncoragem.add(new Ponto(posx, posy, (int) (largura/1.5), 0));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, altura));
		super.pontosAncoragem.add(new Ponto(posx, posy, 0, altura/2));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura, altura/2));
		descricao.setTextBaseLine(VPos.TOP);
	}
	
	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
	    descricao.render(gc);
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
		this.descricao.setxRelativo(x);
		this.descricao.setyRelativo(y);
	}
	
	
	@Override
	public void injetarDetalhes(Pane display, Diagrama diagrama) {
		super.injetarDetalhes(display, diagrama);
		TextArea t1 = new TextArea(descricao.getTexto());
		t1.setMinHeight(30);
		t1.setOnKeyTyped(e->{this.descricao.setTexto(t1.getText()); diagrama.render();});
		DI_Cena node = this;
		
		t1.textProperty().addListener(
			new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	node.setDescricao(newValue);
			    	diagrama.render();
			    }
		    });
		display.getChildren().add(new Label("Descrição"));
		display.getChildren().add(t1);
		
	}
	
	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}
	
	@Override
	public void setTexto(String texto) {
		super.setTexto(texto);
		this.descricao.setPosx(this.getLargura()/2);
	}
	
	public void setDescricao(String texto) {
		descricao.setTexto(texto);
		if(descricao.getLargura() > this.getLargura()) {
			this.setLargura(this.descricao.getLargura());
			this.descricao.setPosx(this.getLargura()/2);
			this.texto.setPosx(this.getLargura()/2);
		}
		if(descricao.getAltura() > this.getAltura() - this.texto.getAltura() - 5) {
			this.setAltura(this.descricao.getAltura() + this.texto.getAltura() + 5);
			this.descricao.setPosy(this.texto.getAltura()+10);	
		}
	} 

	@Override
	public void updatePontosAncoragem() {
		super.pontosAncoragem.get(0).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura()/2, 0));
		super.pontosAncoragem.get(1).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura()/2, this.getAltura()));
		super.pontosAncoragem.get(2).replicarPonto(new Ponto(this.getX(), this.getY(), 0, this.getAltura()/2));
		super.pontosAncoragem.get(3).replicarPonto(new Ponto(this.getX(), this.getY(), this.getLargura(), this.getAltura()/2));
	}
	
	public void updateElementos() {
		super.texto.setxRelativo(this.getX());
		super.texto.setyRelativo(this.getY());
		this.descricao.setxRelativo(this.getX());
		this.descricao.setyRelativo(this.getY());
		super.texto.setX(this.getLargura()/2);
		super.texto.setY(this.getAltura()/10);
		this.descricao.setX(this.getLargura()/2);
		this.descricao.setY(this.getAltura()/2);
	}
	
	@Override
	public Node clone() {
		
		DI_Cena node = new DI_Cena(this.getX(), this.getY(), this.getLargura(), this.getAltura(), this.svg);
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
