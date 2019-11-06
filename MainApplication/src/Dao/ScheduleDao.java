package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ClassPackage.DayOff;
import ClassPackage.Reg;
import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;

public class ScheduleDao {
	//���� ������ �������� �޼���
	public void loadPrivateSchedule(ObservableList<Reg> list, String userNo, String date) {
		String sql = "select * from testschedule where schuserno = ? and schgroup = 1 and schentrydate = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			pstmt.setString(2, date);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Reg(rs.getString("schuserno"), rs.getString("schtitle"), rs.getString("schcontent"), rs.getString("schentrydate"), rs.getString("schgroup")));
			}
		} catch (Exception e) {
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
	}
	
	//��ü ������ �����ö��� ������ ��ȣ�� ������� �μ� ��ȣ�� ��Ī�ؼ� ������
	public void loadGroupSchedule(ObservableList<Reg> list, String userNo, String date) {
		String sql = "select * from testschedule where schuserno = 9999 and schgroup in (10, 0) and schentrydate = ?;";
		PreparedStatement pstmt = null;
		try {
			list.clear();
			pstmt = InitializeDao.conn.prepareStatement(sql);
//			pstmt.setString(1, userNo);
//			pstmt.setString(2, date);
			pstmt.setString(1, date);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Reg(rs.getString("schuserno"), rs.getString("schtitle"), rs.getString("schcontent"), rs.getString("schentrydate"), rs.getString("schgroup")));
			}
		} catch (Exception e) {
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
	}
	
	//���� ���� ����ϴ� �޼���
	public boolean entrySchedule(Reg reg) {
		String sql = "insert into testschedule values(null, ?, ?, ?, ?, default);";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, reg.getSchUserNo());
			pstmt.setString(2, reg.getSchTitle());
			pstmt.setString(3, reg.getSchContent());
			pstmt.setString(4, reg.getSchEntryDate());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
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
		return false;
	}
	
	//���� ���� �����ϴ� �޼���
	public boolean updateSchedule(Reg reg) {
		String sql = "update testschedule set schtitle = ?, schcontent = ? where schuserno = ? and schentrydate = ? and schgroup = 1;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, reg.getSchTitle());
			pstmt.setString(2, reg.getSchContent());
			pstmt.setString(3, reg.getSchUserNo());
			pstmt.setString(4, reg.getSchEntryDate());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
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
		return false;
	}
	
	public boolean deleteSchedule(Reg reg) {
		String sql = "delete from testschedule where schuserno = ? and schtitle = ? and schcontent = ? and schentrydate = ? and schgroup = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, reg.getSchUserNo());
			pstmt.setString(2, reg.getSchTitle());
			pstmt.setString(3, reg.getSchContent());
			pstmt.setString(4, reg.getSchEntryDate());
			pstmt.setString(5, reg.getSchGroup());
			pstmt.executeUpdate();
		} catch (Exception e) {
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
		return false;
	}
	
	//�ش� �޿� ������ ��ϵ� ��¥�� �������� �޼���
	public void entryDate(int year, int month, ArrayList<String> privateList, ArrayList<String> groupList) {
		privateList.clear();
		groupList.clear();
		String sql = "select distinct day(schentrydate), schgroup from testschedule where substring(schentrydate, 1, 5) = ? and substring(schentrydate, 6, 8) = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getString("schgroup").equals("1")) {
					privateList.add(rs.getString(1));
				}
				else {
					groupList.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
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
	}
	
	public void entryDayOff(DayOff dayoff) {
		String sql = "insert into dayofftbl values(null, ?, ?, ?, ?);";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, dayoff.getDoUserNo());
			pstmt.setString(2, dayoff.getDoStart());
			pstmt.setString(3, dayoff.getDoEnd());
			pstmt.setString(4, dayoff.getDoContent());
			pstmt.executeUpdate();
		} catch (Exception e) {
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
	}
}