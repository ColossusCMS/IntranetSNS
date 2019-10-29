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
import javafx.scene.control.Tab;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
������ : 0.6.0
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- MainController.java (�α��� ���� �����ϴ� ���α׷��� ����ȭ��)

�ʿ� fxml����
- main.fxml (����ȭ�� â fxml)

�ʿ� import ����� ���� package
- 

�ش� Ŭ���� �ֿ� ���
- ���α׷��� ����ȭ��, �ʿ��� ��Ҹ� ��� ����� �ʱ�ȭ��

���� ���� ����
1.0.0
- 
 */
public class MainController implements Initializable {
	//���� ����
	@FXML private ToggleButton toggleBtnNotice, toggleBtnUser, toggleBtnChat, toggleBtnBoard; // ���� ���� ��ư
	@FXML private ToggleGroup groupCategory;	//���� ���� ��ư ��� �׷�
	@FXML private Button btnSchedule;	//���� ���� ���� ��ư
	
	//������ ����
	@FXML private ComboBox<String> comboBoxSideFilter;		//������ ���� �μ����� �޺��ڽ�
	@FXML private TableView<User> tblViewSideUserList;	//������ ���� ����� ��� ���̺��
	@FXML private TextField txtFieldSideFilter;	//������ ���� �̸� �˻� �ʵ�
	
	//��� ����
	@FXML private AnchorPane anchorPaneNotice, anchorPaneUser, anchorPaneChat, anchorPaneBoard;
	
	//�˸�
	
	
	//���������
	@FXML private AnchorPane anchorPaneStackedPane;	//�� ���� ī�� ���� Ŭ�� �� ���
	@FXML private Button btnUserRefresh;	//����� �� ��� ���ΰ�ħ ��ư
	@FXML private ImageView imgViewUserImg;
	@FXML private TabPane tabPaneUser;	//����� �� ��� ����
	@FXML private TextField txtFieldUserFilter;	//����� �� �̸� �˻� �ʵ�
	@FXML private Label lblMyDept, lblMyName, lblMyPosition, lblMyStatusMsg;
	
	//ä��
	
	
	//�Խ���
	@FXML private Button btnWrite, btnBoardRefresh;	//�Խù� �� �۾���, ���ΰ�ħ ��ư
	@FXML private TableView<BoardTableView> tblViewBoardList;	//�Խ��� �� ���̺��
	@FXML private ComboBox<String> comboBoxBoardFilter;

	BackgroundFill selectedFill = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
	Background selectedBack = new Background(selectedFill);

	BackgroundFill notSelectedFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
	Background notSelectedBack = new Background(notSelectedFill);

	BoardDao boardDao = new BoardDao();
	LoginDao loginDao = new LoginDao();
	UserInfoDao userInfoDao = new UserInfoDao();
	DeptDao deptDao = new DeptDao();
	
	User myProfile;	//���� �������� ������ ������ �������� ����
	public static String USER_NO;	//���� �������� ����� ��ȣ�� �������� ����
	
	
	//������ �������� ����ϴ� ����Ʈ
	ObservableList<User> sideTblViewUserList = FXCollections.observableArrayList();	//������ ���� ���̺�� ����� ��� ����Ʈ
	ObservableList<String> sideComboBoxList = FXCollections.observableArrayList();	//������ ���� �޺��ڽ� ���Ϳ� ����Ʈ
	
	//����� �ǿ��� ����ϴ� ����Ʈ
	ObservableList<User> userTblViewUserList = FXCollections.observableArrayList();	//����� �� ���̺� ���� ����� ����Ʈ
	ObservableList<String> userTabDeptList = FXCollections.observableArrayList();		//����� �� ������ �Ǹ�� ���� �μ� ����Ʈ
	
	
	//�Խ��ǿ��� ����ϴ� ����Ʈ
	ObservableList<BoardTableView> boardTblViewBoardList = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		USER_NO = loadUserNo();
		myProfile = userInfoDao.selectMyInfo(USER_NO);
		
		// ���� ���� ��ư �ʱ�ȭ
		selected(toggleBtnNotice);
		notSelected(toggleBtnUser);
		notSelected(toggleBtnChat);
		notSelected(toggleBtnBoard);
		anchorPaneNotice.setVisible(true);
		anchorPaneUser.setVisible(false);
		anchorPaneChat.setVisible(false);
		anchorPaneBoard.setVisible(false);
		
		//���� ���� ��� ��ư �׷�ȭ
		groupCategory.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				click(newValue.getUserData().toString());
			}
		});
		
		//������ ����
		//������ ���� ���̺� �κ�
		createSideTable(tblViewSideUserList);	//������ ���� ���̺� ����
		tblViewSideUserList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);	//���� ��ũ�ѹ� ���ַ��� ��
		tblViewSideUserList.setOnMouseClicked(event -> {
			if(event.getClickCount() >= 2) {
				String userNo = tblViewSideUserList.getSelectionModel().getSelectedItem().getUserNo();
				ChkDialogMain.businessCardDialog(userNo);
			}
		});
		
		//������ ���� �޺��ڽ� ����� �κ�
		sideComboBoxList.add("��ü");
		deptDao.loadAllDept(sideComboBoxList);
		comboBoxSideFilter.setItems(sideComboBoxList);
		comboBoxSideFilter.getSelectionModel().selectFirst();
		//�޺��ڽ� ���͸� �������� �������� ���
		comboBoxSideFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> sideFilterAction());
		//�ؽ�Ʈ�ʵ� ���͸� �������� �������� ���
		txtFieldSideFilter.setOnKeyPressed(event -> {
			KeyCode keyCode = event.getCode();
			//Ű���忡�� ���͸� ������ �� �����Ѵٴ� ��
			if(keyCode.equals(KeyCode.ENTER)) {
				sideFilterAction();
			}
		});
		
	
		
		// �˸� ��

		
		//����� ���� ��
		//������ ���� �κ�
		lblMyName.setText(myProfile.getUserName());
		lblMyDept.setText(myProfile.getUserDept());
		lblMyPosition.setText(myProfile.getUserPosition());
		lblMyStatusMsg.setText(myProfile.getUserStatusMsg());
		//������ �̹��� ���� �������� ��
		if(!myProfile.getUserImgPath().isEmpty() && !(myProfile.getUserImgPath() == null)) {
			imgViewUserImg.setImage(new Image(myProfile.getUserImgPath()));
		}
		//������ ����Ŭ�� ���� �� ����
		anchorPaneStackedPane.setOnMouseClicked(event -> {
			if(event.getClickCount() >= 2) {
				ChkDialogMain.businessCardDialog(USER_NO);
			}
		});
		
		//������� �ǿ��� ����
		userTabDeptList.add("��ü");	//�μ����� ��ü��� ���� ���� ������ ��ü ����ڸ� ����ϱ� ����
											//����Ʈ�� �� ó���� ��ü��� �׸��� ����
		createTabPane(tabPaneUser);
		//�� ���ÿ� ���� ����
		tabPaneUser.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> centerFilterAction());
		
		//������� ���ΰ�ħ ��ư
		btnUserRefresh.setOnAction(event -> {
			comboBoxSideFilter.getSelectionModel().selectFirst();
			txtFieldSideFilter.clear();
			userInfoDao.loadAllUser(sideTblViewUserList, USER_NO);
			createSideTable(tblViewSideUserList);
		});
		//�� �������� ���¸޽��� �ۼ��� �� �ֵ��� �߰��� ��
		
		
		
		// ä�ù� �� �κ�

		
		
		// �Խ��� �� �κ�
		// ���̺�信�� ��ũ�ѹ� ���ִ� �ڵ�
		createBoardTable(tblViewBoardList);
		btnWrite.setOnAction(event -> writeBoard());
		btnBoardRefresh.setOnAction(event -> {
			boardDao.loadAllBoardList(boardTblViewBoardList);
			tblViewBoardList.getSelectionModel().selectFirst();
		});
		
		//�Խ��� �� �޺��ڽ� ����� �κ�
		comboBoxBoardFilter.setItems(userTabDeptList);
		comboBoxBoardFilter.getSelectionModel().selectFirst();		//�⺻������ �޺��ڽ��� ù ��° ��Ҹ� ����
		comboBoxBoardFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue)->{
			if(newValue.equals("��ü")) {	//�޺��ڽ����� ��ü�� �����ϸ� ��� �Խ����� �����ߴٴ� ��
				boardDao.loadAllBoardList(boardTblViewBoardList);	//���͸��� ������ ��� �Խù� ���
			}
			else {	//�޺��ڽ����� ���𰡸� �����ߴٸ�
				boardDao.loadFilteredBoardList(boardTblViewBoardList, newValue);	//���͸��� ����� ���
			}
		});
		
		
		// ���� �� �κ�
		btnSchedule.setOnAction(event -> handleBtnScheduleAction());
	}
	
	//������ ���� ���̺� ���� �޼���
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
					private VBox vbox;
					private HBox hbox;
					private Label dept, name, position;
					{
						vbox = new VBox();
						hbox = new HBox();
						dept = new Label();
						name = new Label();
						position = new Label();
						hbox.getChildren().addAll(position, name);
						vbox.getChildren().addAll(hbox, dept);
						hbox.setStyle("-fx-pref-width:95; -fx-pref-height:25;");
						vbox.setStyle("-fx-pref-width:95; -fx-pref-height:50;");
						dept.setStyle("-fx-pref-width:95; -fx-font-size:13; -fx-alignment:center_right");
						name.setStyle("-fx-pref-width:60; -fx-font-size:14; -fx-font-weight:bold; -fx-alignment:center_left;");
						position.setStyle("-fx-pref-width:30; -fx-font-size:12; -fx-alignment:center_left");
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
							setGraphic(vbox);
						}
					}
				};
			}
		});
		
		userInfoDao.loadAllUser(sideTblViewUserList, USER_NO);
		sideTable.setItems(sideTblViewUserList);
		sideTable.getColumns().add(userInfoCol);
	}
	
	//������ ���� ���͸�
	//�޺��ڽ��� ����, �ʵ带 �Է�.
	public void sideFilterAction() {
		if(txtFieldSideFilter.getText().isEmpty() || txtFieldSideFilter.getText().equals("")) {	//�˻��ʵ尡 ����ִٸ�
			if(comboBoxSideFilter.getSelectionModel().getSelectedItem().equals("��ü")) {		//�޺��ڽ��� ��ü�� �����ϸ� ��� ���� �ҷ���
				userInfoDao.loadAllUser(sideTblViewUserList, USER_NO);
			}
			else {	//�ʵ尡 ����ְ� �޺��ڽ��� �����ϸ� �޺��ڽ��� �������� ��� �ҷ���
				userInfoDao.loadFilteredAllUser(sideTblViewUserList, USER_NO, comboBoxSideFilter.getSelectionModel().getSelectedItem());
			}
		}
		else {	//�ʵ忡 ���𰡰� �����ִٸ�
			if(comboBoxSideFilter.getSelectionModel().getSelectedItem().equals("��ü")) {	//�ʵ忡 �����ְ� ��ü�� �����ϸ� �ʵ带 �������� ��� �ҷ���
				userInfoDao.loadFilteredAllUser(USER_NO, sideTblViewUserList, txtFieldSideFilter.getText());
			}
			else {	//�ʵ忡 �����ְ� �޺��ڽ��� �����ϸ� �� �� ������ ��� �ҷ���
				userInfoDao.loadFilteredAllUser(sideTblViewUserList, USER_NO, comboBoxSideFilter.getSelectionModel().getSelectedItem(), txtFieldSideFilter.getText());
			}
		}
	}
	
	public void createTabPane(TabPane tabPane) {
		int rowCnt = deptDao.loadAllDept(userTabDeptList);
		for(int i = 0; i < rowCnt; i++) {
			Tab tab = new Tab();
			tab.setText(userTabDeptList.get(i));
			tabPaneUser.getTabs().add(tab);
		}
		//�� �κ��� ��ü �ǿ� ����� ������ �ֱ� ���� �۾�
		tabPaneUser.getSelectionModel().getSelectedItem().setContent(createUserInfoTable(userTabDeptList.get(0), ""));
	}
	
	//����� �� ���� �ǿ� �� ������ ���̺� ����
	@SuppressWarnings("unchecked")
	public TableView<User> createUserInfoTable(String tabName, String filterTxt) {
		TableView<User> userTable = new TableView<User>();
		//�� �̸��� ���� �ٸ� ���̺��� �����ϱ� ���� �ۼ�
		if(filterTxt.equals("")) {	//�ؽ�Ʈ �ʵ忡 �ƹ��͵� �Է����� �ʰ�
			if(tabName.equals("��ü")) {	//�޺��ڽ��� ��ü��� ���õǾ� �ִٸ� �ڽ��� ������ ��� ����� ���
				userInfoDao.loadAllUser(userTblViewUserList, USER_NO);
			}
			else {	//���� ���õ� ���¶�� �ش� �ǿ� �´� ���̺� ����
				userInfoDao.loadFilteredAllUser(userTblViewUserList, USER_NO, tabName);
			}
		}
		else {	//�ؽ�Ʈ �ʵ忡 ���𰡰� �Էµ� ���¶��
			if(tabName.equals("��ü")) {	//�ʵ忡 �Էµ� ���� �������� ��� ����ڸ� ���
				userInfoDao.loadAllUser(userTblViewUserList, USER_NO, filterTxt);
			}
			else {	//�ʵ�� �� ��� �����ϴ� ���̺� ����
				userInfoDao.loadFilteredAllUser(userTblViewUserList, USER_NO, tabName, filterTxt);
			}
		}
		//���̺� ��� ���ִ� �κ�
		userTable.widthProperty().addListener((obs, oldValue, newValue) -> {
			Pane header = (Pane)userTable.lookup("TableHeaderRow");
			if(header.isVisible()) {
				header.setPrefHeight(0);
				header.setVisible(false);
			}
		});
		
		//����� �̹����� �� ù��° ��
		TableColumn<User, User> userImgCol = new TableColumn<User, User>();
		userImgCol.setStyle("-fx-pref-width:80; -fx-border-width:1; -fx-pref-height:80; -fx-alignment:center");
		userImgCol.setCellValueFactory(new Callback<CellDataFeatures<User,User>, ObservableValue<User>>() {
			@Override
			public ObservableValue<User> call(CellDataFeatures<User, User> param) {
				return new ReadOnlyObjectWrapper<User>(param.getValue());
			}
		});
		userImgCol.setCellFactory(new Callback<TableColumn<User,User>, TableCell<User,User>>() {
			@Override
			public TableCell<User, User> call(TableColumn<User, User> param) {
				return new TableCell<User, User>() {
					private Pane box;
					private ImageView imgView;
					{
						box = new Pane();
						imgView = new ImageView();
						imgView.setFitHeight(75.0);
						box.getChildren().add(imgView);
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					}
					@Override
					protected void updateItem(User item, boolean empty) {
						super.updateItem(item, empty);
						if(item == null) {
							setGraphic(null);
						}
						else {
							//���߿� DB���� ������ �ش� ������� �̹����� ��ü�� �� �ֵ���
							imgView.setImage(new Image("File://D:/Java �۾�/Private_Project/UserInfoTest/src/UserInfo/DefaultImage.jpg"));
							setGraphic(box);
						}
					}
				};
			}
		});
		
		
		//������� �̸�, �Ҽ� ���� ��µǴ� ��
		TableColumn<User, User> userInfoCol = new TableColumn<User, User>();
		userInfoCol.setStyle("-fx-pref-width:220; -fx-border-width:1; -fx-pref-height:80; -fx-alignment:center_left");
		userInfoCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User,User>, ObservableValue<User>>() {
			@Override
			public ObservableValue<User> call(CellDataFeatures<User, User> param) {
				return new ReadOnlyObjectWrapper<User>(param.getValue());
			}
		});
		userInfoCol.setCellFactory(new Callback<TableColumn<User,User>, TableCell<User,User>>() {
			@Override
			public TableCell<User, User> call(TableColumn<User, User> param) {
				return new TableCell<User, User>() {
					private VBox vbox;
					private HBox hbox;
					private Label dept, name, position, msg;
					{
						vbox = new VBox();
						hbox = new HBox();
						dept = new Label();
						name = new Label();
						position = new Label();
						msg = new Label();
						hbox.getChildren().addAll(dept, name, position);
						vbox.getChildren().addAll(hbox, msg);
						hbox.setStyle("-fx-pref-height:30");
						dept.setStyle("-fx-pref-width:70; -fx-pref-height:30; -fx-font-size:15px; -fx-alignment:center_left;");
						name.setStyle("-fx-pref-width:80; -fx-pref-height:30; -fx-font-size:20px; -fx-alignment:center_left; -fx-font-weight:bold;");
						position.setStyle("-fx-pref-width:50; -fx-pref-height:30; -fx-font-size:14px; -fx-alignment:center_left;");
						msg.setStyle("-fx-pref-width:200; -fx-pref-height:30; -fx-font-size:12px; -fx-alignment:center_left; -fx-padding:5");
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
							msg.setText(item.getUserStatusMsg());
							setGraphic(vbox);
						}
					}
				};
			}
		});
		//���̺���� ���� ��ũ�� ���ִ� �κ�
		userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//����Ŭ�� ���� ����
		userTable.setOnMouseClicked(event -> {
			if(event.getClickCount() >= 2) {
				ChkDialogMain.businessCardDialog(userTable.getSelectionModel().getSelectedItem().getUserNo());
			}
		});
		userTable.getColumns().addAll(userImgCol, userInfoCol);
		userTable.setItems(userTblViewUserList);
		return userTable;
	}

	//����� �ǿ��� ����� ���� ����
	//���� ������ ������ �޶����� �ϱ� ������
	public void centerFilterAction() {
		//���͸� ���� ����
		//�ʵ忡 ���� �ִ��� ���� �Ǵ�
		//���õ� ���� Ȯ��
		//�ʵ忡 ������ �� ���� ���� �Ǵ� �ʵ忡 ���� �ִ� ���¿��� ���� ����
		Tab selectedTab = tabPaneUser.getSelectionModel().getSelectedItem();
		String inputField = txtFieldUserFilter.getText();
		TableView<User> table = new TableView<User>();
		if(inputField.equals("") || inputField.isEmpty()) {	//�ʵ忡 ���� ���� ���¶��
			//�׷��� �ǿ� ���缭 ����� ���
			table = createUserInfoTable(selectedTab.getText(), "");
		}
		else {	//�ʵ忡 ��� ���� �ִٸ�
			//�ʵ尪�� ���� �̸��� ���� ���ؼ� ���
			table = createUserInfoTable(selectedTab.getText(), inputField);
		}
		selectedTab.setContent(table);
	}

	// �Խ��� ���� ���̺� �����ϴ� �κ�
	@SuppressWarnings("unchecked")
	public void createBoardTable(TableView<BoardTableView> boardTable) {
		// ���̺���� ��� ������ ���ִ� �ڵ�
		boardTable.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Pane header = (Pane) boardTable.lookup("TableHeaderRow");
				if (header.isVisible()) {
					header.setPrefHeight(0);
					header.setVisible(false);
				}
			}
		});
		
		TableColumn<BoardTableView, String> headerCol = new TableColumn<BoardTableView, String>();
		headerCol.setStyle("-fx-pref-width:40; -fx-border-width:1; -fx-pref-height:40; -fx-font-size:10px; -fx-alignment:center");
		headerCol.setCellValueFactory(new PropertyValueFactory<BoardTableView, String>("boardHeader"));
		TableColumn<BoardTableView, String> titleCol = new TableColumn<BoardTableView, String>();
		titleCol.setStyle("-fx-pref-width:180; -fx-border-width:1; -fx-pref-height:40; -fx-font-size:15px; -fx-alignment:center-left");
		titleCol.setCellValueFactory(new PropertyValueFactory<BoardTableView, String>("boardTitle"));
		TableColumn<BoardTableView, BoardTableView> writerDateCol = new TableColumn<BoardTableView, BoardTableView>();
		writerDateCol.setStyle("-fx-pref-width:85; -fx-border-width:1; -fx-pref-height:40; -fx-alignment:center-left");
		//3��° ���� ����� �������·� ����� ���ؼ� ���� �۾�
		writerDateCol.setCellValueFactory(new Callback<CellDataFeatures<BoardTableView, BoardTableView>, ObservableValue<BoardTableView>>() {
			@Override
			public ObservableValue<BoardTableView> call(
				CellDataFeatures<BoardTableView, BoardTableView> features) {
				return new ReadOnlyObjectWrapper<BoardTableView>(features.getValue());
			}
		});
		writerDateCol.setCellFactory(	new Callback<TableColumn<BoardTableView, BoardTableView>, TableCell<BoardTableView, BoardTableView>>() {
			@Override
			public TableCell<BoardTableView, BoardTableView> call(
					TableColumn<BoardTableView, BoardTableView> param) {
				return new TableCell<BoardTableView, BoardTableView>() {
					private VBox box;
					private Label writer;
					private Label date;
					{
						box = new VBox();
						writer = new Label();
						date = new Label();
						writer.setStyle("-fx-alignment:center_left; -fx-pref-height:20");
						date.setStyle("-fx-font-size:10px; -fx-alignment:center_left; -fx-pref-height:20");
						box.getChildren().addAll(writer, date);
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					}
					@Override
					protected void updateItem(BoardTableView item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null) {
							setGraphic(null);
						}
						else {
							writer.setText(item.getBoardWriter());
							date.setText(item.getBoardDate().substring(0, 10));
							setGraphic(box);
						}
					}
				};
			}
		});
		
		//���� ��ũ�� ����
		boardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);		
		// ���̺�信�� ���콺 ����Ŭ�� ������ ����
		// ���̺�信�� �ش� �Խù��� ����Ŭ���ϸ� �ش� �Խù��� ������ ������ �� ����.
		boardTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() >= 2) {
					// ����Ŭ���� �ߴٸ� �Խù� ���� â�� ����
					readContent(boardTable.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		boardDao.loadAllBoardList(boardTblViewBoardList);
		boardTable.getColumns().addAll(headerCol, titleCol, writerDateCol);
		boardTable.setItems(boardTblViewBoardList);	
	}

	
	
	public String loadUserNo() {
//		String path = System.getProperty("user.home") + "/Documents/MySNS/id.txt;		//��10���� �� ������ �ִ� ���� ã���� �� �� �ִ� ���
//		String path = "c:/MySNS/id.txt";
		String path = "e:/MySNS/id.txt";
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
			stage.setResizable(false);
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

			anchorPaneNotice.setVisible(true);
			anchorPaneUser.setVisible(false);
			anchorPaneChat.setVisible(false);
			anchorPaneBoard.setVisible(false);
		}
		//����� �� ��ư�� ������ ��
		else if (btnName.equals("user")) {
			notSelected(toggleBtnChat);
			selected(toggleBtnUser);
			notSelected(toggleBtnNotice);
			notSelected(toggleBtnBoard);

			anchorPaneNotice.setVisible(false);
			anchorPaneUser.setVisible(true);
			anchorPaneChat.setVisible(false);
			anchorPaneBoard.setVisible(false);
		}
		//ä�� ���� ������ ��
		else if (btnName.equals("chat")) {
			selected(toggleBtnChat);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnNotice);
			notSelected(toggleBtnBoard);

			anchorPaneNotice.setVisible(false);
			anchorPaneUser.setVisible(false);
			anchorPaneChat.setVisible(true);
			anchorPaneBoard.setVisible(false);
		}
		//�Խ��� ���� ������ ��
		else if (btnName.equals("board")) {
			selected(toggleBtnBoard);
			notSelected(toggleBtnUser);
			notSelected(toggleBtnChat);
			notSelected(toggleBtnNotice);

			anchorPaneNotice.setVisible(false);
			anchorPaneUser.setVisible(false);
			anchorPaneChat.setVisible(false);
			anchorPaneBoard.setVisible(true);
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
	public void handleBtnScheduleAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent readBoardWindow = FXMLLoader.load(getClass().getResource("/ScheduleModule/schedule.fxml"));
			Scene scene = new Scene(readBoardWindow);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
