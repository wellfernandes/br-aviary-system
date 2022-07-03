package wellfernandes.com.github.controller;

import java.io.PrintWriter;

public class AutomationControl {

	private float currentTemperature = 0;
	private float maxTemp = 0;
	private float minTemp = 0;

	private String btnAction = null;

	public AutomationControl() {

	}

	// activation of relays
	public void relay01(String commandRelay) {
		if (commandRelay.equalsIgnoreCase("turn on")) {
			Thread threadEnviaDados = new Thread() {
				@Override
				public void run() {
					PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
					sendData.print("a");
					sendData.flush();
				}
			};
			threadEnviaDados.start();
		} else {
			PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
			sendData.print("b");
			sendData.flush();
		}
	}

	public void relay02(String commandRelay) {
		if (commandRelay.equalsIgnoreCase("turn on")) {
			Thread threadEnviaDados = new Thread() {
				@Override
				public void run() {
					PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
					sendData.print("c");
					sendData.flush();
				}
			};
			threadEnviaDados.start();
		} else {
			PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
			sendData.print("d");
			sendData.flush();
		}
	}

	public void relay03(String relayCommand) {
		if (relayCommand.equalsIgnoreCase("turn on")) {
			Thread threadSenData = new Thread() {
				@Override
				public void run() {
					PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
					sendData.print("e");
					sendData.flush();
				}
			};
			threadSenData.start();
		} else {
			PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
			sendData.print("f");
			sendData.flush();
		}
	}

	public void relay04(String relayCommand) {
		if (relayCommand.equalsIgnoreCase("turn on")) {
			Thread threadSenData = new Thread() {
				@Override
				public void run() {
					PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
					sendData.print("g");
					sendData.flush();
				}
			};
			threadSenData.start();
		} else {
			PrintWriter sendData = new PrintWriter(SerialConnectionControl.getSerialPort().getOutputStream());
			sendData.print("h");
			sendData.flush();
		}
	}

	public float getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(float currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public float getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}

	public float getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}

	public String getBtnAction() {
		return btnAction;
	}

	public void setBtnAction(String btnAction) {
		this.btnAction = btnAction;
	}
}
