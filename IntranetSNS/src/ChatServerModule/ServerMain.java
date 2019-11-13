package ChatServerModule;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import InitializePackage.DataProperties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ChatServerModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : ServerMain
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ä�� ���� Ŭ����

��Ű�� ���� ���� ����
 */
public class ServerMain extends Application {
	public static ExecutorService threadPool;	//���� �����带 ���ÿ� ó���ϱ� ���� ������Ǯ ����
	HashMap<String, HashMap<String, Client>> hashMapDept;
	TextArea textAreaLog = null;

	ServerSocket serverSocket = null;
	Socket socket = null;

	public ServerMain() {
		//������ ä�ù��� �ϳ��� ��Ƶδ� ū ���� ����
		hashMapDept = new HashMap<String, HashMap<String, Client>>();
		Collections.synchronizedMap(hashMapDept);
		
		//�� �μ��� �´� ä�ù��� �ϳ��� ����
		HashMap<String, Client> dept1 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept1);	//�ؽø� ����ȭó��
		HashMap<String, Client> dept2 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept2);
		HashMap<String, Client> dept3 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept3);
		HashMap<String, Client> dept4 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept4);
		HashMap<String, Client> dept5 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept5);
		HashMap<String, Client> dept6 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept6);
		HashMap<String, Client> dept7 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept7);
		
		//�� �μ��� �̸��� �����ϳ� �ؽø��� ū �濡 ������� ����
		//����� ����ϴ� ������ ������ �����ϴ� ��������̿���
		//�ѱ� ���ڼ��� ������ ������ �߻��ؼ� ��������̿��� ó���ϱ� ���ؼ�
		//�ε����ϰ� ���� �̸����� ��ȯ�ϴ� ������ �ʿ�����
		hashMapDept.put("all", dept1);
		hashMapDept.put("dev", dept2);
		hashMapDept.put("opt", dept3);
		hashMapDept.put("hr", dept4);
		hashMapDept.put("sales", dept5);
		hashMapDept.put("design", dept6);
		hashMapDept.put("plan", dept7);
	}
	
	//���� ����
	public void startServer(String IP, int port) {
		try {
			serverSocket = new ServerSocket(port);	//������ ���� ����
		} catch (Exception e) {
			System.out.println("Exception in open server");
			e.printStackTrace();
			if(!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}
		//�������� �����带 ����
		//������ �����ϴ� Ŭ���̾�Ʈ���� �޾ƿ��� ������
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						socket = serverSocket.accept();		//Ŭ���̾�Ʈ�� �������� �� accept�� �ϰ� Ŭ���̾�Ʈ�� ���� ������ �޾ƿ�
						Thread msr = new Client(socket);	//Ŭ���̾�Ʈ ó���� ������ ����
						threadPool.submit(msr);				//������Ǯ�� ���
						textAreaLog.appendText(socket.getInetAddress()+":"+socket.getPort() + "\n");//Ŭ���̾�Ʈ ���� (ip, ��Ʈ) ���
					} catch (Exception e) {
						if(!serverSocket.isClosed()) {
							stopServer();
						}
						break;
					}
				}
			}
		};
		threadPool = Executors.newCachedThreadPool();
		threadPool.submit(thread);	//���� ������ ������Ǯ�� ���
	}
	
	//���� ����
	public void stopServer() {
		try {
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//���� â ������
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));
		
		textAreaLog = new TextArea();
		textAreaLog.setEditable(false);
		textAreaLog.setFont(new Font(13));
		root.setCenter(textAreaLog);
		root.getCenter().prefWidth(300);
		
		Button btnServerOpen = new Button("Start");
		btnServerOpen.setMaxWidth(Double.MAX_VALUE);
		BorderPane.setMargin(btnServerOpen, new Insets(1, 0, 0, 0));
		root.setBottom(btnServerOpen);
		
		String IP = "127.0.0.1";
		int port = DataProperties.getPortNumber("Server");
		
		btnServerOpen.setOnAction(event -> {
			if(btnServerOpen.getText().equals("Start")) {
				startServer(IP, port);
				Platform.runLater(() -> {
					String message = String.format("[Start Server]\n", IP, port);
					textAreaLog.appendText(message);
					btnServerOpen.setText("Stop");
				});
			}
			else {
				stopServer();
				Platform.runLater(() -> {
					String message = String.format("[Stop Server]\n", IP, port);
					textAreaLog.appendText(message);
					btnServerOpen.setText("Start");
				});
			}
		});
		Scene scene = new Scene(root, 200, 500);
		primaryStage.setOnCloseRequest(event -> stopServer());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chat Server");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		new ServerMain();	//�ؽø��� �����ϴ� �ν��Ͻ�
	}
	
	//Ŭ���̾�Ʈ�� ó���ϴ� ������
	class Client extends Thread {
		Socket socket;		//Ŭ���̾�Ʈ ������ ��Ƶδ� ����
		DataInputStream in;	//Ŭ���̾�Ʈ�κ��� �޾ƿ��� ��Ʈ��
		DataOutputStream out;	//Ŭ���̾�Ʈ�� ������ ��Ʈ��
		String name = "";			//Ŭ���̾�Ʈ�� �̸�
		String dept = "";			//Ŭ���̾�Ʈ�� �μ�
		
		public Client(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			HashMap<String, Client> clientMap = null;	//������ Ŭ���̾�Ʈ�� ����Ǿ� �ִ� �ؽø�
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				name = in.readUTF();	//Ŭ���̾�Ʈ�� �̸��� �޾ƿ�
				dept = in.readUTF();	//Ŭ���̾�Ʈ�� �μ��� �޾ƿ�
				clientMap = hashMapDept.get(dept);	//�޾ƿ� �μ��� ������ �ش��ϴ� �ؽø����� ����
				clientMap.put(name, this);				//Ŭ���̾�Ʈ�� �̸��� ���� ������ �ؽøʿ� ����
				
				//�޾ƿ� �޽����� ó���ϴ� �κ�
				while(in != null) {
					String msg = in.readUTF();
					sendMsg(dept, msg);
				}
			} catch (Exception e) {
				System.out.println("--> " + e);
			} finally {
				if(clientMap != null) {
					clientMap.remove(name);
				}
			}
		}
		
		//�ش� �׷� ���� ����ڵ鿡�� �޽����� ����
		public void sendMsg(String dept, String msg) {
			//�ش� �μ��� ���� �ؽø��� ������(�������� ����Ǿ� ����)
			HashMap<String, Client> clientMap = hashMapDept.get(dept);
			//�ݺ��ڿ� �ش� �μ��� ���� �ؽø��� Ű(�����̸�)�� ����
			Iterator<String> user = hashMapDept.get(dept).keySet().iterator();
			//�ش� �μ��� ������ �������� �ϳ��� �����ͼ�
			while(user.hasNext()) {
				try {
					//���ο� Ŭ������ �ϳ��� ������.
					Client client = clientMap.get(user.next());
					client.out.writeUTF(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
