package CreateDialogModule;

import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.User;
import Dao.UserInfoDao;
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
���α׷� ���� : 0.7.0
��� �̸� : ����� �� ���� ���̾�α�
��� ���� : 1.0.0
Ŭ���� �̸� : PrivateCardController
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- PrivateCardController.java (���� ����� ������ �����ִ� ����� �߽� �ȳ��� �ӽ� ���̾�α�)

�ʿ� fxml����
- privateCard.fxml (���� ���� �� â fxml)

�ش� Ŭ���� �ֿ� ���
- ������� �� ������ ���
- ���� �޽����� ������ �� ����
 */
public class PrivateCardController implements Initializable {
	@FXML private Label lblDept, lblPosition, lblUserName, lblUserMail, lblUserTel;
	@FXML private ImageView viewImg;
	@FXML private AnchorPane paneProfile;
	@FXML private Button btnCancel, btnStatusSave;
	@FXML private TextArea txtAreaStatus;
	
	public static String userNo;
	User user;
	UserInfoDao userInfoDao = new UserInfoDao();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		user = userInfoDao.selectMyInfo(userNo);
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnStatusSave.setOnAction(event -> handleBtnStatusSaveAction());
		
		lblDept.setText(user.getUserDept());
		lblUserName.setText(user.getUserName());
		lblPosition.setText(user.getUserPosition());
		lblUserMail.setText(user.getUserMail());
		lblUserTel.setText(user.getUserTel());
		String url = "http://yaahq.dothome.co.kr/" + user.getUserImgPath();
		viewImg.setImage(new Image(url));
		txtAreaStatus.setText(user.getUserStatusMsg());
	}
	
	public void handleBtnStatusSaveAction() {
		userInfoDao.updateStatusMsg(txtAreaStatus.getText(), userNo);
		ChkDialogMain.chkDialog("��ϵǾ����ϴ�.");
	}
	
	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}
