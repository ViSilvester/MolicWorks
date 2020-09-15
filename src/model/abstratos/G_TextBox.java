package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Diagrama;
import model.Node;
import model.SVG;
import model.TextBox;
import model.estrutura.Ponto;

public class G_TextBox extends Node{
	
	boolean IsTransparent = false;

	public G_TextBox(int posx, int posy, int largura, int altura, SVG SVGPath) {
		super(posx, posy, largura, altura, SVGPath);
		super.texto = new TextBox(posx, posy, 0, 0 , "Texto");
		super.setLargura(super.texto.getLargura());
		super.setAltura(super.texto.getAltura());
		super.texto.setTextAlingnment(TextAlignment.LEFT);
		super.texto.setTexto("Texto");
	}

	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}
	
	@Override
	public void render(GraphicsContext gc) {
		if(!this.IsTransparent) {
			gc.setFill(Color.WHITE);
			gc.fillRect(getX(), getY(), getLargura(), getAltura());
		}
			super.render(gc);
			super.texto.render(gc);
	}
	
	@Override
	public void setTexto(String texto) {
		super.setTexto(texto);
		this.setLargura(this.texto.getLargura());
		this.texto.setPosx(0);
	}
	
	@Override
	public void injetarDetalhes(Pane display, Diagrama diagrama) {
		display.getChildren().clear();
		TextArea t1 = new TextArea(this.texto.getTexto());
		t1.setMaxHeight(50);
		t1.setOnKeyTyped(e->{this.texto.setTexto(t1.getText()); diagrama.render();});
		Node node = this;
		t1.textProperty().addListener(
			new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	node.setTexto(newValue);
			    	diagrama.render();
			    }
		    });
		
		Label ft = new Label("Fundo Transparente");
		CheckBox cb = new CheckBox();
		cb.setSelected(this.IsTransparent);
		cb.setOnAction(e->{
			this.IsTransparent = cb.isSelected();
			diagrama.render();
		});
		
		
		display.getChildren().add(new Label("Texto"));
		display.getChildren().add(t1);
		display.getChildren().add(ft);
		display.getChildren().add(cb);
	}
	
	public void updatePontosAncoragem() {
	}
	
	public void updateElementos() {
		super.texto.setxRelativo(this.getX());
		super.texto.setyRelativo(this.getY());
	}
	
	@Override
	public Node clone() {
		
		G_TextBox node = new G_TextBox(this.getX(), this.getY(), this.getLargura(), this.getAltura(), this.svg);
		node.setTexto(this.getTexto().clone());
		node.IsTransparent = this.IsTransparent;
		
		return node;
	}

}
