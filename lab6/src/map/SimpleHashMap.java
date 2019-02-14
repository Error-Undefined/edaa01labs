package map;

public class SimpleHashMap<K, V> implements Map<K, V> {

	private Entry<K, V>[] table;

	private static final double loadFactor = 0.75;

	private int size;
	private int capacity;

	/**
	 * Constructs an empty hashmap with the default initial capacity (16) and the
	 * default load factor (0.75).
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashMap() {
		this.capacity = 16;
		this.table = (Entry<K, V>[]) new Entry[capacity];
		this.size = 0;
	}

	/**
	 * Constructs an empty hashmap with the specified initial capacity and the
	 * default load factor (0.75).
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashMap(int capacity) {
		this.capacity = capacity;
		this.table = (Entry<K, V>[]) new Entry[capacity];
		this.size = 0;
	}

	/**
	 * Returns the value of the object, or null if it didn't exist.
	 */
	@SuppressWarnings("unchecked")
	public V get(Object arg0) {
		K key = (K) arg0;
		Entry<K, V> result = find(index(key), key);
		return result == null ? null : result.value;
	}

	/**
	 * Return true if the map is empty, else false.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Associates the specified value with the specified key in this map. Returns an
	 * old value if it already existed, otherwise null.
	 */
	public V put(K arg0, V arg1) {
		Entry<K, V> entry = find(index(arg0), arg0);

		// Case entry existed: update the value, return old value.
		if (entry != null) {
			V ret = entry.value;
			entry.value = arg1;
			return ret;
		}

		// Case entry didn't exist: create a new entry.
		entry = new Entry<K, V>(arg0, arg1);
		int index = index(arg0);
		Entry<K, V> parent = table[index];

		// Check if the position is occupied.
		if (parent == null) {
			table[index] = entry;
		} else {
			// Couple the current list at the position onto the new element.
			entry.next = parent;
			table[index] = entry;
		}

		size++;

		// Check if table needs rehashing after entering a value.
		if ((double) size / (double) capacity > loadFactor) {
			rehash();
		}
		return null;
	}

	/**
	 * Remove the object object. Returns the value or null if object didn't exist.
	 */
	@SuppressWarnings("unchecked")
	public V remove(Object arg0) {

		K key = (K) arg0;
		int index = index(key);
		Entry<K, V> entry = find(index, key);

		// Case: the object didn't exist in the map.
		if (entry == null) {
			return null;
		}
		// We can now reduce the size because the element exists in the map and will be removed.
		size--;

		// Case: the object was the first in the list.
		if (table[index].key.equals(key)) {
			V val = table[index].value;
			table[index] = table[index].next;
			return val;
		}

		// Case: the object is in the list, but not in the first in the list.
		Entry<K, V> parent = table[index];

		// Move to the element before the chosen one in the list.
		while (parent.next != entry) {
			parent = parent.next;
		}

		// Save the value to return.
		V val = parent.next.value;
		
		// Remove the element by removing the reference, garbage collection does the
		// work.
		parent.next = parent.next.next;

		return val;
	}

	/**
	 * Returns the size of the map.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns a string representation of all the entries in the map.
	 */
	public String show() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < table.length; i++) {
			Entry<K, V> entry = table[i];
			if (entry != null) {
				sb.append("\n");
			}
			while (entry != null) {
				sb.append(entry.toString());
				sb.append(" ; ");
				entry = entry.next;
			}
		}
		return sb.toString();
	}

	/*
	 * Rehashes the table with double the capacity.
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {

		capacity = 2 * capacity;

		Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[capacity];

		for (int i = 0; i < table.length; i++) {
			Entry<K, V> entry = table[i];// Get the element to transfer.

			// Don't do anything if the element is null.
			while (entry != null) {
				int index = index(entry.key);

				// Move the rest of the list to a different reference.
				Entry<K, V> next = entry.next;
				entry.next = null;

				// Check if the new position is occupied.
				Entry<K, V> parent = newTable[index];

				if (parent == null) {
					newTable[index] = entry;
				} else {
					// Couple the whole list onto the new element.
					entry.next = parent;
					newTable[index] = entry;
				}

				entry = next;
			}
		}
		// Update the table.
		table = newTable;
	}

	/*
	 * Returns the index that will be used for the key K.
	 */
	private int index(K key) {
		return key.hashCode() % capacity >= 0 ? key.hashCode() % capacity : key.hashCode() % capacity + capacity;
	}

	/*
	 * Returns the entry with index index and key key in the table, or null if it
	 * doesn't exist.
	 */
	private Entry<K, V> find(int index, K key) {
		Entry<K, V> e = table[index];
		if (e == null) {
			return null;
		}
		if (e.key.equals(key)) {
			return e;
		}
		while (e.next != null) {
			e = e.next;
			if (e.key.equals(key)) {
				return e;
			}
		}
		return null;
	}

	/*
	 * Private class for a map entry.
	 */
	private static class Entry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;
		private Entry<K, V> next;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}

		public String toString() {
			return key + "=" + value;
		}

	}

}
