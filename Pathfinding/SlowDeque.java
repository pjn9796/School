/**
 * San Diego State University.<br>
 * CS 310: Data Structures<br>
 * Spring 2016<br>
 * 
 * @author Patrick Nolan
 * @account pjn9796@gmail.com
 */
package edu.sdsu.cs.prog2;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.sdsu.cs.datastructures.Vector;

/**
 * in a state where it compiles. You need to fill in the blanks. Remember, you
 * must construct this deque via composition with a Vector (not by extending
 * one).

 * @param <E>
 */
public class SlowDeque<E> implements Deque<E> {
	private Vector vector;
	
	public SlowDeque(){
		vector = new Vector();
	}
	
	public SlowDeque(Collection<?> c){
		vector = new Vector(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return vector.addAll(c);
	}
	
	@Override
	public void clear() {
		vector.clear();
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return vector.containsAll(c);
	}
	
	@Override
	public boolean isEmpty() {
		return vector.isEmpty();
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return vector.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return vector.retainAll(c);
	}
	
	@Override
	public Object[] toArray() {
		return vector.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return (T[])vector.toArray(a);
	}
	
	@Override
	public boolean add(E e) {
		return vector.add(e);
	}
	
	@Override
	public void addFirst(E e) {
		vector.add(0, e);
	}
	
	@Override
	public void addLast(E e) {
		vector.add(e);
	}
	
	@Override
	public boolean contains(Object o) {
		return vector.contains(o);
	}
	
	@Override
	public Iterator<E> descendingIterator() {
		return new DescendingIterator(vector.toArray());
	}
	
	@Override
	public E element() {
		return (E)vector.get(0);
	}
	
	@Override
	public E getFirst() {
		if(vector.size() == 0)
			throw new NoSuchElementException();
		return (E)vector.get(0);
	}
	
	@Override
	public E getLast() {
		if(vector.size() == 0)
			throw new NoSuchElementException();
		return (E)vector.get(vector.size()-1);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new AscendingIterator(vector.toArray());
	}
	
	@Override
	public boolean offer(E e) {
		try{
			vector.add(e);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	@Override
	public boolean offerFirst(E e) {
		try{
			vector.add(0, e);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	@Override
	public boolean offerLast(E e) {
		try{
			vector.add(vector.size()-1, e);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	@Override
	public E peek() {
		try{
			return (E)vector.get(0);
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public E peekFirst() {
		try{
			return (E)vector.get(0);
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public E peekLast() {		
		try{
			return (E)vector.get(vector.size()-1);
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public E poll() {
		try{
			return (E)vector.remove(0);
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public E pollFirst() {
		try{
			return (E)vector.remove(0);
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public E pollLast() {
		try{
			return (E)vector.remove(vector.size()-1);
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public E pop() {
		if(vector.size() == 0)
			throw new NoSuchElementException();
		E obj = (E)vector.get(0);
		vector.remove(0);
		return obj;
	}
	
	@Override
	public void push(E e) {
		vector.add(0, e);
	}
	
	@Override
	public E remove() {
		E obj = (E)vector.get(0);
		vector.remove(0);
		return obj;
	}
	
	@Override
	public boolean remove(Object o) {
		int index = vector.indexOf(o);
		if(index == -1){
			return false;
		}else{
			vector.remove(index);
			return true;
		}
	}
	
	@Override
	public E removeFirst() {
		E obj = (E)vector.get(0);
		vector.remove(0);
		return obj;
	}
	
	@Override
	public boolean removeFirstOccurrence(Object o) {
		for(int i = 0; i < vector.size()-1; i++){
			if(vector.get(i).equals((E)o)){
				vector.remove(i);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public E removeLast() {
		E obj = (E)vector.get(vector.size()-1);
		vector.remove(vector.size()-1);
		return obj;
	}
	
	@Override
	public boolean removeLastOccurrence(Object o) {
		for(int i = vector.size(); i != 0; i--){
			if(vector.get(i-1).equals((E)o)){
				vector.remove(i-1);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int size() {
		return vector.size();
	}
	
	private class AscendingIterator<E> implements Iterator{
		private Vector<E> iterator;
		private int position;
		
		public AscendingIterator(E[] array){
			iterator = new Vector<E>();
			position = 0;
			for(int i = 0; i < array.length; i++){
				iterator.add(array[i]);
			}
		}
		
		@Override
		public boolean hasNext() {
			if(position < iterator.size()){
				return true;
			}
			return false;
		}

		@Override
		public Object next() throws NoSuchElementException {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			Object obj = iterator.get(position);
			position++;
			return obj;
		}
	}
	
	private class DescendingIterator<E> implements Iterator{
		private Vector<E> iterator;
		private int position;
		
		public DescendingIterator(E[] array){
			iterator = new Vector<E>();
			position = 0;
			for(int i = array.length; i != 0; i--){
				iterator.add(array[i-1]);
			}
		}

		@Override
		public boolean hasNext() {
			if(position < iterator.size()){
				return true;
			}
			return false;
		}

		@Override
		public Object next() throws NoSuchElementException {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			Object obj = iterator.get(position);
			position++;
			return obj;
		}
	}
	
}
