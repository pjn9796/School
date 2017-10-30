package main.java.edu.sdsu.cs.prog1;

import main.java.edu.sdsu.cs.datastructures.LoggedVector;
import java.io.IOException;
import java.util.Comparator;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggedVectorDriver {
	public static void main(String[] args) {
		try {
			new LoggedVectorDriver();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class CanBeStoredInNumBitsSigned implements Comparator<Integer> {
		@Override
    	public int compare(Integer num, Integer numSignedBits) {
			int numValues = (int)Math.pow(2, numSignedBits);
			int half = numValues/2;
			int maxNegative = -1*half;
			int maxPositive = half-1;
			if(maxNegative <= num && maxPositive >= num)
				return 0;
			return -1;
    	}
	}

	final Logger myLogger = Logger.getLogger("edu.sdsu.cs.datastructures.LoggedVector");

	static FileHandler fileTxt;
	static SimpleFormatter formatterTxt;

	LoggedVector<Integer> stringVector = new LoggedVector<Integer>(2, 16);

	private LoggedVectorDriver() throws SecurityException, IOException {
		initLogger();
		stringVector.useLog(myLogger);

		myLogger.info("Running with Anonymous comparator . . . ");
		stringVector.addTrigger(new Comparator<Integer>() {
			@Override
			public int compare(Integer num, Integer numMultOf) {
				if(numMultOf == 0){
					return -1;
				}
				if (num%numMultOf == 0){
					return 0;
				}
				return -1;
			}
		}, 4);

		runTests(stringVector);
		stringVector.clearTriggers();
		
		myLogger.info("Running with CanBeStoredInNumBitsSigned comparator . . . ");
		stringVector.addTrigger(new CanBeStoredInNumBitsSigned(), 8);
		runTests(stringVector);
    
		System.out.println("complete");
	}

	void runTests(LoggedVector<Integer> sut) {
		sut.add(-4);
		sut.add(127);
		sut.add(0);
		sut.add(-235);
		sut.add(400);
		sut.add(402);
		sut.add(-128);
		sut.remove(0);
		sut.set(1, 5);
		sut.set(1, 4);
		sut.get(sut.size()-1);
	}

	void disableConsoleLogger() {
		Logger rootLogger = Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		if (handlers[0] instanceof ConsoleHandler) {
    		rootLogger.removeHandler(handlers[0]);
		}
	}

	void initLogger() throws SecurityException, IOException{
		disableConsoleLogger();
		fileTxt = new FileHandler("Logging.xml");
		myLogger.setLevel(Level.INFO);
		myLogger.addHandler(fileTxt);
	}
}