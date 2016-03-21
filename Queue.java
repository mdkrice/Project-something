import java.util.*;

/**
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class Queue<E> implements QueueADT<E>
	{

	private final int defaultCapacity = 10;
	private E[] items;
	private int numItems;
	private int rearIndex;
	private int frontIndex;
	// TODO
	// You may use a naive expandable circular array or a chain of listnodes.
	// You may NOT use Java's predefined classes such as ArrayList or
	// LinkedList.
	

	public Queue()	{
		this.items = (E[])(new Object[defaultCapacity]);
		this.numItems = 0;
		this.rearIndex = 0;
		this.frontIndex = 0;
		
	}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item)	{
		
		// check for full array and expand if necessary
	    if (this.items.length == this.size()) {
	    	this.expandCapacity();
	    }

	    // insert new item at rear of queue
	    this.items[this.rearIndex] = item;
	    
	    // use auxiliary method to increment rear index with wraparound
	    this.rearIndex = incrementIndex(rearIndex);
	    
	    this.numItems ++;

	}
	
	private int incrementIndex(int index) 	{
	    if (index == items.length-1) 
	        return 0;
	    else 
	        return index + 1;
	}

	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue() throws EmptyQueueException	{
		if(this.isEmpty())	{
			throw new EmptyQueueException();
		}
		E itemToReturn = this.items[frontIndex];
		
		frontIndex = this.incrementIndex(frontIndex);
		
		this.numItems --;
		
		return itemToReturn;
	}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek()	throws EmptyQueueException	{
		if(this.isEmpty())	{
			throw new EmptyQueueException();
		}
		
	
		return this.items[frontIndex];
	}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty()	{
		
		return this.size() == 0;
	}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear()	{
		this.items = (E[])(new Object[defaultCapacity]);
		this.numItems = 0;
		this.frontIndex = 0;
		this.rearIndex = 0;
	}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size()	{
		
		return this.numItems;
	}

	private void expandCapacity()	{
		//expanding should be done using the naive copy-all-elements approach
		E[] tmp = (E[])(new Object[items.length*2]);
        System.arraycopy(items, frontIndex, tmp, frontIndex,
	                 items.length-frontIndex);
        if (frontIndex != 0) {
            System.arraycopy(items, 0, tmp, items.length, frontIndex);
        }
        items = tmp;
	    rearIndex = frontIndex + numItems - 1;

	}
}
