/**
 * Jing Xie
 * 05/01/2016
 */
package lotterySimulator;

import java.util.Random;
import java.util.UUID;

public class Customer {
	
	//unique ID to identify the customer
	private UUID ID;
	
	// how many tickets the customer wants to purchase
	private int ticketNum;
	
	public Customer() {
		ID = UUID.randomUUID();
		Random r = new Random();
		ticketNum = r.nextInt(5)+1;
		
	}
	
	public UUID getID(){
		return ID;
	}

	public int getTicketNum() {
		return ticketNum;
	}

}
