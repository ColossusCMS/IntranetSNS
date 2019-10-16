package SendMail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	private final String id = "yaahqjp";
	private final String pw = "pmxiljjcqrqrlsqd";
	String to;
	String name;
	String userNo;
	String userPw;
	Properties props = null;
	
	public SendMail(String to, String name, String userNo, String userPw) {	//���� ���� �ּҿ� ����� �̸�
		this.to = to;
		this.name = name;
		this.userNo = userNo;
		this.userPw = userPw;
		
		//����smtp�� �����ϴ� �ڵ�
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		this.send();
	}
	
	public void send() {
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(id, pw);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.id));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.to));
			
			//����κ�
			message.setSubject("���̵�� ��й�ȣ �ȳ��Դϴ�.");
			
			//����κ�
			String bodyMsg = "�ȳ��ϼ���. 000��!\nSNS �������Դϴ�.\n�����Ͻ� �����ȣ�� ��й�ȣ �ȳ��Դϴ�.\n\n"
					+ "�����ȣ : " + userNo + "\n��й�ȣ : " + userPw + "\n\n���õ� ���� �Ϸ� �Ǽ���.\n�����մϴ�.";
			message.setText(bodyMsg);
			
			Transport.send(message);
			System.out.println("������ ���������� ���½��ϴ�.");
		} catch (MessagingException e) {
			System.out.println("����");
			e.printStackTrace();
		}
	}
}
