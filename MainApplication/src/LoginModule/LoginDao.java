package LoginModule;

import EncryptionDecryption.PasswordEncryption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
������Ʈ ���� : �系 SNS
��� �̸� : �α���
Ŭ���� �̸� : LoginDao
���� : 1.1.0
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- LoginMain.java (�α��� ȭ���� ����Ǵ� ���� Ŭ����)
- LoginDao.java (�����ͺ��̽� ����, ������ �ҷ�����, ������ ���� ��)
- LoginController.java (�α��� â ��Ʈ�ѷ�)
- SignUpController.java (����� ��� â ��Ʈ�ѷ�)
- FindAccountController.java (���� ã�� â ��Ʈ�ѷ�)
- User.java (����� ��Ͽ� ����ϴ� ����� ���� Ŭ����[������� ��� ������ ��� ����])
- UserData.java (���� ã�⿡�� ����ϴ� ����� ���� Ŭ����[����ڹ�ȣ, �̸�, �̸���, ��й�ȣ])

�ʿ� fxml����
- login.fxml (�α��� â fxml)
- signUp.fxml (����ڵ�� â fxml)
- findAccount.fxml (���� ã�� â fxml)

�ʿ� import ����� ���� package
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- �����ͺ��̽��� ����
- �α����� �õ��ϰų� �ߺ��� ����� ��ȣ üũ �Ǵ� �̸��� Ȯ���� ���� �����ͺ��̽����� �˻��� ����� ������
- ����� ��� â���� �Է��� �������� �����ͺ��̽��� ������ ����

���� ���� ����
1.1.0
- DAO �ν��Ͻ��� �ʿ�ÿ��� ������ ������ �̵� ���� �ε� �ð��� ����.
- ����� ���â���� �̸��� �ߺ�üũ ��ư �߰� �� �̸��� �ߺ�üũ �׼� �߰�
- LoginDao Ŭ������ �̸��� üũ�ϴ� �޼��� �߰�
- ���� �� �޼��� �̸� ����ȭ
 */
public class LoginDao {
	private Connection conn;
	private static final String USERNAME = "sample";
	private static final String PASSWORD = "9999";
	private static final String URL = "jdbc:mysql://125.185.21.163:3306/sampledb";
//	private static final String URL = "jdbc:mysql://192.168.219.14:3306/sampledb";
	
	UserData ud;
	
	public LoginDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("����̹� �ε� ����");
		} catch (Exception e) {
			System.out.println("����̹� �ε� ����");
			e.printStackTrace();
		}
	}
	
	//�α����� �õ����� �� DB���� �˻��ؼ� ������ ���� �ִ��� üũ����
	//������ true, ������ false����
	public String chkUserData(String userNo, String password) {
		String sql = "select username from login where userno = ? and userpw = ?;";
		String encPassword = PasswordEncryption.pwEncryption(password);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
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
			pstmt = conn.prepareStatement(sql);
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
			pstmt = conn.prepareStatement(sql);
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
			pstmt = conn.prepareStatement(sql);
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
			pstmt = conn.prepareStatement(sql);
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
}
