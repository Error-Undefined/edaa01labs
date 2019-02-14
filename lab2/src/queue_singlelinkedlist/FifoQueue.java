package queue_singlelinkedlist;

import java.util.*;

public class FifoQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private QueueNode<E> last;
	private int size;

	public FifoQueue() {
		super();
		last = null;
		size = 0;
	}

	/**
	 * Inserts the specified element into this queue, if possible post: The
	 * specified element is added to the rear of this queue
	 * 
	 * @param e
	 *            the element to insert
	 * @return true if it was possible to add the element to this queue, else false
	 */
	public boolean offer(E e) {
		if (size == 0) {
			last = new QueueNode<E>(e);
			last.next = last;
		} else {
			QueueNode<E> temp = new QueueNode<E>(e);
			temp.next = last.next;
			last.next = temp;
			last = temp;
		}
		size++;
		return true;
	}

	/**
	 * Returns the number of elements in this queue
	 * 
	 * @return the number of elements in this queue
	 */
	public int size() {
		return size;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty
	 * 
	 * @return the head element of this queue, or null if this queue is empty
	 */
	public E peek() {
		if (last == null) {
			return null;
		}
		return last.next.element;
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is empty.
	 * post: the head of the queue is removed if it was not empty
	 * 
	 * @return the head of this queue, or null if the queue is empty
	 */
	public E poll() {
		if (size == 0) {
			last = null;
			return null;
		}
		QueueNode<E> temp = last.next;
		last.next = last.next.next;
		size--;
		return temp.element;
	}

	/**
	 * Appends the specified queue to this queue post: all elements from the
	 * specified queue are appended to this queue. The specified queue (q) is empty
	 * after the call.
	 * 
	 * @param q
	 *            the queue to append
	 * @throws IllegalArgumentException
	 *             if this queue and q are identical
	 */
	public void append(FifoQueue<E> q) {
		if (q == this) {
			throw new IllegalArgumentException();
		}
		if (q.size == 0) {
			return;
		} else {
			QueueNode<E> finalFirst;

			if (this.size != 0) {
				finalFirst = this.last.next;
			} else {
				finalFirst = q.last.next;
			}
			QueueNode<E> finalLast = q.last;
			this.last = finalLast;
			this.last.next = finalFirst;
			this.size += q.size;
			q.size = 0;
			q.last = null;
		}
	}

	/**
	 * Returns an iterator over the elements in this queue
	 * 
	 * @return an iterator over the elements in this queue
	 */
	public Iterator<E> iterator() {
		return new QueueIterator();
	}

	private static class QueueNode<E> {
		E element;
		QueueNode<E> next;

		private QueueNode(E x) {
			element = x;
			next = null;
		}
	}

	private class QueueIterator implements Iterator<E> {

		private QueueNode<E> pos;

		private QueueIterator() {
			if (last != null) {
				pos = last.next;
			}
		}

		public boolean hasNext() {
			if (last == null || pos.next == last.next) {
				return false;
			}
			return true;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			QueueNode<E> temp = pos;
			pos = pos.next;
			return temp.element;
		}

	}

}
