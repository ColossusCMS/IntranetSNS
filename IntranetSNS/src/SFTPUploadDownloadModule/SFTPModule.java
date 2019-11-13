package SFTPUploadDownloadModule;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : SFTPUploadDownloadModule
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : SFTPModule
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- SFTP������ ���ε�� �ٿ�ε带 ����

����� �ܺ� ���̺귯��
- jsch-0.1.55.jar (SFTP������ ����� �� �ִ� ���̺귯���� ����)

��Ű�� ���� ���� ����
 */

/*
Jsch�� ����� ����
���� ����ϰ� �ִ� SFTP������ ����������� SSH�� �̿��� ������.
�׷��⿡ �Ϲ� FTP�� �ƴ� SFTP�� ����ϰ� �Ǿ��� �̸� ���� �� �����ϴ� ���� Jsch�̴�.
 */
public class SFTPModule {
	String host, id, pw;
	int port;
	JSch jsch;
	Session session;
	Channel channel;
	ChannelSftp sftpChannel;
	
	//�ν��Ͻ� �����ÿ� ������ �ּ�, ��Ʈ, ���� �� �ʿ��� ������ �޾ƿ�
	public SFTPModule(String host, int port, String id, String pw) {
		this.host = host;
		this.port = port;
		this.id = id;
		this.pw = pw;
	}
	
	//���� ���� �κ�
	public void connect() throws JSchException {
		System.out.println("Connecting...." + this.host);
		jsch = new JSch();
		session = jsch.getSession(this.id, this.host, this.port);
		
		//������ ȣ��ƮŰ�� ����Ǿ��ų� ������ �� �� ���� ��� ������ �۾��� ����
		//yes : ���� �ź�, ask : Ű�� �߰����� �������� ����ڰ� ����, no : �׻� �� Ű�� ����.
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(this.pw);
		session.connect();
		channel = session.openChannel("sftp");
		channel.connect();
		sftpChannel = (ChannelSftp) channel;
	}
	
	//���� ���� ����
	public void disconnect() {
		if(session.isConnected()) {
			System.out.println("Disconnecting");
			sftpChannel.disconnect();
			channel.disconnect();
			session.disconnect();
		}
	}
	
	//���� ���ε�
	//�Ű����� : ���� ��ü ��� �� ���ϸ�, ���ε��� ������ ����
	//�����ϴ°� ���ϸ�.
	//���ϵ� ���� �����ͺ��̽��� �����ϴµ� ����
	public String upload(String fileName, String remote) throws Exception {
		FileInputStream fis = null;
		File file = null;
		connect();	//SFTP���� ����
		try {
			String remoteDir = new String();	//�������� ��θ� �����ϴ� ����
			switch(remote) {
			case "images":	//���ε� Ÿ���� �̹�����(������ �����̸�)
				remoteDir = "/home/pi/MySNS/www/images/";
				break;
			case "files":		//�ε� Ÿ���� �����̸�(÷�� �����̸�)
				remoteDir = "/home/pi/MySNS/www/files/";
				break;
			}
			sftpChannel.cd(remoteDir);	//���� ���� ä����ġ�� ������ ��η� ����
			file = new File(fileName);		//���� ���ۿ� ���� ���� ��ü ����
			fis = new FileInputStream(file);
			sftpChannel.put(fis, file.getName());	//�ش� ä�ο� ������ �����ϸ鼭 �ش� ���ϸ����� ����
			fis.close();
			System.out.println("���� ���ε� �Ϸ� - " + file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		disconnect();	//�Ϸ������� ���� ���� ����
		return file.getName();
	}
	
	//���� �ٿ�ε�
	//�Ű����� : ������ ����� ���ϸ�, �ٿ�ε���� ��ü ���
	public void download(String fileName, String saveFile) throws Exception {
		byte[] buffer = new byte[1024];
		BufferedInputStream bis;	//������ �޾ƿ� ����
		connect();	//���� ����
		try {
			String cdDir = "/home/pi/MySNS/www/files/";	//���� ���� ���
			sftpChannel.cd(cdDir);
			File file = new File(cdDir + fileName);	//������ ��ο� ���ϸ��� ������ ���� ��ü ����
			bis = new BufferedInputStream(sftpChannel.get(file.getName()));	//������ �޾ƿ�
			File newFile = new File(saveFile);	//�� ��ü�� ����� PC�� ����� ���� ��ü
			OutputStream out = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			//�� �κ��� ������ �����ͼ� �����ϴ� �κ�
			int readCnt;
			while((readCnt = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCnt);
			}
			bis.close();
			bos.flush();
			bos.close();
			System.out.println("���� �ٿ�ε� �Ϸ� - " + file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		disconnect();	//�Ϸ������� ���� ���� ����
	}
}