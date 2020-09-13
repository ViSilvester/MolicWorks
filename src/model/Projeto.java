package model;

import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import application.MainController;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.estrutura.Ponto;

public class Projeto implements Serializable {
	
	transient MainController mainController;
	transient Diagrama diagramaSelecionado;
	transient String modo = "selecionar";
	transient Node NodeASerAdicionado;
	transient Relacao relacaoASerAdicionado;
	transient Relacao tempRel;
	transient private List<Node> nodesSelecionados = new LinkedList<Node>();
	transient private List<Relacao> relacoesSelecionadas = new LinkedList<Relacao>();
	transient private List<Node> nodesCopiados = new LinkedList<Node>();
	transient private List<Relacao> relacoesCopiadas = new LinkedList<Relacao>();
	private List<Diagrama> diagramas = new LinkedList<Diagrama>();
	private int diagramaCounter;
	private String path;
	public Boolean salvo;
	private Ponto pontoInicialDrag = new Ponto(0,0,0,0);
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public Projeto(MainController controller) {
		this.mainController = controller;
		this.diagramaCounter = 0;
		this.salvo = false;
	}
	
	public void reserializar(MainController controller) {
		this.mainController = controller;
		this.nodesSelecionados = new LinkedList<Node>();
	}
	
	public Diagrama novoDiagrama() {
		
		this.incrementCounter();
		
		for(int i=0;i< diagramas.size() ; i++) {
			if(diagramas.get(i).getNome().equals("Diagrama "+this.diagramaCounter)) {
				i=-1;
				this.incrementCounter();
			}
		}
		
		Diagrama nd = new DHT("Diagrama "+diagramaCounter);
		this.diagramas.add(nd);
		return nd;
	}
	
	public int getDiagramasSize() {
		return this.diagramas.size();
	}
	
	public int getCounter() {
		return diagramaCounter;
	}
	
	public void incrementCounter() {
		this.diagramaCounter++;
	}
	
	public String getDiagramaName(int index) {
		return this.diagramas.get(index).nome;
	}
	
	public List<Diagrama> getDiagramas(){
		return diagramas;
	}
	
	public void resetarAmbiente() {
		this.setModo("selecionar");
		this.NodeASerAdicionado = null;
		this.nodesSelecionados.clear();
		this.tempRel = null;
	}
	
	public void setDiagramaSelecionado(Diagrama diagrama){
		this.diagramaSelecionado = diagrama;
		this.resetarAmbiente();
		diagrama.render();
	}
		
	public Diagrama getDiagramaSelecionado(){
		return this.diagramaSelecionado;
	}
	
	public void setModo(String modo) {
		if(!modo.equals(this.modo)) {
			
			if(modo.equals("selecionar")) {
				this.diagramaSelecionado.getCanvas().setCursor(Cursor.DEFAULT);
				this.tempRel = null;
			}
			else if (modo.equals("adicionar")) {
				this.diagramaSelecionado.getCanvas().setCursor(Cursor.CLOSED_HAND);
				this.tempRel = null;
			}
			else if (modo.equals("conectar")) {
				this.diagramaSelecionado.getCanvas().setCursor(Cursor.CROSSHAIR);
			}
			this.modo = modo;
			this.diagramaSelecionado.render();
		}
	}
	
	public void limparSelecaoNodes() {
		for(int i=0; i<this.nodesSelecionados.size(); i++) {
			this.nodesSelecionados.get(i).setSelected(false);
		}
		this.nodesSelecionados.clear();
	}
	
	public void setNodeASerAdicionado(Node node) {
		this.NodeASerAdicionado = node;
	}
	private void adicionarNovoNode(Node node) {
		diagramaSelecionado.novoNode(node);
	}
	public void OnCanvasClicked(MouseEvent event) {
		
		if(event.getButton() == MouseButton.PRIMARY) {
		
			if(this.modo.equals("transicaoSelecionar")) {
				this.setModo("selecionar");
			}
			else if(this.modo.equals("selecionar")) {
			//System.out.println("Canvas Clicado");
			Node nodeClicado = null;
			for(int i=0; i< diagramaSelecionado.getNodes().size(); i++) {
				if (diagramaSelecionado.getNodes().get(i).inArea((int)event.getX(), (int)event.getY())) {
					nodeClicado = diagramaSelecionado.getNodes().get(i);
				}
			}
			
			if(nodeClicado == null) {
				if (!this.nodesSelecionados.isEmpty()) {
					this.limparSelecaoNodes();
					this.mainController.clearDetalhes();
				}
			}
			else {
				this.limparSelecaoNodes();
				this.nodesSelecionados.add(nodeClicado);
				nodeClicado.setSelected(true);
				nodeClicado.injetarDetalhes(this.mainController.display_detalhes, diagramaSelecionado);
			}
			diagramaSelecionado.render();
			}
			else if(this.modo.equals("adicionar")) {
				this.tempRel = null;
				this.NodeASerAdicionado.setXY((int)event.getX(), (int)event.getY());
				this.adicionarNovoNode(this.NodeASerAdicionado);
				this.NodeASerAdicionado = null;
				this.setModo("selecionar");
				this.diagramaSelecionado.render();
				this.mainController.atualizarAlertas(diagramaSelecionado.avaliar());
			}
			else if(this.modo.equals("conectar")){
				
				Node nodeClicado = null;
				for(int i=0; i< diagramaSelecionado.getNodes().size(); i++) {
					if (diagramaSelecionado.getNodes().get(i).inArea((int)event.getX(), (int)event.getY())) {
						nodeClicado = diagramaSelecionado.getNodes().get(i);
					}
				}
				if(nodeClicado != null  && !nodeClicado.getPontosAncoragem().isEmpty()) {
					if (tempRel == null) {
						tempRel = relacaoASerAdicionado;
						tempRel.setOrigem(nodeClicado, new Ponto(0,0,(int)(event.getX()), (int)(event.getY())) );
					}
					else if(tempRel.getOrigem() != null && tempRel.getDestino() == null) {
						
						tempRel.setDestino(nodeClicado, new Ponto(0,0,(int)(event.getX()), (int)(event.getY())) );
						diagramaSelecionado.addRelacao(tempRel);
						diagramaSelecionado.render();
						this.mainController.atualizarAlertas(diagramaSelecionado.avaliar());
						tempRel = null;
						this.setModo("selecionar");
						this.diagramaSelecionado.render();
					}
				}
			}
		}
	}
	
	public void onCanvasDragged(MouseEvent event) {
				
		if(modo.equals("selecionar") && !this.nodesSelecionados.isEmpty()) {
			this.setModo("arrastar");
			this.pontoInicialDrag.setX((int)event.getX());
			this.pontoInicialDrag.setY((int)event.getY());
		}
		else if(modo.equals("selecionar") && this.nodesSelecionados.isEmpty()) {
			this.setModo("selecionarMultiplos");
			this.pontoInicialDrag.setX((int)event.getX());
			this.pontoInicialDrag.setY((int)event.getY());
		}
		else if(modo.equals("selecionarMultiplos")) {
			this.nodesSelecionados = this.diagramaSelecionado.selecionarMultiplosNodes(pontoInicialDrag,
					new Ponto(0,0, (int)event.getX(),(int)event.getY()));
			this.diagramaSelecionado.render();
			this.diagramaSelecionado.renderSelectionBox(pontoInicialDrag,
					new Ponto(0,0, (int)event.getX(),(int)event.getY()));
		}
		if(this.modo == "arrastar") {
			for(int i=0; i< this.nodesSelecionados.size(); i++) {
				Node n = nodesSelecionados.get(i);
				int x =  -(this.pontoInicialDrag.getX() - (int) event.getX());
				int y =  -(this.pontoInicialDrag.getY() - (int) event.getY());
				n.getRect().getCoordenada().setRelativoX(x);
				n.getRect().getCoordenada().setRelativoY(y);
				n.updatePontosAncoragem();
				n.updateElementos();
			}
			//nodeSelecionado.setXY((int)event.getX(), (int)event.getY());
			this.diagramaSelecionado.render();
		}
	}
	
	public void onCanvasMouseReleased(MouseEvent event) {
		
		if(this.modo == "selecionarMultiplos") {
			this.diagramaSelecionado.render();
			this.setModo("transicaoSelecionar");
		}
		else if(this.modo == "arrastar"){for(int i=0; i< this.nodesSelecionados.size(); i++) {
				Node n = nodesSelecionados.get(i);
				int x = n.getRect().getCoordenada().getXAbsoluto();
				int y =  n.getRect().getCoordenada().getYAbsoluto();
				n.getRect().getCoordenada().setRelativoX(0);
				n.getRect().getCoordenada().setRelativoY(0);
				n.getRect().getCoordenada().setX(x);
				n.getRect().getCoordenada().setY(y);
			}
			this.setModo("transicaoSelecionar");
		}
	}
	
	public void onCanvasMoved(MouseEvent event) {
		if(this.modo == "conectar" && tempRel != null) {
			this.diagramaSelecionado.render();
			GraphicsContext gc = this.diagramaSelecionado.getCanvas().getGraphicsContext2D();
			gc.setLineWidth(2);
			gc.setStroke(Color.AQUA);
			gc.strokeLine(tempRel.getPontoOrigem().getXAbsoluto(),tempRel.getPontoOrigem().getYAbsoluto(), event.getX(), event.getY());
		}
	}
	
	public int renomerDiagrama(String nomeAtual, String novoNome) {
		
		Diagrama dia = null;;
		
		for(int i=0; i< diagramas.size(); i++) {
			if (diagramas.get(i).getNome().equals(nomeAtual)) {
				if(dia != null) {
					return -1;
				}
				else {
					dia = diagramas.get(i);
				}
			}
			if (diagramas.get(i).getNome().equals(novoNome)) {
				return -2;
			}
		}
		
		dia.setNome(novoNome);
		return 0;
	}
	
	public void ExcluirNode() {
		for(int j=0; j< this.nodesSelecionados.size(); j++) {
			Node node = this.nodesSelecionados.get(j);
			for(int i=0; i< this.diagramaSelecionado.getRelacoes().size(); i++) {
				if (this.diagramaSelecionado.getRelacoes().get(i).getOrigem() == node|| 
					this.diagramaSelecionado.getRelacoes().get(i).getDestino() == node){
					this.diagramaSelecionado.getRelacoes().remove(i);
					this.diagramaSelecionado.render();
					i--;
				}
			}
			for(int i=0; i< this.diagramaSelecionado.getNodes().size(); i++) {
				if (node == this.diagramaSelecionado.getNodes().get(i)) {
					this.diagramaSelecionado.getNodes().remove(i);
					node = null;
					this.diagramaSelecionado.render();
					i--;
				}
			}
		}
	}
	public void excluirDiagrama(String nome) {
		
		for(int i=0; i< diagramas.size(); i++) {
			if(diagramas.get(i).getNome().equals(nome)) {
				diagramas.remove(i);
			}
		}
	}
	
	public void copiar() {
		this.nodesCopiados.clear();
		this.relacoesCopiadas.clear();
		for(int i=0; i<this.nodesSelecionados.size(); i++) {
			this.nodesCopiados.add(this.nodesSelecionados.get(i).clone());
		}
		int copiar;
		Relacao r = null;
		for(int i=0; i<this.diagramaSelecionado.getRelacoes().size(); i++) {
			copiar= 0;
			r = diagramaSelecionado.getRelacoes().get(i).clone();
			for(int j=0; j<this.nodesSelecionados.size(); j++) {
				if(this.diagramaSelecionado.getRelacoes().get(i).getOrigem() == nodesSelecionados.get(j)) {
					Ponto p =  r.getPontoOrigem();
					r.setOrigem(this.nodesCopiados.get(j), new Ponto(0,0, p.getXAbsoluto(), p.getYAbsoluto()));
					copiar ++;
				}
				if(this.diagramaSelecionado.getRelacoes().get(i).getDestino() == nodesSelecionados.get(j)) {
					Ponto p =  r.getPontoDestino();
					r.setDestino(this.nodesCopiados.get(j), new Ponto(0,0, p.getXAbsoluto(), p.getYAbsoluto()));
					copiar ++;
				}
			}
			if(copiar > 1) {
				this.relacoesCopiadas.add(r);
			}
			
		}
		System.out.println("relacoes copiadas: "+ this.relacoesCopiadas.size());
	}
	public void colar() {
		
		List<Node> nodesAColar = new LinkedList<Node>();
		
		
		for(int i=0; i<this.nodesCopiados.size(); i++) {
			nodesAColar.add(this.nodesCopiados.get(i).clone());
		}
		int copiar;
		Relacao r = null;
		for(int i=0; i<this.relacoesCopiadas.size(); i++) {
			copiar= 0;
			r = relacoesCopiadas.get(i).clone();
			for(int j=0; j<nodesAColar.size(); j++) {
				if(this.relacoesCopiadas.get(i).getOrigem() == nodesCopiados.get(j)) {
					Ponto p =  r.getPontoOrigem();
					r.setOrigem(nodesAColar.get(j), new Ponto(0,0, p.getXAbsoluto(), p.getYAbsoluto()));
					copiar ++;
				}
				if(this.relacoesCopiadas.get(i).getDestino() == nodesCopiados.get(j)) {
					Ponto p =  r.getPontoDestino();
					r.setDestino(nodesAColar.get(j), new Ponto(0,0, p.getXAbsoluto(), p.getYAbsoluto()));
					copiar ++;
				}
			}
			if(copiar > 1) {
				this.diagramaSelecionado.getRelacoes().add(r);
				System.out.println("ponto x "+ r.getPontoOrigem().getXAbsoluto());
				System.out.println("ponto y "+ r.getPontoOrigem().getYAbsoluto());
			}
			
			
		}
		this.diagramaSelecionado.getNodes().addAll(nodesAColar);
		this.diagramaSelecionado.render();
		
	}

	public Relacao getRelacaoASerAdicionado() {
		return relacaoASerAdicionado;
	}
	public void setRelacaoASerAdicionado(Relacao relacaoASerAdicionado) {
		this.relacaoASerAdicionado = relacaoASerAdicionado;
	}
	
}