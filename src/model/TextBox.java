package model;

import java.io.Serializable;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextBox implements Serializable{
	
	private int xRelativo, yRelativo;
	private int posx, posy;
	private int largura, altura;
	private String texto;
	private int StrokeSize = 1;
	private int fontSize = 10;
	private TextAlignment textAlignment = TextAlignment.CENTER;
	private VPos textBaseline = VPos.CENTER;
	
	public TextBox(int xRelativo, int yRelativo, int x, int y, String texto) {
		
		this.xRelativo = xRelativo;
		this.yRelativo = yRelativo;
		this.posx = x;
		this.posy = y;
		this.setTexto(texto);		
	}
	
	public void render(GraphicsContext gc) {
		gc.setLineWidth(0.5);
		gc.setStroke(Color.BLACK);
        gc.setTextAlign(textAlignment);
        gc.setTextBaseline(textBaseline);
        gc.setFill(Color.BLACK);
		gc.setFont(Font.font(14));
		gc.fillText(this.texto, this.posx + this.xRelativo, this.posy + this.yRelativo);
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getTexto() {
		
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
		Text t = new Text(texto);
		t.setFont(Font.font(14));
		this.largura = (int) t.getLayoutBounds().getWidth();
		this.altura = (int) t.getLayoutBounds().getHeight() + 5;
	}

	public int getStrokeSize() {
		return StrokeSize;
	}

	public void setStrokeSize(int strokeSize) {
		StrokeSize = strokeSize;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getxRelativo() {
		return xRelativo;
	}

	public void setxRelativo(int xRelativo) {
		this.xRelativo = xRelativo;
	}

	public int getyRelativo() {
		return yRelativo;
	}

	public void setyRelativo(int yRelativo) {
		this.yRelativo = yRelativo;
	}

	public TextAlignment getTextAlingnment() {
		return textAlignment;
	}

	public void setTextAlingnment(TextAlignment textAlingnment) {
		this.textAlignment = textAlingnment;
	}

	public VPos getTextBaseLine() {
		return textBaseline;
	}

	public void setTextBaseLine(VPos textBaseLine) {
		this.textBaseline = textBaseLine;
	}
	
	public TextBox clone() {
		TextBox tb= new TextBox(xRelativo,yRelativo,posx,posy, texto);
		tb.setTextAlingnment(this.textAlignment);
		tb.setTextBaseLine(textBaseline);
		return tb;
	}

}
