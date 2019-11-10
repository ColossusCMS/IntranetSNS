package FTPUploadDownloadModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPDownloader {
	public static boolean receiveFTPServer(String sourceFile, String targetFile) {
		String ip = "112.175.184.69";
		int port = 21;
		String id = "yaahq";
		String pw = "q1w2e3r4!";
		
		boolean result = false;
		FTPClient ftp = null;
		int reply = 0;
		FileOutputStream fos = null;
		try {
			ftp = new FTPClient();
			ftp.setControlEncoding("UTF-8");
			ftp.connect(ip, port);
			reply = ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			if(!ftp.login(id, pw)) {
				ftp.logout();
				return result;
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			fos = new FileOutputStream(new File(targetFile));
			result = ftp.retrieveFile(sourceFile, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(ftp != null && ftp.isConnected()) {
					try {
						ftp.disconnect();
					} catch (IOException e) {}
				}
				if(fos != null) {
					fos.close();
				}
				ftp.logout();
			} catch (Exception e) {}
		}
		return result;
	}
}