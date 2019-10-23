package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class DeptDao {
	//�μ� ����Ʈ �������� �޼���
	public void loadDept(ObservableList<String> list) {
		String sql = "select deptname from depttbl;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("deptname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
