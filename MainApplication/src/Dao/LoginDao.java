package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import EncryptionDecryption.PasswordEncryption;
import InitializePackage.InitializeDao;
import LoginModule.User;
import LoginModule.UserData;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �α��� �����ͺ��̽� Ŭ����
��� ���� : 1.1.0
Ŭ���� �̸� : LoginDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- LoginDao.java (�����ͺ��̽� ����, ������ �ҷ�����, ������ ���� ��)

�ʿ� import ����� ���� package
- InitializePackage.InitializeDao (������ ���̽� ���� �ʱ�ȭ)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- �����ͺ��̽��� ����
- �α����� �õ��ϰų� �ߺ��� ����� ��ȣ üũ �Ǵ� �̸��� Ȯ���� ���� �����ͺ��̽����� �˻��� ����� ������
- ����� ��� â���� �Է��� �������� �����ͺ��̽��� ������ ����

���� ���� ����
1.1.0
- �����ͺ��̽� ���� �κ��� �����ϰ� static ������ ȣ���� �����ͺ��̽� ������ �� ���� ����
- �̸��� üũ�ϴ� �޼��� �߰�
 */
public class LoginDao {
	UserData ud;

	//�α����� �õ����� �� DB���� �˻��ؼ� ������ ���� �ִ��� üũ����
	//������ true, ������ false����
	public String chkUserData(String userNo, String password) {
		String sql = "select username from login where userno = ? and userpw = ?;";
		String encPassword = PasswordEncryption.pwEncryption(password);
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			pstmt.setString(2, encPassword);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {	//�˻��ߴµ� ���� ���� ����� �Ǿ��ٸ� ������ �´ٴ� ���̴� �̸��� ������
				return rs.getString("username");
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
		return null;
	}
	
	//Ȯ�� ������ ������ ���� �����ͺ��̽����� ����� ������ �˻��ϰ� ����� ������
	public UserData chkUserNameMail(String userName, String userMail) {
		String sql = "select userno, username, userpw, usermail from login where username = ? and usermail = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userMail);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {	//�˻��ߴµ� ���� ���� ����� �Ǿ��ٸ� ������ �´ٴ� ���̴� true�� ������
				String decPassword = PasswordEncryption.pwDecryption(rs.getString("userpw"));
				ud = new UserData(rs.getString("userno"), rs.getString("username"), decPassword, rs.getString("usermail"));
				return ud;
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
		return null;
	}
	
	//����ڹ�ȣ�� �ߺ����� �����ͺ��̽����� Ȯ���ϴ� �޼���
	public boolean chkUserNo(String userNo) {
		String sql = "select userno from login where userno = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {	//���� db�� �˻��ߴµ� ����� ���Դٸ�(�ߺ��� �����ȣ�� �����Ѵٴ� �ǹ�)
				return true;
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
		return false;
	}
	
	//����ڹ�ȣ�� �ߺ����� �����ͺ��̽����� Ȯ���ϴ� �޼���
	public boolean chkUserMail(String userMail) {
		String sql = "select userno from login where usermail = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userMail);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {	//���� db�� �˻��ߴµ� ����� ���Դٸ�(�ߺ��� �̸����� �����Ѵٴ� �ǹ�)
				return true;
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
		return false;
	}
	
	//�����ͺ��̽��� ����� ������ ����ϴ� �޼���
	public void insertUserData(User user) {
		String sql = "insert into login values (?, ?, ?, ?, ?, ?, ?, null, 0);";
		String encPassword = PasswordEncryption.pwEncryption(user.getPassword());
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserNo());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, encPassword);
			pstmt.setString(4, user.getUserMail());
			pstmt.setString(5, user.getUserTel());
			pstmt.setString(6, user.getImgPath());
			pstmt.setString(7, user.getDept());
			pstmt.executeUpdate();
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
	
	//�μ� ����Ʈ �������� �޼���
	public void loadDept(ComboBox<String> dept, ObservableList<String> list) {
		String sql = "select deptname from dept;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("deptname"));
			}
			dept.setItems(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
