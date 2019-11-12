package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ClassPackage.User;
import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : Dao
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : UserInfoDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �����ͺ��̽��� ����
- ��ϵ� ����ڵ��� �������� ���� Ŭ����
- ����� ���, �� ���� ��� ������ ����ϱ� ���� ���

��Ű�� ���� ���� ����
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
	public void loadAllUser(String side, ObservableList<User> userList, String userId) {
		String sql = new String();
		if(side.equals("right")) {
			sql = "select * from usertbl where userno not in (?) and userLoginStatus = 1;";
		}
		else if(side.equals("center")) {
			sql = "select * from usertbl where userno not in (?);";
		}
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
	public void loadAllUser(String side, ObservableList<User> userList, String userId, String filterText) {
		String sql = new String();
		if(side.equals("right")) {
			sql = "select * from usertbl where userno not in (?) and username = ? and userLoginStatus = 1;";
		}
		else if(side.equals("center")) {
			sql = "select * from usertbl where userno not in (?) and username = ?;";
		}
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
	public void loadFilteredAllUser(String side, ObservableList<User> userList, String userId, String dept) {
		String sql = new String();
		if(side.equals("right")) {
			sql = "select * from usertbl where userno not in (?) and userdept = ? and userLoginStatus = 1;";
		}
		else if(side.equals("center")) {
			sql =  "select * from usertbl where userno not in (?) and userdept = ?;";
		}
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
	public void loadFilteredAllUser(String side, ObservableList<User> userList, String userId, String dept, String filterText) {
		String sql = new String();
		if(side.equals("right")) {
			sql = "select * from usertbl where userno not in (?) and userdept = ? and username = ? and userLoginStatus = 1;";
		}
		else if(side.equals("center")) {
			sql = "select * from usertbl where userno not in (?) and userdept = ? and username = ?;";
		}
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
	public void loadFilteredAllUser(String side, String userId, ObservableList<User> userList, String filterText) {
		String sql = new String();
		if(side.equals("right")) {
			sql = "select * from usertbl where userno not in (?) and username = ? and userLoginStatus = 1;";
		}
		else if(side.equals("center")) {
			sql = "select * from usertbl where userno not in (?) and username = ?;";
		}
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
	
	public void updateStatusMsg(String statusMsg, String userId) {
		String sql = "update usertbl set userstatusmsg = ? where userno = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, statusMsg);
			pstmt.setString(2, userId);
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
