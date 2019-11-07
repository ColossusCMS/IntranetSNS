package BoardModule;
 
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import ClassPackage.Board;
import CreateDialogModule.ChkDialogMain;
import Dao.BoardDao;
import Dao.DeptDao;
import FTPUploadDownloadModule.FTPUploader;
import MainModule.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �Խ���
��� ���� : 1.0.0
Ŭ���� �̸� : BoardWriterController
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
- MainModule.MainController;

�ش� Ŭ���� �ֿ� ���
- �Խ����ǿ��� �۾��� ��ư�� ������ �� �����ϴ� â�� ����
- �Խù��� ������ �ۼ��� �� �ְ� �ۼ��� �Ϸ��ϰ� ��Ϲ�ư�� ������ �����ͺ��̽��� ������ ������.

��� ���� ���� ����
1.0.0
 */
public class BoardWriteController implements Initializable {
	@FXML private TextField txtFieldTitle, txtFieldFilePath;
	@FXML private PasswordField txtFieldPassword;
	@FXML private ComboBox<String> comboBoxHeader;
	@FXML private TextArea txtAreaContent;
	@FXML private Button btnReg, btnCancel, btnFileAttach;
	
	BoardDao boardDao = new BoardDao();
	DeptDao deptDao = new DeptDao();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	//�۾����� �μ� ����Ʈ
	//�̰� ����Ʈ �ϳ��� �����ؼ� ������ ������ �� �ֵ���
	ObservableList<String> deptList = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReg.setOnAction(event -> handleBtnRegAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnFileAttach.setOnAction(event -> handleBtnFileAttachAction());
		
		//�μ� ���̺� �ܾ�ð�
		deptDao.loadAllDept(deptList);
		comboBoxHeader.setItems(deptList);
	}
	
	public void handleBtnRegAction() {
		if(txtFieldTitle.getText().isEmpty()) {	//���� ������ �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("������ �Է��ϼ���.");
		}
		else if(comboBoxHeader.getSelectionModel().isEmpty()) {	//�Խ����� �������� �ʾҴٸ�
			ChkDialogMain.chkDialog("�Խ����� �����ϼ���.");
		}
		else if(txtFieldPassword.getText().isEmpty()) {	//��й�ȣ�� �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("��й�ȣ�� �Է��ϼ���.");
		}
		else {	//��� �����Ѵٸ� db�� ����
			File attachedFile = new File(txtFieldFilePath.getText());
			String filePath = FTPUploader.uploadFile("file", attachedFile);
			Date now = new Date();
			boolean write = boardDao.insertBoardContent(new Board(null, comboBoxHeader.getSelectionModel().getSelectedItem().toString(), txtFieldTitle.getText(), txtAreaContent.getText(), txtFieldPassword.getText(), MainController.USER_NO, sdf.format(now), filePath, null));
			if(write == true) {
				ChkDialogMain.chkDialog("�Խù��� ����߽��ϴ�.");
				btnReg.getScene().getWindow().hide();
			}
			else {
				ChkDialogMain.chkDialog("������ ���Ͽ� �Խù� ��Ͽ� �����߽��ϴ�.");
			}
		}
	}
	
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
	
	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}