package br.com.procempa.modus.synchronizer;

import java.util.Timer;
import java.util.TimerTask;


public class SynchronizerTimer {
	
	private static final int START_TIME = 1000*20;

	private static SynchronizerTimer synchronizerTimer;
	
	private TimerTask task;
	private long periodo;
	
	private Timer timer;
		
	private SynchronizerTimer() {
		this.timer = new Timer();	
	}
	
	public static SynchronizerTimer getInstance(TimerTask task, long periodo) {
		synchronizerTimer = new SynchronizerTimer();
		synchronizerTimer.setTask(task);
		synchronizerTimer.setPeriodo(periodo);
		return synchronizerTimer;
	}

	
	public void start() {
		timer.schedule(task, START_TIME, periodo);
	}
	
	public void stop() {
		timer.cancel();
		timer.purge();
	}

	public long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(long periodo) {
		this.periodo = periodo;
	}

	public TimerTask getTask() {
		return task;
	}

	public void setTask(TimerTask task) {
		this.task = task;
	}
}