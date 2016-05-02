/**
 * Jing Xie
 * 05/01/2016
 */
package lotterySimulator;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class test {
	
	@BeforeClass
	public static void setUp(){
		Lottery_Machine.setup();
		Lottery_Machine.play_lottery();
		Lottery_Machine.generatReport();
	}
	@Test
	public void drawingTotalTickeatsNum(){
		if(Lottery_Machine.getAllTickets().size() != 150)
		fail("The sum of total tickets are not right");
	}
	
	@Test
	public void checkAllTicketContainsWinningTickets(){
		assertEquals(true, Lottery_Machine.getAllTickets().contains(Lottery_Machine.generateWinningTickets().get(0)));
	}
	
}
