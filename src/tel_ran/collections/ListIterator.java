package tel_ran.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
@SuppressWarnings("unchecked")
class ListIterator<E> implements Iterator<E> {
LinkedList<E> linkedList;
LinkedList<E>.NodeList current;
LinkedList<E>.NodeList removable=null;
	public ListIterator(LinkedList<E> linkedList) {
		current=linkedList.head;
		this.linkedList=linkedList;
	}

	@Override
	public boolean hasNext() {
		
		return current != null;
	}

	
	@Override
	public E next() {
		if(current == null)
			throw new NoSuchElementException();
		removable=current;
		current=current.next;
		return (E) removable.data;
	}
	@Override
	public void remove() {
		if(removable==null)
			throw new NoSuchElementException();
		linkedList.removeNode(removable);
		removable=null;
	}

}
