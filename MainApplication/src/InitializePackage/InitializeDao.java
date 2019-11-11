package InitializePackage;

import java.sql.Connection;
import java.sql.DriverManager;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �����ͺ��̽� ���� �� ��������
��� ���� : 1.0.0
Ŭ���� �̸� : InitializeDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- InitializeDao.java (�����ͺ��̽��� �����ϴ� Ŭ����)

�ش� Ŭ���� �ֿ� ���
- ���α׷��� ����Ǹ� �����ͺ��̽��� �����ؼ�
- ������ ���¸� ������ �����ͺ��̽��� �������� ���� �����ð��� ����.
 */
public class InitializeDao {
	public static Connection conn;
	private static final String USERNAME = DataProperties.idProfile("MainDatabase");
	private static final String PASSWORD = DataProperties.password("MainDatabase");
	private static final String IP = DataProperties.ipAddress("MainDatabase");
	private static final int PORT = DataProperties.portNumber("MainDatabase");
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
