package ClassPackage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 0.7.0
��� �̸� : Ŭ���� ��Ű��
��� ���� : 1.1.2
Ŭ���� �̸� : Dept
�ش� Ŭ���� �ۼ� : �ֹ���

�ʿ��� Java����
- Dept.java

�ʿ� import ����� ���� package
- 

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
public class Dept {
	private String deptNo;
	private String deptName;
	
	public Dept(String deptNo, String deptName) {
		this.deptNo = deptNo;
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
