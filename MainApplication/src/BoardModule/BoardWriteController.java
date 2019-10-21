package BoardModule;
 
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import BoardModule.Board;
import ChkDialogModule.ChkDialogMain;
import Dao.BoardDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BoardWriteController implements Initializable {
	@FXML private TextField fieldTitle;
	@FXML private PasswordField fieldPw;
	@FXML private ComboBox<String> comboHeader;
	@FXML private TextArea txtAreaContent;
	@FXML private Button btnReg, btnCancel;
	
	BoardDao bd;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReg.setOnAction(event -> handleBtnRegAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		
		ObservableList<String> comboList = FXCollections.observableArrayList("��ü", "�渮��", "������", "�濵��", "�λ���");
		comboHeader.setItems(comboList);	
	}
	
	public void handleBtnRegAction() {
		if(fieldTitle.getText().isEmpty()) {	//���� ������ �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("������ �Է��ϼ���.");
		}
		else if(comboHeader.getSelectionModel().isEmpty()) {	//�Խ����� �������� �ʾҴٸ�
			ChkDialogMain.chkDialog("�Խ����� �����ϼ���.");
		}
		else if(fieldPw.getText().isEmpty()) {	//��й�ȣ�� �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("��й�ȣ�� �Է��ϼ���.");
		}
		else {	//��� �����Ѵٸ� db�� ����
			bd = new BoardDao();
			boolean write = bd.insertBbsContent(new Board(comboHeader.getSelectionModel().getSelectedItem().toString(), fieldTitle.getText(), 1234, "20191112", txtAreaContent.getText(), fieldPw.getText()));
			if(write == true) {
				ChkDialogMain.chkDialog("�Խù��� ����߽��ϴ�.");
				btnReg.getScene().getWindow().hide();
			}
			else {
				ChkDialogMain.chkDialog("������ ���Ͽ� �Խù� ��Ͽ� �����߽��ϴ�.");
			}
		}
	}
	
	public void handleBtnCancelAction() {
		bd = new BoardDao();
		bd.callProcedure();
	}
}