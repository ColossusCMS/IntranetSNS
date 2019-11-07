package ClassPackage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : Ŭ���� ��Ű��
��� ���� : 1.1.2
Ŭ���� �̸� : UserData
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ��� Java����
- UserData.java (���� ã�⿡�� ����ϴ� ����� ���� Ŭ����[����ڹ�ȣ, �̸�, �̸���, ��й�ȣ])

�ʿ� import ����� ���� package
- Dao.LoginDao (�α��� ������ ������ ���̽��� ó���� �� �ִ� �޼���)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- 

��� ���� ���� ����
1.1.0
- DAO �ν��Ͻ��� �ʿ�ÿ��� ������ ������ �̵� ���� �ε� �ð��� ����.
- ����� ���â���� �̸��� �ߺ�üũ ��ư �߰� �� �̸��� �ߺ�üũ �׼� �߰�
- LoginDao Ŭ������ �̸��� üũ�ϴ� �޼��� �߰�
- ���� �� �޼��� �̸� ����ȭ

1.1.1
- Dao �ν��Ͻ� ���� (������ ���̽� �ʱ�ȭ Ŭ���� ����)
- �޺��ڽ��� ������ �����ͺ��̽��� ����

1.1.2
- ����� ����, ���� �޽�����, ������Ȯ�� ���� �߰�
 */

//�Խù��� ����ϰų� �Խù� ����, ����, ���� �ÿ� ����ϴ� Ŭ����
public class Board {
	private Integer boardNo;
	private String boardHeader;
	private String boardTitle;
	private String boardContent;
	private String boardPassword;
	private String boardUserNo;
	private String boardDate;
	private String boardFile;
	private Integer boardAvailable;
	
	public Board(Integer boardNo, String boardHeader, String boardTitle, String boardContent, String boardPassword,
			String boardUserNo, String boardDate, String boardFile, Integer boardAvailable) {
		this.boardNo = boardNo;
		this.boardHeader = boardHeader;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardPassword = boardPassword;
		this.boardUserNo = boardUserNo;
		this.boardDate = boardDate;
		this.boardFile = boardFile;
		this.boardAvailable = boardAvailable;
	}
	public Integer getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(Integer boardNo) {
		this.boardNo = boardNo;
	}
	public String getBoardHeader() {
		return boardHeader;
	}
	public void setBoardHeader(String boardHeader) {
		this.boardHeader = boardHeader;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardPassword() {
		return boardPassword;
	}
	public void setBoardPassword(String boardPassword) {
		this.boardPassword = boardPassword;
	}
	public String getBoardUserNo() {
		return boardUserNo;
	}
	public void setBoardUserNo(String boardUserNo) {
		this.boardUserNo = boardUserNo;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public String getBoardFile() {
		return boardFile;
	}
	public void setBoardFile(String boardFile) {
		this.boardFile = boardFile;
	}
	public Integer getBoardAvailable() {
		return boardAvailable;
	}
	public void setBoardAvailable(Integer boardAvailable) {
		this.boardAvailable = boardAvailable;
	}
}
