package CreateDialogModule;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : CreateDialogModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : ChkDialogMain
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ����ڰ� ������ ��� �Ǵ� ������ �ȳ��ϱ� ���� ���̾�α�
- ���� �ٸ� ��Ȳ���� �ش��ϴ� �ȳ����� �����

��Ű�� ���� ���� ����
 */
public class ChkDialogMain {
	//�ȳ��� �ӽ� ���̾�α׸� ���� �޼���
	//�Ű������� �ش� �ȳ����� �Է¹޾� ���� �ٸ� ��Ȳ���� �ٸ� ���̾�α� �ȳ����� ���
	public static void chkDialog(String labelText) {
		Stage chkDialog = new Stage(StageStyle.UTILITY);
		Parent another;
		try {
			another = (Parent)FXMLLoader.load(ChkDialogMain.class.getResource("chkDialog.fxml"));
			Scene scene = new Scene(another);
			Label dialogLbl = (Label)another.lookup("#dialogLbl");
			dialogLbl.setText(labelText);
			dialogLbl.setFont(new Font(12));
			Button dialogBtn = (Button)another.lookup("#dialogBtn");
			dialogBtn.setOnAction(e -> chkDialog.hide());
			chkDialog.initModality(Modality.APPLICATION_MODAL);
			chkDialog.setAlwaysOnTop(true);
			chkDialog.setResizable(false);
			chkDialog.setScene(scene);
			chkDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�ٸ� �������� ����ִ� ���̾�α�
	public static void businessCardDialog(String userNo) {
		BusinessCardController.userNo = userNo;
		Stage businessCard = new Stage(StageStyle.UNDECORATED);
		Parent another;
		try {
			another = FXMLLoader.load(ChkDialogMain.class.getResource("businessCard.fxml"));
			Scene scene = new Scene(another);
			businessCard.setScene(scene);
			businessCard.setResizable(false);
			businessCard.initModality(Modality.APPLICATION_MODAL);
			businessCard.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���� �������� ����ִ� ���̾�α�
	public static void privateCardDialog() {
		Stage businessCard = new Stage(StageStyle.UNDECORATED);
		Parent another;
		try {
			another = FXMLLoader.load(ChkDialogMain.class.getResource("privateCard.fxml"));
			Scene scene = new Scene(another);
			businessCard.setScene(scene);
			businessCard.setResizable(false);
			businessCard.initModality(Modality.APPLICATION_MODAL);
			businessCard.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���������� ���� â
	public static void noticeDialog() {
		Stage noticeDialog = new Stage(StageStyle.UTILITY);
		Parent another;
		try {
			another = FXMLLoader.load(ChkDialogMain.class.getResource("noticeDialog.fxml"));
			Scene scene = new Scene(another);
			noticeDialog.setScene(scene);
			noticeDialog.setResizable(false);
			noticeDialog.initModality(Modality.APPLICATION_MODAL);
			noticeDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}