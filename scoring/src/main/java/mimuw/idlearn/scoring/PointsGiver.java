package mimuw.idlearn.scoring;

import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class PointsGiver {
	private static final long POINTS_PER_TICK = 10;
	private static final Timer givingTimer = new Timer();
	private static final Map<String, PointsTimerTask> givingTasks = new HashMap<>();
	private static final Map<String, Long> timeStamps = new HashMap<>();
	private static final Semaphore mutex = new Semaphore(1);

	private static class PointsTimerTask extends TimerTask {
		long timeMillis;
		public PointsTimerTask(long time) {
			super();
			timeMillis = time;
		}

		@Override
		public void run() {
			DataManager.addPoints(POINTS_PER_TICK);
		}

		public void start() {
			givingTimer.scheduleAtFixedRate(this, timeMillis, timeMillis);
		}
	}

	public static void setSolutionSpeed(String problem, long timeInMillis) throws IOException {
		Date date = new Date();
		setSolutionSpeed(problem, timeInMillis, date.getTime());
	}

	public static void setSolutionSpeed(String problem, long timeInMillis, long timeStamp) throws IOException {

		try {
			mutex.acquire();
		}
		catch (InterruptedException e) {
			throw new RuntimeException("Process interrupted");
		}

		Long oldStamp = timeStamps.get(problem);
		if (oldStamp != null && oldStamp > timeStamp) {
			mutex.release();
			return;
		}

		DataManager.setSpeed(problem, timeInMillis);
		PointsTimerTask task = givingTasks.get(problem);

		if (task != null) {
			task.cancel();
		}

		task = new PointsTimerTask(timeInMillis);
		task.start();
		givingTasks.put(problem, task);
		timeStamps.put(problem, timeStamp);


		mutex.release();
	}

	public static void resetSolution(String problem) {

		try {
			mutex.acquire();
		}
		catch (InterruptedException e) {
			throw new RuntimeException("Process interrupted");
		}

		PointsTimerTask task = givingTasks.get(problem);
		if (task != null) {
			task.cancel();
		}
		givingTasks.remove(problem);
		timeStamps.remove(problem);

		mutex.release();
	}
	public static void exit() {

		try {
			mutex.acquire();
		}
		catch (InterruptedException e) {
			throw new RuntimeException("Process interrupted");
		}

		for (Map.Entry<String, PointsTimerTask> entry : givingTasks.entrySet()) {
			entry.getValue().cancel();
			timeStamps.remove(entry.getKey());
		}
		givingTasks.clear();

		mutex.release();
	}

	public static void loadSpeeds() {
		HashMap<String, Long> speeds = DataManager.getSpeeds();
		Date date = new Date();

		for (Map.Entry<String, Long> entry : speeds.entrySet()) {
			PointsTimerTask task = new PointsTimerTask(entry.getValue());
			task.start();
			givingTasks.put(entry.getKey(), task);
			timeStamps.put(entry.getKey(), date.getTime());
		}
	}
}
