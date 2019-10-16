package LoginModule;
/*
 * ������Ʈ ���� : ����� ���
 * ���� : 0.9.0
 * �ۼ� : �ֹ���
 * 
 * �ʿ� Java����
 * - Main.java (����� ����� ����Ǵ� ���� Ŭ����)
 * - SignUpDao.java (�����ͺ��̽� ����, ������ �ҷ�����, ������ ����)
 * - SignUpController.java (����� ���â ��Ʈ�ѷ�)
 * - User.java (����� ���� ������ ���� Ŭ����)
 * 
 * �ʿ� fxml���� :
 * - signUp.fxml (����� ���â fxml)
 * - chkDialog.fxml (�ȳ� ���̾�α� fxml)
 * 
 * �ֿ� ���
 * - ����� ����� ���� ����� ���� �Է� (�����ȣ, �̸�, ��й�ȣ, �̸���, ��ȭ��ȣ, ��ǥ����),
 * - �����ͺ��̽��� ������ �����ϱ� ���� �����ͺ��̽� ���� �� �����ͺ��̽� ����,
 * - �ߺ�üũ�� ���� �����ͺ��̽��κ��� ���� �о��
 * 
 * ���İ�ȹ : �̹����� �����ϴ� ��ɿ��� ������ �ƴ� ������ URL�� �̿��� ��� �����ϰ� ����� �߰�
 */
public class User {
	private String userNo;
	private String userName;
	private String password;
	private String userMail;
	private String userTel;
	private String imgPath;
	private String dept;
	
	public User() {}
	
	public User(String userNo, String userName, String password, String userMail, String userTel, String imgPath, String dept) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.password = password;
		this.userMail = userMail;
		this.userTel = userTel;
		this.imgPath = imgPath;
		this.dept = dept;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
}
