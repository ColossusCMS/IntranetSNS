package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ClassPackage.Notice;
import ClassPackage.NoticeTableView;
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
public class NoticeDao {
	Notice notice;
	
	//�������� ��ü�� �������� �޼���
	public void getAllNotice(ObservableList<NoticeTableView> list) {
		String sql = "select * from noticetbl where noticeavailable = 1 order by noticeno desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new NoticeTableView(rs.getInt("noticeno"), rs.getString("noticeclass"), rs.getString("noticetitle")));
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
	
	//�˸� ��Ͽ��� ���������� �������� �� �ش� ���������� ������ �������� �޼���
	public Notice getSelectedNotice(String noticeNo) {
		String sql = "select * from noticetbl where noticeno = ? and noticeavailable = 1;";
		Notice notice = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, noticeNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				notice = new Notice(rs.getString("noticeclass"), rs.getString("noticetitle"), rs.getString("noticecontent"));
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
		return notice;
	}
	
	//�������� �� ���� �ֱ��� ���������� �������� �޼���
	public Notice getMainNotice() {
		Notice notice = null;
		String sql = "select * from noticetbl where noticeclass = '����' and noticeavailable = 1 order by noticeno desc limit 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				notice = new Notice(rs.getString("noticeclass"), rs.getString("noticetitle"), rs.getString("noticecontent"));
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
		return notice;
	}
	
	//�Խ����� ������ ������� �μ��� �Խù� �� ���� �ֽű��� �������� �޼���
	public void getRecentlyDeptBoard(ObservableList<NoticeTableView> list, String userno) {
		String sql = "select b.boardno, u.userdept, b.boardtitle from boardtbl b inner join usertbl u on b.boarduserno = u.userno\r\n" + 
				"where b.boardheader = (select d.deptname from depttbl d inner join usertbl u on u.userdept = d.deptname where u.userno = ?)" + 
				"and b.boardavailable = 1 order by b.boardno desc limit 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userno);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(new NoticeTableView(rs.getInt("boardno"), "�Խ���-" + rs.getString("userdept"), rs.getString("b.boardtitle")));
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
	
	//�Խ����� ������ ��ü�� �Խù� �� ���� �ֽű��� �������� �޼���
	public void getRecentlyBoard(ObservableList<NoticeTableView> list) {
		String sql = "select b.boardno, b.boardtitle, u.username from boardtbl b inner join usertbl u on b.boarduserno = u.userno where b.boardheader = '��ü'"
				+ "and b.boardavailable = 1 order by b.boardno desc limit 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(new NoticeTableView(rs.getInt("boardno"), "�Խ���-��ü", rs.getString("b.boardtitle")));
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
	
	//�������� ������ ���� ������ �������� �޼���
	public void getPrivateSchedule(ObservableList<NoticeTableView> list, String userNo) {
		String sql = "select schno, schtitle, schcontent from scheduletbl where schuserno = ? and schentrydate = date(now()) order by schentrydate desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new NoticeTableView(rs.getInt("schno"), "����-����", rs.getString("schtitle")));
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
	
	//�������� ������ ��ü ������ �������� �޼���
	public void getGroupSchedule(ObservableList<NoticeTableView> list, String userNo) {
		String sql = "select schno, schtitle, schcontent, schgroup from scheduletbl where (schgroup = ("
				+ "select d.deptno from depttbl d inner join usertbl u on u.userdept = d.deptname where u.userno = ?)"
				+ "or schgroup = 0) and schentrydate = date(now()) order by schentrydate desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String dept = new String();
				switch(rs.getInt("schgroup")) {
				case 0:
					dept = "��ü";
					break;
				case 10:
					dept = "����";
					break;
				case 20:
					dept = "��ȹ";
					break;
				case 30:
					dept = "�濵";
					break;
				case 40:
					dept = "�λ�";
					break;
				case 50:
					dept = "����";
					break;
				case 60:
					dept = "������";
					break;
				}
				list.add(new NoticeTableView(rs.getInt("schno"), "����-" + dept, rs.getString("schtitle")));
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
