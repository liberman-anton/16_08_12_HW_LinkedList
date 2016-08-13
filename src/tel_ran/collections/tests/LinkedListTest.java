package tel_ran.collections.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import tel_ran.collections.LinkedList;

public class LinkedListTest {
	private static final int MAX_ELEMENTS = 1000;
	private static final int N_ITERATIONS = 100;
	Random gen=new Random();

	@Test
	public void reverseTest() {
		for (int i = 0; i < N_ITERATIONS; i++) {
			LinkedList<Integer> list =
					createLinkedList(gen.nextInt(MAX_ELEMENTS));
			LinkedList<Integer> reversedExpected = getReversedList(list);
			list.reverse();
			assertEquals(reversedExpected, list);
		}
	}

	private LinkedList<Integer> getReversedList(LinkedList<Integer> list) {
		LinkedList<Integer> resList=new LinkedList<>();
		for(Integer number: list)
			resList.add(0, number);
		return resList;
	}

	private LinkedList<Integer> createLinkedList(int nElements) {
		LinkedList<Integer> resList=new LinkedList<>();
		for(int i=0; i<nElements;i++)
			resList.add(gen.nextInt());
		return resList;
	}
	@Test
	public void hasLoopTest(){
		for (int i = 0; i < N_ITERATIONS; i++) {
			int nElements = gen.nextInt(MAX_ELEMENTS);
			if(nElements < 0) nElements = Math.abs(nElements);
			if(nElements == 0) nElements = 1;
			int half = Math.abs((nElements / 2));
			if(half == 0) half = 1;
			LinkedList<Integer> list = createLinkedList(nElements);
			assertFalse(list.hasLoop());
			int indTo = gen.nextInt(half);
			int indFrom = half + gen.nextInt(half);
			list.setLoop(indTo, indFrom);
			assertTrue(list.hasLoop());
		}
	}
	@Test
	public void hasLoopNoPreviousTest(){
		for (int i = 0; i < N_ITERATIONS; i++) {
			int nElements = gen.nextInt(MAX_ELEMENTS);
			if(nElements < 0) nElements = Math.abs(nElements);
			if(nElements == 0) nElements = 1;
			int half = 1 + nElements / 2;
			LinkedList<Integer> list = createLinkedList(nElements);
			assertFalse(list.hasLoopPreviousCorrupted());
			int indTo = gen.nextInt(half);
			int indFrom = gen.nextInt(N_ITERATIONS);
			if(indTo > indFrom){
				indTo = indTo + indFrom;
				indFrom = indTo - indFrom;
				indTo = indTo - indFrom;
			}
			if(list.setLoop(indTo, indFrom))
				assertTrue(list.hasLoopPreviousCorrupted());
		}
	}
	@Test
	public void testShared() {
		for(int i = 0; i < N_ITERATIONS; i++){
			int nElements = gen.nextInt(MAX_ELEMENTS);
			if(nElements < 0) nElements = - nElements;
			if(nElements == 0) nElements = 1;
			LinkedList<Integer> list = createLinkedList(nElements);
			LinkedList<Integer> other = createLinkedList(nElements);
			
			assertEquals(-1, list.getSharedIndex(other));
			
			int indShared = gen.nextInt(MAX_ELEMENTS);
			if(indShared < 0) indShared = - indShared;
			boolean isShared = list.setSharedList(other, indShared);
			if(isShared)
				assertEquals(indShared, list.getSharedIndex(other));
			else
				assertEquals(-1, list.getSharedIndex(other));
		}
	}
	@Test
	public void testFixLoop() {
		for(int i = 0; i < N_ITERATIONS; i++){
			int nElements = gen.nextInt(MAX_ELEMENTS);
			if(nElements < 0) nElements = - nElements;
			if(nElements == 0) nElements = 1;
			LinkedList<Integer> list = createLinkedList(nElements);
			assertFalse(list.hasLoopPreviousCorrupted());
			
			int indTo = gen.nextInt(list.getSize());
			int indFrom = gen.nextInt(list.getSize());
			if(indTo > indFrom){
				indTo = indTo + indFrom;
				indFrom = indTo - indFrom;
				indTo = indTo - indFrom;
			}
			list.setLoop(indTo, indFrom);
			
			list.fixLoop();
			
			assertFalse(list.hasLoopPreviousCorrupted());
		}
	}
	@Test
	public void testFixLoopUseHas() {
		for(int i = 0; i < N_ITERATIONS; i++){
			int nElements = gen.nextInt(MAX_ELEMENTS);
			if(nElements < 0) nElements = - nElements;
			if(nElements == 0) nElements = 1;
			LinkedList<Integer> list = createLinkedList(nElements);
			assertFalse(list.hasLoopPreviousCorrupted());
			
			int indTo = gen.nextInt(list.getSize());
			int indFrom = gen.nextInt(list.getSize());
			if(indTo > indFrom){
				indTo = indTo + indFrom;
				indFrom = indTo - indFrom;
				indTo = indTo - indFrom;
			}
			list.setLoop(indTo, indFrom);
			
			list.fixLoopUseHas();
			
			assertFalse(list.hasLoopPreviousCorrupted());
		}
	}
}
