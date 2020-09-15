package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Diagrama;
import model.Node;
import model.Projeto;
import model.Relacao;
import model.estrutura.Rect;

public class MainController {
	
	private ExportacaoController controller;
	public TabPane displayTabs;
	public FlowPane displayNodes;
	public FlowPane displayConexoes;
	public VBox display_detalhes;
	public ListView<String> displayAlertas;
	public ListView<String> displayDiagramas;
	public Button botaoAddTab;
	public Button botaoListaDiagramas;
	private Projeto projeto;
	private FileChooser fc = new FileChooser();
	private List<Node> areaTansferenciaNode = new LinkedList<Node>();
	private List<Relacao> areaTransferenciaRelacoes = new LinkedList<Relacao>(); 
	public NodeFactory nodeFactory = new NodeFactory();
	public RelacaoFactory relacaoFactory= new RelacaoFactory();
	public Button ferramenta_selecao;
	

	public void initialize() {
		// instancia variaveis
		projeto = new Projeto(this);
		
		//instancia elementos dinamicos
		try {
			initializeButtons();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		novaDiagrama();
		
		//configura elementos estaticos
		
		this.ferramenta_selecao.setOnAction(e->{
			this.projeto.setModo("selecionar");
		});
		
		
			//adicionar diagrama
		botaoAddTab.setOnAction(e ->{this.novaDiagrama();});
			//display diagramas
		displayDiagramas.setCellFactory(lv -> {

            ListCell<String> celulas = new ListCell<>();

            ContextMenu contextMenu = new ContextMenu();

            MenuItem exibir = new MenuItem("Exibir");
            exibir.setOnAction(e ->{ 
            	this.exibirDiagrama(this.displayDiagramas.getSelectionModel().getSelectedItem());
            	//System.out.println("Buscando Diagrama "+ this.displayDiagramas.getSelectionModel().getSelectedItem());
            	});
                    
          
            MenuItem renomear = new MenuItem("Renomear");
            renomear.setOnAction(e ->{ this.renomearDiagrama(
            		this.displayDiagramas.getSelectionModel().getSelectedItem()
            		);});
            
            
            
            MenuItem excluir = new MenuItem("Excluir");
            excluir.setOnAction(e->{this.excluirDiagrama(
            		this.displayDiagramas.getSelectionModel().getSelectedItem()
            		);});
            
            
            contextMenu.getItems().addAll(exibir, renomear, excluir);
            celulas.textProperty().bind(celulas.itemProperty());

            celulas.emptyProperty().addListener((obs, estavaVazia, estaVazia) -> {
                if (estaVazia) {
                    celulas.setContextMenu(null);
                } else {
                    celulas.setContextMenu(contextMenu);
                }
            });
            return celulas ;
        });
		
		//configuraEventos
		
		
		//configurações finais
		projeto.getDiagramaSelecionado().render();
		projeto.setModo("selecionar");
			
	}
	
	public void novoProjeto() {
		// limpa interface
		this.displayTabs.getTabs().clear();
		this.displayNodes.getChildren().clear();
		this.displayConexoes.getChildren().clear();
		initialize();
	}
	
	private void carregarProjeto(Projeto p) {
		// limpa interface
		this.displayTabs.getTabs().clear();
		//carrega valores
		p.reserializar(this);
		this.projeto = p;
		
		for(int i=0; i<projeto.getDiagramasSize(); i++) {
			this.carregarDiagrama(projeto.getDiagramas().get(i));
		}
	
	}
	
	private void initializeButtons() throws FileNotFoundException {
		
		NodeFactory factory = new NodeFactory();
		
		for (int i = 0; i<12; i++) {
			
			//botoes para adicionar nodes
			//instacia elemento
			String path = getClass().getResource("../view/images/icone0"+i+".png").getPath();
			//System.out.println(path);
			FileInputStream is = new FileInputStream(path);
			Image img = new Image(is);
			ImageView imv = new ImageView(img);
			Button tmpButton = new Button("", imv);
					
			// configura elemento
			int id = i;
			tmpButton.setOnAction(e -> {
				Node node = nodeFactory.getNode(id);
				this.projeto.setModo("adicionar");
				this.projeto.setNodeASerAdicionado(node);
			});
			displayNodes.getChildren().add(tmpButton);
			
		}
		
		for (int i = 0; i<3; i++) {
			
			//botoes para adicionar nodes
			//instacia elemento
			String path = getClass().getResource("../view/images/relacao0"+i+".png").getPath();
			//System.out.println(path);
			FileInputStream is = new FileInputStream(path);
			Image img = new Image(is);
			ImageView imv = new ImageView(img);
			Button tmpButton = new Button("", imv);
			//botoes para adicionar conexões
			int codR = i;
			displayNodes.getChildren().add(tmpButton);	
			tmpButton.setOnAction(e -> {
				projeto.setModo("conectar");
				projeto.setRelacaoASerAdicionado(relacaoFactory.getRelacao(codR));});
			displayConexoes.getChildren().add(tmpButton);
		}
			
	}
	
	private void novaDiagrama() {
		// instancia novos elementos
		Diagrama novoDiagrama = projeto.novoDiagrama();
		Tab nt = new Tab(novoDiagrama.getNome());
		AnchorPane pane = new AnchorPane();
		Canvas canvas = novoDiagrama.getCanvas();
		ContextMenu cm = new ContextMenu();
		MenuItem copiar = new MenuItem("Copiar");
		MenuItem colar = new MenuItem("Colar");
		MenuItem excluir = new MenuItem("Excluir");
		
		excluir.setOnAction(e->{this.projeto.ExcluirNode(); this.clearDetalhes();});
		copiar.setOnAction(e ->{this.projeto.copiar();
			
		});
		colar.setOnAction(e ->{this.projeto.colar();
		});
		
		cm.getItems().addAll(copiar, colar, excluir);
		
		
		
		
		//configura novos elementos
		canvas.setOnMouseClicked(e->{projeto.OnCanvasClicked(e); cm.hide();});
		canvas.setOnMouseDragged(e->{projeto.onCanvasDragged(e);});
		canvas.setOnMouseReleased(e->{projeto.onCanvasMouseReleased(e);});
		canvas.setOnContextMenuRequested(e -> {
			if(!this.projeto.getNodesSelecionados().isEmpty()) {
				excluir.setVisible(true);
				copiar.setVisible(true);
				colar.setVisible(true);
				cm.show(canvas, e.getScreenX(), e.getScreenY());
			}
			else if(!this.projeto.getNodesCopiados().isEmpty()){
				excluir.setVisible(false);
				copiar.setVisible(false);
				colar.setVisible(true);
				cm.show(canvas, e.getScreenX(), e.getScreenY());
			}
			});
		canvas.setOnMouseMoved(e->{projeto.onCanvasMoved(e);});
		
		
		pane.getChildren().add(canvas);
		nt.setContent(pane);
		
		// configura eventos dos elementos
		nt.setOnSelectionChanged(e->{
			projeto.setDiagramaSelecionado(novoDiagrama);
			this.clearDetalhes();
		});
		// altera ambiente
		displayTabs.getTabs().add(nt);
		this.atualizarDisplayDiagramas();
	}
	
	private void carregarDiagrama(Diagrama dia) {
		// instancia novos elementos
		dia.reserializar();
		String nome = dia.getNome();
		Tab nt = new Tab(nome);
		AnchorPane pane = new AnchorPane();
		Canvas canvas = dia.getCanvas();
		ContextMenu cm = new ContextMenu();
		MenuItem copiar = new MenuItem("Copiar");
		MenuItem colar = new MenuItem("Colar");
		MenuItem excluir = new MenuItem("Excluir");
		excluir.setOnAction(e->{this.projeto.ExcluirNode(); this.clearDetalhes();});
		cm.getItems().addAll(copiar, colar, excluir);
		
		copiar.setOnAction(e ->{this.projeto.copiar();
		
		});
		
		colar.setOnAction(e ->{this.projeto.colar();
		
		});
		
		
		//configura novos elementos
		canvas.setOnMouseClicked(e->{projeto.OnCanvasClicked(e); cm.hide();});
		canvas.setOnMouseDragged(e->{projeto.onCanvasDragged(e);});
		canvas.setOnMouseReleased(e->{projeto.onCanvasMouseReleased(e);});
		canvas.setOnMouseMoved(e->{projeto.onCanvasMoved(e);});
		canvas.setOnContextMenuRequested(e -> cm.show(canvas, e.getScreenX(), e.getScreenY()));
		pane.getChildren().add(canvas);
		nt.setContent(pane);
		
		// configura eventos dos elementos
		nt.setOnSelectionChanged(e->{
			projeto.setDiagramaSelecionado(dia);
			this.clearDetalhes();
		});
		// altera ambiente
		displayTabs.getTabs().add(nt);
		this.atualizarDisplayDiagramas();
	}
	
	private void atualizarDisplayDiagramas() {
		this.displayDiagramas.getItems().clear();
		for(int i=0; i<projeto.getDiagramasSize(); i++) {
			this.displayDiagramas.getItems().add(projeto.getDiagramaName(i));
		}
	}
	
	public void clearDetalhes() {
		this.display_detalhes.getChildren().clear();
	}
	
	public void salvar() {
		if (this.projeto.salvo) {
			try {
				File file = new File(projeto.getPath());
				FileOutputStream fs = new FileOutputStream(file.getAbsolutePath());
				ObjectOutputStream os = new ObjectOutputStream(fs);
				os.writeObject(this.projeto);
				os.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			salvarComo();
		}
	}
	
	
	public void salvarComo() {
		
		fc.setInitialDirectory(new File(System.getProperty("user.dir")+"/projects"));
		fc.setTitle("Salvar Projeto");
		fc.setInitialFileName("Projeto Sem Titulo");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Projeto MolicWorks (*.mws)", "*.mws"));
		
		try{
			File file = fc.showSaveDialog(this.displayDiagramas.getScene().getWindow());
			fc.setInitialDirectory(file.getParentFile());
			
			try {
				projeto.setPath(file.getAbsolutePath());
				projeto.salvo = true;
				FileOutputStream fs = new FileOutputStream(file.getAbsolutePath());
				ObjectOutputStream os = new ObjectOutputStream(fs);
				os.writeObject(this.projeto);
				os.close();
			}
			catch(Exception e) {
				projeto.setPath("");
				projeto.salvo = false;
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void abrir() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.dir")+"/projects"));
		fc.setTitle("Abrir Projeto");
		fc.setInitialFileName("");
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Projeto MolicWorks (*.mws)", "*.mws"));
		
		try{
			File file = fc.showOpenDialog(this.displayDiagramas.getScene().getWindow());
			fc.setInitialDirectory(file.getParentFile());
			
			try {
				FileInputStream fs = new FileInputStream(file.getAbsolutePath());
				ObjectInputStream os = new ObjectInputStream(fs);
				//Projeto teste = (Projeto)os.readObject();
				carregarProjeto((Projeto)os.readObject());
				//System.out.println(teste.getDiagramasSize());
				os.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int exibirDiagrama(String d){
		ObservableList<Tab> lista =  this.displayTabs.getTabs();
		for(int i=0; i< lista.size(); i++) {
			if(lista.get(i).getText() == d) {
				this.displayTabs.getSelectionModel().select(lista.get(i));
				return 0;
			}
		}
		Diagrama di;
		for(int i=0; i< this.projeto.getDiagramas().size(); i++) {
			di = this.projeto.getDiagramas().get(i);
			if(di.getNome().equals(d)) {
				this.carregarDiagrama(di);
				return 1;
			}
		}
		return -1;
	}
	
	public void renomearDiagrama(String nomeDiagrama) {
		
		
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Renomear Diagrama");
		dialog.setHeaderText("Renomear o diagrama");
		dialog.setContentText("Insira o novo nome:");
		
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(name ->{
			
				//System.out.println("Buscando diagrama "+ nomeDiagrama);
				//System.out.println("Renomeado para "+ result.get());
			
			
				int message = projeto.renomerDiagrama(nomeDiagrama, result.get());
				this.atualizarDisplayDiagramas();
				Alert alert;
				switch(message) {
				case 0:
					
					for(int i=0; i < this.displayTabs.getTabs().size(); i++) {
						if(this.displayTabs.getTabs().get(i).getText().equals(nomeDiagrama)) {
							this.displayTabs.getTabs().get(i).setText(result.get());
						}
					}
					
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Diagrama Renomeado");
					alert.setHeaderText("O diagrama foi Renomeado");
					alert.showAndWait();
					break;
				case -2:
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText("O nome inserido ja esta sendo utilizado");
					alert.showAndWait();
					break;
				case -1:
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro");
					alert.setHeaderText("Um erro foi indentificado na estrutura do projeto\n"
							+ "O projeto esta corrompido");
					alert.showAndWait();
					break;
				}
		});
	}
	public void excluirDiagrama(String nome) {
		projeto.excluirDiagrama(nome);
		for(int i=0; i< displayTabs.getTabs().size(); i++) {
			if(displayTabs.getTabs().get(i).getText().equals(nome)) {
				displayTabs.getTabs().remove(i);
			}
		}
		this.atualizarDisplayDiagramas();
		
	}
	
	public void atualizarAlertas(List<String> alertas){
		this.displayAlertas.getItems().clear();
		for(int i=0; i< alertas.size(); i++) {
			this.displayAlertas.getItems().add(alertas.get(i));
		}
		
	}
	
	public void exportarDiagrama() throws IOException {
		controller = new ExportacaoController();
		controller.injetarController(this);		
		final Stage dialog = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/exportarDiagrama.fxml"));
		loader.setController(controller);
		dialog.setScene(new Scene(loader.load()));
		dialog.setTitle("Exportar Diagramas");
		dialog.show();
	}
	
	public void copiar() {
		this.projeto.copiar();
	}
	
	public void colar() {
		this.projeto.colar();
	}
	
	public void excluir() {
		this.projeto.ExcluirNode();
	}
	
	public void selecionarTodos() {
		this.projeto.selecionarTodos();
	}
	
	public void deselecionarTodos() {
		this.projeto.deselecionarTodos();
	}
	
	public Projeto getProjeto() {
		return this.projeto;
	}
	
	public void encerrar() {
		System.exit(0);
	}
}
