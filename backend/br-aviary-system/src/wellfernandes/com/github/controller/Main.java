package wellfernandes.com.github.controller;

import wellfernandes.com.github.view.ConnectionErrorView;
import wellfernandes.com.github.view.MonitorView;

public class Main {
	public static void main(String[] args) {

		// check for available ports
		SerialConnectionControl controlePortas = new SerialConnectionControl();
		if (controlePortas.validateAvailablePorts() != null) {
			new MonitorView(SerialConnectionControl.getPortList());
		} else {
			new ConnectionErrorView();
		}
	}
}
