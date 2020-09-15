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
		
		//Se ponto de origem se alinhar em X
		if( pontoOrigem.getX() == origem.getLargura() ||
			pontoOrigem.getX() == 0) {
			
			y1 = pontoOrigem.getYAbsoluto();
			
			//Se ponto de destino se alinhar em Y
			if( pontoDestino.getY() == destino.getAltura() ||
				pontoDestino.getY() == 0) {
				
				
				// Se ponto de destino esta relativo ao topo norte de seu node
				// E ponto de Origem esta abaixo do ponto de Destino
				
				if(pontoDestino.getY() == 0 && pontoOrigem.getYAbsoluto() > pontoDestino.getYAbsoluto() - 15) {
					
					int disty = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
					int distx = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
					
					if (disty < 0) {
						disty *= -1;
					}
					if (distx < 0) {
						distx *= -1;
					}
					
					y1 = pontoDestino.getYAbsoluto();
					
					if(pontoDestino.getXAbsoluto() >  pontoOrigem.getXAbsoluto()) {
						x1 = pontoDestino.getXAbsoluto() - destino.getLargura()/2;
						x1 -= (distx/5) + 15;
						y1 -= (disty/5) + 15;
					}
					else {
						x1 = pontoDestino.getXAbsoluto() + destino.getLargura()/2;
						x1 += (distx/5) + 15;
						y1 -= (disty/5) + 15;
					}
	
					gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, pontoOrigem.getYAbsoluto());
					gc.strokeLine(x1, pontoOrigem.getYAbsoluto(), x1,y1);
					gc.strokeLine(x1,y1, pontoDestino.getXAbsoluto(), y1);
					gc.strokeLine(pontoDestino.getXAbsoluto(), y1,pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
				}
				else if(pontoDestino.getY() == destino.getAltura() && pontoOrigem.getYAbsoluto() < pontoDestino.getYAbsoluto() + 15) {
					
					int disty = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
					int distx = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
					
					if (disty < 0) {
						disty *= -1;
					}
					if (distx < 0) {
						distx *= -1;
					}
					
					y1 = pontoDestino.getYAbsoluto();
					
					if(pontoDestino.getXAbsoluto() >  pontoOrigem.getXAbsoluto()) {
						x1 = pontoDestino.getXAbsoluto() - destino.getLargura()/2;
						x1 -= (distx/5) + 15;
						y1 += (disty/5) + 15;
					}
					else {
						x1 = pontoDestino.getXAbsoluto() + destino.getLargura()/2;
						x1 += (distx/5) + 15;
						y1 += (disty/5) + 15;
					}
	
					gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, pontoOrigem.getYAbsoluto());
					gc.strokeLine(x1, pontoOrigem.getYAbsoluto(), x1,y1);
					gc.strokeLine(x1,y1, pontoDestino.getXAbsoluto(), y1);
					gc.strokeLine(pontoDestino.getXAbsoluto(), y1,pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
				}
				else {
					x1 = pontoDestino.getXAbsoluto();
					gc.strokeLine(pontoOrigem.getXAbsoluto(),pontoOrigem.getYAbsoluto(), x1, y1);
					gc.strokeLine(x1, y1, pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
				}

			}
			// Se ponto de destino de alinhar em X
			else{
				
				// Se ambos pontos se alinharem em X e estao relativos a direita de seus nodes
				if( pontoOrigem.getX() == origem.getLargura() &&
					pontoDestino.getX() == destino.getLargura()  ) {
					
					x1 = pontoOrigem.getXAbsoluto();
					y1 = pontoOrigem.getYAbsoluto();
					if(x1 < pontoDestino.getXAbsoluto()) {
						x1 = pontoDestino.getXAbsoluto();
					}
					
					int dist = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
					if (dist < 0) {
						dist *= -1;
					}
					x1 += dist/5 + 15; 
					gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
					gc.strokeLine(x1, y1, x1, pontoDestino.getYAbsoluto());
					gc.strokeLine(x1, pontoDestino.getYAbsoluto(),pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
					
					
				}
				// Se ambos pontos se alinharem em X e estao relativos a esquerda de seus nodes
				else if( pontoOrigem.getX() == 0 &&
						pontoDestino.getX() == 0) {
					
					x1 = pontoOrigem.getXAbsoluto();
					y1 = pontoOrigem.getYAbsoluto();
					if(x1 > pontoDestino.getXAbsoluto()) {
						x1 = pontoDestino.getXAbsoluto();
					}
					int dist = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
					if (dist < 0) {
						dist *= -1;
					}
					x1 -= dist/5 + 15;
					gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
					gc.strokeLine(x1, y1, x1, pontoDestino.getYAbsoluto());
					gc.strokeLine(x1, pontoDestino.getYAbsoluto(),pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
					
				}
				// Se ponto destino se alinha em X e os ponto estao relativos as seus nodes de maneira inversa entre si
				else {
						x1 = pontoOrigem.getXAbsoluto() + ((pontoDestino.getXAbsoluto() - pontoOrigem.getXAbsoluto())/2);
						int x2 = x1;
						int y2 = pontoDestino.getYAbsoluto();
						
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
						gc.strokeLine(x2, y2, pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
						gc.strokeLine(x1, y1, x2, y2);	
				}
			}
		}
		// Se ponto origem se alinha em Y
		else {
			
			x1 =pontoOrigem.getXAbsoluto();
			
			// se ponto destino se alinha a X
			if( pontoDestino.getX() == destino.getLargura() ||
				pontoDestino.getX() == 0) {
				
				if(pontoDestino.getX() == 0 && pontoOrigem.getXAbsoluto() > pontoDestino.getXAbsoluto() - 15) {
					
					int disty = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
					int distx = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
					
					if (disty < 0) {
						disty *= -1;
					}
					if (distx < 0) {
						distx *= -1;
					}
					
					x1 = pontoDestino.getXAbsoluto();
					
					if(pontoDestino.getYAbsoluto() >  pontoOrigem.getYAbsoluto()) {
						y1 = pontoDestino.getYAbsoluto() - destino.getAltura()/2;
						x1 -= (disty/5) + 15;
						y1 -= (distx/5) + 15;
					}
					else {
						y1 = pontoDestino.getYAbsoluto() + destino.getAltura()/2;
						x1 -= (disty/5) + 15;
						y1 += (distx/5) + 15;
					}
					
					if(pontoOrigem.getY() == 0) {
	
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), pontoOrigem.getXAbsoluto(), y1);
						gc.strokeLine(pontoOrigem.getXAbsoluto(), y1, x1,y1);
						gc.strokeLine(x1,y1, x1, pontoDestino.getYAbsoluto());
						gc.strokeLine(x1, pontoDestino.getYAbsoluto(),pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
					
					}
					else {
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto() + (distx/5) + 15);
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto()+(distx/5) + 15, x1 ,pontoOrigem.getYAbsoluto() + (distx/5) + 15);
						gc.strokeLine(x1 ,pontoOrigem.getYAbsoluto() + (distx/5) + 15, x1, pontoDestino.getYAbsoluto());
						gc.strokeLine(x1, pontoDestino.getYAbsoluto(),pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
					
					}
				}
				else if(pontoDestino.getX() == destino.getLargura() && pontoOrigem.getXAbsoluto() < pontoDestino.getXAbsoluto() + 15) {
					
					int disty = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
					int distx = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
					
					if (disty < 0) {
						disty *= -1;
					}
					if (distx < 0) {
						distx *= -1;
					}
					
					x1 = pontoDestino.getXAbsoluto();
					
					if(pontoDestino.getYAbsoluto() >  pontoOrigem.getYAbsoluto()) {
						y1 = pontoDestino.getYAbsoluto() - destino.getAltura()/2;
						x1 += (disty/5) + 15;
						y1 -= (distx/5) + 15;
					}
					else {
						y1 = pontoDestino.getYAbsoluto() + destino.getAltura()/2;
						x1 += (disty/5) + 15;
						y1 += (distx/5) + 15;
					}
					
					if(pontoOrigem.getY() == 0) {
	
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), pontoOrigem.getXAbsoluto(), y1);
						gc.strokeLine(pontoOrigem.getXAbsoluto(), y1, x1,y1);
						gc.strokeLine(x1,y1, x1, pontoDestino.getYAbsoluto());
						gc.strokeLine(x1, pontoDestino.getYAbsoluto(),pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
					}
					else {
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto() + (distx/5) + 15);
						gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto()+(distx/5) + 15, x1 ,pontoOrigem.getYAbsoluto() + (distx/5) + 15);
						gc.strokeLine(x1 ,pontoOrigem.getYAbsoluto() + (distx/5) + 15, x1, pontoDestino.getYAbsoluto());
						gc.strokeLine(x1, pontoDestino.getYAbsoluto(),pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
					
					}
				}
				
				else {
				
					y1 = pontoDestino.getYAbsoluto();
					gc.strokeLine(pontoOrigem.getXAbsoluto(),pontoOrigem.getYAbsoluto(), x1, y1);
					gc.strokeLine(x1, y1, pontoDestino.getXAbsoluto(),pontoDestino.getYAbsoluto());
				}
				
			}
			// se ponto destino de alinha em Y
			else {
				
				// Se ponto destino se alinha a Y e ambos pontos estao relativos ao sul de seus nodes
				if( pontoOrigem.getY() == origem.getAltura() &&
					pontoDestino.getY() == destino.getAltura()  ) {
						
					x1 = pontoOrigem.getXAbsoluto();
					y1 = pontoOrigem.getYAbsoluto();
					if(y1 < pontoDestino.getYAbsoluto()) {
						y1 = pontoDestino.getYAbsoluto();
					}
					int dist = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
					if (dist < 0) {
						dist *= -1;
					}	
					y1 += dist/5 + 15;
					gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
					gc.strokeLine(x1, y1, pontoDestino.getXAbsoluto(), y1);
					gc.strokeLine(pontoDestino.getXAbsoluto(), y1,pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());	
						
				}
				// Se ponto destino se alinha a Y e ambos pontos estao relativos ao norte de seus nodes
				else if( pontoOrigem.getY() == 0 &&
					pontoDestino.getY() == 0  ) {
						
					x1 = pontoOrigem.getXAbsoluto();
					y1 = pontoOrigem.getYAbsoluto();
					if(y1 > pontoDestino.getYAbsoluto()) {
						y1 = pontoDestino.getYAbsoluto();
					}
					int dist = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
					if (dist < 0) {
						dist *= -1;
					}	
					y1 -= dist/5 + 15;
					gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(), x1, y1);
					gc.strokeLine(x1, y1, pontoDestino.getXAbsoluto(), y1);
					gc.strokeLine(pontoDestino.getXAbsoluto(), y1,pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());	
						
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
