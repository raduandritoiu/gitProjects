package models;

import java.util.concurrent.atomic.AtomicBoolean;

public class Runway {
	public final String name;
	public final boolean isLong;
	private final AtomicBoolean empty;
	
	public Runway(String nName, boolean nIsLong) {
		name = nName;
		isLong = nIsLong;
		empty = new AtomicBoolean(true);
	}
	
	public boolean tryOccupy() {
		boolean succ = empty.compareAndSet(true, false);
		return succ;
	}
	
	public void free() {
		empty.set(true);
	}
}
