package MainModule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import BoardModule.BoardController;
import ClassPackage.BoardTableView;
import ClassPackage.User;
import CreateDialogModule.ChkDialogMain;
import Dao.BoardDao;
import Dao.DeptDao;
import Dao.LoginDao;
import Dao.UserInfoDao;
import EncryptionDecryption.PasswordEncryption;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : ���� ȭ��
Ŭ���� �̸� : MainController
������ : 0.5.0
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- MainController.java (�α��� ���� �����ϴ� ���α׷��� ����ȭ��)

�ʿ� fxml����
- main.fxml (����ȭ�� â fxml)

�ʿ� import ����� ���� package
- 

�ش� Ŭ���� �ֿ� ���
- 

���� ���� ����
1.0.0
- 
 */
public class MainController implements Initializable {
	@FXML private ToggleButton toggleBtnNotice, toggleBtnUser, toggleBtnChat, toggleBtnBoard; // ���� ���� ��ư
	@FXML private ToggleGroup groupCategory;	//���� ���� ��ư ��� �׷�
	@FXML private Button btnSchedule;	//���� ���� ���� ��ư
	@FXML private Button btnWrite, btnBoardRefresh;	//�Խù� �� �۾���, ���ΰ�ħ ��ư
	@FXML private Button btnUserRefresh;	//����� �� ��� ���ΰ�ħ ��ư
	@FXML private ComboBox<String> comboSideFilter;		//������ ���� �̸� �˻� �ʵ�
	@FXML private TableView<BoardTableView> viewBoardList;	//�Խ��� �� ���̺��
	@FXML private TableView<User> viewSIdeUserList;	//������ ���� ����� ��� ���̺��
	@FXML private AnchorPane paneNotice, paneUser, paneChat, paneBoard, stackedPane;
	@FXML private TextField fieldCenterFilter, fieldSideFilter;	//�̸� �˻� �ʵ�
	@FXML private TabPane paneUserList;	//����� �� ��� ����
	@FXML private ImageView viewUserImg;
	@FXML private Label lblMyDept, lblMyName, lblMyPosition, lblMyGreet;

	BackgroundFill selectedFill = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
	Background selectedBack = new Background(selectedFill);

	BackgroundFill notSelectedFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
	Background notSelectedBack = new Background(notSelectedFill);
	
	//�Խ��ǿ��� ����ϴ� ����Ʈ
	ObservableList<BoardTableView> list = FXCollections.observableArrayList();
	ObservableList<String> comboList = FXCollections.observableArrayList();
	
	BoardDao bd = new BoardDao();
	LoginDao ld = new LoginDao();
	UserInfoDao userInfoDao = new UserInfoDao();
	DeptDao deptDao = new DeptDao();
	
	User myProfile;	//���� �������� ������ ������ �������� ����
	public static String USER_NO;	//���� �������� ����� ��ȣ�� �������� ����
	
	
	//������ �������� ����ϴ� ����Ʈ
	ObservableList<User> sideUserList = FXCollections.observableArrayList();	//������ ���� ���̺�� ����� ��� ����Ʈ
	ObservableList<String> sideFilterList = FXCollections.observableArrayList();	//������ ���� �޺��ڽ� ���Ϳ� ����Ʈ
	
	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		USER_NO = loadUserNo();
		myProfile = userInfoDao.selectMyInfo(USER_NO);
		
		// ���� ���� ��ư �ʱ�ȭ
		selected(toggleBtnNotice);
		notSelected(toggleBtnUser);
		notSelected(toggleBtnChat);
		notSelected(toggleBtnBoard);
		paneNotice.setVisible(true);
		paneUser.setVisible(false);
		paneChat.setVisible(false);
		paneBoard.setVisible(false);
		btnSchedule.setOnMouseClicked(event -> schedule());
		
		//���� ���� ��� ��ư �׷�ȭ
		groupCategory.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				click(newValue.getUserData().toString());
			}
		});

		// ������ ���� �κ�
		viewSIdeUserList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);	//���� ��ũ�ѹ� ���ַ��� ��
		createSideTable(viewSIdeUserList);
		viewSIdeUserList.setOnMouseClicked(event -> {
			if(event.getClickCount() >= 2) {
				String userNo = viewSIdeUserList.getSelectionModel().getSelectedItem().getUserNo();
				ChkDialogMain.businessCardDialog(userNo);
			}
		});
		
		//������ ���� �޺��ڽ� ����� �κ�
		deptDao.loadDept(sideFilterList);
		comboSideFilter.setItems(sideFilterList);
		//�޺��ڽ� ���͸� �������� �������� ���
		comboSideFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> filterAction());
		//�ؽ�Ʈ�ʵ� ���͸� �������� �������� ���
		fieldSideFilter.setOnKeyPressed(event -> {
			KeyCode keyCode = event.getCode();
			//Ű���忡�� ���͸� ������ �� �����Ѵٴ� ��
			if(keyCode.equals(KeyCode.ENTER)) {
				filterAction();
			}
		});
		
		//������ ���� ���ΰ�ħ ��ư
		btnUserRefresh.setOnAction(event -> {
			comboSideFilter.getSelectionModel().selectFirst();
			fieldSideFilter.clear();
			userInfoDao.loadAllUser(sideUserList, USER_NO);
			createSideTable(viewSIdeUserList);
		});
		
		// �˸� �� �κ�

		// ����� ���� �� �κ�

		// ä�ù� �� �κ�

		// �Խ��� �� �κ�
		// ���̺�信�� ��ũ�ѹ� ���ִ� �ڵ�
//		viewBoardList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		createTable();
//		btnWrite.setOnAction(event -> writeBoard());
//		btnBoardRefresh.setOnAction(event -> {
//			bd.loadAllBbsList(viewBoardList, list);
//			comboFilter.getSelectionModel().selectFirst();
//		});
		// ���� �� �κ�
	}

	public void createSideTable(TableView<User> sideTable) {
		//���̺���� ��� �κо��ִ� �κ�
		sideTable.widthProperty().addListener((obs, oldValue, newValue) -> {
			Pane header = (Pane)sideTable.lookup("TableHeaderRow");
			if(header.isVisible()) {
				header.setPrefHeight(0);
				header.setVisible(false);
			}
		});
		
		//���̺���� ���� ����� �۾�
		TableColumn<User, User> userInfoCol = new TableColumn<User, User>();
		userInfoCol.setStyle("-fx-pref-width:90; -fx-border-width:1; -fx-pref-height:20; -fx-alignment:center-left");
		userInfoCol.setCellValueFactory(new Callback<CellDataFeatures<User,User>, ObservableValue<User>>() {
			@Override
			public ObservableValue<User> call(CellDataFeatures<User, User> param) {
				return new ReadOnlyObjectWrapper<User>(param.getValue());
			}
		});
		userInfoCol.setCellFactory(new Callback<TableColumn<User,User>, TableCell<User,User>>() {
			@Override
			public TableCell<User, User> call(TableColumn<User, User> param) {
				return new TableCell<User, User>() {
					private HBox box;
					private Label dept, name, position;
					{
						box = new HBox();
						dept = new Label();
						name = new Label();
						position = new Label();
						box.getChildren().addAll(dept, name, position);
						dept.setStyle("-fx-pref-width:30; -fx-font-size:13; -fx-alignment:ceter-right");
						name.setStyle("-fx-pref-width:45; -fx-font-size:14; -fx-font-weight:bold; -fx-alignment:center-left;");
						position.setStyle("-fx-pref-width:20; -fx-font-size:12; -fx-alignment:center-left");
					}
					@Override
					protected void updateItem(User item, boolean empty) {
						super.updateItem(item, empty);
						if(item == null) {
							setGraphic(null);
						}
						else {
							dept.setText(item.getUserDept());
							name.setText(item.getUserName());
							position.setText(item.getUserPosition());
							setGraphic(box);
						}
					}
				};
			}
		});
		
		userInfoDao.loadAllUser(sideUserList, USER_NO);
		sideTable.setItems(sideUserList);
		sideTable.getColumns().add(userInfoCol);
	}
	
	
//	public void filterAction() {
//		if(fieldFilter.getText().isEmpty() || fieldFilter.getText().equals("")) {	//�˻��ʵ尡 ����ִٸ�
//			if(comboFilter.getSelectionModel().getSelectedItem().equals("��ü")) {		//�޺��ڽ��� ��ü�� �����ϸ� ��� ���� �ҷ���
//				uid.loadAllUser(userList, viewUserList, userNo);
//			}
//			else {	//�ʵ尡 ����ְ� �޺��ڽ��� �����ϸ� �޺��ڽ��� �������� ��� �ҷ���
//				uid.loadFilteredAllUser(userList, viewUserList, userNo, comboFilter.getSelectionModel().getSelectedItem());
//			}
//		}
//		else {	//�ʵ忡 ���𰡰� �����ִٸ�
//			if(comboFilter.getSelectionModel().getSelectedItem().equals("��ü")) {	//�ʵ忡 �����ְ� ��ü�� �����ϸ� �ʵ带 �������� ��� �ҷ���
//				uid.loadFilteredAllUser(userNo, userList, viewUserList, fieldFilter.getText());
//			}
//			else {	//�ʵ忡 �����ְ� �޺��ڽ��� �����ϸ� �� �� ������ ��� �ҷ���
//				uid.loadFilteredAllUser(userList, viewUserList, userNo, comboFilter.getSelectionModel().getSelectedItem(), fieldFilter.getText());
//			}
//		}
//	}
	
	
	
	public void filterAction() {
		
	}
	
	// �Խ��� ���� ���̺� �����ϴ� �κ�
	public void createTable() {
		TableColumn<BoardTableView, String> col1 = new TableColumn<BoardTableView, String>();
		col1.setStyle(
				"-fx-pref-width:40; -fx-border-width:1; -fx-pref-height:40; -fx-font-size:10px; -fx-alignment:center");
		col1.setCellValueFactory(new PropertyValueFactory<BoardTableView, String>("boardHeader"));
		TableColumn<BoardTableView, String> col2 = new TableColumn<BoardTableView, String>();
		col2.setStyle(
				"-fx-pref-width:180; -fx-border-width:1; -fx-pref-height:40; -fx-font-size:15px; -fx-alignment:center-left");
		col2.setCellValueFactory(new PropertyValueFactory<BoardTableView, String>("boardTitle"));
		TableColumn<BoardTableView, BoardTableView> col3 = new TableColumn<BoardTableView, BoardTableView>();
		col3.setStyle("-fx-pref-width:85; -fx-border-width:1; -fx-pref-height:40; -fx-alignment:center-left");

		col3.setCellValueFactory(
				new Callback<CellDataFeatures<BoardTableView, BoardTableView>, ObservableValue<BoardTableView>>() {
					@SuppressWarnings("rawtypes")
					@Override
					public ObservableValue<BoardTableView> call(
							CellDataFeatures<BoardTableView, BoardTableView> features) {
						return new ReadOnlyObjectWrapper<BoardTableView>(features.getValue());
					}
				});
		col3.setCellFactory(
				new Callback<TableColumn<BoardTableView, BoardTableView>, TableCell<BoardTableView, BoardTableView>>() {
					@Override
					public TableCell<BoardTableView, BoardTableView> call(
							TableColumn<BoardTableView, BoardTableView> param) {
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
								if (item == null) {
									setGraphic(null);
								} else {
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

		bd.loadAllBbsList(viewBoardList, list);
		comboFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("��ü�Խ���")) {
					bd.loadAllBbsList(viewBoardList, list);
				} else {
					bd.loadFilteredBbsList(viewBoardList, list, newValue);
				}
			}
		});

		// ���̺���� ��� ������ ���ִ� �ڵ�
		viewBoardList.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Pane header = (Pane) viewBoardList.lookup("TableHeaderRow");
				if (header.isVisible()) {
					header.setPrefHeight(0);
					header.setVisible(false);
				}
			}
		});

		// ���̺�信�� ���콺 ����Ŭ�� ������ ����
		// ���̺�信�� �ش� �Խù��� ����Ŭ���ϸ� �ش� �Խù��� ������ ������ �� ����.
		viewBoardList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					// ����Ŭ���� �ߴٸ� �Խù� ���� â�� ����
					readContent(viewBoardList.getSelectionModel().getSelectedItem());
				}
			}
		});
	}
	
	public String loadUserNo() {
//		String path = System.getProperty("user.home") + "/Documents/MySNS/id.txt;		//��10���� �� ������ �ִ� ���� ã���� �� �� �ִ� ���
		String path = "c:/MySNS/id.txt";
//		String path = "e:/MySNS/id.txt";
		String id = new String();
		FileReader fr = null;
		BufferedReader br = null;
		StringWriter sw = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			sw = new StringWriter();
			int ch = 0;
			while((ch = br.read()) != -1) {
				sw.write(ch);
			}
			br.close();
			//��ȣȭ�ؼ� ����ڹ�ȣ�� ������
			id = PasswordEncryption.pwDecryption(sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	//�Խ��� �ǿ��� �Խù��� ����Ŭ������ �� �Խù� ���� â�� ����� �޼���
	public void readContent(BoardTableView selectedCell) {
		BoardController.BBS_ID = selectedCell.getBoardNo();
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
	
	//�Խ��� �ǿ��� �۾��� ��ư�� ������ ��
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
	
	//���� ���� ��۹�ư �޼���
	//������ ������ ��ư ���°� �޶������� ��
	public void click(String btnName) {
		//�˸� �� ��ư�� ������ ��
		if (btnName.equals("notice")) {
			selected(toggleBtnNotice);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnChat);
			notSelected(toggleBtnBoard);

			paneNotice.setVisible(true);
			paneUser.setVisible(false);
			paneChat.setVisible(false);
			paneBoard.setVisible(false);
		}
		//����� �� ��ư�� ������ ��
		else if (btnName.equals("user")) {
			notSelected(toggleBtnChat);
			selected(toggleBtnUser);
			notSelected(toggleBtnNotice);
			notSelected(toggleBtnBoard);

			paneNotice.setVisible(false);
			paneUser.setVisible(true);
			paneChat.setVisible(false);
			paneBoard.setVisible(false);
		}
		//ä�� ���� ������ ��
		else if (btnName.equals("chat")) {
			selected(toggleBtnChat);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnNotice);
			notSelected(toggleBtnBoard);

			paneNotice.setVisible(false);
			paneUser.setVisible(false);
			paneChat.setVisible(true);
			paneBoard.setVisible(false);
		}
		//�Խ��� ���� ������ ��
		else if (btnName.equals("board")) {
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
	
	//��� ��ư�� ������ ��
	public void selected(ToggleButton btn) {
		btn.setDisable(true);
		btn.setBackground(selectedBack);
	}
	//����� Ǯ���� ��
	public void notSelected(ToggleButton btn) {
		btn.setDisable(false);
		btn.setBackground(notSelectedBack);
	}
	
	//���� ��ư�� ������ �� ����
	public void schedule() {

	}
}
