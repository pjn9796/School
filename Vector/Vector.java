package main.java.edu.sdsu.cs.datastructures;

import java.util.AbstractList;
import java.util.Collection;

public class Vector<E> extends AbstractList<E>{
	protected E[] vector;
	protected int currentSize;
	protected int capacityIncrement;
	
	@SuppressWarnings("unchecked")
	public Vector(){
		currentSize = 0;
		capacityIncrement = 0;
		vector = (E[])(new Object[10]);
	}
	
	@SuppressWarnings("unchecked")
	public Vector(Collection<E> c){
		currentSize = 0;
		capacityIncrement = 0;
		E[] array = (E[])(c.toArray());
		for(int i = 0; i < array.length; i++){
			vector[i] = array[i];
			currentSize++;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Vector(int initialCapacity, int blockSize){
		currentSize = 0;
		this.capacityIncrement = blockSize;
		vector = (E[])(new Object[initialCapacity]);
	}
	
	@Override
	public E get(int index) {
		if(index < 0 || index > currentSize-1)
			throw new IndexOutOfBoundsException();
		else{
			return vector[index];
		}
	}

	@Override
	public int size() {
		return currentSize;
	}
	
	@SuppressWarnings("unchecked")
	public E set(int index, Object e){
		if(index < 0 || index > currentSize-1)
			throw new IndexOutOfBoundsException();
		else{
			E oldE = vector[index];
			vector[index] = (E)e;
			return oldE;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void add(int index, Object e){
		if(index < 0 || index > currentSize+1)
			throw new IndexOutOfBoundsException();
		else{
			if(currentSize+1 > vector.length)
				grow();
				for(int i = index; i < currentSize-2; i++){
					vector[i+1] = vector[i];
				}
				vector[index] = (E)e;
				currentSize++;
		}
	}
	
	public E remove(int index){
		if(index < 0 || index > currentSize-1)
			throw new IndexOutOfBoundsException();
		else{
			E oldE = vector[index];
			for(int i = index; i < currentSize-1; i++){
				vector[i] = vector[i+1];
			}
			vector[currentSize-1] = null;
			currentSize--;
			if(currentSize < vector.length-(int)(capacityIncrement*1.5))
				shrink();
			return oldE;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void grow(){
		E[] array = vector;
		int increment = capacityIncrement;
		if(increment <= 0)
			increment = 2*currentSize;
		vector = (E[])(new Object[array.length+increment]);
		for(int i = 0; i < array.length; i++){
			vector[i] = array[i];
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void shrink(){
		E[] array = vector;
		if(array.length-capacityIncrement < 0)
			throw new IndexOutOfBoundsException();
		vector = (E[])(new Object[array.length-capacityIncrement]);
		for(int i = 0; i < vector.length; i++){
			vector[i] = array[i];
		}
	}
}
