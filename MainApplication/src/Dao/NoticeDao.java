package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ClassPackage.Notice;
import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;

public class NoticeDao {
	Notice notice;
	
	//�������� ��ü�� �������� �޼���
	public void getAllNotice(ObservableList<Notice> list) {
		String sql = "select * from noticetbl where available = 1 order by noticeno desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
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
	public void getRecentlyBoard(ObservableList<Notice> list, String dept) {
		String sql = "select b.boardtitle, u.username from boardtbl b inner join usertbl u on b.boarduserno = u.userno where b.boardheader = ?"
				+ "and b.boardavailable = 1 order by b.boardno desc limit 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, dept);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(new Notice("�Խ���-" + dept, rs.getString("b.boardtitle"), rs.getString("u.username")));
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
	public void getRecentlyDeptBoard(ObservableList<Notice> list) {
		String sql = "select b.boardtitle, u.username from boardtbl b inner join usertbl u on b.boarduserno = u.userno where b.boardheader = '��ü'"
				+ "and b.boardavailable = 1 order by b.boardno desc limit 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				list.add(new Notice("�Խ���-��ü", rs.getString("b.boardtitle"), rs.getString("u.username")));
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
	public void getPrivateSchedule(ObservableList<Notice> list, String userNo) {
		String sql = "select schtitle, schcontent from testschedule where schuserno = ? and schentrydate = date(now()) order by schentrydate desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Notice("����-����", rs.getString("schtitle"), rs.getString("schcontent")));
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
	public void getGroupSchedule(ObservableList<Notice> list, String userNo) {
		String sql = "select schtitle, schcontent from testschedule where (schgroup = ("
				+ "select d.deptno from depttbl d inner join usertbl u on u.userdept = d.deptname where u.userno = ?)"
				+ "or schgroup = 0) and schentrydate = date(now()) order by schentrydate desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Notice("����-��ü", rs.getString("schtitle"), rs.getString("schcontent")));
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
