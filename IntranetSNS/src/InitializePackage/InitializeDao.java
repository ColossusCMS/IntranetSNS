package InitializePackage;

import java.sql.Connection;
import java.sql.DriverManager;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : InitializePackage
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : InitializeDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ���α׷��� ����Ǹ� �����ͺ��̽��� �����ؼ�
- ������ ���¸� ������ �����ͺ��̽��� �������� ���� �����ð��� ����.

����� �ܺ� ���̺귯��
- mysql-connector-java-5.1.47.jar

��Ű�� ���� ���� ����
 */
public class InitializeDao {
	public static Connection conn;
	private static final String USERNAME = DataProperties.getIdProfile("MainDatabase");
	private static final String PASSWORD = DataProperties.getPassword("MainDatabase");
	private static final String IP = DataProperties.getIpAddress();
	private static final int PORT = DataProperties.getPortNumber("MainDatabase");
	private static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/snsproject";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("����̹� �ε� ����");
		} catch (Exception e) {
			System.out.println("����̹� �ε� ����");
			e.printStackTrace();
		}
	}
}
