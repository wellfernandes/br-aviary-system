package wellfernandes.com.github.controller;

import java.util.Scanner;

import javax.swing.JComboBox;

import com.fazecast.jSerialComm.SerialPort;

import wellfernandes.com.github.bean.TemperatureBEAN;
import wellfernandes.com.github.view.ConnectionErrorView;
import wellfernandes.com.github.view.ConnectionOkView;

public class SerialConnectionControl {

	private static SerialPort serialPort;
	private static JComboBox<String> portList = new JComboBox<String>();
	private Thread connectionThread;

	private float arduinoTemperature = 0;
	private String receivedData = null;
	private TemperatureBEAN temperaturaBean;

	// Receive serial read data
	private Scanner scanner;

	public SerialConnectionControl() {

	}

	// connect automation
	public boolean connectAutomation() {

		serialPort = SerialPort.getCommPort(getPortList().getSelectedItem().toString());
		serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

		// connect to serial port - thread dedicated to serial connection
		connectionThread = new Thread() {
			public void run() {
				if (serialPort.openPort()) {
					System.out.println("Connected automation.");
					System.out.println("Port used for connection: " + portList.getSelectedItem().toString());
					new ConnectionOkView();
				} else {
					System.err.println("-> Failed to connect automation <-");
					System.out.println("Port used " + portList.getSelectedItem().toString() + " not allowed!");
					new ConnectionErrorView();
				}
			}
		};
		connectionThread.start();
		return true;
	}

	// disconnect automation
	public void desconnectAutomation() {
		serialPort.closePort();
		serialPort = null;
		receivedData = null;
		arduinoTemperature = 0;
		connectionThread.interrupt();
		System.out.println("Automation disconnected.");
	}

	// Validate connections if ports are available
	public JComboBox<String> validateAvailablePorts() {
		SerialPort[] portNames = SerialPort.getCommPorts();
		if (portNames.length != 0) {
			for (int i = 0; i < portNames.length; i++) {
				portList.addItem(portNames[i].getSystemPortName());
			}
			return portList;
		} else {
			System.err.println("No serial port available.");
			return null;
		}
	}

	public void receiveArduinoData() {
		scanner = new Scanner(serialPort.getInputStream());
		while (scanner.hasNextLine()) {
			try {
				receivedData = scanner.nextLine();
				arduinoTemperature = Float.parseFloat(receivedData);
				System.out.println("Ok, getting the automation data." + arduinoTemperature);
			} catch (Exception e) {
				System.err.println("Unable to receive automation data.");
				break;
			}
		}
		scanner.close();
	}

	public static SerialPort getSerialPort() {
		return serialPort;
	}

	public static void setSerialPort(SerialPort serialPort) {
		SerialConnectionControl.serialPort = serialPort;
	}

	public static JComboBox<String> getPortList() {
		return portList;
	}

	public static void setPortList(JComboBox<String> portList) {
		SerialConnectionControl.portList = portList;
	}

	public Thread getConnectionThread() {
		return connectionThread;
	}

	public void setConnectionThread(Thread connectionThread) {
		this.connectionThread = connectionThread;
	}

	public float getArduinoTemperature() {
		return arduinoTemperature;
	}

	public void setArduinoTemperature(float arduinoTemperature) {
		this.arduinoTemperature = arduinoTemperature;
	}

	public String getReceivedData() {
		return receivedData;
	}

	public void setReceivedData(String receivedData) {
		this.receivedData = receivedData;
	}

	public TemperatureBEAN getTemperaturaBean() {
		return temperaturaBean;
	}

	public void setTemperaturaBean(TemperatureBEAN temperaturaBean) {
		this.temperaturaBean = temperaturaBean;
	}

}