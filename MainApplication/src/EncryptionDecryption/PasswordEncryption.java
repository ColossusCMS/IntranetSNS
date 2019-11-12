package EncryptionDecryption;

import java.io.UnsupportedEncodingException;
/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : EncryptionDecrypion
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : PasswordEncryption
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ��й�ȣ�� ��ȣȭ�ϰų� ��ȣȭ�ϴ� �޼��带 ����
 */
public class PasswordEncryption {
	//��й�ȣ�� ��ȣȭ�ϴ� �޼���
	public static String pwEncryption(String plainPw) {
		String cipherPw = new String();
		byte[] encByte;
		try {
			encByte = plainPw.getBytes("UTF-8");
			for(int i = 0; i < encByte.length / 2; i++) {
				byte tmp = 0;
				tmp = encByte[i];
				encByte[i] = encByte[encByte.length - (i + 1)];
				encByte[encByte.length - (i + 1)] = tmp;
				for(int j = 0; j < encByte.length; j++) {
					encByte[i] += 1;
				}
			}
			cipherPw = new String(encByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cipherPw;
	}
	
	//��й�ȣ�� ��ȣȭ�ϴ� �޼���
	public static String pwDecryption(String cipherPw) {
		String plainPw = new String();
		byte[] decByte;
		try {
			decByte = cipherPw.getBytes("UTF-8");
			for(int i = 0; i < decByte.length / 2; i++) {
				for(int j = 0; j < decByte.length; j++) {
					decByte[i] -= 1;
				}
				byte tmp = 0;
				tmp = decByte[i];
				decByte[i] = decByte[decByte.length - (i + 1)];
				decByte[decByte.length - (i + 1)] = tmp;
			}
			plainPw = new String(decByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return plainPw;
	}
}
