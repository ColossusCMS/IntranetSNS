package ClassPackage;

import javafx.beans.property.SimpleStringProperty;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ClassPackage
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : BoardTableView
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �˸� ���� ���̺� �Խ��� ����� ����ϴµ� ����ϴ� Ŭ����
- SimpleStringProperty�� �̿��� �Ӽ��� ���̺� ���� ������ ����

��Ű�� ���� ���� ����
 */

//���̺�� ����
//���̺�信�� ����Ʈ�� ���� ����ϱ� ���ؼ�
//SimpleStringProperty�� �����.
public class BoardTableView {
	private SimpleStringProperty boardNo;
	private SimpleStringProperty boardHeader;
	private SimpleStringProperty boardTitle;
	private SimpleStringProperty boardWriter;
	private SimpleStringProperty boardDate;
	
	public BoardTableView(String boardNo, String boardHeader, String boardTitle, String boardWriter, String boardDate) {
		this.boardNo = new SimpleStringProperty(boardNo);
		this.boardHeader = new SimpleStringProperty(boardHeader);
		this.boardTitle = new SimpleStringProperty(boardTitle);
		this.boardWriter = new SimpleStringProperty(boardWriter);
		this.boardDate = new SimpleStringProperty(boardDate);
	}
	public String getBoardNo() {
		return boardNo.get();
	}
	public void setBoardNo(String boardNo) {
		this.boardNo.set(boardNo);
	}
	public String getBoardHeader() {
		return boardHeader.get();
	}
	public void setBoardHeader(String boardHeader) {
		this.boardHeader.set(boardHeader);
	}
	public String getBoardTitle() {
		return boardTitle.get();
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle.set(boardTitle);
	}
	public String getBoardWriter() {
		return boardWriter.get();
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter.set(boardWriter);
	}
	public String getBoardDate() {
		return boardDate.get();
	}
	public void setBoardDate(String boardDate) {
		this.boardDate.set(boardDate);
	}
}
