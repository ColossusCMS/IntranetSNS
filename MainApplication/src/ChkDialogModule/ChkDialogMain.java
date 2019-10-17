package ChkDialogModule;

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
��� �̸� : �ȳ��� ���̾�α�
Ŭ���� �̸� : ChkDialogMain
���� : 1.0.0
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- ChkDialogMain.java (���� ����� ������ �����ִ� ����� �߽� �ȳ��� �ӽ� ���̾�α�)

�ʿ� fxml����
- chkDialog.fxml (�ȳ��� �ӽ� ���̾�α� fxml)

�ش� Ŭ���� �ֿ� ���
- ����ڰ� ������ ��� �Ǵ� ������ �ȳ��ϱ� ���� ���̾�α�
- ���� �ٸ� ��Ȳ���� �ش��ϴ� �ȳ����� �����
 */
public class ChkDialogMain {
	//�ȳ��� �ӽ� ���̾�α׸� ���� �޼���
	//�Ű������� �ش� �ȳ����� �Է¹޾� ���� �ٸ� ��Ȳ���� �ٸ� ���̾�α� �ȳ����� ���
	public static void chkDialog(String labelText) {
		Stage chkDialog = new Stage(StageStyle.UTILITY);
		Parent parent;
		try {
			parent = (Parent)FXMLLoader.load(ChkDialogMain.class.getResource("chkDialog.fxml"));
			Scene scene = new Scene(parent);
			Label dialogLbl = (Label)parent.lookup("#dialogLbl");
			dialogLbl.setText(labelText);
			dialogLbl.setFont(new Font(12));
			Button dialogBtn = (Button)parent.lookup("#dialogBtn");
			dialogBtn.setOnAction(e -> chkDialog.hide());
			chkDialog.initModality(Modality.APPLICATION_MODAL);
			chkDialog.setResizable(false);
			chkDialog.setScene(scene);
			chkDialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}