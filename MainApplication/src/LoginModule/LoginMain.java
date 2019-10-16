package LoginModule;
    
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
������Ʈ ���� : �α���
���� : 0.9.0
�ۼ� : �赵��, �ֹ���

�ʿ� Java����
- Main.java (�α��� ȭ���� ����Ǵ� ���� Ŭ����)
- LoginDao.java (�����ͺ��̽� ����, ������ �ҷ�����)
- LoginController.java (����� ���â ��Ʈ�ѷ�)

�ʿ� fxml���� :
- login.fxml (�α���â fxml)
- chkDialog.fxml (�ȳ� ���̾�α� fxml)

�ֿ� ���
- �α����� ���� ����� ���� �Է�(�����ȣ, ��й�ȣ),
- �����ͺ��̽����� ����� ������ �������� ���� �����ͺ��̽� ����,
- �ش��ϴ� �����Ͱ� �����ϴ��� üũ�� ���� �����ͺ��̽��κ��� ���� �о��
- ��� ������ �����Ѵٸ� �α��ο� �����ϰ� ���� ȭ������ �Ѿ
- ����� ��� ��ư�̳� ���� ã�� ��ư�� ������ �ش��ϴ� â�� ���� ���
 */
public class LoginMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("project.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setTitle("�α���");
    }
    
    public static void main(String[] args) {
    	Application.launch(args);
    }
}