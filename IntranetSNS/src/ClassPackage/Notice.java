package ClassPackage;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : ClassPackage
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : Notice
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- �������� ���̾�α׸� ����ϴµ� �ʿ��� ������ ��Ƶδ� Ŭ����

��Ű�� ���� ���� ����
 */
public class Notice {
	private String noticeClass;
	private String noticeTitle;
	private String noticeContent;
	
	public Notice(String noticeClass, String noticeTitle, String noticeContent) {		
		this.noticeClass = noticeClass;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
	}

	public String getNoticeClass() {
		return noticeClass;
	}

	public void setNoticeClass(String noticeClass) {
		this.noticeClass = noticeClass;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
}
