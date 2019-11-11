package ScheduleModule;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import ClassPackage.DayOff;
import CreateDialogModule.ChkDialogMain;
import Dao.ScheduleDao;
import MainModule.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class DayOffApplyController implements Initializable {
	@FXML private DatePicker pickerDayOffStart, pickerDayOffFinish;
	@FXML private TextArea txtAreaReason;
	@FXML private Button btnApply;
	LocalDate dateStart, dateFinish;
	ScheduleDao scheduleDao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnApply.setOnAction(event -> handleBtnApplyAction());
	}
	
	//üũ�ؾ��� ��Ȳ
	//�ް� ������ �Է����� ���� ���
	//������ ��¥�� ���� ��¥���� ���� ���
	//���۳�¥ �Ǵ� ������ ��¥�� ���� ��¥���� ���� ���
	//��¥�� �������� ���� ���
	public void handleBtnApplyAction() {
		dateStart = pickerDayOffStart.getValue();
		dateFinish = pickerDayOffFinish.getValue();
		if(dateStart == null || dateFinish == null) {
			ChkDialogMain.chkDialog("��¥�� �����ϼ���.");
		}
		else if(dateStart.until(dateFinish).isNegative() || dateStart.until(dateFinish).isZero()) {
			ChkDialogMain.chkDialog("������ ��¥�� �����ϴ� ��¥���� ���ų� ���� �� �����ϴ�.");
		}
		else if(!dateStart.until(LocalDate.now()).isNegative() || dateStart.until(LocalDate.now()).isZero()) {
			ChkDialogMain.chkDialog("�����ϴ� ��¥�� ���ú��� ���ų� ���� �� �����ϴ�.");
		}
		else if(txtAreaReason.getText().isEmpty()) {	//�ް� ������ �Է����� �ʾҴٸ�
			ChkDialogMain.chkDialog("�ް� ������ �Է��ϼ���.");
		}
		else {
			String userid = MainController.USER_NO;
			scheduleDao = new ScheduleDao();
			scheduleDao.entryDayOff(new DayOff(userid, pickerDayOffStart.getPromptText(), pickerDayOffFinish.getPromptText(), txtAreaReason.getText()));
			ChkDialogMain.chkDialog(dateStart.until(dateFinish).getDays() + "�ϰ�\n�ް���û�� �Ϸ�Ǿ����ϴ�.");
			btnApply.getScene().getWindow().hide();
		}
	}
}