package application;

import java.io.Serializable;

import model.Node;
import model.SVG;
import model.abstratos.DI_AcessoUbiquo;
import model.abstratos.DI_Cena;
import model.abstratos.DI_Fechamento;
import model.abstratos.DI_ProcessoInterno;
import model.abstratos.G_TextBox;
import model.abstratos.DHT_TarefaOpcional;
import model.abstratos.DHT_Tarefa;
import model.abstratos.DI_Abertura;

public class NodeFactory implements Serializable {
	
	SVGFactory factory = new SVGFactory();
	
	public Node getNode(int id) {
		Node novoNode;
		switch (id) {
		case 0:
			//retangulo
			novoNode = new DHT_Tarefa(0,0,120,80, factory.buildSVG("DHT_000"));
			break;
		case 1:
			novoNode = new DHT_Tarefa(0,0,120,80, factory.buildSVG("DHT_001"));
			break;
		case 2:
			// retangulo pontas arrendondadas
			novoNode = new DHT_Tarefa(0,0,120,80, factory.buildSVG("DHT_002"));
			break;
		case 3:
			novoNode = new DHT_Tarefa(0,0,120,80, factory.buildSVG("DHT_003"));
			break;
		case 4:
			//cena
			novoNode = new DI_Cena(0,0,120,80, factory.buildSVG("DHT_004"));
			break;
		case 5:
			// Processo interno
			novoNode = new DI_ProcessoInterno(0,0,20,20, factory.buildSVG("DHT_000"));
			break;
		case 6:
			// acesso ubiquo
			novoNode = new DI_AcessoUbiquo(0,0,130,40, factory.buildSVG("DHT_006"));
			break;
		case 7:
			// abertura
			novoNode = new DI_Abertura(0,0,26,20, factory.buildSVG("DHT_007"));
			break;
		case 8:
			// fechamento
			novoNode = new DI_Fechamento(0,0,27,21, factory.buildSVG("DHT_008"));
			break;
		case 9:
			// fechamento
			novoNode = new DHT_TarefaOpcional(0,0,120,80, factory.buildSVG("DHT_000"));
			break;
		case 10:
			// fechamento
			novoNode = new G_TextBox(0,0,120,80,factory.buildSVG(""));
			break;
		default:
			novoNode = new DHT_Tarefa(0,0,120,80, factory.buildSVG(""));
			break;
				
		}
		
		return novoNode;
	}
}
