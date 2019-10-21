package IdSaveLoadModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;


//�α��ε� ������ ���α׷��� ����Ǵ� ���ȿ��� ��� ������ �ֱ� ���ؼ�
//�����ȣ�� txt���Ϸ� ������ ������ �����ص�.
//(�α׾ƿ��ϰų�) ���α׷��� ������ �����ϸ� txt������ ������ ���� ����.
public class IdSaveLoad {
	//�α����� �ϰ� ���� �α��ε� ����ڹ�ȣ�� ����ؼ� ������ �ֱ� ���� ������.
	public static void saveUserId(String id) {
//		String path = System.getProperty("user.home") + "/Documents/MySNS/id.txt";
//		String path = "e:/MySNS/id.txt";
		File filePath = new File("e:/MySNS/");
		File fileName = new File("e:/MySNS/id.txt");
		try {
			if(!filePath.exists()) {
				filePath.mkdirs();
			}
			if(!fileName.exists()) {
				fileName.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(fileName);
			Writer writer = new OutputStreamWriter(fos);
			writer.write(id);
			writer.flush();
			writer.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����ڹ�ȣ�� �ʿ��� ����̳� �޼��忡�� �� �޼��带 ȣ���ؼ� ���� �α��� ���� ������� ��ȣ��
	//�������� ������ �� ����.
	public static String loadUserId() {
//		String path = System.getProperty("user.home") + "/Documents/MySNS/id.txt";
		String path = "e:/MySNS/id.txt";
		String file = new String();
		FileReader fr = null;
		BufferedReader br = null;
		StringWriter sw = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			sw = new StringWriter();
			int ch = 0;
			while((ch = br.read()) != -1) {
				sw.write(ch);
			}
			br.close();
			file = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	//�α׾ƿ��̳� ���α׷��� ������ ������ ��� �����ߴ� ����ڹ�ȣ�� ����� txt������ �ʱ�ȭ��
	public static void resetUserId() {
		String path = System.getProperty("user.home") + "/My Documents/MySNS/id.txt";
		File file = new File(path);
		try {
			FileWriter fw = new FileWriter(file, false);
			fw.write("");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
