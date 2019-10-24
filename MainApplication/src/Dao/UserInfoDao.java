package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ClassPackage.User;
import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �α��� �����ͺ��̽� Ŭ����
��� ���� : 0.7.0
Ŭ���� �̸� : UserInfoDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- UserInfoDao.java (����� ���̺� �����ͺ��̽� ����, ������ �ҷ�����, ������ ���� ��)

�ʿ� import ����� ���� package
- InitializePackage.InitializeDao (������ ���̽� ���� �ʱ�ȭ)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- �����ͺ��̽��� ����
- ��ϵ� ����ڵ��� �������� ���� Ŭ����
- ����� ���, �� ���� ��� ������ ����ϱ� ���� ���

���� ���� ����
1.0.0

 */
public class UserInfoDao {
	public User selectMyInfo(String myNo) {
		User user = null;
		String sql = "select * from usertbl where userno = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, myNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				user = new User(rs.getString("userno"), rs.getString("username"), rs.getString("userpassword"), rs.getString("usermail"), rs.getString("usertel"), rs.getString("userimgpath"), rs.getString("userdept"), rs.getString("userposition"), rs.getString("userstatusmsg"), rs.getInt("adminavailable"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {                
                e.printStackTrace();
            }
        }
		return user;
	}
	
	//�ڽ��� ������ ��ϵ� ����� ��θ� �������� ����
	//�ǿ��� ��ü ���ý� ���
	public void loadAllUser(ObservableList<User> userList, String userId) {
		String sql = "select * from usertbl where userno not in (?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			userList.clear();
			while(rs.next()) {
				userList.add(new User(rs.getString("userno"), rs.getString("username"), rs.getString("userpassword"), rs.getString("usermail"), rs.getString("usertel"), rs.getString("userimgpath"), rs.getString("userdept"), rs.getString("userposition"), rs.getString("userstatusmsg"), rs.getInt("adminavailable")));
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
	}
	
	//��ü ����ε� �Է°����� ���͸��ϴ� ����
	public void loadAllUser(ObservableList<User> userList, String userId, String filterText) {
		String sql = "select * from usertbl where userno not in (?) and username = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, filterText);
			ResultSet rs = pstmt.executeQuery();
			userList.clear();
			while(rs.next()) {
				userList.add(new User(rs.getString("userno"), rs.getString("username"), rs.getString("userpassword"), rs.getString("usermail"), rs.getString("usertel"), rs.getString("userimgpath"), rs.getString("userdept"), rs.getString("userposition"), rs.getString("userstatusmsg"), rs.getInt("adminavailable")));
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
	}
	
	
	//�ڽ��� ������ �ش� �μ��� ����ڸ� �������� ����
	//�ǿ��� �μ� �������� �� ���
	public void loadFilteredAllUser(ObservableList<User> userList, String userId, String dept) {
		String sql = "select * from usertbl where userno not in (?) and userdept = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, dept);
			ResultSet rs = pstmt.executeQuery();
			userList.clear();
			while(rs.next()) {
				userList.add(new User(rs.getString("userno"), rs.getString("username"), rs.getString("userpassword"), rs.getString("usermail"), rs.getString("usertel"), rs.getString("userimgpath"), rs.getString("userdept"), rs.getString("userposition"), rs.getString("userstatusmsg"), rs.getInt("adminavailable")));
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
	}
	
	//�ڽ��� ������ �ش� �μ��� ����ڵ��� ���Ϳ� �°� �������� ����
	public void loadFilteredAllUser(ObservableList<User> userList, String userId, String dept, String filterText) {
		String sql = "select * from usertbl where userno not in (?) and userdept = ? and username = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, dept);
			pstmt.setString(3, filterText);
			ResultSet rs = pstmt.executeQuery();
			userList.clear();
			while(rs.next()) {
				userList.add(new User(rs.getString("userno"), rs.getString("username"), rs.getString("userpassword"), rs.getString("usermail"), rs.getString("usertel"), rs.getString("userimgpath"), rs.getString("userdept"), rs.getString("userposition"), rs.getString("userstatusmsg"), rs.getInt("adminavailable")));
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
	}
	
	//�ʵ�θ� ���͸�
	//�Ű����� �������
	public void loadFilteredAllUser(String userId, ObservableList<User> userList, String filterText) {
		String sql = "select * from usertbl where userno not in (?) and username = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, filterText);
			ResultSet rs = pstmt.executeQuery();
			userList.clear();
			while(rs.next()) {
				userList.add(new User(rs.getString("userno"), rs.getString("username"), rs.getString("userpassword"), rs.getString("usermail"), rs.getString("usertel"), rs.getString("userimgpath"), rs.getString("userdept"), rs.getString("userposition"), rs.getString("userstatusmsg"), rs.getInt("adminavailable")));
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
	}
}
