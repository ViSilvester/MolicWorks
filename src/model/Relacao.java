package model;

import java.io.Serializable;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.estrutura.Ponto;

public abstract class Relacao implements Serializable {
	protected Node origem;
	protected Ponto pontoOrigem;
	protected Node destino;
	protected Ponto pontoDestino;
	
	public void setOrigem(Node origem, Ponto e) {
		this.origem = origem;
		pontoOrigem = origem.pontoProximo(e);
	}
	
	public void setDestino(Node destino, Ponto e) {
		this.destino = destino;	
		pontoDestino = destino.pontoProximo(e);
	}
	
	public Node getOrigem() {
		return origem;
	}
	
	public Node getDestino() {
		return destino;
	}
	
	public Ponto getPontoOrigem() {
		return pontoOrigem;
	}
	
	public Ponto getPontoDestino() {
		return pontoDestino;
	}
	
	public void render(GraphicsContext gc) {
		
		int x1;
		int y1;
		
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(0.9);
		
		if( pontoOrigem.getX() == origem.getLargura() ||
			pontoOrigem.getX() == 0) {
			
			y1 = pontoOrigem.getYAbsoluto();
			
			if( pontoDestino.getY() == destino.getAltura() ||
				pontoDestino.getY() == 0) {
				
				x1 = pontoDestino.getXAbsoluto();
				gc.strokeLine(pontoOrigem.getXAbsoluto(),pontoOrigem.getYAbsoluto(), x1, y1);
				gc.strokeLine(x1, y1, pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
				
			}
			else {
				
				
				x1 = pontoOrigem.getXAbsoluto() + ((pontoDestino.getXAbsoluto() - pontoOrigem.getXAbsoluto())/2);
				
					
				int x2 = x1;
				int y2 = pontoDestino.getYAbsoluto();
				
				gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
				gc.strokeLine(x2, y2, pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
				gc.strokeLine(x1, y1, x2, y2);
			}
		}
		else {
			
			x1 =pontoOrigem.getXAbsoluto();
			
			if( pontoDestino.getX() == destino.getLargura() ||
				pontoDestino.getX() == 0) {
				
				y1 = pontoDestino.getYAbsoluto();
				gc.strokeLine(pontoOrigem.getXAbsoluto(),pontoOrigem.getYAbsoluto(), x1, y1);
				gc.strokeLine(x1, y1, pontoDestino.getXAbsoluto(),pontoDestino.getYAbsoluto());
				
			}
			else {
				
				y1 = pontoOrigem.getYAbsoluto() + ((pontoDestino.getYAbsoluto() - pontoOrigem.getYAbsoluto())/2);
				
					
				int x2 = pontoDestino.getXAbsoluto();
				int y2 = y1;
				
				gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
				gc.strokeLine(x2, y2, pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
				gc.strokeLine(x1, y1, x2, y2);
			}
			
		}
		
		gc.setFill(Color.BLACK);
		
		double pontosx[] = {0,0,0};
		double pontosy[] = {0,0,0};
		
		if(this.pontoDestino.getX() == 0) {
			
			pontosx[0] = (double) this.pontoDestino.getXAbsoluto();
			pontosy[0] = (double) this.pontoDestino.getYAbsoluto();
			pontosx[1] = (double) this.pontoDestino.getXAbsoluto()-10;
			pontosy[1] = (double) this.pontoDestino.getYAbsoluto()+5;
			pontosx[2] = (double) this.pontoDestino.getXAbsoluto()-10;
			pontosy[2] = (double) this.pontoDestino.getYAbsoluto()-5;
			
		}
		else if(this.pontoDestino.getX() == this.destino.getLargura()) {
			
		
			pontosx[0] = (double) this.pontoDestino.getXAbsoluto();
			pontosy[0] = (double) this.pontoDestino.getYAbsoluto();
			pontosx[1] = (double) this.pontoDestino.getXAbsoluto()+10;
			pontosy[1] = (double) this.pontoDestino.getYAbsoluto()+5;
			pontosx[2] = (double) this.pontoDestino.getXAbsoluto()+10;
			pontosy[2] = (double) this.pontoDestino.getYAbsoluto()-5;
			
		}
		
		else if(this.pontoDestino.getY() == this.destino.getAltura()) {

			pontosx[0] = (double) this.pontoDestino.getXAbsoluto();
			pontosy[0] = (double) this.pontoDestino.getYAbsoluto();
			pontosx[1] = (double) this.pontoDestino.getXAbsoluto()+5;
			pontosy[1] = (double) this.pontoDestino.getYAbsoluto()+10;
			pontosx[2] = (double) this.pontoDestino.getXAbsoluto()-5;
			pontosy[2] = (double) this.pontoDestino.getYAbsoluto()+10;
		
		}
		else if(this.pontoDestino.getY() == 0) {

			pontosx[0] = (double) this.pontoDestino.getXAbsoluto();
			pontosy[0] = (double) this.pontoDestino.getYAbsoluto();
			pontosx[1] = (double) this.pontoDestino.getXAbsoluto()+5;
			pontosy[1] = (double) this.pontoDestino.getYAbsoluto()-10;
			pontosx[2] = (double) this.pontoDestino.getXAbsoluto()-5;
			pontosy[2] = (double) this.pontoDestino.getYAbsoluto()-10;
		}
		
		gc.fillPolygon(pontosx, pontosy, 3);
	}
	
	public abstract List<String> avaliar();
	public abstract Relacao clone();

	public void setPontoOrigem(Ponto pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}

	public void setPontoDestino(Ponto pontoDestino) {
		this.pontoDestino = pontoDestino;
	}
	

}
