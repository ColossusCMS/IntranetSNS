package ScheduleModule;

import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.Reg;
import ClassPackage.Schedule;
import Dao.ScheduleDao;
import MainModule.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ScheduleModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : CreateScheduleController
�ش� Ŭ���� �ۼ� : �ֹ���, �ɴ���

�ش� Ŭ���� �ֿ� ���
- ���� ��� â ��Ʈ�ѷ�
- ȭ�� ���ʿ����� ���� ������ ��� �Ǵ� ����
- ������ ȭ�鿡���� ��ϵ� ���� ���� �Ǵ� ��ü ���� ����Ʈ�� Ȯ��

��Ű�� ���� ���� ����
 */
public class CreateScheduleController implements Initializable {
	@FXML TextField txtFieldTitle;
	@FXML TextArea txtAreaContent;
	@FXML Button btnReg, btnCancel,btnDel;
	@FXML Label lblDate;
	@FXML TableView<Reg> tblViewPrivateSch, tblViewGroupSch;

	ObservableList<Reg> privateList = FXCollections.observableArrayList();	//���� ������ ��Ƶδ� ����Ʈ
	ObservableList<Reg> groupList = FXCollections.observableArrayList();	//��ü ������ ��Ƶδ� ����Ʈ
	ScheduleDao sd = new ScheduleDao();
	
	//��ư���� ������ ��¥�� ������
	int year = Schedule.year;
	int month = Schedule.month;
	String date = Schedule.date;
	//String userno = ScheduleController.USERNO;
	String today;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblDate.setText(year + "�� " + month + "�� " + date + "��");
		btnReg.setOnAction(event -> handleBtnRegAction());
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnDel.setOnAction(event -> handleBtnDelAction());
		
		today = year + "-" + month + "-" + date;
		sd.loadPrivateSchedule(privateList, MainController.USER_NO, today);
		sd.loadGroupSchedule(groupList, MainController.USER_NO, today);
		
		//���̺� ����
		createTbl(tblViewPrivateSch, privateList);
		createTbl(tblViewGroupSch, groupList);
		
		//���̺� ���� �׼�
		tblViewPrivateSch.setOnMouseClicked(event -> {
			if(!tblViewPrivateSch.getSelectionModel().isEmpty()) {
				txtFieldTitle.setText(tblViewPrivateSch.getSelectionModel().getSelectedItem().getSchTitle());
				txtAreaContent.setText(tblViewPrivateSch.getSelectionModel().getSelectedItem().getSchContent());
				btnReg.setDisable(false);
				btnDel.setDisable(false);
			}
		});
		tblViewGroupSch.setOnMouseClicked(event -> {
			if(!tblViewPrivateSch.getSelectionModel().isEmpty()) {
				txtFieldTitle.setText(tblViewGroupSch.getSelectionModel().getSelectedItem().getSchTitle());
				txtAreaContent.setText(tblViewGroupSch.getSelectionModel().getSelectedItem().getSchContent());
				btnReg.setDisable(true);
				btnDel.setDisable(true);
			}
		});
	}
	
	//���̺��� �����ϴ� �޼���
	//���� ���� ���̺�� ��ü ���� ���̺��� �����ϴµ� �Ű������� ���� �ٸ� ���̺� ����
	public void createTbl(TableView<Reg> table, ObservableList<Reg> list) {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);	//���̺� �� ũ�� ���� �Ұ�
		//���� �����ϴ� �κ�
		TableColumn<Reg, String> schTitleCol = new TableColumn<Reg, String>();
		schTitleCol.setCellValueFactory(new PropertyValueFactory<Reg, String>("schTitle"));
		table.getColumns().add(schTitleCol);
		table.setItems(list);
		//���̺��� ��� ����
		table.widthProperty().addListener((obs, oldValue, newValue) -> {
			Pane header = (Pane)table.lookup("TableHeaderRow");
			if(header.isVisible()) {
				header.setPrefHeight(0);
				header.setVisible(false);
			}
		});
	}
	
	//���� ��� ��ư
	//����ϰ� ����Ʈ �ڵ� ����
	public void handleBtnRegAction() {
		if(tblViewPrivateSch.getSelectionModel().isEmpty()) {
			sd.entrySchedule(new Reg(MainController.USER_NO, txtFieldTitle.getText(), txtAreaContent.getText(), today, null));
		}
		else {
			sd.updateSchedule(new Reg(MainController.USER_NO, txtFieldTitle.getText(), txtAreaContent.getText(), today, null));
		}
		privateList.clear();
		sd.loadPrivateSchedule(privateList, MainController.USER_NO, today);
		tblViewPrivateSch.setItems(privateList);
		txtFieldTitle.clear();
		txtAreaContent.clear();
	}
	
	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
	
	//���� ���� ���� �޼���
	//�����ͺ��̽����� �ش� ������ �����ϰ�
	//���� ���� ����Ʈ �籸���ϰ�
	//�ٽ� ���̺� ���� ������ ����
	public void handleBtnDelAction() {
		sd.deleteSchedule(new Reg(MainController.USER_NO, txtFieldTitle.getText(), txtAreaContent.getText(), today, "1"));
		privateList.clear();
		sd.loadPrivateSchedule(privateList, MainController.USER_NO, today);
		tblViewPrivateSch.setItems(privateList);
		txtFieldTitle.clear();
		txtAreaContent.clear();
	}
}
