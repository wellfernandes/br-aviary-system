package wellfernandes.com.github.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import wellfernandes.com.github.bean.TemperatureBEAN;
import wellfernandes.com.github.controller.AutomationControl;
import wellfernandes.com.github.controller.SerialConnectionControl;

public class MonitorView extends JFrame implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private JFrame monitorFrame;
	private ImageIcon systemIcon;
	private JMenuBar menuBar;
	private JMenu settingsMenu;
	private JMenu menuProfile;
	private JMenu menuSistema;
	private JMenuItem itemMenuConfigEquipamentos;
	private JMenuItem itemMenuInformacoes;
	private JMenuItem itemMenuNewProfile;
	private JMenuItem itemMenuEditProfile;
	private JButton btnConnect;
	private JButton btnRelay01;
	private JButton btnRelay02;
	private JButton btnRelay03;
	private JButton btnRelay04;
	private JPanel panelButtons;
	private JPanel panelLabels;
	private JPanel panelTempMeter;
	private JLabel lblFooter;

	private TemperatureBEAN temperaturaBean = new TemperatureBEAN();
	private JComboBox<String> listReferencePorts;
	private DefaultValueDataset meterGraphTemp;

	private static SerialConnectionControl serialConnectionControl = new SerialConnectionControl();
	private static AutomationControl automationControl = new AutomationControl();

	// temperature meter
	private static JFreeChart meterTemperature(String s, String s1, ValueDataset meterValue, double d, double d1,
			double d2, int i) {
		DialPlot dialplot = new DialPlot();
		dialplot.setDataset(meterValue);
		dialplot.setDialFrame(new StandardDialFrame());
		dialplot.setBackground(new DialBackground());
		DialTextAnnotation dialtextannotation = new DialTextAnnotation(s1);
		dialtextannotation.setFont(new Font("Dialog", 1, 14));
		dialtextannotation.setRadius(0.69999999999999996D);
		dialplot.addLayer(dialtextannotation);
		DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
		dialplot.addLayer(dialvalueindicator);
		StandardDialScale standarddialscale = new StandardDialScale(d, d1, -120D, -300D, 10D, 4);
		standarddialscale.setMajorTickIncrement(d2);
		standarddialscale.setMinorTickCount(i);
		standarddialscale.setTickRadius(0.88D);
		standarddialscale.setTickLabelOffset(0.14999999999999999D);
		standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
		dialplot.addScale(0, standarddialscale);
		dialplot.addPointer(new org.jfree.chart.plot.dial.DialPointer.Pin());
		DialCap dialcap = new DialCap();
		dialplot.setCap(dialcap);
		return new JFreeChart(s, dialplot);
	}

	public MonitorView(JComboBox<String> listPorts) {
		// frame and buttons
		monitorFrame = new JFrame("Monitor");
		systemIcon = new ImageIcon("");
		menuBar = new JMenuBar();
		settingsMenu = new JMenu("settings");
		menuProfile = new JMenu("profile");
		menuSistema = new JMenu("system");
		itemMenuConfigEquipamentos = new JMenuItem("configure equipment");
		itemMenuNewProfile = new JMenuItem("new profile");
		itemMenuEditProfile = new JMenuItem("edit profile");
		itemMenuInformacoes = new JMenuItem("info");
		btnConnect = new JButton("connect");
		btnRelay01 = new JButton("1 off");
		btnRelay02 = new JButton("2 off");
		btnRelay03 = new JButton("3 off");
		btnRelay04 = new JButton("4 off");
		btnRelay01.setBackground(Color.RED);
		btnRelay02.setBackground(Color.RED);
		btnRelay03.setBackground(Color.RED);
		btnRelay04.setBackground(Color.RED);
		btnRelay01.setEnabled(false);
		btnRelay02.setEnabled(false);
		btnRelay03.setEnabled(false);
		btnRelay04.setEnabled(false);

		// labels
		lblFooter = new JLabel("br aviary system");

		// panels
		panelButtons = new JPanel();
		panelLabels = new JPanel();
		panelTempMeter = new JPanel();

		// constructor
		meterGraphTemp = new DefaultValueDataset(temperaturaBean.gettemperatureValue());
		JFreeChart jfreechartTemperatura = meterTemperature("Monitor", "Temperature °C", meterGraphTemp, 0D, 100D, 10D,
				9);
		DialPlot dialplot = (DialPlot) jfreechartTemperatura.getPlot();

		// final
		StandardDialRange rangeTemperaturaQuente = new StandardDialRange(40D, 100D, Color.red);
		rangeTemperaturaQuente.setInnerRadius(0.52000000000000002D);
		rangeTemperaturaQuente.setOuterRadius(0.55000000000000004D);
		dialplot.addLayer(rangeTemperaturaQuente);

		// average
		StandardDialRange rangeTemperaturaMedia = new StandardDialRange(20D, 40D, Color.orange);
		rangeTemperaturaMedia.setInnerRadius(0.52000000000000002D);
		rangeTemperaturaMedia.setOuterRadius(0.55000000000000004D);
		dialplot.addLayer(rangeTemperaturaMedia);

		// initial
		StandardDialRange rangeTemperaturaFria = new StandardDialRange(0D, 20D, Color.blue);
		rangeTemperaturaFria.setInnerRadius(0.52000000000000002D);
		rangeTemperaturaFria.setOuterRadius(0.55000000000000004D);
		dialplot.addLayer(rangeTemperaturaFria);

		GradientPaint gradientpaintTemperatura = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(),
				new Color(255, 255, 255));
		DialBackground dialbackground = new DialBackground(gradientpaintTemperatura);
		dialbackground
				.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		dialplot.setBackground(dialbackground);
		dialplot.removePointer(0);
		org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();

		// pointer color
		pointer.setFillPaint(Color.black);
		dialplot.addPointer(pointer);
		ChartPanel chartpanel = new ChartPanel(jfreechartTemperatura);
		chartpanel.setPreferredSize(new Dimension(350, 350)); // temperature meter dimensions

		monitorFrame.setIconImage(systemIcon.getImage());

		// settings menu
		settingsMenu.add(itemMenuConfigEquipamentos);

		// profile menu
		menuProfile.add(itemMenuNewProfile);
		menuProfile.add(itemMenuEditProfile);

		// system menu
		menuSistema.add(itemMenuInformacoes);

		// add menu bar
		menuBar.add(settingsMenu);
		menuBar.add(menuProfile);
		menuBar.add(menuSistema);

		// relay buttons Panel - control panel
		// panelButtons.setSize(200, 350);
		panelButtons.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(0, 10, 1, 0)));
		panelButtons.add(btnRelay01);
		panelButtons.add(btnRelay02);
		panelButtons.add(btnRelay03);
		panelButtons.add(btnRelay04);

		// object references listPorts object (to enable and disable menu)
		listReferencePorts = new JComboBox<String>();
		listReferencePorts = listPorts;
		panelButtons.add(listPorts);
		panelButtons.add(btnConnect);

		// panel temperature meter - main panel
		panelTempMeter.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(0, 10, 1, 0)));
		panelTempMeter.add(chartpanel, BorderLayout.NORTH);

		// panel labels buttons
		panelLabels.add(lblFooter, BorderLayout.CENTER);

		// frame Settings
		monitorFrame.setSize(600, 500);
		monitorFrame.setJMenuBar(menuBar);
		monitorFrame.setLayout(new BorderLayout());
		monitorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		monitorFrame.add(panelTempMeter, BorderLayout.NORTH);
		monitorFrame.add(panelButtons, BorderLayout.CENTER);
		monitorFrame.add(panelLabels, BorderLayout.SOUTH);
		monitorFrame.setResizable(true);
		monitorFrame.pack();
		monitorFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		monitorFrame.setLocationRelativeTo(null);
		monitorFrame.setVisible(true);

		// method for handling the menu buttons
		itemMenuConfigEquipamentos.addActionListener(new menuEvents());
		itemMenuNewProfile.addActionListener(new menuEvents());
		itemMenuEditProfile.addActionListener(new menuEvents());
		itemMenuInformacoes.addActionListener(new menuEvents());

		// button handling method
		btnConnect.addMouseListener(new buttonEvents());
		btnRelay01.addMouseListener(new buttonEvents());
		btnRelay02.addMouseListener(new buttonEvents());
		btnRelay03.addMouseListener(new buttonEvents());
		btnRelay04.addMouseListener(new buttonEvents());

	}

	private class menuEvents implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent menuItem) {
			if (menuItem.getSource() == itemMenuConfigEquipamentos) {
				JOptionPane.showMessageDialog(null, "Ok!");
			} else if (menuItem.getSource() == itemMenuNewProfile) {
				JOptionPane.showMessageDialog(null, "OK");
			} else if (menuItem.getSource() == itemMenuEditProfile) {
				JOptionPane.showMessageDialog(null, "Ok!");
			} else if (menuItem.getSource() == itemMenuInformacoes) {
				JOptionPane.showMessageDialog(null, "system info");
			}
		}
	}

	// inner class for handling buttons
	private class buttonEvents implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent btnClicked) {

			if (btnClicked.getSource().equals(btnConnect)) {
				if (btnConnect.getText().equalsIgnoreCase("connect")) {

					if (serialConnectionControl.connectAutomation()) {
						btnConnect.setText("disconnect");
						listReferencePorts.setEnabled(false);
						btnRelay01.setEnabled(true);
						btnRelay02.setEnabled(true);
						btnRelay03.setEnabled(true);
						btnRelay04.setEnabled(true);
					}
				} else if (btnClicked.getSource() == btnConnect
						&& btnConnect.getText().equalsIgnoreCase("disconnect")) {
					serialConnectionControl.desconnectAutomation();
					btnConnect.setText("connect");
					btnRelay01.setEnabled(false);
					btnRelay02.setEnabled(false);
					btnRelay03.setEnabled(false);
					btnRelay04.setEnabled(false);
					listReferencePorts.setEnabled(true);
				}
			}

			// relays
			if (btnClicked.getSource() == btnRelay01 && btnRelay01.getText().equalsIgnoreCase("1 Off")) {
				btnRelay01.setText("1 On");
				btnRelay01.setBackground(Color.GREEN);
				automationControl.relay01("turn on");
			} else if (btnClicked.getSource() == btnRelay01 && btnRelay01.getText().equalsIgnoreCase("1 On")) {
				btnRelay01.setText("1 Off");
				btnRelay01.setBackground(Color.RED);
				automationControl.relay01("turn off");
			}
			if (btnClicked.getSource() == btnRelay02 && btnRelay02.getText().equalsIgnoreCase("2 Off")) {
				btnRelay02.setText("2 On");
				btnRelay02.setBackground(Color.GREEN);
				automationControl.relay02("turn on");
			} else if (btnClicked.getSource() == btnRelay02 && btnRelay02.getText().equalsIgnoreCase("2 On")) {
				btnRelay02.setText("2 Off");
				btnRelay02.setBackground(Color.RED);
				automationControl.relay02("turn off");
			}
			if (btnClicked.getSource() == btnRelay03 && btnRelay03.getText().equalsIgnoreCase("3 Off")) {
				btnRelay03.setText("3 On");
				btnRelay03.setBackground(Color.GREEN);
				automationControl.relay03("turn on");
			} else if (btnClicked.getSource() == btnRelay03 && btnRelay03.getText().equalsIgnoreCase("3 On")) {
				btnRelay03.setText("3 Off");
				btnRelay03.setBackground(Color.RED);
				automationControl.relay03("turn off");
			}
			if (btnClicked.getSource() == btnRelay04 && btnRelay04.getText().equalsIgnoreCase("4 Off")) {
				btnRelay04.setText("4 On");
				btnRelay04.setBackground(Color.GREEN);
				automationControl.relay04("turn on");
			} else if (btnClicked.getSource() == btnRelay04 && btnRelay04.getText().equalsIgnoreCase("4 On")) {
				btnRelay04.setText("4 Off");
				btnRelay04.setBackground(Color.RED);
				automationControl.relay04("turn off");
			}

		}

		@Override
		public void mousePressed(MouseEvent btn) {
		}

		@Override
		public void mouseReleased(MouseEvent btn) {
		}

		@Override
		public void mouseEntered(MouseEvent btn) {
			if (btn.getSource() == btnConnect) {
				System.out.print("Connect to automation.");
			}
			if (btn.getSource() == btnRelay01 && btnRelay01.getText().equalsIgnoreCase("1 Off")) {
				System.out.print("turn on relay 01.");
			}
			if (btn.getSource() == btnRelay01 && btnRelay01.getText().equalsIgnoreCase("1 On")) {
				System.out.print("turn off relay 01.");
			}
		}

		@Override
		public void mouseExited(MouseEvent btn) {
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
	}
}
