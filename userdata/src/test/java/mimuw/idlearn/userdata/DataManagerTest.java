package mimuw.idlearn.userdata;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import mimuw.idlearn.core.Listener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataManagerTest {
	@Test
	public void testBasicPoints() throws IOException {
		DataManager.resetData();
		assertEquals(0, DataManager.showPoints());
		DataManager.addPoints(100);
		assertEquals(100, DataManager.showPoints());
		try {
			DataManager.payPoints(60);
		}
		catch (NotEnoughPointsException e) {
			fail();
		}
		assertEquals(40, DataManager.showPoints());
		DataManager.resetData();
	}

	@Test
	public void testBasicTasks() throws IOException {
		DataManager.resetData();
		assertEquals(new ArrayList<>(Collections.singleton("Addition")), DataManager.getUnlockedTasks());
		DataManager.unlockTask("A");
		assertEquals(new ArrayList<>(List.of(new String[]{"Addition", "A"})), DataManager.getUnlockedTasks());
		DataManager.resetUnlockedTasks();
		assertEquals(new ArrayList<>(), DataManager.getUnlockedTasks());
		DataManager.resetData();
	}

	@Test
	public void testNotEnoughException() throws IOException {
		DataManager.resetData();
		try {
			DataManager.payPoints(10);
			fail();
		}
		catch (NotEnoughPointsException e) {
		}
		assertEquals(0, DataManager.showPoints());
		DataManager.resetData();
	}

	private static class LongWrapper {
		public long value = 0;
	}

	@Test
	public void testPointsEmitter() throws IOException {
		DataManager.resetData();
		final LongWrapper log = new LongWrapper();
		Listener listener = event -> log.value = (long)(event.value());
		DataManager.connectToPoints(listener);

		DataManager.addPoints(50);
		assertEquals(50, log.value);

		try {
			DataManager.payPoints(40);
		}
		catch (NotEnoughPointsException e) {
			fail();
		}
		assertEquals(10, log.value);
		DataManager.resetData();
	}

	@Test
	public void testLoad() throws IOException {

		DataManager.init();
		DataManager.resetData();
		assertEquals(0, DataManager.showPoints());

		DataManager.addPoints(100);
		DataManager.unlockTask("A");
		DataManager.exit();


		DataManager.init();
		assertEquals(100, DataManager.showPoints());
		assertEquals(new ArrayList<>(List.of(new String[]{"Addition", "A"})), DataManager.getUnlockedTasks());

		DataManager.setPoints(0);
		DataManager.init();
		assertEquals(100, DataManager.showPoints());

		DataManager.resetData();
	}

}
