package application;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import model.SVG;

public class SVGFactory implements Serializable{
	
	String DHT[] = new String[10];
	List<SVG> biblioteca = new LinkedList<SVG>();

	
	
	public SVGFactory() {
		
		//quadrado
		DHT[0]="M 0 0 L 31 0 L 31 22 L 0 22 z ";
		// 
		DHT[1] = "M 0 0 L 40 0 L 40 20 L 0 20 L 0 0 M 0 23 L 40 23";
		//circulo de pontas arrendondadas
		DHT[2] = "M 0 10 Q 0 0 10 0 L 50 0 Q 60 0 60 10 L 60 30 Q 60 40 50 40 L 10 40 Q 0 40 0 30 z";
		DHT[3] = "M 0 0 L 40 0 L 40 20 L 0 20 L 0 0 M 45 20 L 45 0";
		//cena
		DHT[4] = "M 0 10 Q 0 0 10 0 L 50 0 Q 60 0 60 10 L 60 30 Q 60 40 50 40 L 10 40 Q 0 40 0 30 L 0 10 M 0 10 L 60 10 z ";
		//circulo
		DHT[5] = "M 5 10 A 1 1 0 0 1 5 0 A 1 1 0 0 1 5 10";
		//Acesso ubiquo
		DHT[6] = "M 7 0 L 33 0 C 40 0 40 10 33 10 L 7 10 C 0 10 0 0 7 0 z ";
		//abertura
		DHT[7] = "M 7 0 L 7 0 C 14 0 14 10 7 10 L 7 10 C 0 10 0 0 7 0 z ";
		DHT[8] = "M 7 0 L 7 0 C 14 0 14 10 7 10 L 7 10 C 0 10 0 0 7 0 M 7 2 C 3 2 3 8 7 8 C 11 8 11 2 7 2";
		
		
		for(int i=0; i< DHT.length-1; i++) {
			biblioteca.add(new SVG(DHT[i],0,0,20,20));
		}
		
	}
	
	
	public SVG buildSVG(String id) { 
	
		switch(id) {
		case "DHT_000":
			return biblioteca.get(0);
		case "DHT_001":
			return biblioteca.get(1);
		case "DHT_002":
			return biblioteca.get(2);
		case "DHT_003":
			return biblioteca.get(3);
		case "DHT_004":
			return biblioteca.get(4);
		case "DHT_005":
			return biblioteca.get(5);
		case "DHT_006":
			return biblioteca.get(6);
		case "DHT_007":
			return biblioteca.get(7);
		case "DHT_008":
			return biblioteca.get(7);
		default:
			return new SVG("",0,0,20,20);
		}
	}

}
