package br.com.procempa.modus.stationmonitor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.utils.SwingUtils;
import br.com.procempa.modus.utils.TimeUtils;

public class TimerScreen extends Window {

	private static final long serialVersionUID = 4672852034745355364L;

	private static TimerScreen screen;

	private JProgressBar timerBar;

	private JToolBar toolBar;

	private JButton terminateButton;

	private TimerThread timerThread;
	
	private int sessionTime = 30; 

	private TimerScreen() {
		super(null);
		setup();		
	}
	
	public static TimerScreen getInstance() {
		if (screen == null) {
			screen = new TimerScreen();
			screen.buildPanel();
		}
		return screen;
	}

	public static void splash() {
		getInstance().setVisible(true);
		if (!getInstance().isAlive()) {
			getInstance().startTimer(getInstance().getSessionTime());
		}
	}

	private void setup() {
		setAlwaysOnTop(true);
		setSize(200, 50);

		Dimension d = SwingUtils.getScreenDimension();
		setLocation((int) (d.getWidth() * 0.80), (int) (d.getWidth() * 0.03));
		toFront();
	}

	public void buildPanel() {
		initComponents();

		addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen(), e.getYOnScreen());
			}

			public void mouseMoved(MouseEvent e) {
			}

		});

		setLayout(new FlowLayout());
		add(timerBar);
		add(toolBar);
	}

	public void initComponents() {
		timerBar = new JProgressBar();
		timerBar.setValue(0);
		timerBar.setMaximum(getSessionTime() * 60);
		timerBar.setStringPainted(true);
		timerBar.setString(TimeUtils.formatTime(0));

		Action actionTerminate = new AbstractAction("") {
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				stopTimer();
				setVisible(false);
				BlockScreen.splash();
			}
		};

		terminateButton = new JButton();
		terminateButton.setAction(actionTerminate);
		terminateButton.setIcon(IconFactory.createDelete());
		terminateButton.setToolTipText("Encerrar estação");

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(terminateButton);
		add(toolBar);
	}

	public void startTimer(int minutes) {
		timerThread = new TimerThread(minutes);
		timerThread.start();
		setVisible(true);
	}

	public void stopTimer() {
		if (timerThread != null) {
			timerThread.interrupt();
		}
		setVisible(false);
	}
	
	public boolean isAlive() {
		return timerThread != null && timerThread.isAlive();
	}
	
	public int getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(int sessionTime) {
		this.sessionTime = sessionTime;
	}	
	
	class TimerThread extends Thread {
		
		private int minutes; 
		
		public TimerThread(int minutes) {
			this.minutes = minutes;
		}
		
		private boolean isInterrupted;
		
		public void run() {
			int interval;
			int time = 60 * this.minutes;
			for (int i = time; i > 0; i--) {
				
				interval = i > 60 * 5 ? 60 : i > 60 * 2 ? 10 : 1; 
				
				if ((i % interval) == 0) {
					timerBar.setValue(time - i);
					timerBar.setString(TimeUtils.formatTime(i * 1000));
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				
				if (isInterrupted) {
					break;
				}
			}
			setVisible(false);
			BlockScreen.splash();
		}

		@Override
		public void interrupt() {
			isInterrupted = true;
		}
	}
}