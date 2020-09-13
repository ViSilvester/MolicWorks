package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Diagrama;
import model.Node;
import model.SVG;
import model.TextBox;
import model.estrutura.Cor;
import model.estrutura.Ponto;
import model.estrutura.Rect;

public class DHT_Tarefa extends Node{
	
	int executabilidade;

	public DHT_Tarefa(int posx, int posy, int largura, int altura, SVG SVGPath) {
		super(posx, posy, largura, altura, SVGPath);
		executabilidade = 0;
		texto = new TextBox(posx, posy, largura/2, altura/2, "Sample Text");
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, 0));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura/2, altura));
		super.pontosAncoragem.add(new Ponto(posx, posy, 0, altura/2));
		super.pontosAncoragem.add(new Ponto(posx, posy, largura, altura/2));
		
	}

	@Override
	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.setFill(Color.BLACK);
		switch(executabilidade) {
		case 1:
			gc.fillOval(this.getX() + this.getLargura() - 15, this.getY()+3, 12, 12);
			break;
		case 2:
			gc.setTextAlign(TextAlignment.LEFT);
			gc.setFont(Font.font ("Verdana", 20));
			gc.fillText("*",this.getX() + this.getLargura() - 15, this.getY()+10);
			gc.setFont(Font.getDefault());
			break;
		case 0:
			break;	
		}	
	}

	@Override
	public void injetarDetalhes(Pane display, Diagrama diagrama) {
		super.injetarDetalhes(display, diagrama);
		Label lb = new Label("Excutabilidade");
		ComboBox<String> cb = new ComboBox<String>();		
		cb.getItems().add("Não exibir");
		cb.getItems().add("Ubiqua");
		cb.getItems().add("Iterativa");
		cb.getSelectionModel().select(executabilidade);
		cb.setOnAction(e->{ this.executabilidade = cb.getSelectionModel().getSelectedIndex();
							diagrama.render();});
		display.getChildren().addAll(lb, cb);
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

	@Override
	public void updateElementos() {
		super.texto.setxRelativo(this.getX());
		super.texto.setyRelativo(this.getY());
	}
	
	public void setExecutabilidade(int executabilidade)
	{
		this.executabilidade = executabilidade;
	}
	
	@Override
	public Node clone() {
		
		DHT_Tarefa node = new DHT_Tarefa(this.getX(), this.getY(), this.getLargura(), this.getAltura(), this.svg);
		node.setBackground(this.getBackground().clone());
		node.setSvg(this.getSvg());
		node.setExecutabilidade(executabilidade);
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
