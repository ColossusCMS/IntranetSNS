package CreateDialogModule;

import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.User;
import Dao.UserInfoDao;
import InitializePackage.DataProperties;
import MainModule.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : CreateDialogModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : PrivateCardController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ���� ����� �� ���� ���̾�α��� ��Ʈ�ѷ�
- �����ͺ��̽��� ����� ���̺��� ���� ������� ������ ������ ���̾�α׸� ����

��Ű�� ���� ���� ����
 */
public class PrivateCardController implements Initializable {
	@FXML
	private Label lblDept, lblPosition, lblUserName, lblUserMail, lblUserTel;
	@FXML
	private ImageView imageViewImg;
	@FXML
	private AnchorPane paneProfile;
	@FXML
	private Button btnCancel, btnStatusSave;
	@FXML
	private TextArea txtAreaStatus;

	User user;
	UserInfoDao userInfoDao = new UserInfoDao();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user = userInfoDao.selectMyInfo(MainController.USER_NO); // �ڱ� �ڽ��� ����� ��ȣ�� �����͸� ������
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnStatusSave.setOnAction(event -> handleBtnStatusSaveAction());

		lblDept.setText(user.getUserDept());
		lblUserName.setText(user.getUserName());
		lblPosition.setText(user.getUserPosition());
		lblUserMail.setText(user.getUserMail());
		lblUserTel.setText(user.getUserTel());
		String url = "http://" + DataProperties.getIpAddress() + ":" + DataProperties.getPortNumber("HTTPServer")
				+ "/images/" + user.getUserImgPath();
		imageViewImg.setImage(new Image(url));
		txtAreaStatus.setText(user.getUserStatusMsg());
	}

	// ���� �޽��� �����ϴ� �޼���
	public void handleBtnStatusSaveAction() {
		userInfoDao.updateStatusMsg(txtAreaStatus.getText(), MainController.USER_NO);
		ChkDialogMain.chkDialog("��ϵǾ����ϴ�.");
	}

	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}
