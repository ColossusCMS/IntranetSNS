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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServerMain extends Application {
	public static ExecutorService threadPool;
	HashMap<String, HashMap<String, Client>> hashMapDept;
	static int connUserCnt = 0;
	TextArea textAreaLog = null;

	ServerSocket serverSocket = null;
	Socket socket = null;

	public ServerMain() {
		hashMapDept = new HashMap<String, HashMap<String, Client>>();
		Collections.synchronizedMap(hashMapDept);
		
		HashMap<String, Client> dept1 = new HashMap<String, Client>();
		Collections.synchronizedMap(dept1);
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

		hashMapDept.put("all", dept1);
		hashMapDept.put("dev", dept2);
		hashMapDept.put("opt", dept3);
		hashMapDept.put("hr", dept4);
		hashMapDept.put("sales", dept5);
		hashMapDept.put("design", dept6);
	}
	
	public void startServer(String IP, int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Exception in open server");
			e.printStackTrace();
			if(!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}
		
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						socket = serverSocket.accept();
						Thread msr = new Client(socket);
						threadPool.submit(msr);
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
		threadPool.submit(thread);
	}
	
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
		int port = 9991;
		
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
		new ServerMain();
	}
	
	class Client extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		String name = "";
		String dept = "";
		
		public Client(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			HashMap<String, Client> clientMap = null;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				name = in.readUTF();
				dept = in.readUTF();
				clientMap = hashMapDept.get(dept);
				clientMap.put(name, this);
				
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