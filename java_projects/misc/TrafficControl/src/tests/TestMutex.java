package tests;

import java.util.concurrent.Semaphore;

import syncs.SimpleMutex;

public class TestMutex {
	public static void main(String args[]) {
		int num = 5;
		SimpleMutex mutex = new SimpleMutex(false);
		
		
		WorkerOne workers[] = new WorkerOne[num];
		for (int i = 0; i < num; i++) {
			workers[i] = new WorkerOne(i+1, mutex);
		}
		
		for (int i = 0; i < num; i++) {
			workers[i].start();
		}
		
		mutex.unlock(1);
		mutex.unlock(1);
		
		System.out.println("DONE");
	}
	
	
	private static class WorkerOne extends Thread {
		private final String id;
		private final SimpleMutex mutex;
		public boolean running;
		
		public WorkerOne(int nId, SimpleMutex nMutex) {
			id = "Worker " + nId;
			mutex = nMutex;
			running = true;
		}
		
		public void run() {
			while (running) {
				System.out.println(id + " has done first half of the task!");
				mutex.lock(1);
				System.out.println(id + " has done second half of the task!");
				mutex.lock(1);
				System.out.println(id + " has done third half of the task!");
			}
		}
	}
}
