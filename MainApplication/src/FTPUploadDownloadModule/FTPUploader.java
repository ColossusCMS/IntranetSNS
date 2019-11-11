package FTPUploadDownloadModule;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import InitializePackage.DataProperties;

public class FTPUploader {
	//FTP������ ������ ���ε��ϴ� �޼���
	public static boolean uploadFTPServer(String ftpPath, String localPath, String file) {
		String ip = DataProperties.ipAddress("FTPServer");
		int port = DataProperties.portNumber("FTPServer");
		String id = DataProperties.idProfile("FTPServer");
		String pw = DataProperties.password("FTPServer");
		
		boolean isSuccess = false;
		FTPClient ftp = null;
		int reply;
		try {
			ftp = new FTPClient();
			ftp.setControlEncoding("UTF-8");
			ftp.connect(ip, port);
			reply = ftp.getReplyCode();
			
			if(!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.exit(1);
			}
			if(!ftp.login(id, pw)) {
				ftp.logout();
				throw new Exception("FTP���� �α��� ����");
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			
			//���⼭ ��ΰ� ����
			ftp.changeWorkingDirectory(ftpPath);
			
			String sourceFile = localPath + file;
			File uploadFile = new File(sourceFile);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(uploadFile);
				isSuccess = ftp.storeFile(file, fis);
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			} finally {
				if(fis != null) {
					try {
						fis.close();
					} catch (Exception e2) {}
				}
			}
			ftp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (Exception e2) {}
			}
		}
		return isSuccess;
	}
	
	//�Ű������� �޴°�
	//������ ������ image����, file���� ����
	//������ �����ϰ� ������ File ��ü
	public static String uploadFile(String type, File localFile) {
		String ftpFilesPath = new String();
		if(type.equals("image")) {
			ftpFilesPath = "uploadedfiles/images/";
		}
		else if(type.equals("file")) {
			ftpFilesPath = "uploadedfiles/files/";
		}
		String localPath = localFile.getParent();	//���ϸ��� ������ ��� + /�ʿ�
		String fileName = localFile.getName();		//���ϸ� ������
		uploadFTPServer("html/" + ftpFilesPath, localPath + "/", fileName);
		
		ftpFilesPath += blankReplace(fileName);
		return ftpFilesPath;//��ȯ�� �ּҸ� ��ȯ
	}
	
	//���� ���� ��ȯ�ϴ°�
	public static String blankReplace(String str) {
		return str.replaceAll(" ", "%20");
	}
}
