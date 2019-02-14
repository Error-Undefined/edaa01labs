package testqueue;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import queue_singlelinkedlist.FifoQueue;

public class TestAppendFifoQueue {

	private FifoQueue<Integer> queue1;
	private FifoQueue<Integer> queue2;

	@Before
	public void setUp() throws Exception {
		queue1 = new FifoQueue<Integer>();
		queue2 = new FifoQueue<Integer>();
	}

	@After
	public void tearDown() throws Exception {
		queue1 = null;
		queue2 = null;
	}

	/**
	 * Test appending two empty queues
	 */
	@Test
	public final void testTwoEmptyQueues() {
		queue1.append(queue2);
		assertTrue(queue1.size() == 0);
		assertTrue(queue1.isEmpty());
		assertTrue(queue2.size() == 0);
		assertTrue(queue2.isEmpty());
	}

	/**
	 * Test appending a full queue to an empty one.
	 */
	@Test
	public final void testFullQueueToOneEmptyQueue() {
		queue2.offer(new Integer(1));
		queue2.offer(new Integer(2));
		queue2.offer(new Integer(3));
		queue2.offer(new Integer(4));
		queue2.offer(new Integer(5));
		queue1.append(queue2);

		assertEquals("Second queue should be empty", 0, queue2.size());
		assertTrue(queue2.isEmpty());
		assertEquals("First queue should have 5 elements", 5, queue1.size());

		Iterator<Integer> itr = queue1.iterator();
		int i = 1;
		while (itr.hasNext()) {
			assertEquals("Order should be untouched", itr.next().intValue(), i);
			i++;
		}
	}
	
	/**
	 * Test appending an empty queue to a full one.
	 */
	@Test
	public final void testOneEmptyQueueToFullQueue() {
		queue2.offer(new Integer(1));
		queue2.offer(new Integer(2));
		queue2.offer(new Integer(3));
		queue2.offer(new Integer(4));
		queue2.offer(new Integer(5));

		queue2.append(queue1);

		assertTrue(queue1.size() == 0);
		assertTrue(queue1.isEmpty());
		assertTrue(queue2.size() == 5);

		Iterator<Integer> itr = queue2.iterator();
		int i = 1;
		while (itr.hasNext()) {
			assertEquals("Order should be untouched", itr.next().intValue(), i);
			i++;
		}
	}

	/**
	 * Test appending two full queues.
	 */
	@Test
	public final void testTwoFullQueues() {
		queue1.offer(new Integer(1));
		queue1.offer(new Integer(2));
		queue1.offer(new Integer(3));
		queue1.offer(new Integer(4));
		queue1.offer(new Integer(5));
		queue2.offer(new Integer(6));
		queue2.offer(new Integer(7));
		queue2.offer(new Integer(8));
		queue2.offer(new Integer(9));
		queue2.offer(new Integer(10));

		queue1.append(queue2);

		assertEquals("Length of queue 1 should be 5+5=10", 10, queue1.size());
		assertEquals("queue2 should be empty", 0, queue2.size());
		assertTrue("queue2 should be empty", queue2.isEmpty());

		Iterator<Integer> itr = queue1.iterator();
		int i = 1;
		while (itr.hasNext()) {
			assertEquals("Order should be untouched", itr.next().intValue(), i);
			i++;
		}
	}

	/**
	 * Test appending the same queue to itslef;
	 */
	@Test
	public final void testSameQueue() {
		for (int i = 0; i < 5; i++) {
			queue1.offer(new Integer((int) Math.random() % 20));
		}

		queue2 = queue1;
		try {
			queue1.append(queue2);
			fail("Should throw exception");
		} catch (IllegalArgumentException e) {
			// sucess
		}
	}

}
