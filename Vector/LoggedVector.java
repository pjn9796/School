package main.java.edu.sdsu.cs.datastructures;

import java.util.Collection;
import java.util.Comparator;
import java.util.logging.Logger;
import test.java.edu.sdsu.cs.datastructures.ILoggable;

public class LoggedVector<E> extends Vector<E> implements ILoggable<E>{
	private Logger logger;
	private TriggerArray triggers = new TriggerArray();
	
	public LoggedVector(){
		super();
		clearTriggers();
	}
	
	public LoggedVector(Collection<E> c){
		super(c);
		clearTriggers();
	}
	
	@SuppressWarnings("unchecked")
	public LoggedVector(int initialCapacity, int blockSize){
		clearTriggers();
		currentSize = 0;
		this.capacityIncrement = blockSize;
		vector = (E[])(new Object[initialCapacity]);
	}

	@Override
	public E get(int index){
		E gotObject = (E)super.get(index);
		triggers.compare(gotObject, ("*"+gotObject+"*" + " was gotten at index " + index + "."));
		return gotObject;
	}

	@Override
	public int size(){
		return currentSize;
	}
	
	@SuppressWarnings("unchecked")
	public E set(int index, Object e){
		E removedObject = (E)super.set(index, e);
		triggers.compare(removedObject, "*"+removedObject+"*" + " was replaced by " + (E)e + " at " + index + ".");
		triggers.compare((E)e, "*"+(E)e+"*" + " replaced " + removedObject + " at " + index + ".");
		return removedObject;
	}
	
	@SuppressWarnings("unchecked")
	public void add(int index, Object e){
		super.add(index, e);
		triggers.compare((E)e, "*"+(E)e+"*" + " was added at index " + index + ".");
	}
	
	public E remove(int index){
		E removedObject = (E)super.remove(index);
		triggers.compare(removedObject, "*"+removedObject+"*" + " was removed from index " + index + ".");
		return removedObject;
	}
	
	public void grow(){
		super.grow();
		logger.info("Info: LoggedVector array has grown.");
	}
	
	public void shrink(){
		super.shrink();
		logger.info("Info: LoggedVector array has shrunk.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addTrigger(Comparator trigger, Object value){
		if(trigger == null || value == null)
			return false;
		triggers.add(trigger, (E)value);
		if(logger != null){
			logger.info("INFO: Trigger added.");
		}
		return true;
	}

	@Override
	public void clearTriggers(){
		triggers.clear();
		if(logger != null){
			logger.info("INFO: Triggers cleared.");
		}
	}

	@Override
	public Logger useLog(Logger log){
		Logger prevLog = logger;
		logger = log;
		if(prevLog == null){
			logger.info("INFO: Log " + logger + " is now being used.");
		}else{
			logger.info("INFO: Log " + prevLog + " is no longer being used. Log " + logger + " is now being used.");
		}
		return prevLog;
	}
	
	private class TriggerArray{
		private Comparator<E>[] comparators;
		private E[] values;
		private int currentSize;
		
		public TriggerArray(){
			clear();
		}
		
		@SuppressWarnings("unchecked")
		public void clear(){
			comparators = new Comparator[10];
			values = (E[])(new Object[10]);
			currentSize = 0;
		}
		
		@SuppressWarnings("unchecked")
		private void grow(){
			int size = (int)(comparators.length*1.5);
			Comparator<E>[] oldComparators = comparators;
			E[] oldValues = values;
			comparators = new Comparator[size];
			values = (E[])(new Object[size]);
			for(int i = 0; i < currentSize; i++){
				comparators[i] = oldComparators[i];
				values[i] = oldValues[i];
			}
		}
		
		public void compare(E object, String callInfo){
			int result;
			for(int i = 0; i < currentSize; i++){
				result = comparators[i].compare((E)object, values[i]);
				if(result == 0){
					logger.info("INFO: " + callInfo);
				}
			}
		}
		
		public void add(Comparator<E> c, E v){
			if(values.length == currentSize)
				grow();
			comparators[currentSize] = c;
			values[currentSize] = v;
			currentSize++;
		}
	}
}
