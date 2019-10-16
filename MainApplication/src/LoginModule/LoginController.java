package LoginModule;

import java.net.URL;
import java.util.ResourceBundle;

import ChkDialogModule.ChkDialogMain;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
��� �̸� : �α���
Ŭ���� �̸� : LoginController
���� : 1.0.0
�ش� Ŭ���� �ۼ� : �ֹ���, �赵��

�ʿ� ��ü Java����
- LoginMain.java (�α��� ȭ���� ����Ǵ� ���� Ŭ����)
- LoginDao.java (�����ͺ��̽� ����, ������ �ҷ�����, ������ ���� ��)
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
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- 
 */
public class LoginController implements Initializable {	
	@FXML private Button loginBtn, findAccountBtn, signUpBtn;
	@FXML private TextField userNoField;
	@FXML private PasswordField passwordField;
	
	LoginDao ld = new LoginDao();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginBtn.setOnAction(event -> loginSubmit());
		findAccountBtn.setOnAction(event -> findAccount());
		signUpBtn.setOnAction(event -> signUp());
		
		//��й�ȣ�� �Է��ϰ� �α��� ��ư�� �������ʰ� ����Ű�� ������ �α����� �õ��� �� �ְ� ����.
		passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode keyCode = event.getCode();
				if(keyCode.equals(KeyCode.ENTER)) {
					loginSubmit();
				}
			}
		});
	}
	
	//�α��� ��ư�� ��������
	//1. �����ȣ�� �Է����� �ʾҴٸ� �����ȣ�� �Է��϶�� ���̾�α� ���
	//2. ��й�ȣ�� �Է����� �ʾҴٸ� ��й�ȣ�� �Է��϶�� ���̾�α� ���
	//3_1. ���� �����ȣ�� ��й�ȣ �� �� �ϳ��� Ʋ�ȴٸ�(���ϰ����� null�� ����) ������ Ȯ���϶�� ���̾�α� ���
	//3_2. �α��ο� �����ߴٸ�(���ϰ����� �̸��� ����) ����� �̸��� ���鼭 �α��ο� �����ߴٴ� ���̾�α� ���
	public void loginSubmit() {
		String labelText = new String();
		if(userNoField.getText().isEmpty() || passwordField.getText().isEmpty()) {	//�����ȣ, ��й�ȣ �� �� �ϳ��� �Է����� �ʾҴٸ�
			if(userNoField.getText().isEmpty()) {
				labelText = "�����ȣ�� �Է��ϼ���.";
				userNoField.requestFocus();
			}
			else {
				labelText = "��й�ȣ�� �Է��ϼ���.";
				passwordField.requestFocus();
			}
			ChkDialogMain.chkDialog(labelText);
		}
		else {	//���� �Է��ߴٸ� �ϴ� �Ѿ
			String name = ld.chkUserData(userNoField.getText(), passwordField.getText());
			if(name == null) {	//�Է��� ������ ���� �ʴٴ� ��
				labelText = "��ġ�ϴ� ȸ�������� �����ϴ�.";
				ChkDialogMain.chkDialog(labelText);
			}
			else {	//�װ� �ƴ϶�� �Է��� ������ �ùٸ�
				labelText = name + "��\n�������!";
				ChkDialogMain.chkDialog(labelText);
			}
		}
	}
	
	//����ã�� ��ư�� ������ ��
	//����ã�� â���� ��ȯ��
	public void findAccount() {
		Stage stage = (Stage)findAccountBtn.getScene().getWindow();
		try {
			Parent findAccountDialog = FXMLLoader.load(getClass().getResource("findAccount.fxml"));
			Scene scene = new Scene(findAccountDialog);
			stage.setTitle("���� ã��");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����ڵ�� ��ư�� ������ ��
	//����ڵ�� â���� ��ȯ��
	public void signUp() {
		Stage stage = (Stage)signUpBtn.getScene().getWindow();
		try {
			Parent signUpDialog = FXMLLoader.load(getClass().getResource("signUp.fxml"));
			Scene scene = new Scene(signUpDialog);
			stage.setTitle("����� ���");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
