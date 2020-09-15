package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Diagrama;
import model.Projeto;
import model.estrutura.Rect;

public class ExportacaoController implements Initializable{
	
	MainController mainController;
	public Button left;
	public Button right;
	public Button exportar;
	public Button cancelar;
	public ImageView iv;
	public ScrollPane sp;
	public VBox vb;
	public Label contador;
	public Label nome;
	private int qtdDiagramas;
	private int imagemSelecionada;
	private List<CheckBox> checkBoxes = new LinkedList<CheckBox>();
	private List<Diagrama> diagramas = new LinkedList<Diagrama>();
	private List<Integer> ids = new LinkedList<Integer>();
	private List<WritableImage> imagens = new LinkedList<WritableImage>();
	private DirectoryChooser dc = new DirectoryChooser();

	
	
	public void injetarController(MainController mc) {
		this.mainController = mc;
	}
	
	private void atualizarDiagramasExportaveis(){
		ObservableList<Node> lista = vb.getChildren();
		this.ids.clear();
		this.qtdDiagramas = 0;
		this.imagemSelecionada= 0;
		for(int i=0; i < checkBoxes.size(); i++) {
			if(this.checkBoxes.get(i).isSelected()) {
				this.qtdDiagramas++;
				this.ids.add(i);
			}
		}
		
		this.update();
	}
	
	private void update() {
		this.iv.setImage(imagens.get(ids.get(imagemSelecionada)));
		contador.setText((this.imagemSelecionada+1) + "/" +this.qtdDiagramas);
		this.nome.setText(diagramas.get(ids.get(imagemSelecionada)).getNome());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		diagramas = mainController.getProjeto().getDiagramas();
		Projeto projeto = mainController.getProjeto();
		
		for(int i=0; i< diagramas.size(); i++) {
			CheckBox cb = new CheckBox();
			cb.setSelected(true);
			cb.setId("0"+i);
			this.checkBoxes.add(cb);
			
			cb.setOnAction(e ->{
				atualizarDiagramasExportaveis();
			});
			
			Label nome = new Label(diagramas.get(i).getNome()+"   ");
			HBox linha = new HBox(nome, cb);
			vb.getChildren().add(linha);
			
			diagramas.get(i).render();
			Rect borda  = diagramas.get(i).getBordas();
			diagramas.get(i).renderFinal();
			Image snap = diagramas.get(i).getCanvas().snapshot(null, null);
			PixelReader reader = snap.getPixelReader();
			WritableImage newImage = new WritableImage(reader, borda.getX(), borda.getY(), borda.getLargura()+2, borda.getAltura()+2);
			this.imagens.add(newImage);
			diagramas.get(i).render();
		
		}
		

		cancelar.setOnAction(e->{
			Stage stage = (Stage)cancelar.getScene().getWindow();
			stage.close();
		});
		
		exportar.setOnAction(e ->{
			this.exportarDiagramas();
			Stage stage = (Stage)cancelar.getScene().getWindow();
			stage.close();
		});
		
		right.setOnAction(e ->{
			if(this.imagemSelecionada < qtdDiagramas-1) {
				this.imagemSelecionada++;
				this.update();
			}			
		});
		
		left.setOnAction(e ->{
			if(this.imagemSelecionada > 0) {
				this.imagemSelecionada--;
				this.update();
			}	
		});
		
		this.iv.setImage(imagens.get(imagemSelecionada));
		
		this.atualizarDiagramasExportaveis();
	}
	
	public int exportarDiagramas() {
		dc.setInitialDirectory(new File(System.getProperty("user.dir")+"/projects"));
		dc.setTitle("Selecionar Pasta");
		
		try{
			File file = dc.showDialog(mainController.display_detalhes.getScene().getWindow());
			dc.setInitialDirectory(file.getParentFile());
			
			try {
				
				for(int i = 0; i< this.qtdDiagramas; i++) {
					String nome = "\\"+ diagramas.get(ids.get(i)).getNome()+".png";
					if(new File(file.getAbsolutePath()+nome).exists()) {
						Alert alert;
						alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Conflito entre arquivos");
						String mensagem = "Foram indentidicados arquivo(s)\ncom o mesmo(s) nome(s) de alguns de seus diagramas.\n";
						mensagem += "Continuar o processo irá sobreescrever os arquivos, deseja conbtinuar?";
						alert.setHeaderText(mensagem);
						Optional<ButtonType> result = alert.showAndWait();
						if(result.get() != ButtonType.OK) {
							return -1;
						}
					}
					break;
				}
				for(int i = 0; i< this.qtdDiagramas; i++) {
					WritableImage image = imagens.get(ids.get(i));
					String nome = "\\"+ diagramas.get(ids.get(i)).getNome()+".png";
					ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(file.getAbsolutePath()+nome));
					//System.out.println(new File(file.getAbsolutePath()+nome).getAbsolutePath());
				}	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}

