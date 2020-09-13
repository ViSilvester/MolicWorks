package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import application.SVGFactory;
import javafx.scene.canvas.GraphicsContext;

public class DHT extends Diagrama implements Serializable {
	
	public DHT(String nome) {
		super(nome);
	}

	SVGFactory SVGFac = new SVGFactory();
	
	@Override
	public Node novoNode(Node novoNode) {
		getNodes().add(novoNode);
		//System.out.println("NodeAdicionado em x:"+novoNode.getX()+" y:"+novoNode.getY());
		return novoNode;
	}

}
