package edu.uic.cs554.thread;

/**
 * 
 * @author Arvind Gupta
 *
 */
public class Philosopher implements Runnable {

	private Object fork1;
	private Object fork2;

	public Philosopher(Object fork1, Object fork2) {
		super();
		this.fork1 = fork1;
		this.fork2 = fork2;
	}

	@Override
	public void run() {
		while (true) {
			try {
				eatThinkOrPickFork("Thinking");
				synchronized (getFork1()) {
					eatThinkOrPickFork("Picking up first fork on left");
					synchronized (getFork2()) {
						eatThinkOrPickFork("Picking up second fork on right and started eating");
						eatThinkOrPickFork("Putting down second fork on right");
					}
					eatThinkOrPickFork("Putting down first fork on left and started thinking again");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void eatThinkOrPickFork(String actionToBePerformed) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " is : " + actionToBePerformed);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	/**
	 * @return the fork1
	 */
	public Object getFork1() {
		return fork1;
	}

	/**
	 * @param fork1 the fork1 to set
	 */
	public void setFork1(Object fork1) {
		this.fork1 = fork1;
	}

	/**
	 * @return the fork2
	 */
	public Object getFork2() {
		return fork2;
	}

	/**
	 * @param fork2 the fork2 to set
	 */
	public void setFork2(Object fork2) {
		this.fork2 = fork2;
	}
}
