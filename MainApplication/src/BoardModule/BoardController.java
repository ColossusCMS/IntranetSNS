package BoardModule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ClassPackage.Board;
import CreateDialogModule.ChkDialogMain;
import Dao.BoardDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �Խ���
��� ���� : 1.0.0
Ŭ���� �̸� : BoardController
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��� Java����
- BoardController.java (����� ��Ͽ� ����ϴ� ����� ���� Ŭ����[������� ��� ������ ��� ����])
- BoardWriteController
- BoardModifyController

�ʿ� fxml����
- board.fxml
- boardWrite.fxml
- boardModify.fxml

�ʿ� import ����� ���� package
- ClassPackage.Board;
- CreateDialogModule.ChkDialogMain;
- Dao.BoardDao;

�ش� Ŭ���� �ֿ� ���
- �Խ��� ��Ͽ��� �Խù��� �������� �� ������ �����ϴ� â�� ����

��� ���� ���� ����
1.0.0
 */
public class BoardController implements Initializable {
	@FXML
	private Button btnCancel, btnModify, btnDelete;
	@FXML
	private Label lblHeader, lblWriter, lblDate, lblTitle, lblContent;
	
	public static String BBS_ID;	//�Խù� ��ȣ�� DaoŬ������ �����ϱ� ���� ����
	
	BoardDao boardDao = new BoardDao();
	Board board;	//�Խù��� ��� ������ ��� ���� Ŭ����
	
	
	//��й�ȣ �κ��� �ۼ��ڿ� ���� �������� ����� �񱳰� �����ϸ�
	//���� �ʿ���� �κ��̴� ���߿� ������ ��
	//����� ��ȣ�� �Խù��� �ۼ��� ��ȣ�� ��Ī�ؼ� ���� ���� ������ư�� ������ư�� Ȱ��ȭ�ϵ��� ������ ��
	@Override
	public void initialize(URL loc, ResourceBundle resources) {
		btnCancel.setOnAction(event -> handleBtnCancelAction());
		btnModify.setOnAction(event -> handleBtnModifyAction());
		btnDelete.setOnAction(event -> handleBtnDeleteAction());
		
		board = boardDao.loadAllBoardContent(BBS_ID);	//�Խù� ������ ���� �� �ش��ϴ� ��� ������ �������� ���� �Ű������� �Խù� ��ȣ�� ����
		lblHeader.setText(board.getBoardHeader());
		lblWriter.setText(board.getBoardUserNo());
		lblDate.setText(board.getBoardDate());
		lblTitle.setText(board.getBoardTitle());
		lblContent.setText(board.getBoardContent());
	}
	
	public void handleBtnModifyAction() {
		Stage stage = (Stage)btnModify.getScene().getWindow();
		try {
			Parent readBoardWindow = FXMLLoader.load(getClass().getResource("boardModify.fxml"));
			Scene scene = new Scene(readBoardWindow);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//������ �����ͺ��̽����� �����ϴ� ���� �ƴ϶�
	//�����ͺ��̽��� ���� ���θ� �˷��ִ� bbsAvailable�� 1���� 0���� ���游 ��
	//���� ���� ����
	//1��
	//���� ��ư�� ������ ��й�ȣ�� �Է��϶�� â�� ���
	//��й�ȣ�� ��ġ�ϴٸ� �����ߴٴ� ���̾�α׸� ���� â�� ����
	//2��
	//����� Ȯ�εǴ� �׳� ������ư ������ �ٷ� �����ϰ� ���̾�α� ���
	//����Ʈ���� ���ΰ�ħ�ϸ� �������°� Ȯ���ϵ���
	
	
	public void handleBtnDeleteAction() {
		//���� ������ ��
		boardDao.deleteBoardContent(BBS_ID);
		ChkDialogMain.chkDialog("�����Ǿ����ϴ�.");
		btnDelete.getScene().getWindow().hide();
	}

	public void handleBtnCancelAction() {
		btnCancel.getScene().getWindow().hide();
	}
}
