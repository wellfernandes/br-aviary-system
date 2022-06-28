package wellfernandes.com.github.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame settingsView;
	private JPanel panelSettings;
	private ImageIcon systemIcon;
	private String nameRelay01 = null;
	private String nameRelay02 = null;
	private String nameRelay03 = null;
	private String nameRelay04 = null;

	public SettingsView() {
		settingsView = new JFrame("System Settings");
		panelSettings = new JPanel();
		systemIcon = new ImageIcon("");

	}

	public void changeEquipmentName(String rele, String nomeEquipamento) {
		if (rele.equals("relay01")) {
			nameRelay01 = nomeEquipamento;
		} else if (rele.equals("relay02")) {
			nameRelay02 = nomeEquipamento;
		} else if (rele.equals("relay03")) {
			nameRelay03 = nomeEquipamento;
		} else if (rele.equals("relay04")) {
			nameRelay04 = nomeEquipamento;
		} else {
			this.nameRelay01 = "Not identified.";
			this.nameRelay02 = "Not identified.";
			this.nameRelay03 = "Not identified.";
			this.nameRelay04 = "Not identified.";
		}
	}

	public String getnameRelay01() {
		return nameRelay01;
	}

	public void setnameRelay01(String nameRelay01) {
		this.nameRelay01 = nameRelay01;
	}

	public String getnameRelay02() {
		return nameRelay02;
	}

	public void setnameRelay02(String nameRelay02) {
		this.nameRelay02 = nameRelay02;
	}

	public String getnameRelay03() {
		return nameRelay03;
	}

	public void setnameRelay03(String nameRelay03) {
		this.nameRelay03 = nameRelay03;
	}

	public String getnameRelay04() {
		return nameRelay04;
	}

	public void setnameRelay04(String nameRelay04) {
		this.nameRelay04 = nameRelay04;
	}

	public JFrame getSettingsView() {
		return settingsView;
	}

	public void setSettingsView(JFrame settingsView) {
		this.settingsView = settingsView;
	}

	public JPanel getPanelSettings() {
		return panelSettings;
	}

	public void setPanelSettings(JPanel panelSettings) {
		this.panelSettings = panelSettings;
	}

	public ImageIcon getSystemIcon() {
		return systemIcon;
	}

	public void setSystemIcon(ImageIcon systemIcon) {
		this.systemIcon = systemIcon;
	}
}
