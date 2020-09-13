package model.abstratos;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Relacao;
import model.estrutura.Ponto;

public class Relacao_FlechaUnica extends Relacao {
		
	@Override
	public List<String> avaliar() {
		return new LinkedList<String>();
	}

	@Override
	public Relacao clone() {
		Relacao_FlechaUnica r = new Relacao_FlechaUnica();
		r.setOrigem(this.origem, new Ponto(0,0,0,0));
		r.setDestino(this.destino, new Ponto(0,0,0,0));
		r.setPontoOrigem(this.pontoOrigem.clone());
		r.setPontoDestino(this.pontoDestino.clone());
		return r;
	}

}
