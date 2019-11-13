package BoardModule;
 
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.Board;
import CreateDialogModule.ChkDialogMain;
import Dao.BoardDao;
import Dao.DeptDao;
import InitializePackage.DataProperties;
import SFTPUploadDownloadModule.SFTPModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �Խ���
��� ���� : 1.0.0
Ŭ���� �̸� : BoardModifyController
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��� Java����
- BoardController.java (����� ��Ͽ� ����ϴ� ����� ���� Ŭ����[������� ��� ������ ��� ����])
- BoardWriteController
- BoardModifyController

�ʿ� fxml����
- board.fxml
- boardWrite.fxml
- boardModify.fxml

�ʿ� import ����� ���� package
- ClassPackage.Board;
- CreateDialogModule.ChkDialogMain;
- Dao.BoardDao;
- Dao.DeptDao;

�ش� Ŭ���� �ֿ� ���
- �Խ��� ���� ����â���� ������ư�� ������ �� ��Ÿ���� â�� ����
- �ش� �Խù��� ���� �߿��� ������ ������ �κ��� �����ָ�
- ������ �Ϸ��ϰ� ���� ��ư�� ������ ���ŵ� ������ �����ͺ��̽��� �����

��� ���� ���� ����
1.0.0
 */
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : BoardModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : BoardModifyController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �Խù� ���� â ��Ʈ�ѷ�
- �Խù� ���������� �ۼ��ߴ� ������� ������ ���̽����� ������ ������ ����ϰ�
- ����� ������ ������ ���̽��� ����

��Ű�� ���� ���� ����
 */

public class BoardModifyController implements Initializable {
	@FXML private TextField txtFieldTitle, txtFieldFilePath;
	@FXML private TextArea txtAreaContent;
	@FXML private Button btnModify, btnCancel, btnFileAttach;
	@FXML private ComboBox<String> comboBoxHeader;
	@FXML private PasswordField txtFieldPassword;
	
	Board board;
	DeptDao deptDao = new DeptDao();
	BoardDao boardDao = new BoardDao();
	//�� �κе� �����ͺ��̽����� �ܾ�� ��
	//�μ� ���̺� ���
	ObservableList<String> deptList = FXCollections.observableArrayList();	//�Ӹ���(�μ�)�� ����� ���� ����Ʈ
	String boardUserNo, boardDate, boardFile;	//������ �ʿ���� ��, DB���� ���ϴ� �������� ����
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		deptList.add("��ü");	//�޺��ڽ� ù ��° ��ҷ� ��ü�� �߰�
		deptDao.loadAllDept(deptList);	//�μ� ����Ʈ ������
		board = boardDao.loadAllBoardContent(BoardController.BBS_ID);	//�Խù� ��ȣ
		comboBoxHeader.setItems(deptList);	//�޺��ڽ� ����
		
		//��ư ����
		btnModify.setOnAction(event -> handleBtnModifyAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnFileAttach.setOnAction(event -> handleBtnFileAttachAction());
		
		//������ ������ �κ��� ��Ʈ�ѷ� ó��
		txtFieldTitle.setText(board.getBoardTitle());
		txtFieldPassword.setText(board.getBoardPassword());
		txtAreaContent.setText(board.getBoardContent());
		comboBoxHeader.getSelectionModel().select(board.getBoardHeader());
		
		//�Ʒ��� �������� ������ �� ���� �����̰� ���� ������ DB�� �ѱ�� ���ؼ� ������ ����
		boardUserNo = board.getBoardUserNo();
		boardDate = board.getBoardDate();
		boardFile = board.getBoardFile();
		//��й�ȣ�� �ٲ� �� ����
		txtFieldPassword.setEditable(false);
	}
	
	//���� ��ư ������ ��
	public void handleBtnModifyAction() {
		String filePath = "";
		if(!txtFieldFilePath.getText().isEmpty()) {	//���� ÷�ΰ� �Ǿ� �ִٸ�
			//SFTP������ ����
			SFTPModule sftpModule = new SFTPModule(DataProperties.getIpAddress(), DataProperties.getPortNumber("SFTPServer"), DataProperties.getIdProfile("SFTPServer"), DataProperties.getPassword("SFTPServer"));
			try {
				filePath = sftpModule.upload(txtFieldFilePath.getText(), "files"); //SFTP������ files��ο� �ش� ������ ���ε��ϰ� ���ϸ��� ����
			} catch (Exception e) {
				e.printStackTrace();
			}
			boardFile = filePath;	//���� ÷�ο� ���� �ִ� ���� ���ο� ������ �����
		}
		board = new Board(Integer.parseInt(BoardController.BBS_ID), comboBoxHeader.getSelectionModel().getSelectedItem().toString(), txtFieldTitle.getText(), txtAreaContent.getText(),
				txtFieldPassword.getText(), boardUserNo, boardDate, boardFile, 0);
		boolean modify = boardDao.updateBoardContent(board);	//�����ͺ��̽����� �ش� �Խù��� ������ ����
		if(modify) {	//������ �Ǿ��ٸ� �Ǿ��ٰ� �˸�â ���� ���� â �ݱ�
			ChkDialogMain.chkDialog("������ �Ϸ�Ǿ����ϴ�.");
			btnModify.getScene().getWindow().hide();
		}
		else {	//��� ������ �߻��ߴٸ� ���� �߻��ߴٰ� �˸�â ���� ���� ���� ����
			ChkDialogMain.chkDialog("������ �߻��߽��ϴ�.");
		}
	}
	
	//���� ÷�� ��ư
	public void handleBtnFileAttachAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("��� ����(*.*)", "*.*"));
		File selectedFile = fileChooser.showOpenDialog((Stage)btnFileAttach.getScene().getWindow());
		try {
			txtFieldFilePath.setText(selectedFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��� ��ư
	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}