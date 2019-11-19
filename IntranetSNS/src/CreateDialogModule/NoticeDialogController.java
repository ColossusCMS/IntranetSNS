package CreateDialogModule;

import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.Notice;
import Dao.NoticeDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : CreateDialogModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : NoticeDialogController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ���������� �� ������ ����ϴ� ���̾�α��� ��Ʈ�ѷ�

��Ű�� ���� ���� ����
 */
public class NoticeDialogController implements Initializable {
	@FXML
	private Label lblTitle, lblContent;
	@FXML
	private Button btnClose;

	NoticeDao noticeDao = new NoticeDao();
	public static String noticeNo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Notice notice = noticeDao.getSelectedNotice(noticeNo);
		btnClose.setOnAction(event -> btnClose.getScene().getWindow().hide());
		lblTitle.setText(notice.getNoticeTitle());
		lblContent.setText(notice.getNoticeContent());
	}
}
