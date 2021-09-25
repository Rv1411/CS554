package edu.uic.cs554.lmax;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * @author Arvind Gupta
 *
 */
public class LmaxDisruptor {
	
	public static volatile ArrayList<Integer> forkList = new ArrayList<Integer>();
    public static int philosophers = 8;

    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void main(String[] args) {

    	initializeForks();

        ExecutorService executorService = Executors.newCachedThreadPool();
        Disruptor<MutableValue> disruptor = new Disruptor<MutableValue>(MutableValue.EVENT_FACTORY, philosophers, executorService);
        final EventHandler<MutableValue> eventHandler = new EventHandler<MutableValue>() {
            public void onEvent(MutableValue event, long sequence, boolean endOfBatch) throws Exception {
                int activePhilosopher = event.getValue();
                int fork1 = activePhilosopher;
                int fork2 = (activePhilosopher + philosophers - 1) %  philosophers;

                if(forkList.get(fork1) == activePhilosopher && forkList.get(fork2) == activePhilosopher) {
                    forkList.set(fork1, -1);
                    forkList.set(fork2, -1);
                    System.out.println("Philosopher_#" + activePhilosopher + " is done eating and is now back to thinking");
                } else if(forkList.get(fork1) != -1 || forkList.get(fork2) != -1) {
                	System.out.println("Philosopher_#" + activePhilosopher + " is thinking");
                } else {
                    forkList.set(fork1, activePhilosopher);
                    forkList.set(fork2, activePhilosopher);
                    System.out.println("Philosopher_#" + activePhilosopher + " is now eating");
                }
            }
        };
        disruptor.handleEventsWith(eventHandler);
        RingBuffer<MutableValue> ringBuffer = disruptor.start();

        while (true) {
            int activePhilosopher = new Random().nextInt(philosophers);
            long seq = ringBuffer.next();
            MutableValue mutableValue = ringBuffer.get(seq);
            mutableValue.setValue(activePhilosopher);
            ringBuffer.publish(seq);
            try {
                Thread.sleep(((int) (Math.random() * 100)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void initializeForks() {
    	for(int i = 0; i<philosophers; i++) {
            forkList.add(-1);
        }
    }
}
