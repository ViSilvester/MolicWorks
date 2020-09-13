package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Relacao;
import model.estrutura.Ponto;

public class Relacao_Reta extends Relacao{
	
	@Override
	public void render(GraphicsContext gc) {
		
		double x, y, x1, y1, x2, y2;
		x = pontoOrigem.getXAbsoluto() - pontoDestino.getXAbsoluto();
		y = pontoOrigem.getYAbsoluto() - pontoDestino.getYAbsoluto();
		
		double tamanho = Math.sqrt(x*x + y*y);
		x /= tamanho;
		y /= tamanho;
		
		if (x > 0 && y < 0) {
			x *= -1;
			
			x1 = this.pontoDestino.getXAbsoluto() - (Math.cos((Math.acos(x) + Math.PI/6)) * 11);
			y1 = this.pontoDestino.getYAbsoluto() + (Math.sin((Math.asin(y) + Math.PI/6)) * 11);
			
			x2 = this.pontoDestino.getXAbsoluto() - (Math.cos((Math.acos(x) - Math.PI/6)) * 11);
			y2 = this.pontoDestino.getYAbsoluto() + (Math.sin((Math.asin(y) - Math.PI/6)) * 11);
		}
		else if (x < 0 && y > 0) {
			y *= -1;
			
			x1 = this.pontoDestino.getXAbsoluto() + (Math.cos((Math.acos(x) + Math.PI/6)) * 11);
			y1 = this.pontoDestino.getYAbsoluto() - (Math.sin((Math.asin(y) + Math.PI/6)) * 11);
			
			x2 = this.pontoDestino.getXAbsoluto() + (Math.cos((Math.acos(x) - Math.PI/6)) * 11);
			y2 = this.pontoDestino.getYAbsoluto() - (Math.sin((Math.asin(y) - Math.PI/6)) * 11);
		}
		else {
			x1 = this.pontoDestino.getXAbsoluto() + (Math.cos((Math.acos(x) + Math.PI/6)) * 11);
			y1 = this.pontoDestino.getYAbsoluto() + (Math.sin((Math.asin(y) + Math.PI/6)) * 11);
			
			x2 = this.pontoDestino.getXAbsoluto() + (Math.cos((Math.acos(x) - Math.PI/6)) * 11);
			y2 = this.pontoDestino.getYAbsoluto() + (Math.sin((Math.asin(y) - Math.PI/6)) * 11);
			
		}
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		gc.setLineWidth(1.5);
		gc.strokeLine(pontoOrigem.getXAbsoluto(), pontoOrigem.getYAbsoluto(),
						pontoDestino.getXAbsoluto(), pontoDestino.getYAbsoluto());
		
		
		double pontosx[] = {0,0,0};
		double pontosy[] = {0,0,0};
		pontosx[0] = pontoDestino.getXAbsoluto();
		pontosx[1] = x1;
		pontosx[2] = x2;
		
		pontosy[0] = pontoDestino.getYAbsoluto();
		pontosy[1] = y1;
		pontosy[2] = y2;
		
		
		
		gc.fillPolygon(pontosx, pontosy, 3);
	}

	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}
	
	@Override
	public Relacao clone() {
		Relacao_Reta r = new Relacao_Reta();
		r.setOrigem(this.origem, new Ponto(0,0,0,0));
		r.setDestino(this.destino, new Ponto(0,0,0,0));
		r.setPontoOrigem(this.pontoOrigem.clone());
		r.setPontoDestino(this.pontoDestino.clone());
		return r;
	}

}
