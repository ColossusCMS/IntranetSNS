package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : Dao
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : UserInfoDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- DeptDao.java (����� ���̺� �����ͺ��̽� ����, ������ �ҷ�����, ������ ���� ��)

�ʿ� import ����� ���� package
- InitializePackage.InitializeDao (������ ���̽� ���� �ʱ�ȭ)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- �����ͺ��̽��� ����
- �μ� ������ �������� ����

���� ���� ����
1.0.0

 */
public class DeptDao {	
	//�μ��� ���� ������
	public int loadAllDept(ObservableList<String> deptList) {
		int rowCnt = 0;
		String sql = "select deptname from depttbl;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rowCnt++;
				deptList.add(rs.getString("deptname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rowCnt;
	}
	
	//�ش� �μ��� �Խ��� ��ϸ� ����
	public int loadAllDept(ObservableList<String> deptList, String userNo) {
		int rowCnt = 0;
		String sql = "select d.deptname from depttbl d inner join usertbl u on u.userdept = d.deptname where u.userno = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rowCnt++;
				deptList.add(rs.getString("deptname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rowCnt;
	}
}
