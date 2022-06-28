package wellfernandes.com.github.view;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ConnectionOkView {
	public ConnectionOkView(){
		JOptionPane.showMessageDialog(null, "Successfully connected!","Connection Status",JOptionPane.OK_OPTION,
		new ImageIcon(this.getClass().getResource("/wellfernandes/com/github/images/connectedSerial.png")));
	}
}
