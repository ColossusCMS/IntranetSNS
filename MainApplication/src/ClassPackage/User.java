package ClassPackage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : �α���
��� ���� : 1.1.2
Ŭ���� �̸� : User
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ� ��� Java����
- LoginMain.java (�α��� ȭ���� ����Ǵ� ���� Ŭ����)
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
- Dao.LoginDao (�α��� ������ ������ ���̽��� ó���� �� �ִ� �޼���)
- EncryptionDecryption.PasswordEncryption (��й�ȣ�� ��ȣȭ�ϰ� ��ȣȭ�ϴ� �޼��带 �����ϰ� ����)
- ChkDialogModule.ChkDialogMain (�ȳ��� ����� ���� �ӽ� ���̾�α׸� �����ϴ� ��Ű��)
- SendMail.SendMail (���� ������ �޼��带 �����ϰ� ����)

�ش� Ŭ���� �ֿ� ���
- ����� ��� â���� �Է¹��� ��� ������ �ϳ��� Ŭ������ ����� �����ͺ��̽��� ������ �� ���

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
public class User {
	private String userNo;
	private String userName;
	private String userPassword;
	private String userMail;
	private String userTel;
	private String userImgPath;
	private String userDept;
	private String userPosition;
	private String userStatusMsg;
	private Integer adminAvailable;
	
	public User() {}
	
	public User(String userNo, String userName, String userPassword, String userMail, String userTel, String userImgPath, String userDept,
			String userPosition, String userStatusMsg, Integer adminAvailable) {
		this.userNo = userNo;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userTel = userTel;
		this.userImgPath = userImgPath;
		this.userDept = userDept;
		this.userPosition = userPosition;
		this.userStatusMsg = userStatusMsg;
		this.adminAvailable = adminAvailable;
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
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPosition() {
		return userPosition;
	}
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
	public String getUserStatusMsg() {
		return userStatusMsg;
	}
	public void setUserStatusMsg(String userStatusMsg) {
		this.userStatusMsg = userStatusMsg;
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
	public String getUserImgPath() {
		return userImgPath;
	}
	public void setUserImgPath(String userImgPath) {
		this.userImgPath = userImgPath;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	public Integer getAdminAvailable() {
		return adminAvailable;
	}
	public void setAdminAvailable(Integer adminAvailable) {
		this.adminAvailable = adminAvailable;
	}
}
