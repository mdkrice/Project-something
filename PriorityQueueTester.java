import java.util.Iterator;

public class PriorityQueueTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PriorityQueue<Integer> priQ = new PriorityQueue<Integer>();
		
		for(int i = 0; i < 10; i ++)	{
			priQ.enqueue(new PriorityQueueItem<Integer>(i));
		}
		Iterator<PriorityQueueItem<Integer>> iter = priQ.iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next() + ", ");
		}
	}

}
