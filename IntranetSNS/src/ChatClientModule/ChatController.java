package ChatClientModule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import InitializePackage.DataProperties;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ChatClientModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : ChatController
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ä�� Ŭ���̾�Ʈ ��Ʈ�ѷ�

��Ű�� ���� ���� ����
 */
public class ChatController implements Initializable {
	@FXML
	Button btnSend;
	@FXML
	TextArea txtArea;
	@FXML
	TextField txtFieldInput;
	@FXML
	Label lblRoomTitle;

	Socket socket;
	String ip = DataProperties.getIpAddress(); // ä�� ���� IP�ּҸ� ��Ƶδ� ����
	public static String name;
	public static String dept;
	int port = DataProperties.getPortNumber("Client");
	String deptKor; // �μ����� �ѱ۷� ��ȯ�ϱ� ���� ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startClient(ip, port, name);
		Platform.runLater(() -> txtArea.appendText("[ä�ù� ����]\n"));

		// �����κ��� �μ����� �޾ƿ��� �� �������� �� �̸��� �ѱ۷� �ٲٴ� ����
		switch (dept) {
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
		case "plan":
			deptKor = "��ȹ";
			break;
		}
		lblRoomTitle.setText(deptKor); // ���� �������� ä�ù� �̸�(�μ���)

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

	// Ŭ���̾�Ʈ ����
	public void startClient(String ip, int port, String name) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(ip, port);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(name); // ����� �̸��� ������ ����
					dos.writeUTF(dept); // ����ڰ� ������ ä�ù��̸�(�μ���)�� ������ ����
					receive();
				} catch (Exception e) {
					if (!socket.isClosed()) {
						stopClient();
						txtArea.appendText("[���� ���� ����]\n");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}

	// ���� ����
	public void stopClient() {
		try {
			if (!socket.isClosed() && socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����κ��� �޽����� �޾ƿ��� �޼���
	public void receive() {
		while (true) {
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

	// ������ �޽����� ������ �޼���
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