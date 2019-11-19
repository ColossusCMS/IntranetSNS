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
import InitializePackage.DataProperties;
import MainModule.MainController;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : BoardModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : BoardWriteController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �Խ����ǿ��� �۾��� ��ư�� ������ �� �����ϴ� â�� ����
- �Խù��� ������ �ۼ��� �� �ְ� �ۼ��� �Ϸ��ϰ� ��Ϲ�ư�� ������ �����ͺ��̽��� ������ ������.

��Ű�� ���� ���� ����
 */
public class BoardWriteController implements Initializable {
	@FXML
	private TextField txtFieldTitle, txtFieldFilePath;
	@FXML
	private PasswordField txtFieldPassword;
	@FXML
	private ComboBox<String> comboBoxHeader;
	@FXML
	private TextArea txtAreaContent;
	@FXML
	private Button btnReg, btnCancel, btnFileAttach;

	BoardDao boardDao = new BoardDao();
	DeptDao deptDao = new DeptDao();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	// �۾����� �μ� ����Ʈ
	// �̰� ����Ʈ �ϳ��� �����ؼ� ������ ������ �� �ֵ���
	ObservableList<String> deptList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReg.setOnAction(event -> handleBtnRegAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnFileAttach.setOnAction(event -> handleBtnFileAttachAction());

		// �μ� ���̺� �ܾ�ð�
		deptList.add("��ü");
		deptDao.loadAllDept(deptList);
		comboBoxHeader.setItems(deptList);
	}

	// �۾��� ��ư ����
	public void handleBtnRegAction() {
		if (txtFieldTitle.getText().isEmpty()) { // ���� ������ �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("������ �Է��ϼ���.");
		} else if (comboBoxHeader.getSelectionModel().isEmpty()) { // �Խ����� �������� �ʾҴٸ�
			ChkDialogMain.chkDialog("�Խ����� �����ϼ���.");
		} else if (txtFieldPassword.getText().isEmpty()) { // ��й�ȣ�� �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("��й�ȣ�� �Է��ϼ���.");
		} else { // ��� �����Ѵٸ� db�� ����
			String filePath = "";
			if (!txtFieldFilePath.getText().isEmpty()) { // ���� ÷�θ� �ߴٸ�
				// SFTP������ ����
				SFTPModule sftpModule = new SFTPModule(DataProperties.getIpAddress(),
						DataProperties.getPortNumber("SFTPServer"), DataProperties.getIdProfile("SFTPServer"),
						DataProperties.getPassword("SFTPServer"));
				try {
					filePath = sftpModule.upload(txtFieldFilePath.getText(), "files"); // SFTP������ files��ο� ÷���� ������ ���ε��ϰ� ���ϸ��� ����
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Date now = new Date(); // ���� ��¥(�Խù��� �ۼ��� ��¥/�ð�)
			boolean write = boardDao
					.insertBoardContent(new Board(null, comboBoxHeader.getSelectionModel().getSelectedItem().toString(),
							txtFieldTitle.getText(), txtAreaContent.getText(), txtFieldPassword.getText(),
							MainController.USER_NO, sdf.format(now), filePath, null));
			if (write == true) {
				ChkDialogMain.chkDialog("�Խù��� ����߽��ϴ�.");
				btnReg.getScene().getWindow().hide();
			} else {
				ChkDialogMain.chkDialog("������ ���Ͽ� �Խù� ��Ͽ� �����߽��ϴ�.");
			}
		}
	}

	// ����÷�� ��ư
	public void handleBtnFileAttachAction() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("��� ����(*.*)", "*.*"));
		File selectedFile = fileChooser.showOpenDialog((Stage) btnFileAttach.getScene().getWindow());
		try {
			txtFieldFilePath.setText(selectedFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ҹ�ư
	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}