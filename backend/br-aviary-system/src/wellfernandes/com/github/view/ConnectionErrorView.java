package wellfernandes.com.github.view;

import javax.swing.JOptionPane;

public class ConnectionErrorView {

	public ConnectionErrorView() {
		JOptionPane.showMessageDialog(null,
				"Serial communication not available.\n" + "Please, select the correct port to connect.", "", 0);
	}
}
