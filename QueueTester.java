
public class QueueTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PriorityQueue<Integer> testQ = new PriorityQueue<Integer>();
		for(int i = 0; i < 15; i ++){
			testQ.enqueue(new PriorityQueueItem<Integer>(i));
		}
		
		System.out.println(testQ.peek());
		
		System.out.println(testQ.size());
		
		for(int i = 0; i < 5; i++)
			System.out.print(testQ.dequeue() + ", ");
		
		System.out.println("\n" + testQ.size());
		
		System.out.println(testQ.peek());
		
		

	}

}
