package FTPUpload;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		FTPUploader up = new FTPUploader();
		FTPDownloader down = new FTPDownloader();
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("�̴Ͼ�ó.png");
		list.add("������.mp3");
		
//		boolean re = up.sendFtpServer("112.175.184.69", 21, "yaahq", "q1w2e3r4!", "test", "c:\\MySNS\\", list);
		try {
			down.receiveFTPServer("112.175.184.69", 21, "yaahq", "q1w2e3r4!", "html/uploadedfiles/files/Convenient.jpg", "c:/MySNS/Convenient.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if(re) {
//			System.out.println("FTP ���ε� ����");
//		}
//		else {
//			System.out.println("FTP ���ε� ����");
//		}
	}
}
