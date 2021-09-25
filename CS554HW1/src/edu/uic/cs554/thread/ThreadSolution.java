package edu.uic.cs554.thread;

/**
 * Main class to start the program.
 * 
 * @author Arvind Gupta
 *
 */
public class ThreadSolution {

	public static void main(String args[]) {
		int n = 5;
		Philosopher[] philosophers = new Philosopher[5];

		Object[] listOfForkObjects = new Object[n];
		for (int i = 0; i < n; i++) {
			listOfForkObjects[i] = new Object();
		}

		for (int i = 0; i < n; i++) {
			Object fork1 = listOfForkObjects[i];
			Object fork2 = listOfForkObjects[(i + 1) % n];

			if (i == philosophers.length - 1) {
				philosophers[i] = new Philosopher(fork2, fork1);
			} else {
				philosophers[i] = new Philosopher(fork1, fork2);
			}

			Thread t = new Thread(philosophers[i], "Philosopher_#" + (i + 1));
			t.start();
		}
	}
}