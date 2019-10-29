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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScheduleController implements Initializable {
	@FXML RadioButton radioBtnAll, radioBtnPrivate, radioBtnGroup;
	@FXML ToggleGroup radioGroupSchedule;
	@FXML Button btnPrevMonth, btnNextMonth, btnDayOffAttend;
	@FXML GridPane gridPaneCalendar;
	@FXML Label lblYear, lblMonth, lblMonthTxt;
	
	private ArrayList<Integer> daysList = new ArrayList<Integer>(42);	//�޷��Էµ� ��¥��
	private ArrayList<String> privateSchList = new ArrayList<String>();
	private ArrayList<String> groupSchList = new ArrayList<String>();
	YearMonth yearMonth;
	LocalDate localDate;
	ObservableList<Integer> list = FXCollections.observableArrayList(daysList);
	
	int thisYear;
	int thisMonth;
	public static String USERNO = MainController.USER_NO;
	ScheduleDao scheduleDao = new ScheduleDao();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		yearMonth = YearMonth.now();
		thisYear = yearMonth.getYear();
		thisMonth = yearMonth.getMonthValue();
		lblYear.setText(thisYear + "");
		lblMonth.setText(thisMonth + "");
		lblMonthTxt.setText(yearMonth.getMonth().toString());
		
		btnPrevMonth.setPrefSize(100, 30);
		btnNextMonth.setPrefSize(100, 30);
		btnPrevMonth.setOnAction(event -> handleBtnPrevMonth());
		btnNextMonth.setOnAction(event -> handleBtnNextMonth());
		btnPrevMonth.setText(yearMonth.minusMonths(1).getMonthValue() + "��");
		btnNextMonth.setText(yearMonth.plusMonths(1).getMonthValue() + "��");
		
		inputListDays(yearMonth);
		createCalendar();
		radioGroupSchedule.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(javafx.beans.value.ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				createCalendar();
			};
		});
	}
	
	public void inputListDays(YearMonth yearMonth) {
		daysList.clear();
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
	
	public void createCalendar() {
		int index = 0;
		gridPaneCalendar.getChildren().clear();
		scheduleDao.entryDate(thisYear, thisMonth, privateSchList, groupSchList);
		for(int i = 0; i < 6; i++) {	//�� ��
			for(int j = 0; j < 7; j++) {	//�� ��
				Button btn = new Button();
				btn.setText(daysList.get(index) + "");
				btn.setFont(new Font(20));
				btn.setPrefSize(gridPaneCalendar.getPrefWidth()/7, gridPaneCalendar.getPrefHeight()/6);
				if(daysList.get(index) == 0) {
					btn.setText("");
					btn.setDisable(true);
				}
				if(j == 0) {
					btn.setTextFill(Paint.valueOf("RED"));
				}
				else if(j == 6) {
					btn.setTextFill(Paint.valueOf("BLUE"));
				}
				gridPaneCalendar.add(btn, j, i);
				
				if(radioBtnAll.isSelected()) {
					if(privateSchList.contains(daysList.get(index) + "") && groupSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
					}
					else if(privateSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
					}
					else if(groupSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
					}
				}
				else if(radioBtnPrivate.isSelected()) {
					if(privateSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
					}
				}
				else if(radioBtnGroup.isSelected()) {
					if(groupSchList.contains(daysList.get(index) + "")) {
						btn.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
					}
				}
				
				index++;
				btn.setOnAction(event -> writeSchedule(btn.getText()));
			}
		}
	}
	
	public void colorChange() {
		scheduleDao.entryDate(thisYear, thisMonth, privateSchList, groupSchList);
	}
	
	public void handleBtnPrevMonth() {
		yearMonth = yearMonth.minusMonths(1);
		moveMonth();
	}
	
	public void handleBtnNextMonth() {
		yearMonth = yearMonth.plusMonths(1);
		moveMonth();
	}
	
	public void moveMonth() {
		inputListDays(yearMonth);
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
		Schedule.year = thisYear;
		Schedule.month = thisMonth;
		Schedule.date = date;
		Stage stage = new Stage(StageStyle.UNDECORATED);
		try {
			Parent anotherPane = FXMLLoader.load(getClass().getResource("createSchedule.fxml"));
			Scene scene = new Scene(anotherPane);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
