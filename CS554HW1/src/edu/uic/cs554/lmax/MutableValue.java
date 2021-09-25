package edu.uic.cs554.lmax;

import com.lmax.disruptor.EventFactory;

/**
 * 
 * @author Arvind Gupta
 *
 */
public class MutableValue {

	private int value;

	public final static EventFactory<MutableValue> EVENT_FACTORY = () -> new MutableValue();
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
