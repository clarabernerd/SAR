package course_14_09;

public class Main {
	
	public static void main(String args[]) throws InterruptedException {
		
		Broker broker = new Broker("Test 1");
		Task1 task1 = new Task1(broker);
		Task2 task2 = new Task2(broker);
		
		task1.start();
		task2.start();
		
		try {
			task1.join();
			task2.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
