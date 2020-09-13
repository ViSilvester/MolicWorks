package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import model.Relacao;
import model.estrutura.Ponto;

public class Relacao_Indirecao extends Relacao {

	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}
	
	@Override
	public void render (GraphicsContext gc) {
		gc.setLineDashes(5);
		super.render(gc);
		gc.setLineDashes(0);
	}
	
	@Override
	public Relacao clone() {
		Relacao_Indirecao r = new Relacao_Indirecao();
		r.setOrigem(this.origem, new Ponto(0,0,0,0));
		r.setDestino(this.destino, new Ponto(0,0,0,0));
		r.setPontoOrigem(this.pontoOrigem.clone());
		r.setPontoDestino(this.pontoDestino.clone());
		return r;
	}

}
