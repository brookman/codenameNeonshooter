package eu32k.neonshooter.core.fx.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimedQueue<T> {
	private int index;
	private float nextTime;
	private Entry<T> nextEntry;
	private float currentTime;

	private TimedQueueListener<T> listener;

	private List<Entry<T>> entries;

	public TimedQueue() {
		entries = new ArrayList<Entry<T>>();
	}

	public void clear() {
		entries.clear();
	}

	public void add(T value, float time) {
		entries.add(new Entry<T>(value, time));
	}

	public void sort() {
		Collections.sort(entries);
	}

	public void init() {
		index = 0;
		currentTime = 0;
		selectNext();
	}

	private void selectNext() {
		if (index < entries.size()) {
			nextEntry = entries.get(index);
			nextTime = nextEntry.time;
		} else {
			nextEntry = null;
		}
		index++;
	}

	public void update(float delta) {
		currentTime += delta;
		while (nextEntry != null && nextTime <= currentTime) {
			notify(nextEntry);
			selectNext();
		}
	}

	private void notify(Entry<T> entry) {
		if (listener != null) {
			listener.event(entry.value, entry.time);
		}
	}

	public TimedQueueListener<T> getListener() {
		return listener;
	}

	public void setListener(TimedQueueListener<T> listener) {
		this.listener = listener;
	}

	public interface TimedQueueListener<T> {
		public void event(T value, float time);
	}

	private class Entry<T> implements Comparable<Entry<T>> {
		T value;
		float time;

		public Entry(T value, float time) {
			this.value = value;
			this.time = time;
		}

		@Override
		public int compareTo(Entry<T> arg0) {
			return Float.compare(time, arg0.time);
		}

	}
}
