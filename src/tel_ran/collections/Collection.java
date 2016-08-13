package tel_ran.collections;

import java.util.Iterator;

public interface Collection<T> extends Iterable<T>{
	void add(T number);

	void remove(T number);

	default void retainAll(Collection<T> collection){
		Iterator<T>it=iterator();
		while(it.hasNext()){
			T data=it.next();
			if(!collection.contains(data))
				it.remove();
		}
	}

	default void removeAll(Collection<T> collection){
		Iterator<T>it=iterator();
		while(it.hasNext()){
			T data=it.next();
			if(collection.contains(data))
				it.remove();
		}
	}

	boolean contains(T number);

}
