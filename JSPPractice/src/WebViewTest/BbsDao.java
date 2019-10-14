package WebViewTest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class BbsDao {
	
	private Connection conn;			//DB�� �����ϴµ� ����ϴ� Ŀ�ؼ� ����
	private static final String USERNAME = "root";		//DB ���̵� ���߿� �� DB�� ������ ����
	private static final String PASSWORD = "1234";		//DB ��й�ȣ ���߿� �� DB�� ������ ����
	private static final String URL = "jdbc:mysql://localhost/sqldb";		//DB �ּ� ���߿� �� DB�� ������ ����

	//������
	public BbsDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");		//����̹� �ε�
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("����̹� �ε� ����!");
			callProcedure();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("����̹� �ε� ����!");
		}
	}
	
	public ArrayList<Bbs> getList() {
		String sql = "select * from bbs where bbsavailable = 1 order by bbsid desc;";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsId(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserId(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//�ڹ� �ȿ��� ���ν��� call�ϴ� ���
	public void callProcedure() {
		try {
			CallableStatement cs = conn.prepareCall("call casePrac(?, ?)");
			cs.setString(1, "99");
			cs.registerOutParameter(2, Types.CHAR);
			cs.execute();
			System.out.println(cs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
