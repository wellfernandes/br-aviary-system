
package wellfernandes.com.github.model;

import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

import wellfernandes.com.github.bean.TemperatureBEAN;
import wellfernandes.com.github.controller.AutomationControl;

public class ReceiveDataArduino {

	private static AutomationControl automationControl = new AutomationControl();
	private static TemperatureBEAN temperaturaBean = new TemperatureBEAN();

	private float arduinoTemperature = 0;
	private String receivedData = null;

	public ReceiveDataArduino() {

	}

	// receive arduino data
	public String receiveArduinoData(SerialPort serialPort) {
		Thread threadRecebeDados = new Thread() {
			@Override
			public void run() {
				Scanner scanner = new Scanner(serialPort.getInputStream());
				while (scanner.hasNextLine()) {
					try {
						receivedData = scanner.nextLine();
						arduinoTemperature = Float.parseFloat(receivedData);

					} catch (Exception e) {
						System.err.println("Unable to receive data from automation.");
						break;
					}
				}
				scanner.close();
			}
		};
		threadRecebeDados.start();
		return receivedData;
	}

	public static AutomationControl getAutomationControl() {
		return automationControl;
	}

	public static void setAutomationControl(AutomationControl automationControl) {
		ReceiveDataArduino.automationControl = automationControl;
	}

	public static TemperatureBEAN getTemperaturaBean() {
		return temperaturaBean;
	}

	public static void setTemperaturaBean(TemperatureBEAN temperaturaBean) {
		ReceiveDataArduino.temperaturaBean = temperaturaBean;
	}

	public float getArduinoTemperature() {
		return arduinoTemperature;
	}

	public void setArduinoTemperature(float arduinoTemperature) {
		this.arduinoTemperature = arduinoTemperature;
	}

}
