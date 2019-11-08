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
import FTPUploadDownloadModule.FTPUploader;
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
���α׷� ���� : 0.7.0
��� �̸� : �α���
��� ���� : 1.1.2
Ŭ���� �̸� : SignUpController
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��� Java����
- LoginMain.java (�α��� ȭ���� ����Ǵ� ���� Ŭ����)
- LoginController.java (�α��� â ��Ʈ�ѷ�)
- SignUpController.java (����� ��� â ��Ʈ�ѷ�)
- FindAccountController.java (���� ã�� â ��Ʈ�ѷ�)
- User.java (����� ��Ͽ� ����ϴ� ����� ���� Ŭ����[������� ��� ������ ��� ����])
- UserData.java (���� ã�⿡�� ����ϴ� ����� ���� Ŭ����[����ڹ�ȣ, �̸�, �̸���, ��й�ȣ])

�ʿ� fxml����
- login.fxml (�α��� â fxml)
- signUp.fxml (����ڵ�� â fxml)
- findAccount.fxml (���� ã�� â fxml)

�ʿ� import ����� ���� package
- Dao.LoginDao (�α��� ������ ������ ���̽��� ó���� �� �ִ� �޼���)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ��� ���
- singUp.fxml�� �� ��Ʈ�ѷ��� ����� ��� â�� �����ϰ� �Է¹��� ������ ���¸� üũ,
- �����ͺ��̽��� ������ �Է��� ������ �����ͺ��̽��� ������.

��� ���� ���� ����
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
 */
public class SignUpController implements Initializable {
	@FXML private TextField fieldUserNo, fieldUserName, fieldUserTel, fieldUserMail, fieldImgPath;
	@FXML private Button btnUserNoChk, btnImg, btnSubmit, btnCancel, btnUserMailChk, btnUserTelChk;
	@FXML private PasswordField fieldPassword, fieldPasswordChk;
	@FXML private Label lblPwChk;
	@FXML private ImageView viewImg;
	@FXML private ComboBox<String> comboBoxDept;
	
	private ObservableList<String> comboList = FXCollections.observableArrayList();
	
	LoginDao loginDao = new LoginDao();	//�α��ν� ����ϴ� Dao
	DeptDao deptDao = new DeptDao();	//�μ����� ������ �� ����ϴ� Dao
	
	int overlapChkNum = 0;	//�ߺ�üũ ���� Ȯ���� ���� ����
	Pattern telPattern = Pattern.compile("(010)-\\d{4}-\\d{4}");	//�޴��� ��ȣ ����
	Pattern mailPattern = Pattern.compile("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}");	//�̸��� ����
	
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
		
		fieldImgPath.setEditable(false);
		
		//���� �ߺ�üũ�� �ϰ��� �����ȣ�� ���� �Է��ϸ� �ٽ� �ߺ�üũ ��ư�� ���󺹱����Ѿ� �ؾ��ϴ�
		//�Ӽ� ���ø� �ؼ� ���� �ٲ�ٸ� ��ư�� ������Ŵ
		fieldUserNo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!oldValue.equals(newValue)) {
					btnUserNoChk.setText("�ߺ�üũ");
					btnUserNoChk.setDisable(false);
					overlapChkNum = 0;
				}
			}
		});
		
		//��й�ȣȮ�� �ʵ��� �Ӽ��� �׻� üũ�ϸ鼭 �ռ� �Է��� ��й�ȣ�� �������� ����ؼ� üũ
		//���� �������ٸ� ��ġ�Ѵٰ� ����ϰ� �׷��� ������ ����ġ�� ��� ���
		fieldPasswordChk.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals(fieldPassword.getText())) {
					lblPwChk.setText("��ġ");
					lblPwChk.setTextFill(Color.GREEN);
				}
				else {
					lblPwChk.setText("����ġ");
					lblPwChk.setTextFill(Color.RED);
				}
			}
		});
	}
	
	//�����ȣ �ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void handleBtnUserNoChkAction() {
		if(fieldUserNo.getText().equals("")) {		//���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�����ȣ�� �Է��ϼ���.");
		}
		else {	//�װ� �ƴ϶�� ���������� ����
			boolean overlap = loginDao.chkUserNo(fieldUserNo.getText());	//DB���� �ߺ��Ǵ� ��ȣ�� �ִ��� üũ��
			if(overlap) {		//���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� �����ȣ�Դϴ�.");
				fieldUserNo.clear();
				overlapChkNum = 0;
			}
			else {					//false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				btnUserNoChk.setText("��밡��");
//				overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				btnUserNoChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}
	
	//�̸��� �ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void handleBtnUserMailChkAction() {
		if(fieldUserMail.getText().equals("")) {		//���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�̸����� �Է��ϼ���.");
		}
		else if(!mailPattern.matcher(fieldUserMail.getText()).matches()) {	//���� �̸��� ���Ŀ� ���� �ʴٸ� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�̸��� ���Ŀ� ���� �ʽ��ϴ�.");
		}
		else {	//�װ� �ƴ϶�� ���������� ����
			boolean overlap = loginDao.chkUserMail(fieldUserMail.getText());	//DB���� �ߺ��Ǵ� �̸����� �ִ��� üũ��
			if(overlap) {		//���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� �̸����Դϴ�.");
				fieldUserMail.clear();
				overlapChkNum = 0;
			}
			else {					//false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				btnUserMailChk.setText("��밡��");
//				overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				btnUserMailChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}
	
	//��ȭ��ȣ �ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void handleBtnUserTelChkAction() {
		if(fieldUserTel.getText().equals("")) {		//���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("��ȭ��ȣ�� �Է��ϼ���.");
		}
		else if(!telPattern.matcher(fieldUserTel.getText()).matches()) {	//���� ��ȭ��ȣ ���Ŀ� ���� �ʴٸ� ���̾�α׸� ���
			ChkDialogMain.chkDialog("��ȭ��ȣ ���Ŀ� ���� �ʽ��ϴ�.");
		}
		else {	//�װ� �ƴ϶�� ���������� ����
			boolean overlap = loginDao.chkUserTel(fieldUserTel.getText());	//DB���� �ߺ��Ǵ� ��ȭ��ȣ�� �ִ��� üũ��
			if(overlap) {		//���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� ��ȭ��ȣ�Դϴ�.");
				fieldUserTel.clear();
				overlapChkNum = 0;
			}
			else {					//false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				btnUserTelChk.setText("��밡��");
//					overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				btnUserTelChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}	
	
	//ã�� ��ư�� ������ �̹����� ã������ ���� ���̾�αװ� �����
	//������ �����ϸ� �ش� ���丮���� ���ϸ���� imgPath�� �ڵ����� �Է��� ��
	public void handleBtnImgAction() {
		//���� ���� ���̾�α׸� �ҷ���
		FileChooser imgChooser = new FileChooser();
		//���� ������ Ȯ���� ����(��ǥ �̹��� Ȯ����)
		imgChooser.getExtensionFilters().addAll(
				new ExtensionFilter("��� �̹��� ����(*.jpg, *.png, *.gif)", "*.jpg", "*.png", "*.gif")
			);
		//���� ���� ���̾�α׸� ��� ��ġ
		File selectedFile = imgChooser.showOpenDialog((Stage)btnImg.getScene().getWindow());
		try {
			//���� �о����
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			//�̹��� ����
			Image img = new Image(bis);
			//��� �ʵ忡 ������ �̹����� ��θ� ����ϰ�
			fieldImgPath.setText(selectedFile.getAbsolutePath());
			//�̹����信 ������ �̹����� ���
			viewImg.setImage(img);
			viewImg.setFitHeight(133);
			viewImg.setFitWidth(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//���߿� ��ư�� �ϳ� �� �߰��ؼ� ���ͳ� url�� �Է��� �̹����� �߰��� �� �ִ� ����� ����.
	
	//��Ϲ�ư ���� ����
	//1. DB�� not null��Ҹ� ��� üũ�ؼ� �Է����� ���� ���� �ִ��� üũ(�ʼ��׸�)
	//1_1. not null��� �߿� �Է����� ���� ���� �ִٸ� ���̾�α׸� ���
	//2_1. ��� �ԷµǾ��ٸ� �ߺ�üũ�� �ߴ��� ���ߴ��� üũ overlapChkNum == 1�ΰ�
	//2_2. pwChkLbl�� '��ġ'��� �Ǿ� �ִ��� üũ
	//2_3. �Ҽ��� �����ߴ��� üũ
	//2_4. ��ȭ��ȣ ���Ŀ� �°� �Է��ߴ���
	//2_5. �̸��� ���Ŀ� �°� �Է��ߴ��� (���� ���Խ��� Ȱ���� üũ�Ѵ�.)
	//3. ��� �����Ѵٸ� DB�� �����͸� ����
	public void handleBtnSubmitAction() {
		//�ʼ��׸��� üũ�Ѵ�.
		//���� �Է����� ���� ���� �ִٸ� �ٽ� �Է��϶�� ���̾�α׸� ���
		if(fieldUserNo.getText().isEmpty() || fieldUserName.getText().isEmpty() || fieldPassword.getText().isEmpty() || fieldUserTel.getText().isEmpty()
				|| fieldUserMail.getText().isEmpty()) {
			ChkDialogMain.chkDialog("�ʼ��׸��� �Է��ϼ���.");
		}
		//�ߺ�üũ�ߴ��� ���ߴ��� üũ
		else if(overlapChkNum == 0) {
			ChkDialogMain.chkDialog("�ߺ�üũ�� �ϼ���.");
		}
		//��й�ȣ�� ��ġ�ϴ���
		else if(lblPwChk.getText().equals("����ġ")) {
			ChkDialogMain.chkDialog("��й�ȣ�� Ȯ���ϼ���.");
		}
		//�Ҽ� �μ��� �����ߴ���
		else if(comboBoxDept.getSelectionModel().getSelectedItem() == null) {
			ChkDialogMain.chkDialog("�Ҽ��� �����ϼ���.");
		}
		//��� �����ߴٸ� �����ͺ��̽��� �����Ѵ�.
		else {
			String imagePath = new String();
			if(!fieldImgPath.getText().isEmpty()) {
				File imageFile = new File(fieldImgPath.getText());	//������ �̹����� File��ü�� �ϳ� ����
				imagePath = FTPUploader.uploadFile("image", imageFile);
			}
			else {
				imagePath = "uploadedfiles/images/default.jpg";
			}
			User user = new User(fieldUserNo.getText(), fieldUserName.getText(), fieldPassword.getText(), fieldUserMail.getText(), fieldUserTel.getText(), imagePath, comboBoxDept.getSelectionModel().getSelectedItem().toString(), "", "", 0);
			loginDao.insertUserData(user);
			ChkDialogMain.chkDialog("����� �Ϸ�Ǿ����ϴ�.");
			Stage stage = (Stage)btnSubmit.getScene().getWindow();
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
	
	//��� ��ư�� ������ ����� ���â�� ����
	public void handleBtnCancelAction() {
		Stage stage = (Stage)btnCancel.getScene().getWindow();
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
