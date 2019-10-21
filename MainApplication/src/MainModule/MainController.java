package MainModule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import BoardModule.BoardController;
import BoardModule.BoardTableView;
import Dao.BoardDao;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MainController implements Initializable {	
	@FXML
	private ToggleButton toggleBtnNotice, toggleBtnUser, toggleBtnChat, toggleBtnBoard;
	@FXML
	private ToggleGroup groupCategory;
	@FXML
	private Button btnSchedule, btnWrite, btnRefresh;
	@FXML
	private ComboBox<String> comboFilter;
	@FXML
	private TableView<BoardTableView> viewBoardList;
	@FXML
	private AnchorPane paneNotice, paneUser, paneChat, paneBoard;
	
	BackgroundFill selectedFill = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
	Background selectedBack = new Background(selectedFill);

	BackgroundFill notSelectedFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
	Background notSelectedBack = new Background(notSelectedFill);
	
	ObservableList<BoardTableView> list = FXCollections.observableArrayList();
	ObservableList<String> comboList = FXCollections.observableArrayList("��ü�Խ���","�����Խ���","�渮��","������");
	BoardDao bd = new BoardDao();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//���� ���� ��ư �ʱ�ȭ
		selected(toggleBtnNotice);
		notSelected(toggleBtnUser);
		notSelected(toggleBtnChat);
		notSelected(toggleBtnBoard);
		paneNotice.setVisible(true);
		paneUser.setVisible(false);
		paneChat.setVisible(false);
		paneBoard.setVisible(false);
		btnSchedule.setOnMouseClicked(event -> schedule());
		
		
		//������ ���� �κ�
		
		
		//�˸� �� �κ�
		
		
		//����� ���� �� �κ�
		
		//ä�ù� �� �κ�
		
		
		//�Խ��� �� �κ�
		//���̺�信�� ��ũ�ѹ� ���ִ� �ڵ�
		viewBoardList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		createTable();
		btnWrite.setOnAction(event -> writeBoard());
		btnRefresh.setOnAction(event -> {
			bd.loadAllBbsList(viewBoardList, list);
			comboFilter.getSelectionModel().selectFirst();
		});
		//���� �� �κ�
	}
	
	
	//�Խ��� ���� ���̺� �����ϴ� �κ�
	@SuppressWarnings("unchecked")
	public void createTable() {
		TableColumn<BoardTableView, String> col1 = new TableColumn<BoardTableView, String>();
		col1.setStyle("-fx-pref-width:40; -fx-border-width:1; -fx-pref-height:40; -fx-font-size:10px; -fx-alignment:center");
		col1.setCellValueFactory(new PropertyValueFactory<BoardTableView, String>("boardHeader"));
		TableColumn<BoardTableView, String> col2 = new TableColumn<BoardTableView, String>();
		col2.setStyle("-fx-pref-width:190; -fx-border-width:1; -fx-pref-height:40; -fx-font-size:15px; -fx-alignment:center-left");
		col2.setCellValueFactory(new PropertyValueFactory<BoardTableView, String>("boardTitle"));
		TableColumn<BoardTableView, BoardTableView> col3 = new TableColumn<BoardTableView, BoardTableView>();
		col3.setStyle("-fx-pref-width:75; -fx-border-width:1; -fx-pref-height:40; -fx-alignment:center-left");
		
		col3.setCellValueFactory(new Callback<CellDataFeatures<BoardTableView, BoardTableView>, ObservableValue<BoardTableView>>() {
	          @SuppressWarnings("rawtypes")
			@Override public ObservableValue<BoardTableView> call(CellDataFeatures<BoardTableView, BoardTableView> features) {
	              return new ReadOnlyObjectWrapper(features.getValue());
	          }
	        });
		col3.setCellFactory(new Callback<TableColumn<BoardTableView,BoardTableView>, TableCell<BoardTableView,BoardTableView>>() {
			@Override
			public TableCell<BoardTableView, BoardTableView> call(TableColumn<BoardTableView, BoardTableView> param) {
				return new TableCell<BoardTableView, BoardTableView>() {
					private VBox box;
					private Label writer;
					private Label date;
					{
						box = new VBox();
						writer = creatLabel();
						date = creatLabel();
						box.getChildren().addAll(writer, date);
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					}
					
					private final Label creatLabel() {
						Label label = new Label();
						VBox.setVgrow(label, Priority.ALWAYS);
						return label;
					}
					@Override
					protected void updateItem(BoardTableView item, boolean empty) {
						super.updateItem(item, empty);
						if(item == null) {
							setGraphic(null);
						}
						else {
							writer.setText(item.getBoardWriter());
							date.setText(item.getBoardDate());
							setGraphic(box);
						}
					}
				};
			}
		});
		viewBoardList.getColumns().addAll(col1, col2, col3);
		viewBoardList.setItems(list);
		
		//���Ϳ� �޺��ڽ� �����ϴ� �κ�
		comboFilter.setItems(comboList);
		comboFilter.getSelectionModel().selectFirst();
		
		groupCategory.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				click(newValue.getUserData().toString());
			}
		});
		
		bd.loadAllBbsList(viewBoardList, list);
		comboFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("��ü�Խ���")) {
					bd.loadAllBbsList(viewBoardList, list);
				}
				else {
					bd.loadFilteredBbsList(viewBoardList, list, newValue);
				}
			}
		});
		
		//���̺���� ��� ������ ���ִ� �ڵ�
		viewBoardList.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Pane header = (Pane)viewBoardList.lookup("TableHeaderRow");
				if(header.isVisible()) {
					header.setPrefHeight(0);
					header.setVisible(false);
				}
			}
		});
		
		//���̺�信�� ���콺 ����Ŭ�� ������ ����
		//���̺�信�� �ش� �Խù��� ����Ŭ���ϸ� �ش� �Խù��� ������ ������ �� ����.
		viewBoardList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() >= 2) {
					//����Ŭ���� �ߴٸ� �Խù� ���� â�� ����
					readContent(viewBoardList.getSelectionModel().getSelectedItem());
				}
			}
		});
	}
	
	//�Խù� ���� â ���� �޼���
	public void readContent(BoardTableView selectedCell) {
		BoardController.BBS_ID = selectedCell.getBoardId();
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent readBoardWindow = FXMLLoader.load(getClass().getResource("/BoardModule/board.fxml"));
			Scene scene = new Scene(readBoardWindow);
			stage.setResizable(false);
			stage.setTitle(selectedCell.getBoardTitle());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeBoard() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent readBoardWindow = FXMLLoader.load(getClass().getResource("/BoardModule/boardWrite.fxml"));
			Scene scene = new Scene(readBoardWindow);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void click(String btnName) {
		if(btnName.equals("notice")) {
			selected(toggleBtnNotice);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnChat);
			notSelected(toggleBtnBoard);
			
			paneNotice.setVisible(true);
			paneUser.setVisible(false);
			paneChat.setVisible(false);
			paneBoard.setVisible(false);
		}
		else if(btnName.equals("user")) {
			notSelected(toggleBtnChat);
			selected(toggleBtnUser);
			notSelected(toggleBtnNotice);
			notSelected(toggleBtnBoard);
			
			paneNotice.setVisible(false);
			paneUser.setVisible(true);
			paneChat.setVisible(false);
			paneBoard.setVisible(false);
		}
		else if(btnName.equals("chat")) {
			selected(toggleBtnChat);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnNotice);
			notSelected(toggleBtnBoard);
			
			paneNotice.setVisible(false);
			paneUser.setVisible(false);
			paneChat.setVisible(true);
			paneBoard.setVisible(false);
		}
		else if(btnName.equals("board")) {
			selected(toggleBtnBoard);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnChat);
			notSelected(toggleBtnNotice);
			
			paneNotice.setVisible(false);
			paneUser.setVisible(false);
			paneChat.setVisible(false);
			paneBoard.setVisible(true);
		}
	}

	public void selected(ToggleButton btn) {
		btn.setDisable(true);
		btn.setBackground(selectedBack);
	}

	public void notSelected(ToggleButton btn) {
		btn.setDisable(false);
		btn.setBackground(notSelectedBack);
	}

	public void schedule() {

	}
}
