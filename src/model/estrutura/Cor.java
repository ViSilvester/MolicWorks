package model.estrutura;

import java.io.Serializable;

public class Cor implements Serializable {
	public double R, G, B;
	
	public Cor(double R, double G, double B) {
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public Cor clone() {
		return new Cor(this.R, this.G, this.B);
	}
	
}
