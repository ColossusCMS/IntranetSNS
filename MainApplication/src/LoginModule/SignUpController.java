package LoginModule;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import ChkDialogModule.ChkDialogMain;
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
 * ������Ʈ ���� : ����� ���
 * ���� : 0.9.0
 * �ۼ� : �ֹ���
 * 
 * �ʿ� Java����
 * - Main.java (����� ����� ����Ǵ� ���� Ŭ����)
 * - SignUpDao.java (�����ͺ��̽� ����, ������ �ҷ�����, ������ ����)
 * - SignUpController.java (����� ���â ��Ʈ�ѷ�)
 * - User.java (����� ���� ������ ���� Ŭ����)
 * 
 * �ʿ� fxml���� :
 * - signUp.fxml (����� ���â fxml)
 * - chkDialog.fxml (�ȳ� ���̾�α� fxml)
 * 
 * �ֿ� ���
 * - ����� ����� ���� ����� ���� �Է� (�����ȣ, �̸�, ��й�ȣ, �̸���, ��ȭ��ȣ, ��ǥ����),
 * - �����ͺ��̽��� ������ �����ϱ� ���� �����ͺ��̽� ���� �� �����ͺ��̽� ����,
 * - �ߺ�üũ�� ���� �����ͺ��̽��κ��� ���� �о��
 * 
 * ���İ�ȹ : �̹����� �����ϴ� ��ɿ��� ������ �ƴ� ������ URL�� �̿��� ��� �����ϰ� ����� �߰�
 */
public class SignUpController implements Initializable {
	@FXML private TextField userNo, userName, userTel, userMail, imgPath;
	@FXML private Button overlapChk, imgBtn, submitBtn, cancelBtn;
	@FXML private PasswordField password, passwordChk;
	@FXML private Label pwChkLbl;
	@FXML private ImageView imgView;
	@FXML private ComboBox<String> dept;
	
	private ObservableList<String> comboList = FXCollections.observableArrayList("����1��", "����2��", "����1��", "����2��", "��ȹ��", "��������", "�濵��", "�λ���");
	LoginDao ld = new LoginDao();
	int overlapChkNum = 0;	//�ߺ�üũ ���� Ȯ���� ���� ����
	Pattern telPattern = Pattern.compile("(010)-\\d{4}-\\d{4}");	//�޴��� ��ȣ ����
	Pattern mailPattern = Pattern.compile("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}");	//�̸��� ����
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dept.setItems(comboList);
		overlapChk.setOnAction(event -> overlapChk());
		imgBtn.setOnAction(event -> loadImg());
		submitBtn.setOnAction(event -> submit());
		cancelBtn.setOnAction(event -> cancel());
		
		imgPath.setEditable(false);
		
		//���� �ߺ�üũ�� �ϰ��� �����ȣ�� ���� �Է��ϸ� �ٽ� �ߺ�üũ ��ư�� ���󺹱����Ѿ� �ؾ��ϴ�
		//�Ӽ� ���ø� �ؼ� ���� �ٲ�ٸ� ��ư�� ������Ŵ
		userNo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!oldValue.equals(newValue)) {
					overlapChk.setText("�ߺ�üũ");
					overlapChk.setDisable(false);
					overlapChkNum = 0;
				}
			}
		});
		
		//��й�ȣȮ�� �ʵ��� �Ӽ��� �׻� üũ�ϸ鼭 �ռ� �Է��� ��й�ȣ�� �������� ����ؼ� üũ
		//���� �������ٸ� ��ġ�Ѵٰ� ����ϰ� �׷��� ������ ����ġ�� ��� ���
		passwordChk.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals(password.getText())) {
					pwChkLbl.setText("��ġ");
					pwChkLbl.setTextFill(Color.GREEN);
				}
				else {
					pwChkLbl.setText("����ġ");
					pwChkLbl.setTextFill(Color.RED);
				}
			}
		});
	}
	
	//�ߺ�üũ ��ư�� ������ ���� �̺�Ʈ
	public void overlapChk() {
		if(userNo.getText().equals("")) {		//���� �ʵ忡 �ƹ� ���� �Է����� �ʰ� ��ư�� �����ٸ� ���� �Է��϶�� ���̾�α׸� ���
			ChkDialogMain.chkDialog("�����ȣ�� �Է��ϼ���.");
		}
		else {	//�װ� �ƴ϶�� ���������� ����
			boolean overlap = ld.loadUserNo(userNo.getText());	//DB���� �ߺ��Ǵ� ��ȣ�� �ִ��� üũ��
			if(overlap) {		//���� true��� �ߺ��� ���� �ִٴ� ���̴� ����� �� ���ٴ� ���̾�α׸� ���
				ChkDialogMain.chkDialog("�̹� ��ϵ� �����ȣ�Դϴ�.");
				userNo.clear();
				overlapChkNum = 0;
			}
			else {					//false��� �ߺ��Ǵ� ���� ���ٴ� ���̴� �ߺ�üũ ��ư�� �ؽ�Ʈ�� ��밡������ �ٲ���
				overlapChk.setText("��밡��");
//				overlapChk.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY	)));
				overlapChk.setDisable(true);
				overlapChkNum = 1;
			}
		}
	}
	
	//ã�� ��ư�� ������ �̹����� ã������ ���� ���̾�αװ� �����
	//������ �����ϸ� �ش� ���丮���� ���ϸ���� imgPath�� �ڵ����� �Է��� ��
	public void loadImg() {
		//���� ���� ���̾�α׸� �ҷ���
		FileChooser imgChooser = new FileChooser();
		//���� ������ Ȯ���� ����(��ǥ �̹��� Ȯ����)
		imgChooser.getExtensionFilters().addAll(
				new ExtensionFilter("��� �̹��� ����(*.jpg, *.png, *.gif)", "*.jpg", "*.png", "*.gif")
			);
		//���� ���� ���̾�α׸� ��� ��ġ
		File selectedFile = imgChooser.showOpenDialog((Stage)imgBtn.getScene().getWindow());
		try {
			//���� �о����
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			//�̹��� ����
			Image img = new Image(bis);
			//��� �ʵ忡 ������ �̹����� ��θ� ����ϰ�
			imgPath.setText(selectedFile.getAbsolutePath());
			//�̹����信 ������ �̹����� ���
			imgView.setImage(img);
			imgView.setFitHeight(133);
			imgView.setFitWidth(100);
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
	public void submit() {
		//�ʼ��׸��� üũ�Ѵ�.
		//���� �Է����� ���� ���� �ִٸ� �ٽ� �Է��϶�� ���̾�α׸� ���
		if(userNo.getText().isEmpty() || userName.getText().isEmpty() || password.getText().isEmpty() || userTel.getText().isEmpty()
				|| userMail.getText().isEmpty()) {
			ChkDialogMain.chkDialog("�ʼ��׸��� �Է��ϼ���.");
		}
		//�ߺ�üũ�ߴ��� ���ߴ��� üũ
		else if(overlapChkNum == 0) {
			ChkDialogMain.chkDialog("�ߺ�üũ�� �ϼ���.");
		}
		//��й�ȣ�� ��ġ�ϴ���
		else if(pwChkLbl.getText().equals("����ġ")) {
			ChkDialogMain.chkDialog("��й�ȣ�� Ȯ���ϼ���.");
		}
		//�Ҽ� �μ��� �����ߴ���
		else if(dept.getSelectionModel().getSelectedItem() == null) {
			ChkDialogMain.chkDialog("�Ҽ��� �����ϼ���.");
		}
		//��ȭ��ȣ ���Ŀ� �´���
		else if(!telPattern.matcher(userTel.getText()).matches()) {
			ChkDialogMain.chkDialog("��ȭ��ȣ ���Ŀ� ���� �ʽ��ϴ�.");
		}
		//�������Ŀ� �´���
		else if(!mailPattern.matcher(userMail.getText()).matches()) {
			ChkDialogMain.chkDialog("�̸��� ���Ŀ� ���� �ʽ��ϴ�.");
		}
		//��� �����ߴٸ� �����ͺ��̽��� �����Ѵ�.
		else {
			User user = new User(userNo.getText(), userName.getText(), password.getText(), userMail.getText(), userTel.getText(), imgPath.getText(), dept.getSelectionModel().getSelectedItem().toString());
			ld.insertUserData(user);
			ChkDialogMain.chkDialog("����� �Ϸ�Ǿ����ϴ�.");
			Stage stage = (Stage)submitBtn.getScene().getWindow();
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
	public void cancel() {
		Stage stage = (Stage)cancelBtn.getScene().getWindow();
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
