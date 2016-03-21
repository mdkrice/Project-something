import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue implemented as a Binary Heap backed by an array. Implements
 * QueueADT. Each entry in the PriorityQueue is of type PriorityQueueNode<E>.
 * First element is array[1]
 * 
 * @author CS367
 *
 * @param <E>
 */
public class PriorityQueue<E> implements QueueADT<PriorityQueueItem<E>>, Iterable<PriorityQueueItem<E>>	{
	private final int DEFAULT_CAPACITY = 100;

	// Number of elements in heap
	private int currentSize;

	/**
	 * The heap array. First element is array[1]
	 */
	private PriorityQueueItem<E>[] array;

	/**
	 * Construct an empty PriorityQueue.
	 */
	public PriorityQueue()
		{
		currentSize = 0;
		// the below code initializes the array.. similar code used for
		// expanding.
		array = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, DEFAULT_CAPACITY + 1);
		}

	/**
	 * Copy constructor
	 * 
	 * @param pq
	 *            PriorityQueue object to be copied
	 */
	public PriorityQueue(PriorityQueue<E> pq)
		{
		this.currentSize = pq.currentSize;
		this.array = Arrays.copyOf(pq.array, currentSize + 1);
		}

	/**
	 * Adds an item to this PriorityQueue. If array is full, double the array
	 * size.
	 * 
	 * @param item
	 *            object of type PriorityQueueItem<E>.
	 */
	@Override
	public void enqueue(PriorityQueueItem<E> item)	{
		// TODO write appropriate code
		// Check if array is full - double if necessary
		if(this.currentSize == this.array.length)	{
			this.doubleArray();
		}

		// Check all nodes and find if one with equal priority exists.
		// Add to the existing node's list if it does
		
		if(this.currentSize == 0)	{
			this.array[1] = item;
		}
		//Problem is that no item is added to Queue.
		else	{
			boolean foundSamePriority = false;
			int index = 1;
//			for(PriorityQueueItem<E> node : this)	{
//				if (node.getPriority() == item.getPriority())	{
//					foundSamePriority = true;
//					this.array[index].add((E)item);
//					//node.add((E) item);
//				}
//				index ++;
//			}
			
			for(int i = 1; i <= this.currentSize; i ++)	{
				if(this.array[i].getPriority() == item.getPriority())	{
					foundSamePriority = true;
					index = 1;
					this.array[i].add((E)item);
				}
			}
			
			if(!foundSamePriority)	{
				this.array[this.currentSize + 1] = item;
				this.perocolateUp(currentSize + 1);
			}
		}
		
		
		
		
		this.currentSize ++;
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * 
	 * @return the number of items in this PriorityQueue.
	 */
	public int size()
		{
		// TODO write appropriate code
		return this.currentSize;
		}

	/**
	 * Returns a PriorityQueueIterator. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return iterator over the elements in this PriorityQueue
	 */
	public Iterator<PriorityQueueItem<E>> iterator()
		{
		// TODO write appropriate code - see PriortyQueueIterator constructor
		return new PriorityQueueIterator<E>(this);
		}

	/**
	 * Returns the largest item in the priority queue.
	 * 
	 * @return the largest priority item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> peek() throws NoSuchElementException	{
		if(this.currentSize == 0)	{
			throw new NoSuchElementException();
		}
		// TODO fill in appropriate code, replace default return statement
		return this.array[1];
		}

	/**
	 * Removes and returns the largest item in the priority queue. Switch last
	 * element with removed element, and percolate down.
	 * 
	 * @return the largest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> dequeue() throws NoSuchElementException	{
		// TODO
		if (this.currentSize == 0)	{
			throw new NoSuchElementException();
		}
		// Remove first element
		PriorityQueueItem<E> itemToReturn = this.array[1];
		// Replace with last element, percolate down
		this.array[1] = this.array[currentSize];
		this.array[currentSize] = null;
		this.percolateDown(1);
		this.currentSize --;
		return itemToReturn;
		
		}

	/**
	 * Heapify Establish heap order property from an arbitrary arrangement of
	 * items. ie, initial array that does not satisfy heap property. Runs in
	 * linear time.
	 */
	private void buildHeap()	{
		for (int i = currentSize / 2;i > 0;i--)
			percolateDown(i);
	}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear()	{
		this.array = (PriorityQueueItem<E>[])(new Object[DEFAULT_CAPACITY]);
		this.currentSize = 0;
	}

	/**
	 * Internal method to percolate down in the heap. <a
	 * href="https://en.wikipedia.org/wiki/Binary_heap#Extract">Wiki</a>}
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole)	{
		int parent = hole;
		if(this.array[1] == null)	{
			return;
		}

//		while(!done)	{
//			if (parent == 1 && leftChild > 0)	{
//				if(this.array[parent].compareTo(this.array[1]) < 0)	{
//					this.swap(parent, leftChild);
//				}
//				else if(this.array[rightChild].compareTo(this.array[2]) < 0)	{
//					this.swap(parent, rightChild);
//				}
//				else	{
//					done = true;
//				}
//					
//			}
		boolean done = false;
		while (!done)	{
			done = true;
			int leftChild = this.getLeftChild(parent);
			int rightChild = this.getRightChild(parent);
			if(leftChild < 0 && rightChild < 0)	{
				return;
			}
			
			//boolean swappedLeft = false;
			//if rightChild exists, so does left child
			if (rightChild > 0)	{
				if (this.array[rightChild].getPriority() > this.array[leftChild].getPriority())	{
					if(this.array[parent].compareTo(this.array[rightChild]) < 0) {
						this.swap(parent, rightChild);
						parent = rightChild;
						done = false;
					}
				}
				
				else	{	
					if (this.array[parent].compareTo(this.array[leftChild]) < 0) {
						this.swap(parent, leftChild);
						parent = leftChild;
						//swappedLeft = true;
						done = false;
					}
				}
			}
			
			//must be that leftChild exists
			else	{
				if((this.array[parent].compareTo(this.array[leftChild]) < 0)) {
					this.swap(parent, leftChild);
					parent = leftChild;
					done = false;
				}
			}
			
		}
	}
	
	
	private void swap(int parent, int child)	{
		PriorityQueueItem<E> tmp = this.array[parent];
		this.array[parent] = this.array[child];
		this.array[child] = tmp;
	}
	
	
	private void perocolateUp(int bottom)	{
		int currPos = bottom;
		if(currPos == 1)	{
			return;
		}
		//System.out.println(currPos + "" + currPos / 2);
		while(this.array[currPos].compareTo(this.array[currPos/2]) > 0 && currPos != 0)	{
			this.swap(currPos/2, currPos);
			currPos = currPos / 2;
			//System.out.println("Swappy Dap");
			if(currPos == 1) return;
		}
	}
	
	private int getRightChild(int parentPos)	{
		int rightChild = (parentPos * 2) + 1;
		if(this.array.length <= rightChild)	{
			return -1;
		}
		if(this.array[rightChild] == null)	{
			return -1;
		}
		return rightChild;
	}
	
	private int getLeftChild(int parentPos)	{
		int leftChild = (parentPos *2);
		if(this.array.length <= leftChild)	{
			return -1;
		}
		if(this.array[leftChild] == null)	{
			return -1;
		}
		return leftChild;
	}

	/**
	 * Internal method to expand array.
	 */
	private void doubleArray()
		{
		PriorityQueueItem<E>[] newArray;

		newArray = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, array.length * 2);

		for (int i = 0;i < array.length;i++)
			newArray[i] = array[i];
		array = newArray;
		}

	@Override
	public boolean isEmpty()	{
		if(currentSize == 0)	{
			return true;
		}
		return false;
	}
}
