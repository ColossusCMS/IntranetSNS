package LoginModule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : LoginModule
��Ű�� ���� : 1.2.0
Ŭ���� �̸� : LoginMain
�ش� Ŭ���� �ۼ� : �ֹ���, �赵��

�ش� Ŭ���� �ֿ� ���
- ���α׷��� ����Ǿ��� �� ���� ���� �����ϴ� �α��� â�� ���
- ���α׷��� ��ü ������

��Ű�� ���� ���� ����
1.1.0
- DAO �ν��Ͻ��� �ʿ�ÿ��� ������ ������ �̵� ���� �ε� �ð��� ����.
- ����� ���â���� �̸��� �ߺ�üũ ��ư �߰� �� �̸��� �ߺ�üũ �׼� �߰�
- LoginDao Ŭ������ �̸��� üũ�ϴ� �޼��� �߰�
- ���� �� �޼��� �̸� ����ȭ

1.1.1
- Dao �ν��Ͻ� ���� (������ ���̽� �ʱ�ȭ Ŭ���� ����)
- �޺��ڽ��� ������ �����ͺ��̽��� ����

1.1.2
- ��ȭ��ȣ �ߺ�üũ ��ư, ��� �߰�
- �ߺ��α��� ���� ��� �߰�

1.2.0
- ����� �����ʿ� �̹��� ���ε� ��� �߰�, SFTP������ �̿��� ����
 */

//���α׷��� ������
public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		primaryStage.setTitle("�α���");
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}