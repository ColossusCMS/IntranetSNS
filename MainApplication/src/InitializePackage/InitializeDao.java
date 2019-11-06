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
	private static final String USERNAME = "sample";
	private static final String PASSWORD = "9999";
	private static final String URL = "jdbc:mysql://125.185.21.163:3306/sampledb";
//	private static final String URL = "jdbc:mysql://192.168.219.14:3306/sampledb";
	
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
