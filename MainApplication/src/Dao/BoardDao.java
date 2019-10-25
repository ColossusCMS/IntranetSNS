package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ClassPackage.Board;
import ClassPackage.BoardTableView;
import InitializePackage.InitializeDao;
import javafx.collections.ObservableList;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �α��� �����ͺ��̽� Ŭ����
��� ���� : 0.7.0
Ŭ���� �̸� : BoardDao
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��ü Java����
- BoardDao.java (����� ���̺� �����ͺ��̽� ����, ������ �ҷ�����, ������ ���� ��)

�ʿ� import ����� ���� package
- InitializePackage.InitializeDao (������ ���̽� ���� �ʱ�ȭ)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- �����ͺ��̽��� ����
- �Խ��� ���̺��� �����ؼ� �Խù� ����Ʈ�� �Խù� ������ ��� �������� Ŭ����

���� ���� ����
1.0.0

 */
public class BoardDao {
	//�Խ��� ������ ���� �����ͺ��̽��� �����ϴ� �޼���
	public boolean insertBoardContent(Board board) {
		String sql = "insert into boardtbl values(null, ?, ?, ?, ?, ?, ?, ?, default);";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, board.getBoardHeader());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getBoardPassword());
			pstmt.setString(5, board.getBoardUserNo());
			pstmt.setString(6, board.getBoardDate());
			pstmt.setString(7, board.getBoardFile());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {                
                e.printStackTrace();
            }
        }
	}
	
	//�Խ��� ������ ������ �� ����ϴ� �޼���
	//��� ������ �ƴ� ������ �� �ִ� �κи� ������.
	//�Ӹ���, ����, ����, ����
	public boolean updateBoardContent(Board board) {
		String sql = "update boardtbl set boardheader = ?, boardtitle = ?, boardcontent = ?, boardfile = ? where boardno = ? and boarduserno = ? and boardpassword = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, board.getBoardHeader());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getBoardFile());
			pstmt.setString(5, board.getBoardNo() + "");
			pstmt.setString(6, board.getBoardUserNo() + "");
			pstmt.setString(7, board.getBoardPassword());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	//�Խ��ǿ��� �ش� �Խù��� �������� �� ����ϴ� �޼���
	//�Խ��� ���̺����� �ش� �Խù���ȣ�� �Խù��� �������� �޼���
	//�Ű������� �޴� ������ �����ͺ��̽����� pk�� �Խù���ȣ
	public Board loadAllBoardContent(String boardNo) {
		Board board = null;
		String sql = "select * from boardtbl where boardno = ?;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new Board(rs.getInt("boardno"), rs.getString("boardheader"), rs.getString("boardtitle"), rs.getString("boardcontent"),
						rs.getString("boardpassword"), rs.getString("boarduserno"), rs.getString("boarddate"), rs.getString("boardfile"), rs.getInt("boardavailable"));
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
		return board;
	}
	
	//�Խ��� ���̺��� ��� ����� ������. ��, boardAvailable�� 1�� �Խù��� ������
	//0�� �����Ǿ��ٴ� �ǹ�, 1�� �������� �ʾҴٴ� �ǹ�
	//�׸��� userTbl���� join�� �̿��� �ۼ��ڹ�ȣ�� �ۼ����� �̸����� ��ü
	public void loadAllBoardList(ObservableList<BoardTableView> list) {
		String sql = "select b.boardno, b.boardheader, b.boardtitle, u.username, b.boarddate from boardtbl b inner join usertbl u on b.boarduserno = u.userno where boardavailable = 1 order by b.boardno desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			list.clear();
			while(rs.next()) {
				list.add(new BoardTableView(rs.getString("b.boardno"), rs.getString("b.boardheader"), rs.getString("b.boardtitle"), rs.getString("u.username"), rs.getString("b.boarddate")));
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
	}
	
	//���͸����� ���� �Խ��� ����� �������� �޼���
	//�Ű������� �޴� boardHeader�� �Ӹ����� �μ��� ���͸��� ����
	public void loadFilteredBoardList(ObservableList<BoardTableView> list, String boardHeader) {
		String sql = "select b.boardno, b.boardheader, b.boardtitle, u.username, b.boarddate from boardtbl b inner join usertbl u on b.boarduserno = u.userno "
				+ "where boardheader = ? and boardavailable = 1 order by b.boardno desc;";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, boardHeader);
			ResultSet rs = pstmt.executeQuery();
			list.clear();
			while(rs.next()) {
				list.add(new BoardTableView(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
	}
	
	//�Խù��� �����ϴ� �޼���
	//������ �����ͺ��̽����� �����ϴ� ���� �ƴ϶�
	//available�� 1���� 0���� �ٲٴ� �� ��
	//����Ʈ������ ��Ÿ���� �ʾƵ� ������ �Խù��� �����ͺ��̽��� ��� �����־�� �Ѵٴ� ��Ģ�� ����.
	public void deleteBoardContent(String boardNo) {
		String sql = "update boardtbl set boardavailable = 0 where boardno = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = InitializeDao.conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}