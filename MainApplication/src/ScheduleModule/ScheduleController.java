package ScheduleModule;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClassPackage.Schedule;
import Dao.ScheduleDao;
import MainModule.MainController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ScheduleModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : ScheduleController
�ش� Ŭ���� �ۼ� : �ֹ���, �ɴ���

�ش� Ŭ���� �ֿ� ���
- ���� ȭ���� �����ϴ� ��Ʈ�ѷ�
- �޷� ���·� ȭ���� �����Ǿ� �ְ� ���� ���� �Ǵ� ��ü ���� ���� ǥ���Ѵ�.
- ������ư�� �̿��� ������ ������ ���� �ٸ��� ǥ�õ� ����

��Ű�� ���� ���� ����
 */
public class ScheduleController implements Initializable {
	@FXML RadioButton radioBtnAll, radioBtnPrivate, radioBtnGroup;
	@FXML ToggleGroup radioGroupSchedule;
	@FXML Button btnPrevMonth, btnNextMonth, btnDayOffApply;
	@FXML GridPane gridPaneCalendar;
	@FXML Label lblYear, lblMonth, lblMonthTxt;
	
	private ArrayList<Integer> daysList = new ArrayList<Integer>(42);	//�޷��Էµ� ��¥���� �ִ� ����Ʈ
	private ArrayList<String> privateSchList = new ArrayList<String>();	//���� ������ ��Ƶδ� ����Ʈ
	private ArrayList<String> groupSchList = new ArrayList<String>();	//��ü ������ ��Ƶδ� ����Ʈ
	/*
	��¥�� ����ϴµ� Calendar�� Date�� ������� ���� ����
	1. �Һ� ��ü�� �ƴϱ� ������ set���� ������ ���� �� �ùٸ� ���� ��µ��� ���� �� �ִٴ� �������� �ִ�.
	2. ��� �ʵ� ����
	3. ��ǥ�Ⱑ 0���� �����ϴ� ��
	4. ���� ����� �ϰ����� ����. ��𼭴� �Ͽ����� 0, ��𼭴� �Ͽ����� 1
	5. Date�� Calendar��ü�� ���� �д�.
	 */
	YearMonth yearMonth;	//������ �����ϴ� ����
	LocalDate localDate;		//��ǻ���� ���� ��¥�� �������� ����
	
//	ObservableList<Integer> list = FXCollections.observableArrayList(daysList);
	
	int thisYear;		//���� ��¥�� ����
	int thisMonth;	//���� ��¥�� ��
	public static String USERNO = MainController.USER_NO;	//�ش� ����ڸ� Ȯ���ϱ� ���� ���� ����
	ScheduleDao scheduleDao = new ScheduleDao();			//�����ͺ��̽� ����
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		yearMonth = YearMonth.now();	//���� ��¥�� ������ ������ 20XX-XX�� ����
		thisYear = yearMonth.getYear();	//����
		thisMonth = yearMonth.getMonthValue();	//��
		lblYear.setText(thisYear + "");
		lblMonth.setText(thisMonth + "");
		lblMonthTxt.setText(yearMonth.getMonth().toString());
		
		btnPrevMonth.setPrefSize(100, 30);
		btnNextMonth.setPrefSize(100, 30);
		btnPrevMonth.setOnAction(event -> handleBtnPrevMonth());
		btnNextMonth.setOnAction(event -> handleBtnNextMonth());
		btnPrevMonth.setText(yearMonth.minusMonths(1).getMonthValue() + "��");	//���� ���� ���� ��
		btnNextMonth.setText(yearMonth.plusMonths(1).getMonthValue() + "��");		//���� ���� ���� ��
		
		inputListDays(yearMonth);		//�޷��� ��¥�� ����
		createCalendar();	//�޷��� ����
		
		//���� â ����� ���� ��ư�� ���� ����
		radioGroupSchedule.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(javafx.beans.value.ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				createCalendar();
			};
		});
		btnDayOffApply.setOnAction(event -> handleBtnDayOffApplyAction());	//�ް���û ��ư
	}
	
	//�޷��� �����ϴµ� �ش� ��¥���� �ش� ���Ͽ� �°� �ֱ� ���� �ʱ� �۾�
	//���̵��
	//�� ���� �ּ� 4��, �ִ� 6���� ��찡 �߻�, ���� �ִ� �� ���� 42���� ����Ʈ�� ũ��� �����ϰ�
	//�ش� ���� ���Ͽ� ���� 1���� �ش� ���� �������ϱ��� �Է�
	//�Ͽ��� 0 ~ ����� 6���� ������ ����Ʈ�� �������� �����Ѵ�.
	//���� ���� �Է��ϰ� ����Ʈ�� ���� �κ��� ��� 0���� �ʱ�ȭ
	public void inputListDays(YearMonth yearMonth) {
		daysList.clear();	//����Ʈ �ʱ�ȭ
		int dayLength = yearMonth.lengthOfMonth();	//�ش� ���� ��ü �� ���� ����.
		int thisYear = yearMonth.getYear();				//�ش� ������ ������ ����
		int thisMonth = yearMonth.getMonthValue();	//�ش� ���� ������ ����
		String dayOfWeek = LocalDate.of(thisYear, thisMonth, 1).getDayOfWeek().toString();	//�ش� �� ���� 1��(ù° ��)�� ����
		int start = 0;	//����Ʈ�� ������ �ε����� ��
		int end = 0;	//����Ʈ�� ���� �ε����� ��
		//ù° ���� ������ ������ �ε����� �������� ����
		switch(dayOfWeek) {
		case "SUNDAY":
			start = 0;
			break;
		case "MONDAY":
			start = 1;
			break;
		case "TUESDAY":
			start = 2;
			break;
		case "WEDNESDAY":
			start = 3;
			break;
		case "THURSDAY":
			start = 4;
			break;
		case "FRIDAY":
			start = 5;
			break;
		case "SATURDAY":
			start = 6;
			break;
		}
		//������ ����ؼ� ����
		end = dayLength + start;
		//����Ʈ�� ������ ó������ ����־����.
		//����Ʈ�� �� �պ��� ���ʴ�� �������
		for(int i = 0; i < start; i++) {
			daysList.add(0);
		}
		//���������� ��¥�� ���ʴ�� �Է�
		int day = 1;
		for(int i = 0; i < dayLength; i++) {
			daysList.add(day);
			day++;
		}
		for(int i = end; i < 42; i++) {
			daysList.add(0);
		}
	}
	
	//�޷��� �����ϴ� �κ�
	public void createCalendar() {
		int index = 0;
		gridPaneCalendar.getChildren().clear();		//�̰� ���� ������ �� ���� �����Ͱ� ���� ������ ���� ����
		scheduleDao.entryDate(thisYear, thisMonth, privateSchList, groupSchList, USERNO);
		for(int i = 0; i < 6; i++) {	//�� ��
			for(int j = 0; j < 7; j++) {	//�� ��
				Button btn = new Button();
				btn.setText(daysList.get(index) + "");	//����Ʈ ������ ��ư ����
				btn.setFont(new Font(20));
				btn.setPrefSize(gridPaneCalendar.getPrefWidth()/7, gridPaneCalendar.getPrefHeight()/6);
				if(daysList.get(index) == 0) {		//�� ���� 0�̸� �ش� ���� ���� �ƴϱ� ������ ��ư�� �ƹ��͵� �Է����� �ʰ� ��ư ��Ȱ��ȭ
					btn.setText("");
					btn.setDisable(true);
				}
				if(j == 0) {		//1�� : �Ͽ���
					btn.setTextFill(Paint.valueOf("RED"));		//�Ͽ����̴� ��ư ���� ������
				}
				else if(j == 6) {	//7�� : �����
					btn.setTextFill(Paint.valueOf("BLUE"));	//������̴� ��ư ���� �Ķ���
				}
				gridPaneCalendar.add(btn, j, i);	//�׸����ҿ� ������� ��ư �߰�
				
				//���� ��ư�� ���� ��ư ���� �ٸ��� ǥ���ϴ� ����
				if(radioBtnAll.isSelected()) {	//��ü �������� �������� ��
					//���� ������ ��ü ������ ��� ��ϵǾ� �ִٸ� ��ư�� ������� ǥ��
					if(privateSchList.contains(daysList.get(index) + "") && groupSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
					}
					//���� ������ ��ϵǾ� �ִٸ� ��ư�� ���������� ǥ��
					else if(privateSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
					}
					//��ü ������ ��ϵǾ� �ִٸ� ��ư�� �Ķ������� ǥ��
					else if(groupSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
					}
				}
				else if(radioBtnPrivate.isSelected()) {	//���� �������� �������� ��
					//��ư ���� ����������
					if(privateSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
					}
				}
				else if(radioBtnGroup.isSelected()) {		//��ü �������� �������� ��
					//��ư ���� �Ķ�������
					if(groupSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
					}
				}
				index++;
				btn.setOnAction(event -> writeSchedule(btn.getText()));	//��ư �׼�
			}
		}
	}
	
//	public void colorChange() {
//		scheduleDao.entryDate(thisYear, thisMonth, privateSchList, groupSchList, USERNO);
//	}
	
	public void handleBtnPrevMonth() {
		yearMonth = yearMonth.minusMonths(1);
		moveMonth();
	}
	
	public void handleBtnNextMonth() {
		yearMonth = yearMonth.plusMonths(1);
		moveMonth();
	}
	
	//���� �̵����� ���� �޼���
	//�� �޼��尡 ������ ���� ���� �ٲ� ����
	//���� �ٲ� ���·� �޷��� ���� �����ϴ� ��
	public void moveMonth() {
		inputListDays(yearMonth);		//�ش� ���� �� ���� ����Ʈ ���� ����
		thisYear = yearMonth.getYear();
		thisMonth = yearMonth.getMonthValue();
		lblYear.setText(yearMonth.getYear() + "");
		lblMonth.setText(yearMonth.getMonthValue() + "");
		lblMonthTxt.setText(yearMonth.getMonth().toString());
		btnPrevMonth.setText(yearMonth.minusMonths(1).getMonthValue() + "��");
		btnNextMonth.setText(yearMonth.plusMonths(1).getMonthValue() + "��");
		createCalendar();
	}
	
	//��¥�� Ŭ���ϸ� ������ �Է�â�� ��
	public void writeSchedule(String date) {
		//�� �κ��� ���� ���� ��� â���� �����͸� ������ ���� ���� ������ ���
		Schedule.year = thisYear;
		Schedule.month = thisMonth;
		Schedule.date = date;
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent anotherPane = FXMLLoader.load(getClass().getResource("createSchedule.fxml"));
			Scene scene = new Scene(anotherPane);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOnHiding(event -> createCalendar());	//���� ����� ������ �޷��� ���� ������
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�ް���û â ����� �޼���
	public void handleBtnDayOffApplyAction() {
		Stage stage = new Stage(StageStyle.UTILITY);
		try {
			Parent anotherPane = FXMLLoader.load(getClass().getResource("dayOffApply.fxml"));
			Scene scene = new Scene(anotherPane);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
