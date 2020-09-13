package application;
import java.util.LinkedList;
import java.util.List;

import model.Relacao;
import model.abstratos.Relacao_FlechaUnica;
import model.abstratos.Relacao_Indirecao;
import model.abstratos.Relacao_Reta;

public class RelacaoFactory{	
	
	public Relacao getRelacao(int id) {
		
		switch(id) {
		case 0:
			return new Relacao_FlechaUnica();
		case 1:
			return new Relacao_Indirecao();
		default:
			return new Relacao_Reta();
		}
		
	}


}
