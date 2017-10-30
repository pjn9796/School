/**
 * San Diego State University.<br> CS 310: Data Structures<br> Spring 2016<br>
 * 
 * This is the primary source file for the final programming assignment. You
 * must complete the algorithms for merge sort, heap sort, shell sort, and
 * quicksort in this file and submit an additional driver file demonstrating
 * their correctness. For each of these methods, provide the appropriate javadoc
 * briefly describing the algorithm, its best and worst case data cases (if
 * any), its best, worst, and average running times, and identify if it is
 * stable and/or in place. You may (and should) create helper methods to support
 * your implementation, but these must be private.
 * 
 * @version 0.2
 */

package edu.sdsu.cs.datastructures;

import java.lang.reflect.Array;
import java.util.Comparator;

import edu.sdsu.cs.prog4.SortDriver;

/**
 * collection of search and sort static methods.
 * 
 * @author Patrick Nolan
 *
 */
public class Sorters<E> {
	
	/**
	 * performs an iterative search for a specified value within an array.
	 * 
	 * @param items
	 *          the array of comparable objects to sort
	 * @param value
	 *          the value to find
	 * @return index position within the array to an instance of the target value
	 *         or -1 if not in the supplied array.
	 */
	public static <E extends Comparable<E>> int binarySearch(final E[] items,
	    final E value) {
	  return binarySearchIterative(items, value, 0, items.length - 1,
	      Comparator.naturalOrder());
	}
	
	/**
	 * performs an iterative search for a specified value within an array.
	 * 
	 * @param items
	 *          the array of comparable objects to sort
	 * @param value
	 *          the value to find
	 * @param order
	 *          the Comparator object to use when ordering the elements in the
	 *          array
	 * @return index position within the array to an instance of the target value
	 *         or -1 if not in the supplied array.
	 */
	public static <E> int binarySearch(final E[] items, final E value,
	    final Comparator<E> order) {
	  return binarySearchIterative(items, value, 0, items.length - 1, order);
	}
	
	private static <E> int binarySearchIterative(E[] items, E value, int low,
	    int high, Comparator<E> order) {
	  while (low <= high) {
	    int mid = (low + high) >> 1;
	    int result = order.compare(value, items[mid]);
	    if (result > 0) {
	      // value - array > 0: array value smaller, so in right half
	      low = mid + 1;
	    } else if (result < 0) {
	      high = mid - 1;
	    } else if (result == 0) {
	      return mid;
	    }
	  }
	  return -1;
	}
	
	/**
	 * performs a recursive search for a specified value within an array.
	 * 
	 * @param items
	 *          the array of comparable objects to sort
	 * @param value
	 *          the value to find
	 * @return index position within the array to an instance of the target value
	 *         or -1 if not in the supplied array.
	 */
	static <E> int binarySearchRecursive(E[] items, E value,
	    final Comparator<E> order) {
	  return binarySearchRecursive(items, value, 0, items.length - 1, order);
	}
	
	private static <E> int binarySearchRecursive(E[] items, E value,
	    final int low, final int high, final Comparator<E> order) {
	  if (low > high) {
	    return -1;
	  }
	  final int mid = (low + high) >> 1;
	  final int result = order.compare(value, items[mid]);
	  if (result > 0) {
	    return binarySearchRecursive(items, value, mid + 1, high, order);
	  } else if (result < 0) {
	    return binarySearchRecursive(items, value, low, mid - 1, order);
	  }
	  return mid;
	}
	
	/**
	 * constructs a heap data structure and then uses it to place the array
	 * elements in sorted order. <br> In place: yes<br> Stable: not without a
	 * wrapper
	 * 
	 * @param toSort
	 *          the array to sort
	 * @return the sorted array
	 */
	public static <E extends Comparable<E>> E[] heapSort(E[] toSort) {
	  return heapSort(toSort, Comparator.naturalOrder());
	}
	
	/**
	 * constructs a heap data structure and then uses it to place the array
	 * elements in sorted order. <br> In place: yes<br> Stable: not without a
	 * wrapper
	 * 
	 * @param toSort
	 *          the array to sort
	 * @param order
	 *          the Comparator object to consult when ranking the elements
	 * @return the sorted array
	 */
	public static <E> E[] heapSort(E[] toSort, Comparator<E> order) {
		for(int i = 1; i < toSort.length; i++){
			toSort = addToHeap(toSort, order, i);
		}
		for(int i = toSort.length-1; i >= 0; i--){
			toSort = remFromHeap(toSort, order, i);
		}
		return toSort;
	}
	
	private static <E> E[] remFromHeap(E[] toSort, Comparator<E> order, int index){
		int iCurrent = 0;
		int iRightChild = iCurrent*2+2; 
		int iLeftChild = iCurrent*2+1;
		E temp = toSort[index];
		toSort[index] = toSort[iCurrent];
		toSort[iCurrent] = temp;
		while(iRightChild < index){
				if(order.compare(toSort[iRightChild], toSort[iLeftChild]) > 0){
					temp = toSort[iCurrent];
					toSort[iCurrent] = toSort[iRightChild];
					toSort[iRightChild] = temp;
					iCurrent = iRightChild;
					iRightChild = iCurrent*2+2;
					iLeftChild = iCurrent*2+1;
				}else {
					temp = toSort[iCurrent];
					toSort[iCurrent] = toSort[iLeftChild];
					toSort[iLeftChild] = temp;
					iCurrent = iLeftChild;
					iRightChild = iCurrent*2+2;
					iLeftChild = iCurrent*2+1;
			}
		}
		return toSort;
	}
	
	private static <E> E[] addToHeap(E[] toSort, Comparator<E> order, int index){
		int iCurrent = index;
		int iParent;
		while(iCurrent != 0){
			iParent = (iCurrent-1) >> 1;
			if(order.compare(toSort[iCurrent], toSort[iParent]) >= 0){
				E temp = toSort[iCurrent];
				toSort[iCurrent] = toSort[iParent];
				toSort[iParent] = temp;
			}
			iCurrent = iParent;
		}
		return toSort;
	}
	
	/**
	 * places the input array in its natural order using the Comparable pattern.
	 * Insertion sort represents the best of the worst sorts. In practice, one
	 * should consider using quicksort, heapsort, shell sort, or any number of
	 * more efficient algorithms. Insertion sort's computational complexity grows
	 * quadratically as the data set grows. Thus, for large arrays, this algorithm
	 * may render the incorporating class inoperable.<br> <br> That said, in the
	 * best case scenario, insertion sort completes in the minimum amount of time
	 * possible: <i>O(n)</i>. Interestingly, insertion sort actually runs in
	 * <i>O(kn)</i> time where <i>k</i> represents the average number of positions
	 * an element in the array is from its correct position. Thus, insertion sort
	 * not only performs well for an array in sorted order, but it does so for
	 * arrays near their sorted order. One of the faster sorting algorithms,
	 * <i>Shell Sort</i> uses this fact to achieve excellent performance.<br> <br>
	 * In place: yes<br> Stable: yes
	 * 
	 * @see https://en.wikipedia.org/wiki/Insertion_sort
	 * 
	 * @param toSort
	 *          a reference to the unsorted array
	 * @return the sorted array
	 */
	public static <E extends Comparable<E>> E[] insertionSort(E[] toSort) {
	  return insertionSort(toSort, Comparator.naturalOrder());
	}
	
	/**
	 * sorts the parameter array with the insertion sort algorithm using the
	 * provided comparator.<br> In place: yes<br> Stable: yes
	 * 
	 * @param toSort
	 *          the array of elements to sort
	 * @param order
	 *          the Comparator object to consult when ranking elements
	 * @return
	 */
	public static <E> E[] insertionSort(E[] toSort, Comparator<E> order) {
	  for (int idxUnsorted = 1; idxUnsorted < toSort.length; idxUnsorted++) {
	    E elementToInsert = toSort[idxUnsorted];
	    int idxSortedPortion = idxUnsorted;
	
	    while (idxSortedPortion > 0
	        && order.compare(toSort[idxSortedPortion - 1], elementToInsert) > 0) {
	      toSort[idxSortedPortion] = toSort[idxSortedPortion - 1];
	      idxSortedPortion--;
	    }
	    toSort[idxSortedPortion] = elementToInsert;
	  }
	  return toSort;
	}
	
	/**
	 * Breaks array down recursively then merges the pieces. Always nlog(n). <br>In place: No <br> Stable: Yes
	 * 
	 * @param toSort
	 * @return
	 */
	public static <E extends Comparable<E>> E[] mergeSort(E[] toSort) {
	  return mergeSort(toSort, Comparator.naturalOrder());
	}
	
	/**
	 * Breaks array down recursively then merges the pieces. Always nlog(n). <br>In place: No <br> Stable: Yes
	 * 
	 * @param toSort
	 * @param order
	 * @return
	 */
	public static <E> E[] mergeSort(E[] toSort, Comparator<E> order) {
		if(toSort.length <= 1){
			return toSort;
		}else{
			int halfLength = toSort.length/2;
			E[] array1 = (E[])new Object[halfLength];
			E[] array2;
			if(toSort.length % 2 == 0){
				array2 = (E[])new Object[halfLength];
			}else{
				array2 = (E[])new Object[halfLength+1];
			}
			System.arraycopy(toSort, 0, array1, 0, array1.length);
			System.arraycopy(toSort, array1.length, array2, 0, array2.length);
			array1 = mergeSort(array1, order);
			array2 = mergeSort(array2, order);
			E[] returnArray = (E[])new Object[array1.length+array2.length];
			int c1 = 0; int c2 = 0;
			for(int i = 0; i < returnArray.length; i++){
				int compare;
				if(c1 >= array1.length){
					compare = 1;
				}else if(c2 >= array2.length){
					compare = -1;
				}else{
					compare = order.compare(array1[c1], array2[c2]);
				}
				if(compare <= 0){
					returnArray[i] = array1[c1];
					c1++;
				}else{
					returnArray[i] = array2[c2];
					c2++;
				}
			}
			for(int counter = 0; counter < returnArray.length; counter++){
				toSort[counter] = returnArray[counter];
			}
			return toSort;
		}
	}
	
	/**
	 * Recursively uses pivots on smaller and smaller sections of the array ensuring everything to left of pivot is smaller and everything to the right is larger.
	 * Average case is nlog(n). Worse case is n^2 when array is near sorted or reverse sorted order. <br>In place: Yes<br> Stable: No
	 * 
	 * @param toSort
	 * @return
	 */
	public static <E extends Comparable<E>> E[] quickSort(E[] toSort) {
	  return quickSort(toSort, Comparator.naturalOrder());
	}
	
	/**
	 * Recursively uses pivots on smaller and smaller sections of the array ensuring everything to left of pivot is smaller and everything to the right is larger.
	 * Average case is nlog(n). Worse case is n^2 when array is near sorted or reverse sorted order. <br>In place: Yes<br> Stable: No
	 * 
	 * @param toSort
	 * @param order
	 * @return
	 */
	public static <E> E[] quickSort(E[] toSort, Comparator<E> order) {
		return quickSort(toSort, order, 0, toSort.length-1);
	}
	
	private static <E> E[] quickSort(E[] toSort, Comparator<E> order, int start, int finish){
		if(finish-start <= 0){
			return toSort;
		}
		
		int pivot = finish;
		int left = start;
		int right = pivot - 1;
		while(true){
			while(order.compare(toSort[++left], toSort[pivot]) < 0);
			while(right > start && order.compare(toSort[--right], toSort[pivot]) > 0);
			if(left >= right)
				break;
			else{
				E temp = toSort[right];
				toSort[right] = toSort[left];
				toSort[left] = temp;
			}
		}
		E temp = toSort[pivot];
		toSort[pivot] = toSort[left];
		toSort[left] = temp;
		toSort = quickSort(toSort, order, start, pivot-1);
		toSort = quickSort(toSort, order, pivot+1, finish);
		return toSort;
	}
	
	/**
	 * places the parameter object in order using the elements natural ordering.
	 * <br> In place: yes<br> Stable: no
	 * 
	 * @param toSort
	 *          the array to sort
	 * @return the sorted array
	 */
	public static <E extends Comparable<E>> E[] selectionSort(E[] toSort) {
	  return selectionSort(toSort, Comparator.naturalOrder());
	}
	
	/**
	 * places the parameter object in order using the provided comparator.<br> In
	 * place: yes<br> Stable: no
	 * 
	 * @param toSort
	 *          the array to sort
	 * @param order
	 *          the Comparator object to consult when ranking the elements
	 * @return The sorted array
	 */
	public static <E> E[] selectionSort(E[] toSort, Comparator<E> order) {
	  for (int sortedCount = 0; sortedCount < toSort.length; sortedCount++) {
	    int minIndex = sortedCount;
	    for (int index = sortedCount + 1; index < toSort.length; index++) {
	      if (order.compare(toSort[minIndex], toSort[index]) < 0) {
	        minIndex = index;
	      }
	    }
	    final E toSwap = toSort[minIndex];
	    toSort[minIndex] = toSort[sortedCount];
	    toSort[sortedCount] = toSwap;
	  }
	  return toSort;
	}
	
	/**
	 * Performs an insertion sort with decreasing h vales ending with an h value of 1. Always nlog(n). <br>In place: Yes <br> Stable: No
	 * 
	 * @param toSort
	 * @return
	 */
	public static <E extends Comparable<E>> E[] shellSort(E[] toSort) {
	  return shellSort(toSort, Comparator.naturalOrder());
	}
	
	/**
	 * Performs an insertion sort with decreasing h vales ending with an h value of 1. Always nlog(n). <br>In place: Yes <br> Stable: No
	 * 
	 * @param toSort
	 * @param order
	 * @return
	 */
	public static <E> E[] shellSort(E[] toSort, Comparator<E> order) {
		int h = 1;
		while(h < toSort.length/3){
			h = h*3+1;
		}
		while(h >= 1){
			int a = h;
			while(a != 0){
				for (int idxUnsorted = a; idxUnsorted < toSort.length; idxUnsorted=idxUnsorted+h) {
					E elementToInsert = toSort[idxUnsorted];
					int idxSortedPortion = idxUnsorted;
					while (idxSortedPortion-h >= 0 && order.compare(toSort[idxSortedPortion - h], elementToInsert) > 0) {
						toSort[idxSortedPortion] = toSort[idxSortedPortion - h];
						idxSortedPortion = idxSortedPortion - h;
					}
					toSort[idxSortedPortion] = elementToInsert;
				}
				a--;
			}
			h = (h-1)/3;
		}
		return toSort;
	}
}
