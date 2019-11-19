package SystemTray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.imageio.ImageIO;

import Dao.LoginDao;
import IdSaveLoadModule.IdSaveLoad;
import InitializePackage.DataProperties;
import javafx.application.Platform;
import javafx.stage.Stage;

/*
������Ʈ ���� : �系 SNS
���α׷� ���� : 1.0.0
��Ű�� �̸� : SystemTray
��Ű�� ���� : 1.0.0
Ŭ���� �̸� : SystemTrayMain
�ش� Ŭ���� �ۼ� : �ֹ���

�ش� Ŭ���� �ֿ� ���
- ���α׷��� �ý��� Ʈ���� ����� ������ Ŭ����

��Ű�� ���� ���� ����
 */
public class SystemTrayMain {
	private Stage stage; // �ý��� Ʈ���̸� ������ ���������� ������(���⼭�� ���� ��Ʈ�ѷ�)
	private boolean firstTime; // ������ ���� �� ���� ���� ���� ����
	private TrayIcon trayIcon;
	private String userNo; // �����ͺ��̽����� ����� ������ �����ϱ� ���ؼ� ���� ������

	public SystemTrayMain(Stage stage, boolean firstTime, String userNo) {
		this.stage = stage;
		this.firstTime = firstTime;
		this.userNo = userNo;
	}

	public void createTrayIcon() {
		// �ý��� Ʈ���̸� �����Ѵٸ�
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray(); // ���� ����ũž�� �ý��� Ʈ���� �ν��Ͻ��� ������
			Image image = null;
			try {
				// SFTP������ �ִ� Ʈ���̾������� ������
				URL url = new URL("http://" + DataProperties.getIpAddress() + ":"
						+ DataProperties.getPortNumber("HTTPServer") + "/images/TrayIconSample.png");
				image = ImageIO.read(url); // �������� ������ ��η� �̹��� ����
			} catch (Exception e) {
				e.printStackTrace();
			}
			stage.setOnCloseRequest(event -> hide(stage));

			// �� �κ��� �׼Ǹ����ʴ� �ý��� Ʈ������ �޴����� ������ ���� �׼Ǹ�����
			// ���α׷��� ������ ���� �׼Ǹ�����
			final ActionListener closeListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginDao loginDao = new LoginDao();
					loginDao.updateLoginStatus(userNo, "logout"); // ���α׷��� ���������� �����ͺ��̽����� �α׾ƿ� ���·� �ٲ�
					IdSaveLoad.resetUserId(); // ���Ϸ� ����Ǿ� �ִ� ������� ���̵� ����
					System.exit(0); // ���α׷� ���� ����
				}
			};

			// Ʈ���� ���¿��� �ٽ� â�� Ȱ��ȭ�ϴ� �׼Ǹ�����
			ActionListener showListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Platform.runLater(() -> {
						stage.show(); // â�� �ٽ� Ȱ��ȭ
					});
				}
			};

			// �ý��� Ʈ���� �޴� ����� �κ�
			PopupMenu popup = new PopupMenu();
			MenuItem showItem = new MenuItem("����");
			MenuItem closeItem = new MenuItem("���α׷� ����");
			showItem.addActionListener(showListener);
			closeItem.addActionListener(closeListener);
			popup.add(showItem);
			popup.addSeparator();
			popup.add(closeItem);

			// �� �κ��� Ʈ���� �������� �����Ű�� �κ�
			trayIcon = new TrayIcon(image, "�系SNS", popup);
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(showListener);
			try {
				tray.add(trayIcon);
			} catch (Exception e) {
			}
		}
	}

	// �ý��� Ʈ���� ���·� �ٲ� �� ��µǴ� �˾�
	public void showProgramIsMinimizedMsg() {
		// ���� �� ���� ��µǵ���
		if (firstTime) {
			trayIcon.displayMessage("���α׷��� �ּ�ȭ�߽��ϴ�.", "", MessageType.INFO);
			firstTime = false;
		}
	}

	// ���α׷��� �ݾ��� �� �޼���
	public void hide(final Stage stage) {
		Platform.runLater(() -> {
			// �ý��� Ʈ���̰� �����Ѵٸ� ���α׷� ���ᰡ �ƴ϶� â�� �ݰ� �ý��� Ʈ���� ���·� ����
			if (SystemTray.isSupported()) {
				stage.hide();
				showProgramIsMinimizedMsg();
			}
			// �ý��� Ʈ���̸� �������� �ʴ´ٸ� ���α׷� ����
			else {
				System.exit(0);
			}
		});
	}
}
