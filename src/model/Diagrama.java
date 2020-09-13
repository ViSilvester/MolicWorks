package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.estrutura.Cor;
import model.estrutura.Ponto;
import model.estrutura.Rect;

public abstract class Diagrama implements Serializable {
	
	String nome;
	int altura;
	int largura;
	protected List<Node> nodes = new LinkedList<Node>();
	List<Relacao> relacoes = new LinkedList<Relacao>();
	Cor background;
	transient Canvas canvas;
	
	public Diagrama(String nome) {
		this.nome = nome;
		background = new Cor(1, 1, 1);
		canvas = new Canvas(1000,1000);
	}
	
	public void reserializar() {
		this.canvas = new Canvas(1000,1000);
	}
	
	public void render() {
	
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.setFill(Color.color(this.background.R, this.background.G, this.background.B ));
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		gc.setLineWidth(1.5);
		gc.setStroke(Color.LIGHTGRAY);
		for(int i=0; i< canvas.getWidth(); i+=10) {
			gc.strokeLine(i, 0, i, canvas.getHeight());
		}
		for(int i=0; i< canvas.getHeight(); i+=10) {
			gc.strokeLine(0, i, canvas.getWidth(), i);
		}
		
		
		for(int i=0; i< relacoes.size(); i++) {
			relacoes.get(i).render(gc);
		}
		for(int i=0; i< getNodes().size(); i++) {
			getNodes().get(i).render(gc);
		}
	}
	
	
	public List<String> avaliar() {
		List<String> alertas = new LinkedList<String>();
		if(this.relacoes.isEmpty() && nodes.size() > 1) {
			alertas.add("Nenhuma conexão foi realizada entre os nodes !");
		}
		for(int i=0; i < nodes.size(); i++) {
			alertas.addAll(nodes.get(i).avaliar());
		}
		for(int i=0; i < relacoes.size(); i++) {
			alertas.addAll(relacoes.get(i).avaliar());
		}
		
		if(alertas.isEmpty()) {
			alertas.add("Nenhum problema foi encontrado");
		}
		
		return alertas;
	}
	
	
	abstract public Node novoNode(Node novoNode);
	
	public void addRelacao(Relacao rel) {
		relacoes.add(rel);
	}


	public List<Node> getNodes() {
		return nodes;
	}


	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public Canvas getCanvas(){
		return this.canvas;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Rect getBordas() {
		
		if(this.nodes.isEmpty()) {
			 return new Rect(0, 0, 10, 10);
		}
		
		int maxX=0, maxY=0, minX=nodes.get(0).getX(), minY=nodes.get(0).getX();
		
		for(int i=0; i<nodes.size(); i++) {
			if(nodes.get(i).getX() + nodes.get(i).getLargura() > maxX) {
				maxX = nodes.get(i).getX() + nodes.get(i).getLargura();
			}
			if(nodes.get(i).getY() + nodes.get(i).getAltura() > maxY) {
				maxY = nodes.get(i).getY() + nodes.get(i).getAltura();
			}
			
			if(nodes.get(i).getX() < minX) {
				minX = nodes.get(i).getX();
			}
			if(nodes.get(i).getY()< minY) {
				minY = nodes.get(i).getY();
			}
		}
		
		if (minX < 0) {
			minX = 0;
		}
		if(minY < 0) {
			minY  = 0;
		}

		return new Rect(minX, minY, maxX - minX, maxY - minY);
	}
	
	public List<Relacao> getRelacoes(){
		return relacoes;
	}
	
	public void renderSelectionBox(Ponto p1, Ponto p2) {
		
		int altura = p2.getY()- p1.getY();
		int largura =  p2.getX()- p1.getX();
		int offsetX=0;
		int offsetY=0;
		
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(200, 200, 255, 0.2));
		gc.setStroke(Color.AQUA);
		
		
		if(largura < 0) {
			largura*= -1;
			offsetX = largura;
		}
		if(altura < 0){
			altura*= -1;
			offsetY = altura;
		}
		gc.fillRect(p1.getX()-offsetX, p1.getY()-offsetY, largura, altura);
		gc.strokeRect(p1.getX()-offsetX, p1.getY()-offsetY, largura, altura);
	}
	
	public List<Node> selecionarMultiplosNodes(Ponto p1, Ponto p2) {
		
		List<Node> nodesSelecionados = new LinkedList<Node>();
		int altura = p2.getY()- p1.getY();
		int largura =  p2.getX()- p1.getX();
		int offsetX=0;
		int offsetY=0;
		
		if(largura < 0) {
			largura*= -1;
			offsetX = largura;
		}
		if(altura < 0){
			altura*= -1;
			offsetY = altura;
		}
		
		for(int i=0; i< this.nodes.size(); i++) {
			if(this.nodes.get(i).getRect().rectIntersect(p1.getX()-offsetX, p1.getY()-offsetY, largura, altura)) {
				nodesSelecionados.add(this.nodes.get(i));
				this.nodes.get(i).setSelected(true);
			}
			else {
				this.nodes.get(i).setSelected(false);
			}
		}		
		return nodesSelecionados;
	}
	
}
