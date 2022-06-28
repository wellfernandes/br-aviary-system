package wellfernandes.com.github.controller;

import java.io.PrintWriter;

import wellfernandes.com.github.bean.TemperatureBEAN;
import wellfernandes.com.github.dao.TemperatureDAO;

public class AutomationControl {

	private float maxTemp = 0;
	private float minTemp = 0;

	private String btnAction = null;
	private TemperatureDAO TemperatureDao;
	private TemperatureBEAN TemperatureBean;

	public AutomationControl() {

	}

	// collected temperature data
	public void equipmentControl(float TemperatureAtual) {

		System.out.println("Current Temperature: " + TemperatureAtual);
		System.out.println("Minimum temperature: " + minTemp);
		System.out.println("Maximum temperature: " + maxTemp);
		System.out.println("");
	}

	// activation of relays
	public void relay01(String comandoRele) {
		if (comandoRele.equalsIgnoreCase("turn on")) {
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
			sendData.flush();
		}
	}

	public void relay02(String comandoRele) {
		if (comandoRele.equalsIgnoreCase("turn on")) {
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

	public TemperatureDAO getTemperatureDao() {
		return TemperatureDao;
	}

	public void setTemperatureDao(TemperatureDAO temperatureDao) {
		TemperatureDao = temperatureDao;
	}

	public TemperatureBEAN getTemperatureBean() {
		return TemperatureBean;
	}

	public void setTemperatureBean(TemperatureBEAN temperatureBean) {
		TemperatureBean = temperatureBean;
	}
}
