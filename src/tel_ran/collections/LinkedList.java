package tel_ran.collections;

import java.util.Iterator;

//import tel_ran.collections.LinkedList.NodeList;
@SuppressWarnings("unchecked")
public class LinkedList<E> implements List<E> {
class NodeList {
	public Object data;
	public NodeList next;
	public NodeList prev;
	public NodeList step(int diferent) {
		NodeList current = this;
		for(int i = 0; i < diferent; i++){
			current = current.next;
		}
		return current;
	}
}

	NodeList head;
 	NodeList tail;
 	public LinkedList(NodeList head) {
		this.head = head;

	}

	public LinkedList() {
		super();
	}
	@Override
	public void add(E data) {
		NodeList newNode=new NodeList();
		newNode.data=data;
		if(head==null){
			head=tail=newNode;
		}
		else
			addAfterTail(newNode);
	}

	@Override
	public void remove(E data) {
		NodeList node=getNode(data);
		if (node != null) {
			removeNode(node);
		}
		
	}

	private NodeList getNode(E data) {
		NodeList node=null;
		for(node=head; node != null && !node.data.equals(data);node = node.next){}
		return node;
	}
	

	@Override
	public Iterator<E> iterator() {
		return new ListIterator<E>(this);
	}

	@Override
	public boolean contains(E number) {
		NodeList current;
		for(current=head; current != null; current=current.next){
			if(current.data != null ){
				if(current.data.equals(number))
					return true;
			}
			else if (number==null)
				return true;
				

		}
		return false;
	}

	@Override
	public void add(int index, E element) {
		NodeList newNode=new NodeList();
		newNode.data=element;
		if(head==null){
			head=tail=newNode;
		}
		else {
			NodeList current;
			int ind=0;
			for(current=head;current != null && ind < index;ind++,
					current=current.next) {}
			if(current == null){
				//add after tail
				addAfterTail(newNode);
			}
			else {
				if(ind == 0)
					addBeforeHead(newNode);
				else {
					addBeforeNode(current,newNode);
				}
			}
		}
	}

	private void addBeforeNode(NodeList current, NodeList node) {
		NodeList prev=current.prev;
		node.next=current;
		node.prev=prev;
		current.prev=node;
		prev.next=node;
		
	}

	private void addBeforeHead(NodeList node) {
		head.prev=node;
		node.next=head;
		head=node;
		
	}

	private void addAfterTail(NodeList node) {
		tail.next=node;
		node.prev=tail;
		tail=node;
		
	}

	@Override
	public int indexOf(E element) {
		NodeList node=null;
		int ind=0;
		
		for(node=head;node != null && !node.data.equals(element);node=node.next,ind++){}
		return node != null?ind:-1;
	}

	@Override
	public int lastIndexOf(E element) {
		NodeList node=null;
		int ind=0;
		int indRes=-1;
		for(node=head; node != null;node=node.next,ind++){
			if(node.data.equals(element))
				indRes=ind;
		}
		return indRes;
	}

	

	

	@Override
	public E remove(int index) {
		if(head==null)
			return null;
		NodeList current;
		int ind=0;
		if(index < 0)
			return null;
		if(index==0){
			return removeHead();
		}
		for(current=head; current != null && ind < index; current=current.next,ind++)
		{}
		if(current==null)
			return null;
		return removeNode(current);	
	}

	
	E removeNode(NodeList current) {
		if(current==head)
			return removeHead();
		if(current==tail)
			return removeTail();
		NodeList before=current.prev;
		NodeList after=current.next;
		E res=(E) current.data;
		before.next=after;
		after.prev=before;
		return res;
	}

	private E removeTail() {
		if(tail == null)
			return null;
		E res=(E) tail.data;
		tail=tail.prev;
		if(tail!=null)
			tail.next=null;
		else 
			head=null;
		return res;
	}

	private E removeHead() {
		if(head == null)
			return null;
		E res=(E) head.data;
		head=head.next;
		if(head != null)
			head.prev=null;
		else
			tail=null;
		return res;
	}

	@Override
	public boolean isEmpty() {
		return head==null;
	}
/**
 * 
 * @param indTo - index of the element where a loop is directed
 * @param indFrom - index of the element from where a loop is directed
 * @return true if a loop has been set
 */
	public boolean setLoop(int indTo, int indFrom){
		NodeList nodeTo=null,current=head;
		for(int i=0;current != null && i < indFrom; i++,current=current.next){
			if(i==indTo)
				nodeTo=current;
		}
		if(current==null || nodeTo==null)
			return false;
		current.next=nodeTo;
	return true;
}
	/**
	 * recreating of the linked list for reversing
	 */
	public void reverse(){
		NodeList left=head,right=tail;
		while(left != right && right.next != left){
			swapData(left,right);
			left=left.next;
			right=right.prev;
		}
	}
	public boolean equals(Object other){
		Iterator<E> it=((List<E>)other).iterator();
		for(E element: this){
			if(!it.hasNext() || !element.equals(it.next()))
				return false;
		}
		return true;
	}
private void swapData(NodeList left, NodeList right) {
		Object data=left.data;
		left.data=right.data;
		right.data=data;
		
	}

/**
 * using previous (two directional list)
 * @return true if a loop exists
 */
	public boolean hasLoop(){
		if (head==tail)
			return false;
		for (NodeList current=head.next ; current.next!=null ; current=current.next){
			if (current.next.prev!=current)
				return true;
		}
		return false;
}
	/**
	 * using previous references is prohibited 
	 * using additional arrays/collections is prohibited
	 * O[N] - algorithm complexity 
	 * @return true if a loop exists
	 */
	public boolean hasLoopPreviousCorrupted(){
		if (head==null || head.next==null)
			return false;
		NodeList current=head;
		NodeList fast=head.next;
		while(fast != null && fast.next != null && current != fast){
			current=current.next;
			fast=fast.next.next;
		}
		
		return current==fast;
	}
	/**
	 * tail of other (given) list will refer to the node of "this" with indShared index 
	 * @param other
	 * @param indShared
	 * @return true if indShered has the correct value
	 */
	public boolean setSharedList(LinkedList<E> other, int indShared){
		NodeList tailOther = other.head;
		if(tailOther == null)return false;
		while( tailOther.next != null){
			tailOther = tailOther.next;
		}
		int i = 0;
		NodeList current;
		for(current = head; current != null; current = current.next){
			if(i == indShared){
				tailOther.next = current;
				return true;
			}
			i++;
		}
		return false;
	}
	/**
	 * getting index of "this" linked list designing shared node
	 * @param other
	 * @return 0 or positive value if a sharing exist otherwise -1
	 */
	public int getSharedIndex(LinkedList<E> other){
		int sizeThis = this.getSize();
		int sizeOther = other.getSize();
		NodeList currentThis = this.head;
		NodeList currentOther = other.head;
		int diferent = sizeThis - sizeOther;
		int indShared = 0;
		
		if(diferent >= 0){
			currentThis = currentThis.step(diferent);
			indShared = diferent;
		} else {
			currentOther = currentOther.step(-diferent); 
		}
		while(currentThis != currentOther){
			currentThis = currentThis.next;
			currentOther = currentOther.next;
			indShared++;
		}
		if(currentThis != null)
			return indShared;
		return -1;
	}

	public int getSize() {
		int res = 0;
		NodeList current;
		for(current = head; current != null; current = current.next){
			res++;
		}
		return res;
	}
	public void fixLoop(){
		if(!this.hasLoopPreviousCorrupted()) return;
		if(head == null || head.next == null) return;
		NodeList nodeInLoop = this.findNodeInLoop();
		if(nodeInLoop != null){
			NodeList newHead = nodeInLoop.next;
			newHead.prev = null;
			nodeInLoop.next = null;
			
			LinkedList<E> newList = new LinkedList<E>(newHead);
			int ind = this.getSharedIndex(newList);
			nodeInLoop.next = newHead;
			newHead.prev = nodeInLoop;
			
			NodeList secondInLoop = this.getNodeOfIndex(ind);
			NodeList startLoop = this.findStartLoop(secondInLoop);
			startLoop.next = null;
			
		}
	}
	
	private NodeList findNodeInLoop() {
		NodeList current=head;
		NodeList fast=head.next;
		while(fast != null && fast.next != null && current != fast){
			current=current.next;
			fast=fast.next.next;
		}
		return current;
	}

	private NodeList findStartLoop(NodeList secondInLoop) {
		NodeList startLoop = secondInLoop;
		while(startLoop.next != secondInLoop){
			startLoop = startLoop.next;
		}
		return startLoop;
	}
	private NodeList getNodeOfIndex(int index) {
		NodeList node=head;
		for(int i = 0; node != null && i != index;i++,node = node.next){}
		return node;
	}

	
	public void fixLoopUseHas(){
		if(!this.hasLoopPreviousCorrupted()) return;
		if(head == null || head.next == null) return;
		NodeList current;
		boolean isLoop = false;
		NodeList loop = null;
		for(current = head; current.next != null; current = current.next){
			NodeList newHead = current.next;
			newHead.prev = null;
			current.next = null;
			LinkedList<E> newList = new LinkedList<E>(newHead);
			if(!newList.hasLoopPreviousCorrupted()&&!isLoop){
				isLoop = true;
				loop = current;
			}		
			current.next = newHead;
			newHead.prev = current;
			
			if(loop != null && current.next == loop){
				current.next = null;
				return;
			}
		}
	}
}