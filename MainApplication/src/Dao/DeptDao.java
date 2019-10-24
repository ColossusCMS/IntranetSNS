package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �α��� �����ͺ��̽� Ŭ����
��� ���� : 0.7.0
Ŭ���� �̸� : DeptDao
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
}
