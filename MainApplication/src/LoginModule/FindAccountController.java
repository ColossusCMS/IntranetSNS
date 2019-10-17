package LoginModule;
 
import java.net.URL;
import java.util.ResourceBundle;

import ChkDialogModule.ChkDialogMain;
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
��� �̸� : �α���
Ŭ���� �̸� : FindAccountController
���� : 1.1.0
�ش� Ŭ���� �ۼ� : �ֹ���, �ɴ���

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

���� ���� ����
1.1.0
- DAO �ν��Ͻ��� �ʿ�ÿ��� ������ ������ �̵� ���� �ε� �ð��� ����.
- ����� ���â���� �̸��� �ߺ�üũ ��ư �߰� �� �̸��� �ߺ�üũ �׼� �߰�
- LoginDao Ŭ������ �̸��� üũ�ϴ� �޼��� �߰�
- ���� �� �޼��� �̸� ����ȭ
 */
public class FindAccountController implements Initializable {
	//�̸�, �̸���
    @FXML private TextField txtFieldUserName, txtFieldUserMail;
    @FXML private Button btnReg, btnCancel;
    
    LoginDao loginDao;		//DB ���� �� ���
    
    @Override
    public void initialize(URL loc, ResourceBundle resources) {
    	btnReg.setOnAction(event -> handleBtnRegAction());
    	btnCancel.setOnAction(event -> handleBtnCancelAction());
    }
    
    //������ ��ư ���� ����
    //1_1. �̸� �ʵ�Ȯ��, �����̸� �Է��϶�� ���̾�α�
    //1_2. �̸��� �ʵ�Ȯ��, �����̸� �Է��϶�� ���̾�α�
    //2_1. �̸��� �̸����� ������ DB���� �˻��ؼ� ��Ī�Ǹ� ���Ϻ�����
    //2_2. ���� ��Ī�� �ȵȴٸ�(����� null�̸�) ����� ���ٰ� ���̾�α�
    //3. ���Ϻ�������� �Ϸ������� �ٽ� �α���â���� ��ȯ
    public void handleBtnRegAction() {
    	loginDao = new LoginDao();
        if(txtFieldUserName.getText().isEmpty() || txtFieldUserMail.getText().isEmpty()) {	//�ʵ忡 ������ �ִٸ�
        	if(txtFieldUserName.getText().isEmpty()) {
        		ChkDialogMain.chkDialog("�̸��� �Է��ϼ���.");
        		txtFieldUserName.requestFocus();
        	}
        	else {
        		ChkDialogMain.chkDialog("�̸����� �Է��ϼ���.");
        		txtFieldUserMail.requestFocus();
        	}
        }
        else {
        	UserData ud = loginDao.chkUserNameMail(txtFieldUserName.getText(), txtFieldUserMail.getText());
        	//chk�� true��� ���� �´ٴ� ���̴� ������ ������.
        	//�� ���� ������ ���´ٴ� ���̾�α׸� ���� �α���â���� ��ȯ
        	if(ud != null) {
        		new SendMail(ud.getUserMail(), ud.getUserName(), ud.getUserNo(), ud.getUserPw());	//���� ������ ��
        		ChkDialogMain.chkDialog("������ �����߽��ϴ�.");
        		//�α��� â �̵�
        		handleBtnCancelAction();
        	}
        	//���� ���� �������� �ʴ´ٸ� �ش� ����ڰ� ���ٴ� ���̾�α�
        	else {
        		ChkDialogMain.chkDialog("�ش� ����� ������\n�������� �ʽ��ϴ�.");
        		txtFieldUserName.clear();
        		txtFieldUserMail.clear();
        	}
        }
    }
    
    //��ҹ�ư ���� -> �α���â���� ��ȯ
    public void handleBtnCancelAction() {
    	Stage stage = (Stage)btnCancel.getScene().getWindow();
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