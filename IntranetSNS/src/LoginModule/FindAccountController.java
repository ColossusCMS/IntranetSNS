package LoginModule;

import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.UserData;
import CreateDialogModule.ChkDialogMain;
import Dao.LoginDao;
import SendMail.SendMail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : LoginModule
��Ű�� ���� : 1.2.0
Ŭ���� �̸� : FindAccountController
�ش� Ŭ���� �ۼ� : �ֹ���, �ɴ���

�ش� Ŭ���� �ֿ� ���
- ����ڰ� �̸��� �̸����� �Է��ϰ� ������ ��ư�� ������
- �����ͺ��̽����� ������ �����Ͱ� �����ϴ��� Ȯ���ϰ� �����Ѵٸ�
- �ش� ����ڿ��� ����ڹ�ȣ�� ��й�ȣ�� ���Ϸ� �����Ѵ�.

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

public class FindAccountController implements Initializable {
	// �̸�, �̸���
	@FXML
	private TextField txtFieldUserName, txtFieldUserMail;
	@FXML
	private Button btnReg, btnCancel;

	LoginDao loginDao = new LoginDao(); // DB ���� �� ���

	@Override
	public void initialize(URL loc, ResourceBundle resources) {
		btnReg.setOnAction(event -> handleBtnRegAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());
	}

	// ������ ��ư ���� ����
	// 1_1. �̸� �ʵ�Ȯ��, �����̸� �Է��϶�� ���̾�α�
	// 1_2. �̸��� �ʵ�Ȯ��, �����̸� �Է��϶�� ���̾�α�
	// 2_1. �̸��� �̸����� ������ DB���� �˻��ؼ� ��Ī�Ǹ� ���Ϻ�����
	// 2_2. ���� ��Ī�� �ȵȴٸ�(����� null�̸�) ����� ���ٰ� ���̾�α�
	// 3. ���Ϻ�������� �Ϸ������� �ٽ� �α���â���� ��ȯ
	public void handleBtnRegAction() {
		if (txtFieldUserName.getText().equals("") || txtFieldUserMail.getText().equals("")) { // �ʵ忡 ������ �ִٸ�
			if (txtFieldUserName.getText().isEmpty()) {
				ChkDialogMain.chkDialog("�̸��� �Է��ϼ���.");
				txtFieldUserName.requestFocus();
			} else {
				ChkDialogMain.chkDialog("�̸����� �Է��ϼ���.");
				txtFieldUserMail.requestFocus();
			}
		} else {
			UserData ud = loginDao.chkUserNameMail(txtFieldUserName.getText(), txtFieldUserMail.getText());
			// chk�� true��� ���� �´ٴ� ���̴� ������ ������.
			// �� ���� ������ ���´ٴ� ���̾�α׸� ���� �α���â���� ��ȯ
			if (ud != null) {
				new SendMail(ud.getUserMail(), ud.getUserName(), ud.getUserNo(), ud.getUserPw()); // ���� ������ ��
				ChkDialogMain.chkDialog("������ �����߽��ϴ�.");
				// �α��� â �̵�
				handleBtnCancelAction();
			}
			// ���� ���� �������� �ʴ´ٸ� �ش� ����ڰ� ���ٴ� ���̾�α�
			else {
				ChkDialogMain.chkDialog("�ش� ����� ������\n�������� �ʽ��ϴ�.");
				txtFieldUserName.clear();
				txtFieldUserMail.clear();
			}
		}
	}

	// ��ҹ�ư ���� -> �α���â���� ��ȯ
	public void handleBtnCancelAction() {
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		try {
			Parent loginDialog = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(loginDialog);
			stage.setTitle("�α���");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
