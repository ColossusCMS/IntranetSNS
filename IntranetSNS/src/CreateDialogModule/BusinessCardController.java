package CreateDialogModule;

import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.User;
import Dao.UserInfoDao;
import InitializePackage.DataProperties;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : CreateDialogModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : BusinessCardController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �ٸ� ������� �� ������ ����ϴ� ���̾�α��� ��Ʈ�ѷ�

��Ű�� ���� ���� ����
 */
public class BusinessCardController implements Initializable {
	@FXML private Label lblDept, lblPosition, lblUserName, lblUserMail, lblUserTel, lblUserGreet;
	@FXML private ImageView imageViewImg;
	@FXML private AnchorPane paneProfile;
	@FXML private Button btnCancel;
	
	public static String userNo;	//���⿡���� ������ �ٸ� ������� ����� ��ȣ�� �޾ƿ�
	User user;
	UserInfoDao userInfoDao = new UserInfoDao();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		user = userInfoDao.selectMyInfo(userNo);
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		lblDept.setText(user.getUserDept());
		lblUserName.setText(user.getUserName());
		lblPosition.setText(user.getUserPosition());
		lblUserMail.setText(user.getUserMail());
		lblUserTel.setText(user.getUserTel());
		lblUserGreet.setText(user.getUserStatusMsg());
		lblUserGreet.setWrapText(true);
		String url = "http://" + DataProperties.getIpAddress() + ":" + DataProperties.getPortNumber("HTTPServer") + "/images/" + user.getUserImgPath();
		imageViewImg.setImage(new Image(url));
		lblUserGreet.setOnMouseEntered(event -> {
			lblUserGreet.setPrefHeight(70);
			lblUserGreet.setPrefWidth(170);
		});
		lblUserGreet.setOnMouseExited(event -> {
			lblUserGreet.setPrefHeight(52);
			lblUserGreet.setPrefWidth(165);
		});
	}
	
	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}
