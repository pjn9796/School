package edu.sdsu.cs.prog4;

import java.util.Comparator;
import java.util.Random;
import edu.sdsu.cs.datastructures.Sorters;

public class SortDriver {
	public SortDriver(){
		String[][] array = new String[4][];
		array[0] = testShellSort();
		System.out.println();
		array[1] = testHeapSort();
		System.out.println();
		array[2] = testMergeSort();
		System.out.println();
		array[3] = testQuickSort();
		System.out.println();
		System.out.print("                  Shell      Merge       Heap        Quick");
		System.out.println();
		System.out.print("              ...................................................");
		System.out.println();
		System.out.print("1000  (Random): " + array[0][0] + array[1][0] + array[2][0] + array[3][0]);
		System.out.println();                                             
		System.out.print("2000  (Random): " + array[0][1] + array[1][1] + array[2][1] + array[3][1]);
		System.out.println();                                             
		System.out.print("4000  (Random): " + array[0][2] + array[1][2] + array[2][2] + array[3][2]);
		System.out.println();                                             
		System.out.print("8000  (Random): " + array[0][3] + array[1][3] + array[2][3] + array[3][3]);
		System.out.println();                                             
		System.out.print("1000 (Ordered): " + array[0][4] + array[1][4] + array[2][4] + array[3][4]);
		System.out.println();                                             
		System.out.print("2000 (Ordered): " + array[0][5] + array[1][5] + array[2][5] + array[3][5]);
		System.out.println();
		System.out.print("4000 (Ordered): " + array[0][6] + array[1][6] + array[2][6] + array[3][6]);
		System.out.println();
		System.out.print("8000 (Ordered): " + array[0][7] + array[1][7] + array[2][7] + array[3][7]);
		System.out.println();
	}
	public static void main(String[] args){
		new SortDriver();
	}
	
	private static String[] testShellSort(){
		String[] array = new String[8];
		int i = 0;
		for(int x = 1000; x <=8000; x=x*2){
			array[i] = testShellSortRandom(x) + "ns ";
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		for(int x = 1000; x <=8000; x=x*2){
			array[i] = testShellSortOrdered(x) + "ns ";
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		return array;
	}
	
	private static String[] testHeapSort(){
		String[] array = new String[8];
		int i = 0;
		for(int x = 1000; x <=8000; x=x*2){
			array[i] = testHeapSortRandom(x) + "ns ";
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		for(int x = 1000; x <=8000; x=x*2){
			array[i] = testHeapSortOrdered(x) + "ns ";
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		return array;
	}
	
	private static String[] testMergeSort(){
		String[] array = new String[8];
		int i = 0;
		for(int x = 1000; x <=8000; x=x*2){
			array[i] = testMergeSortRandom(x) + "ns ";
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		for(int x = 1000; x <=8000; x=x*2){
			array[i] = testMergeSortOrdered(x) + "ns ";
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		return array;
	}
	
	private static String[] testQuickSort(){
		String[] array = new String[8];
		int i = 0;
		for(int x = 1000; x <= 8000; x=x*2){
			array[i] = testQuickSortRandom(x) + "ns ";
			if(array[i].equals("-1ns ")){
				array[i] = "StackOverflow";
			}
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		for(int x = 1000; x <= 8000; x=x*2){
			array[i] = testQuickSortOrdered(x) + "ns ";
			if(array[i].equals("-1ns ")){
				array[i] = "StackOverflow";
			}
			for(int j = array[i].length(); j <= 11; j++){
				array[i] = " " + array[i];
			}
			i++;
		}
		return array;
	}
	
	private static <E> long testQuickSortRandom(int size){
		Random random = new Random();
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = random.nextInt(100);
		}
		System.out.println("Quick Sort Random(Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		try{
			array = Sorters.quickSort(array);
		}catch(StackOverflowError e){
			return -1;
		}
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testMergeSortRandom(int size){
		Random random = new Random();
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = random.nextInt(100);
		}
		System.out.println("Merge Sort Random(Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		Sorters.mergeSort(array);
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testHeapSortRandom(int size){
		Random random = new Random();
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = random.nextInt(100);
		}
		System.out.println("Heap Sort Random(Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		array = Sorters.heapSort(array);
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testShellSortRandom(int size){
		Random random = new Random();
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = random.nextInt(100);
		}
		System.out.println("Shell Sort Random (Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		array = Sorters.shellSort(array);
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testShellSortOrdered(int size){
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = (int)(((double)i/size)*100);
		}
		System.out.println("Shell Sort Ordered (Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		array = Sorters.shellSort(array);
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testMergeSortOrdered(int size){
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = (int)(((double)i/size)*100);
		}
		System.out.println("Merge Sort Ordered (Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		array = Sorters.mergeSort(array);
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testHeapSortOrdered(int size){
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = (int)(((double)i/size)*100);
		}
		System.out.println("Heap Sort Ordered (Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		array = Sorters.heapSort(array);
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	private static <E> long testQuickSortOrdered(int size){
		Integer[] array = new Integer[size];
		for(int i = 0; i < array.length; i++){
			array[i] = (int)(((double)i/size)*100);
		}
		System.out.println("Quick Sort Ordered (Every 100th element): ");
		printArray(array);
		long t1 = System.nanoTime();
		try{
			array = Sorters.quickSort(array);
		}catch(StackOverflowError e){
			return -1;
		}
		long t2 = System.nanoTime();
		printArray(array);
		return t2-t1;
	}
	
	public static <E> void printArray(E[] array){
		System.out.print("    ");
		for(int i = 0; i < array.length-1; i=i+100){
			System.out.print(array[i] + ", ");
		}
		System.out.println(array[array.length-1]);
	}
	
	public static <E> void printFullArray(E[] array){
		System.out.print("    ");
		for(int i = 0; i < array.length-1; i=i+1){
			System.out.print(array[i] + ", ");
		}
		System.out.println(array[array.length-1]);
	}
}
