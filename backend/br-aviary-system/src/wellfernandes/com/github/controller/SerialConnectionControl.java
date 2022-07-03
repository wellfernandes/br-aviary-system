package wellfernandes.com.github.controller;

import java.util.Scanner;

import javax.swing.JComboBox;

import com.fazecast.jSerialComm.SerialPort;

import wellfernandes.com.github.view.ConnectionErrorView;
import wellfernandes.com.github.view.ConnectionOkView;

public class SerialConnectionControl {

	private static SerialPort serialPort;
	private static JComboBox<String> portList = new JComboBox<String>();

	private Thread connectionThread;
	private Thread threadReceivedData;

	private float arduinoTemperature;

	// Receive serial read data
	private static Scanner scanner;

	public SerialConnectionControl() {

	}

	// connect automation
	public boolean connectAutomation() {
		if (serialPort == null) {
			serialPort = SerialPort.getCommPort(getPortList().getSelectedItem().toString());
			serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

			// connect to serial port - thread dedicated to serial connection
			connectionThread = new Thread() {
				public void run() {
					if (serialPort.openPort()) {
						System.out.println("Connected automation.");
						System.out.println("Port used for connection: " + portList.getSelectedItem().toString());
						new ConnectionOkView();
						receiveArduinoData();
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
		return false;
	}

	// disconnect automation
	public void desconnectAutomation() {
		threadReceivedData.interrupt();
		serialPort.closePort();
		serialPort = null;
		arduinoTemperature = 0;

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

	// receive arduino data
	public void receiveArduinoData() {
		threadReceivedData = new Thread() {
			@Override
			public void run() {
				scanner = new Scanner(serialPort.getInputStream());
				while (true) {
					try {
						if (serialPort.isOpen()) {
							while (scanner.hasNext()) {
								String arduino = scanner.next();
								setArduinoTemperature(Float.parseFloat(arduino));
							}
							scanner.close();
						}
					} catch (Exception e) {
						System.err.println("Unable to receive data from automation.");
						break;
					}
				}
			}
		};
		threadReceivedData.start();
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
}