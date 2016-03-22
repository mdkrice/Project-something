import java.util.Iterator;

public class QueueTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PriorityQueue<Integer> testQ = new PriorityQueue<Integer>();
		for(int i = 0; i < 15; i ++){
			//System.out.println(i);
			testQ.enqueue(new PriorityQueueItem<Integer>(i));
			//System.out.println("fini");
		}
		
		System.out.println(testQ.peek().getPriority());
		
		System.out.println(testQ.size());
		
		for(int j = 0; j < 4; j++)	{
			testQ.enqueue(new PriorityQueueItem<Integer>(j));
		}
		
		for(PriorityQueueItem<Integer> o : testQ)	{
			System.out.println(o.getPriority() + " Ja");
		}
			
		
		System.out.println(testQ.size());
		
		System.out.println(testQ.isEmpty());
		
		//System.out.println(testQ.peek().getPriority());
		
		

	}

}
