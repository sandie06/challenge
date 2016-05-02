/**
 * Jing Xie
 * 05/01/2016
 */
package lotterySimulator;

import java.util.Random;

public class Ticket {
	
	private int pickNum;
	private int capacity;
	private int[] ticketNum;

	public Ticket(int pickNum, int capacity, int[] ticketNum) {
		this.pickNum = pickNum;
		this.capacity = capacity;
		this.ticketNum=ticketNum;
	}
	public Ticket(int pickNum, int capacity){
		this.pickNum = pickNum;
		this.capacity = capacity;
		this.ticketNum=generatTicketNum();
	}

	private int[] generatTicketNum() {
		int[] ticket=new int [pickNum];
		for (int i=0;i<pickNum;i++){
			Random r = new Random();
			int num = r.nextInt(10);
			ticket[i]=num;
		}
		return ticket;
	}

	public int getPickNum() {
		return pickNum;
	}

	public int getCapacity() {
		return capacity;
	}

	public int[] getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(int[] ticketNum) {
		this.ticketNum = ticketNum;
	}
	
	public String printNums(){
		String s="This ticket is Pick_"+pickNum+"_ticket and value is ";
		for (int i=0; i<ticketNum.length; i++){
			s=s+ticketNum[i];
		}
		return s;
	}

}
