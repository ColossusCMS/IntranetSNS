package LoginModule;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import ClassPackage.User;
import CreateDialogModule.ChkDialogMain;
import Dao.DeptDao;
import Dao.LoginDao;
import InitializePackage.DataProperties;
import SFTPUploadDownloadModule.SFTPModule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : LoginModule
��Ű�� ���� : 1.2.0
Ŭ���� �̸� : SignUpController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ��� ���
- singUp.fxml�� �� ��Ʈ�ѷ��� ����� ��� â�� �����ϰ� �Է¹��� ������ ���¸� üũ,
- �����ͺ��̽��� ������ �Է��� ������ �����ͺ��̽��� ������.

��Ű�� ���� ���� ����
1.1.0
- DAO �ν��Ͻ��� �ʿ�ÿ��� ������ ������ �̵� ���� �ε� �ð��� ����.
- ����� ���â���� �̸��� �ߺ�üũ ��ư �߰� �� �̸��� �ߺ�üũ �׼� �߰�
- LoginDao Ŭ������ �̸��� üũ�ϴ� �޼��� �߰�
- ���� �� �޼��� �̸� ����ȭ

1.1.1
- Dao �ν��Ͻ� ���� (������ ���̽� �ʱ�ȭ Ŭ���� ����)
- �޺��ڽ��� ������ �����ͺ��̽��� ����

1.1.2
- ��ȭ��ȣ �ߺ�üũ ��ư, ��� �߰�
- �ߺ��α��� ���� ��� �߰�

1.2.0
- ����� �����ʿ� �̹��� ���ε� ��� �߰�, SFTP������ �̿��� ����
 */
public class SignUpController implements Initializable {
	@FXML
	private TextField txtFieldUserNo, txtFieldUserName, txtFieldUserTel, txtFieldUserMail, txtFieldImgPath;
	@FXML
	private Button btnUserNoChk, btnImg, btnSubmit, btnCancel, btnUserMailChk, btnUserTelChk;
	@FXML
	private PasswordField pwFieldPassword, pwFieldPasswordChk;
	@FXML
	private Label lblPwChk;
	@FXML
	private ImageView imageViewImg;
	@FXML
	private ComboBox<String> comboBoxDept;

	private ObservableList<String> comboList = FXCollections.observableArrayList(); // �޺��ڽ��� ������ �μ� ���

	LoginDao loginDao = new LoginDao(); // �α��ν� ����ϴ� Dao
	DeptDao deptDao = new DeptDao(); // �μ����� ������ �� ����ϴ� Dao

	int overlapChkNum = 0; // �ߺ�üũ ���� Ȯ���� ���� ����
	Pattern telPattern = Pattern.compile("(010)-\\d{4}-\\d{4}"); // �޴��� ��ȣ ����
	Pattern mailPattern = Pattern
			.compile("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}"); // �̸��� ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		deptDao.loadAllDept(comboList);
		comboBoxDept.setItems(comboList);
		btnUserNoChk.setOnAction(event -> handleBtnUserNoChkAction());
		btnUserMailChk.setOnAction(event -> handleBtnUserMailChkAction());
		btnUserTelChk.setOnAction(event -> handleBtnUserTelChkAction());
		btnImg.setOnAction(event -> handleBtnImgAction());
		btnSubmit.setOnAction(event -> handleBtnSubmitAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());

		txtFieldImgPath.setEditable(false);

		// ���� �ߺ�üũ�� �ϰ��� �����ȣ�� ���� �Է��ϸ� �ٽ� �ߺ�üũ ��ư�� ���󺹱����Ѿ� �ؾ��ϴ�
		// �Ӽ� ���ø� �ؼ� ���� �ٲ�ٸ� ��ư�� ������Ŵ
		txtFieldUserNo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!oldValue.equals(newValue)) {
					btnUserNoChk.setText("�ߺ�üũ");
					btnUserNoChk.setDisable(false);
					overlapChkNum = 0;
				}
			}
		});

		// ��й�ȣȮ�� �ʵ��� �Ӽ��� �׻� üũ�ϸ鼭 �ռ� �Է��� ��й�ȣ�� �������� ����ؼ� üũ
		// ���� �������ٸ� ��ġ�Ѵٰ� ����ϰ� �׷��� ������ ����ġ�� ��� ���
		pwFieldPasswordChk.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals(pwFieldPassword.getText())) {
					lblPwChk.setText("��ġ");
					lblPwChk.setTextFill(Color.GREEN);
				} else {
					lblPwChk.setText("����ġ");
					lblPwChk.setTextFill(Color.RED);
				}
			}
		});
	}

	// �����ȣ �ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void handleBtnUserNoChkAction() {
		if (txtFieldUserNo.getText().equals("")) { // ���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�����ȣ�� �Է��ϼ���.");
		} else { // �װ� �ƴ϶�� ���������� ����
			boolean overlap = loginDao.chkUserNo(txtFieldUserNo.getText()); // DB���� �ߺ��Ǵ� ��ȣ�� �ִ��� üũ��
			if (overlap) { // ���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� �����ȣ�Դϴ�.");
				txtFieldUserNo.clear();
				overlapChkNum = 0;
			} else { // false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				btnUserNoChk.setText("��밡��");
//				overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				btnUserNoChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}

	// �̸��� �ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void handleBtnUserMailChkAction() {
		if (txtFieldUserMail.getText().equals("")) { // ���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�̸����� �Է��ϼ���.");
		} else if (!mailPattern.matcher(txtFieldUserMail.getText()).matches()) { // ���� �̸��� ���Ŀ� ���� �ʴٸ� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�̸��� ���Ŀ� ���� �ʽ��ϴ�.");
		} else { // �װ� �ƴ϶�� ���������� ����
			boolean overlap = loginDao.chkUserMail(txtFieldUserMail.getText()); // DB���� �ߺ��Ǵ� �̸����� �ִ��� üũ��
			if (overlap) { // ���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� �̸����Դϴ�.");
				txtFieldUserMail.clear();
				overlapChkNum = 0;
			} else { // false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				btnUserMailChk.setText("��밡��");
//				overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				btnUserMailChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}

	// ��ȭ��ȣ �ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void handleBtnUserTelChkAction() {
		if (txtFieldUserTel.getText().equals("")) { // ���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("��ȭ��ȣ�� �Է��ϼ���.");
		} else if (!telPattern.matcher(txtFieldUserTel.getText()).matches()) { // ���� ��ȭ��ȣ ���Ŀ� ���� �ʴٸ� ���̾�α׸� ���
			ChkDialogMain.chkDialog("��ȭ��ȣ ���Ŀ� ���� �ʽ��ϴ�.");
		} else { // �װ� �ƴ϶�� ���������� ����
			boolean overlap = loginDao.chkUserTel(txtFieldUserTel.getText()); // DB���� �ߺ��Ǵ� ��ȭ��ȣ�� �ִ��� üũ��
			if (overlap) { // ���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� ��ȭ��ȣ�Դϴ�.");
				txtFieldUserTel.clear();
				overlapChkNum = 0;
			} else { // false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				btnUserTelChk.setText("��밡��");
//					overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				btnUserTelChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}

	// ã�� ��ư�� ������ �̹����� ã������ ���� ���̾�αװ� �����
	// ������ �����ϸ� �ش� ���丮���� ���ϸ���� imgPath�� �ڵ����� �Է��� ��
	public void handleBtnImgAction() {
		// ���� ���� ���̾�α׸� �ҷ���
		FileChooser imgChooser = new FileChooser();
		// ���� ������ Ȯ���� ����(��ǥ �̹��� Ȯ����)
		imgChooser.getExtensionFilters()
				.addAll(new ExtensionFilter("��� �̹��� ����(*.jpg, *.png, *.gif)", "*.jpg", "*.png", "*.gif"));
		// ���� ���� ���̾�α׸� ��� ��ġ
		File selectedFile = imgChooser.showOpenDialog((Stage) btnImg.getScene().getWindow());
		try {
			// ���� �о����
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			// �̹��� ����
			Image img = new Image(bis);
			// ��� �ʵ忡 ������ �̹����� ��θ� ����ϰ�
			txtFieldImgPath.setText(selectedFile.getAbsolutePath());
			// �̹����信 ������ �̹����� ���
			imageViewImg.setImage(img);
			imageViewImg.setFitHeight(133);
			imageViewImg.setFitWidth(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ���߿� ��ư�� �ϳ� �� �߰��ؼ� ���ͳ� url�� �Է��� �̹����� �߰��� �� �ִ� ����� ����.

	// ��Ϲ�ư ���� ����
	// 1. DB�� not null��Ҹ� ��� üũ�ؼ� �Է����� ���� ���� �ִ��� üũ(�ʼ��׸�)
	// 1_1. not null��� �߿� �Է����� ���� ���� �ִٸ� ���̾�α׸� ���
	// 2_1. ��� �ԷµǾ��ٸ� �ߺ�üũ�� �ߴ��� ���ߴ��� üũ overlapChkNum == 1�ΰ�
	// 2_2. pwChkLbl�� '��ġ'��� �Ǿ� �ִ��� üũ
	// 2_3. �Ҽ��� �����ߴ��� üũ
	// 2_4. ��ȭ��ȣ ���Ŀ� �°� �Է��ߴ���
	// 2_5. �̸��� ���Ŀ� �°� �Է��ߴ��� (���� ���Խ��� Ȱ���� üũ�Ѵ�.)
	// 3. ��� �����Ѵٸ� DB�� �����͸� ����
	public void handleBtnSubmitAction() {
		// �ʼ��׸��� üũ�Ѵ�.
		// ���� �Է����� ���� ���� �ִٸ� �ٽ� �Է��϶�� ���̾�α׸� ���
		if (txtFieldUserNo.getText().isEmpty() || txtFieldUserName.getText().isEmpty()
				|| pwFieldPassword.getText().isEmpty() || txtFieldUserTel.getText().isEmpty()
				|| txtFieldUserMail.getText().isEmpty()) {
			ChkDialogMain.chkDialog("�ʼ��׸��� �Է��ϼ���.");
		}
		// �ߺ�üũ�ߴ��� ���ߴ��� üũ
		else if (overlapChkNum == 0) {
			ChkDialogMain.chkDialog("�ߺ�üũ�� �ϼ���.");
		}
		// ��й�ȣ�� ��ġ�ϴ���
		else if (lblPwChk.getText().equals("����ġ")) {
			ChkDialogMain.chkDialog("��й�ȣ�� Ȯ���ϼ���.");
		}
		// �Ҽ� �μ��� �����ߴ���
		else if (comboBoxDept.getSelectionModel().getSelectedItem() == null) {
			ChkDialogMain.chkDialog("�Ҽ��� �����ϼ���.");
		}
		// ��� �����ߴٸ� �����ͺ��̽��� �����Ѵ�.
		else {
			// �̹��� ��θ� �����ϴ� �κ�
			String imagePath = new String();
			if (!txtFieldImgPath.getText().isEmpty()) { // ������ �̹����� �����ߴٸ�
				// SFTP������ ������
				SFTPModule sftpModule = new SFTPModule(DataProperties.getIpAddress(),
						DataProperties.getPortNumber("SFTPServer"), DataProperties.getIdProfile("SFTPServer"),
						DataProperties.getPassword("SFTPServer"));
				try {
					// �ش� ������ ���ε��ϰ� �ش� ���ϸ��� ������
					imagePath = sftpModule.upload(txtFieldImgPath.getText(), "images");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // ������ ������ �������� �ʾҴٸ�
				imagePath = "projectsampledefaultimage.jpg"; // �⺻ ������ �������� ����
			}
			// �����ͺ��̽��� �����ϱ� ���� User ��ü�� ����
			User user = new User(txtFieldUserNo.getText(), txtFieldUserName.getText(), pwFieldPassword.getText(),
					txtFieldUserMail.getText(), txtFieldUserTel.getText(), imagePath,
					comboBoxDept.getSelectionModel().getSelectedItem().toString(), "", "", 0);
			loginDao.insertUserData(user); // �Է��� �����͵��� �����ͺ��̽��� ����
			ChkDialogMain.chkDialog("����� �Ϸ�Ǿ����ϴ�.");
			Stage stage = (Stage) btnSubmit.getScene().getWindow();
			try {
				Parent loginDialog = FXMLLoader.load(getClass().getResource("login.fxml"));
				Scene scene = new Scene(loginDialog);
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ��� ��ư�� ������ ����� ���â�� ����
	public void handleBtnCancelAction() {
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		try {
			Parent loginDialog = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(loginDialog);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
