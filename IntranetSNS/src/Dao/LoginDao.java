package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ClassPackage.User;
import ClassPackage.UserData;
import EncryptionDecryption.PasswordEncryption;
import InitializePackage.InitializeDao;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : Dao
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : LoginDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �α����� �õ��ϰų� �ߺ��� ����� ��ȣ üũ �Ǵ� �̸��� Ȯ���� ���� �����ͺ��̽����� �˻��� ����� ������
- ����� ��� â���� �Է��� �������� �����ͺ��̽��� ����� ���̺� ����

����� �ܺ� ���̺귯��
- mysql-connector-java-5.1.47.jar

��Ű�� ���� ���� ����
 */
public class LoginDao {
	UserData userData;

	// �α����� �õ����� �� DB���� �˻��ؼ� ������ ���� �ִ��� üũ����
	// ������ true, ������ false����
	public String chkUserData(String userNo, String userPassword) {
		String sql = "select username from usertbl where userno = ? and userpassword = ?;";
		String encPassword = PasswordEncryption.pwEncryption(userPassword);
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			pstmt.setString(2, encPassword);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // �˻��ߴµ� ���� ���� ����� �Ǿ��ٸ� ������ �´ٴ� ���̴� �̸��� ������
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

	// Ȯ�� ������ ������ ���� �����ͺ��̽����� ����� ������ �˻��ϰ� ����� ������
	public UserData chkUserNameMail(String userName, String userMail) {
		String sql = "select userno, username, userpassword, usermail from usertbl where username = ? and usermail = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userMail);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // �˻��ߴµ� ���� ���� ����� �Ǿ��ٸ� ������ �´ٴ� ���̴� true�� ������
				String decPassword = PasswordEncryption.pwDecryption(rs.getString("userpassword"));
				userData = new UserData(rs.getString("userno"), rs.getString("username"), decPassword,
						rs.getString("usermail"));
				return userData;
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

	// ����ڹ�ȣ�� �ߺ����� �����ͺ��̽����� Ȯ���ϴ� �޼���
	public boolean chkUserNo(String userNo) {
		String sql = "select userno from usertbl where userno = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // ���� db�� �˻��ߴµ� ����� ���Դٸ�(�ߺ��� �����ȣ�� �����Ѵٴ� �ǹ�)
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

	// ����ڹ�ȣ�� �ߺ����� �����ͺ��̽����� Ȯ���ϴ� �޼���
	public boolean chkUserMail(String userMail) {
		String sql = "select userno from usertbl where usermail = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userMail);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // ���� db�� �˻��ߴµ� ����� ���Դٸ�(�ߺ��� �̸����� �����Ѵٴ� �ǹ�)
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

	// ��ȭ��ȣ �ߺ����� üũ�ϴ� �޼���
	public boolean chkUserTel(String userTel) {
		String sql = "select userno from usertbl where usertel = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userTel);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // ���� db�� �˻��ߴµ� ����� ���Դٸ�(�ߺ��� ��ȭ��ȣ�� �����Ѵٴ� �ǹ�)
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

	// �����ͺ��̽��� ����� ������ ����ϴ� �޼���
	public void insertUserData(User user) {
		String sql = "insert into usertbl values (?, ?, ?, ?, ?, ?, ?, default, ?, default, default);";
		String encPassword = PasswordEncryption.pwEncryption(user.getUserPassword());
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserNo());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, encPassword);
			pstmt.setString(4, user.getUserMail());
			pstmt.setString(5, user.getUserTel());
			pstmt.setString(6, user.getUserImgPath());
			pstmt.setString(7, user.getUserDept());
			pstmt.setString(8, user.getUserStatusMsg());
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

	// �ߺ� �α��� ������. userLoginStatus�� ������
	public int getLoginStatus(String userNo) {
		String sql = "select userloginstatus from usertbl where userno = ?;";
		int status = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				status = rs.getInt("userloginstatus");
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
		return status;
	}

	// �α����� �� �α��� ���¸� 1��, �α׾ƿ��� �� �α��� ���¸� 0���� ������Ʈ�ϴ� �޼���
	public void updateLoginStatus(String userNo, String status) {
		String sql = "update usertbl set userloginstatus = ? where userno = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			if (status.equals("login")) { // �α����ϴ� �Ŷ�� 0�� 1�� �ٲ�� ��
				pstmt.setInt(1, 1);
			} else if (status.equals("logout")) { // �α׾ƿ��ϴ� �Ŷ�� 1�� 0���� �ٲ�� ��
				pstmt.setInt(1, 0);
			}
			pstmt.setString(2, userNo);
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
}
