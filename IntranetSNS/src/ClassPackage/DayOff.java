package ClassPackage;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ClassPackage
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : DayOff
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �ް� ��û â���� ������ ���̽��� ������ �����ϱ� ���� ����ϴ� Ŭ����

��Ű�� ���� ���� ����
 */
public class DayOff {
	private String doUserNo;
	private String doStart;
	private String doEnd;
	private String doContent;

	public DayOff(String doUserNo, String doStart, String doEnd, String doContent) {
		this.doUserNo = doUserNo;
		this.doStart = doStart;
		this.doEnd = doEnd;
		this.doContent = doContent;
	}

	public String getDoUserNo() {
		return doUserNo;
	}

	public void setDoUserNo(String doUserNo) {
		this.doUserNo = doUserNo;
	}

	public String getDoStart() {
		return doStart;
	}

	public void setDoStart(String doStart) {
		this.doStart = doStart;
	}

	public String getDoEnd() {
		return doEnd;
	}

	public void setDoEnd(String doEnd) {
		this.doEnd = doEnd;
	}

	public String getDoContent() {
		return doContent;
	}

	public void setDoContent(String doContent) {
		this.doContent = doContent;
	}
}
