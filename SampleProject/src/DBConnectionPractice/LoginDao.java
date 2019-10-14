package DBConnectionPractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao {
	private Connection conn;    //DB Ŀ�ؼ�(����) ��ü
    private static final String USERNAME = "sample";   //DB ���ӽ� ID
    private static final String PASSWORD = "9999";	 //DB ���ӽ� �н�����
    //DB���� ���(��Ű��=�����ͺ��̽���)����
    private static final String URL = "jdbc:mysql://125.185.21.163:3306/sampledb"; 
 
    //������
    public LoginDao() {
        // connection��ü�� �����ؼ� DB�� ������.
        try {
            System.out.println("������");
        	//���� ��ü�� ������� 
            Class.forName("com.mysql.jdbc.Driver"); 
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("����̹� �ε� ����!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("����̹� �ε� ����!!");
        }
    }
    
    //���̺� �����ϴ� ��� ���� �������� �޼�����
    public List<User> slectAll() {
    	String sql = "select * from login;";
        PreparedStatement pstmt = null; 
        List<User> list = new ArrayList<User>();
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
 
            while (rs.next()) {	//�����ð� �ִ���?
                User u = new User();
                u.setUserno(rs.getInt("userno"));
                u.setUsername(rs.getString("username"));
                u.setUserpw(rs.getString("userpw"));
                u.setUsermail(rs.getString("usermail"));
                u.setUsertel(rs.getString("usertel"));
                u.setUserimg(rs.getString("userimg"));
                u.setDept(rs.getString("dept"));
                u.setUsergreet(rs.getString("usergreet"));
                u.setAdmin(rs.getInt("admin"));
                list.add(u);   //List<User>���ٰ� �߰���.
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}