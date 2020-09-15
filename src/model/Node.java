package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.estrutura.Cor;
import model.estrutura.Ponto;
import model.estrutura.Rect;

public abstract class Node implements Serializable{
	Rect rect = new Rect(0,0,100,100);
	protected Double especura = 1.5;
	transient protected boolean selected = false;
	protected Cor background ;
	protected Cor line;
	protected SVG svg;
	protected TextBox texto;
	protected List<Ponto> pontosAncoragem;
	protected boolean isBackground = false;

	
	
	public Node(int x, int y,int largura, int altura, SVG SVGPath) {
		
		this.rect.setX(x);
		this.rect.setY(y);
		this.rect.setLargura(largura);
		this.rect.setAltura(altura);
		pontosAncoragem = new LinkedList<Ponto>();
		background = new Cor(1,1,1);
		line =new Cor(0,0,0);
		svg = SVGPath;
	}
	
	public abstract void updatePontosAncoragem();
	public abstract void updateElementos();
	
	
	public boolean inArea(int x, int y) {
		if( (x >= this.getX() && x <= this.getX() + this.getLargura()) &&
			(y >= this.getY() && y <= this.getY() + this.getAltura())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void render(GraphicsContext gc) {
		
		
		gc.setFill(Color.color(this.background.R, this.background.G, this.background.B ));
        gc.setStroke(Color.color(this.line.R, this.line.G, this.line.B ));
        gc.setLineWidth(especura);
        svg.setPosition(this.getX(), this.getY());
        svg.setScale(this.getLargura(), this.getAltura());
        svg.render(gc);
        svg.render(gc);
	    texto.render(gc);
	    
		
		
        if(selected) {
        	gc.setLineDashes(5);
        	gc.setFill(Color.rgb(200, 200, 255, 0.2));
        	gc.setStroke(Color.AQUA);
        	gc.fillRect(this.getX()-4, this.getY()-4, this.getLargura()+8, this.getAltura()+8);
        	gc.strokeRect(this.getX()-4, this.getY()-4, this.getLargura()+8, this.getAltura()+8);
        	gc.strokeRect(this.getX()-4, this.getY()-4, this.getLargura()+8, this.getAltura()+8);
        	gc.setLineDashes(0);
        	
        }
	}
	
	public void injetarDetalhes(Pane display, Diagrama diagrama) {
		
		TextArea t1 = new TextArea(texto.getTexto());
		t1.setMinHeight(30);
		t1.setOnKeyTyped(e->{this.texto.setTexto(t1.getText()); diagrama.render();});
		Node node = this;
		
		t1.textProperty().addListener(
			new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			    	node.setTexto(newValue);
			    	diagrama.render();
			    }
		    });
		
		ColorPicker cp = new ColorPicker(Color.color(this.background.R, this.background.G, this.background.B ));
		cp.setMinHeight(30);
		cp.setOnAction(e->{
			this.background.R = cp.getValue().getRed();
			this.background.G = cp.getValue().getGreen();
			this.background.B = cp.getValue().getBlue();
			; diagrama.render();
		});
		
		Label largura = new Label("Largura");
		Label altura = new Label("Altura");
		Slider slider1 = new Slider();
		slider1.setMin(10);
		slider1.setMax(1000);
		slider1.setValue(getLargura());
		
		slider1.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						node.setLargura(arg2.intValue());
						diagrama.render();
						//System.out.println("estou sendo alterado");
					}
				}
		);

		Slider slider2 = new Slider();
		slider2.setMin(10);
		slider2.setMax(1000);
		slider2.setValue(getAltura());
		
		slider2.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
						node.setAltura(arg2.intValue());
						diagrama.render();
						//System.out.println("estou sendo alterado");
					}
				}
		);
	
		display.getChildren().clear();
		display.getChildren().add(new Label("Texto"));
		display.getChildren().add(t1);
		display.getChildren().add(new Label("Cor de fundo"));
		display.getChildren().add(cp);
		display.getChildren().add(largura);
		display.getChildren().add(slider1);
		display.getChildren().add(altura);
		display.getChildren().add(slider2);
		
		
	}
	public void setXY(int x, int y) {
		
		this.setX(x);
		this.setY(y);
		texto.setxRelativo(this.rect.getCoordenada().getXAbsoluto());
		texto.setyRelativo(this.rect.getCoordenada().getYAbsoluto());
		for(int i=0; i<this.pontosAncoragem.size(); i++) {
			this.pontosAncoragem.get(i).setRelativoX(x);
			this.pontosAncoragem.get(i).setRelativoY(y);
		}
	}
	
	public Ponto pontoProximo(Ponto e) {
		Ponto p = new Ponto(0,0,0,0);
		for(int i=0; i < pontosAncoragem.size(); i++) {
			
			int a1  = (int) (p.getXAbsoluto() - e.getXAbsoluto());
			int b1  = (int) (p.getYAbsoluto() - e.getYAbsoluto());
			int a2 = (int) (pontosAncoragem.get(i).getXAbsoluto() - e.getXAbsoluto());
			int b2 = (int) (pontosAncoragem.get(i).getYAbsoluto() - e.getYAbsoluto());
			if (Math.sqrt(a2*a2 + b2*b2)< Math.sqrt(a1*a1 + b1*b1)) {
				p = pontosAncoragem.get(i);
			}
		}
		return p;
	}
	
	public abstract List<String> avaliar();
	public boolean GetSelected() { return selected; }
	public void setSelected(boolean status) { selected = status; }
	public int getX() {return this.rect.getX();}
	public int getY() {return this.rect.getY();}
	public int getLargura() {return this.rect.getLargura();}
	public int getAltura() {return this.rect.getAltura();}
	public void setX(int x) {this.rect.setX(x);}
	public void setY(int y) {this.rect.setY(y);}
	public Rect getRect() { return this.rect;}

	public void setLargura(int largura) {
		this.rect.setLargura(largura);
		this.updatePontosAncoragem();
		this.updateElementos();
	}

	public void setAltura(int altura) {
		this.rect.setAltura(altura);
		this.updatePontosAncoragem();
		this.updateElementos();
	}

	public TextBox getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto.setTexto(texto);
		if (this.texto.getLargura() > this.getLargura()) {
			this.setLargura(this.texto.getLargura());
			this.texto.setPosx(this.getLargura()/2);
		}
		if(this.texto.getAltura() > this.getAltura() - 5) {
			this.setAltura(this.texto.getAltura() + 5);
			this.texto.setPosy(this.texto.getAltura()/2);	
		}
	}

	public List<Ponto> getPontosAncoragem() {
		return pontosAncoragem;
	}

	public void setPontosAncoragem(List<Ponto> pontosAncoragem) {
		this.pontosAncoragem = pontosAncoragem;
	}
	
	abstract public Node clone();

	public Cor getBackground() {
		return background;
	}

	public void setBackground(Cor background) {
		this.background = background;
	}

	public SVG getSvg() {
		return svg;
	}

	public void setSvg(SVG svg) {
		this.svg = svg;
	}

	public void setTexto(TextBox texto) {
		this.texto = texto;
	}

	public boolean isBackground() {
		return isBackground;
	}

	public void setBackground(boolean isBackground) {
		this.isBackground = isBackground;
	}
		

}
