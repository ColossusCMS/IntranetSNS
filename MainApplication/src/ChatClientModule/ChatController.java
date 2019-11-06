package ChatClientModule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController implements Initializable {
	@FXML Button btnSend, btnSendFile;
	@FXML TextArea txtArea;
	@FXML TextField txtFieldInput;
	@FXML Label lblRoomTitle;
	
	Socket socket;
	String ip = "125.185.21.163";
//	String ip = "192.168.219.14";
	public static String name;
	public static String dept;
	int port = 10000;
	String deptKor;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startClient(ip, port, name);
		Platform.runLater(() -> {
			txtArea.appendText("[ä�ù� ����]\n");
		});
		
		switch(dept) {
		case "all":
			deptKor = "��ü";
			break;
		case "dev":
			deptKor = "����";
			break;
		case "opt":
			deptKor = "�濵";
			break;
		case "hr":
			deptKor = "�λ�";
			break;
		case "sales":
			deptKor = "����";
			break;
		case "design":
			deptKor = "������";
			break;
		}
		lblRoomTitle.setText(deptKor);
		
		btnSend.setOnAction(event -> {
			send(name + ": " + txtFieldInput.getText() + "\n");
			txtFieldInput.setText("");
			txtFieldInput.requestFocus();
		});
		
		txtFieldInput.setOnAction(event -> {
			send(name + ": " + txtFieldInput.getText() + "\n");
			txtFieldInput.setText("");
			txtFieldInput.requestFocus();
		});
	}
	
	public void startClient(String ip, int port, String name) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(ip, port);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(name);
					dos.writeUTF(dept);
					receive();
				} catch (Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						txtArea.appendText("[���� ���� ����]\n");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}
	
	public void stopClient() {
		try {
			if(!socket.isClosed() && socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receive() {
		while(true) {
			try {
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String msg = dis.readUTF();
				Platform.runLater(() -> txtArea.appendText(msg));
			} catch (Exception e) {
				stopClient();
				break;
			}
		}
	}
	
	public void send(String msg) {
		Thread thread = new Thread() {
			public void run() {
				try {
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(msg);
					dos.flush();
				} catch (Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
}