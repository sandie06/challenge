/**
 * Jing Xie
 * 05/01/2016
 */
package lotterySimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Lottery_Machine {

	/*
	 * All lottery ticket types Currently 3, 4, 5 and can add additional ticket
	 * types
	 */

	private static Map<Integer, Integer> ticket_type_capacity;
	private static Map<Integer, Set<Ticket>> eachTypeTicket_records;
	private static Map<Customer, Set<Ticket>> customers_tickets;
	private static ArrayList<Ticket> winningTickets;
	private static ArrayList<Customer> customersAttemptToPurchaseSoldoutTicket;

	public static void printWinningTickets() {
		for (int i = 0; i < winningTickets.size(); i++) {
			System.out.println(winningTickets.get(i).printNums());
		}
	}

	/*
	 * Setup the config for lottery info. Put each type and the capacity of
	 * tickets. Additional types of tickets can be added.
	 */
	public static void setup() {
		ticket_type_capacity = new HashMap<Integer, Integer>();
		eachTypeTicket_records = new HashMap<Integer, Set<Ticket>>();
		customers_tickets = new HashMap<Customer, Set<Ticket>>();
		winningTickets = new ArrayList<Ticket>();
		customersAttemptToPurchaseSoldoutTicket = new ArrayList<Customer>();

		ticket_type_capacity.put(3, 50);
		ticket_type_capacity.put(4, 40);
		ticket_type_capacity.put(5, 60);
		checkInvalidInput();

		for (int type : ticket_type_capacity.keySet()) {
			Set<Ticket> tickets = new HashSet<Ticket>();
			eachTypeTicket_records.put(type, tickets);
		}

	}
	/*
	 * public static void main(String[] args) { setup(); play_lottery();
	 * printWinningTickets(); generatReport(); System.out.println(
	 * "total tickets "+Lottery_Machine.getAllTickets().size()); }
	 */

	private static void checkInvalidInput() {
		for (int typeInput: ticket_type_capacity.keySet()){
			if (typeInput<=0 || ticket_type_capacity.get(typeInput)<=0){
				System.out.println("Invalid ticket type or capacity. Please setup the ticket data again");
				System.exit(1);
			}
		}
	}

	public static Set<Ticket> getAllTickets() {
		Set<Ticket> allTickets = new HashSet<Ticket>();
		for (int type : eachTypeTicket_records.keySet()) {
			allTickets.addAll(eachTypeTicket_records.get(type));
		}
		return allTickets;
	}

	public static void play_lottery() {
		while (!lotteryMachineIsEmpty()) {
			Customer c = new Customer();
			Set<Ticket> customer_tickets = new HashSet<Ticket>();
			customers_tickets.put(c, customer_tickets);
			Set<Integer> soldout_tickets_types = new HashSet<Integer>();
			while (customers_tickets.get(c).size() < c.getTicketNum()) {
				if (!lotteryMachineIsEmpty()) {
					int type = generatARandomAvailableType(soldout_tickets_types);
					Ticket t = new Ticket(type, ticket_type_capacity.get(type));
					if (eachTypeTicket_records.get(type).size() < ticket_type_capacity.get(type)) {
						eachTypeTicket_records.get(type).add(t);
						customer_tickets.add(t);
					} else {
						if (soldout_tickets_types.size() == ticket_type_capacity.keySet().size()) {
							System.out.println("all types are sold out");
							break;
						}
						soldout_tickets_types.add(type);
						customersAttemptToPurchaseSoldoutTicket.add(c);
					}
				} else
					break;

			}

		}
		winningTickets = generateWinningTickets();
	}

	public static boolean lotteryMachineIsEmpty() {
		boolean flag = true;
		for (int type : eachTypeTicket_records.keySet()) {
			if (eachTypeTicket_records.get(type).size() < ticket_type_capacity.get(type)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static String oneDarrayToString(int[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + array[i];
		}
		return s;
	}

	public static ArrayList<Ticket> generateWinningTickets() {
		ArrayList<Ticket> winningTickets = new ArrayList<Ticket>();
		for (int type : eachTypeTicket_records.keySet()) {
			ArrayList<Ticket> eachTypeTickets = new ArrayList<Ticket>();
			Iterator<Ticket> iterator = eachTypeTicket_records.get(type).iterator();
			while (iterator.hasNext()) {
				Ticket t = iterator.next();
				eachTypeTickets.add(t);
			}

			// shuffle the tickets and get the first ticket of each type of
			// tickets as winning ticket.
			Collections.shuffle(eachTypeTickets);
			winningTickets.add(eachTypeTickets.get(0));
		}

		return winningTickets;
	}

	public static int generatARandomType() {
		Random rnd = new Random();
		List<Integer> keys = new ArrayList<Integer>(ticket_type_capacity.keySet());
		int randomKey = keys.get(rnd.nextInt(keys.size()));
		return randomKey;

	}

	/*
	 * This function takes tickets types which are sold out as input and return
	 * a random ticket type, which are not sold out. e.g. If ticket types are 3,
	 * 4, 5 and type 5 is full, this function would randomly select a number
	 * between 3 and 4.
	 */
	public static int generatARandomAvailableType(Set<Integer> soldout_tickets_types) {
		Random rnd = new Random();
		List<Integer> typesAll = getAllTypesArrayList();
		List<Integer> typesAvailable = new ArrayList<Integer>();
		int randomTypeAvailable;

		if (soldout_tickets_types.size() == 0)
			randomTypeAvailable = typesAll.get(rnd.nextInt(typesAll.size()));
		else {
			// compare each ticket type with each soldout tickets types
			// and get ticket types that are not soldout

			for (int j = 0; j < typesAll.size(); j++) {
				if (!soldout_tickets_types.contains(typesAll.get(j))) {
					typesAvailable.add(typesAll.get(j));
				}
			}
			randomTypeAvailable = typesAvailable.get(rnd.nextInt(typesAvailable.size()));
		}
		return randomTypeAvailable;
	}

	private static List<Integer> getAllTypesArrayList() {
		List<Integer> allTypes = new ArrayList<Integer>();
		for (int type : ticket_type_capacity.keySet())
			allTypes.add(type);
		return allTypes;
	}

	public static Set<Customer> generatWinningCustomers() {
		Set<Customer> winningCustomers = new HashSet<Customer>();
		int i = 0;
		for (int type : eachTypeTicket_records.keySet()) {
			Ticket winningTicket = winningTickets.get(i);
			if (eachTypeTicket_records.get(type).contains(winningTicket)) {
				for (Customer c : customers_tickets.keySet()) {
					if (customers_tickets.get(c).contains(winningTicket)) {
						winningCustomers.add(c);
					}
				}
			}
			i++;
		}
		return winningCustomers;
	}

	public static void generatReport() {
		printWinningTickets();
		printCustomerTicketsPurchased();
		printWinningCustomerTicketsInfo();
	}

	private static void printWinningCustomerTicketsInfo() {
		System.out.println("Winning customers are");
		Set<Customer> winningCustomers = generatWinningCustomers();
		int i = 1;
		for (Customer c : winningCustomers) {
			System.out.println("#" + i + " winning customer is " + c.getID() + ", who bought tickets ");
			Set<Ticket> tickets_bought = customers_tickets.get(c);
			printCustomerTicket(tickets_bought);
			i++;
		}

	}

	private static void printCustomerTicket(Set<Ticket> tickets_bought) {
		for (Ticket t : tickets_bought) {
			int[] ticketNumbers = t.getTicketNum();
			System.out.println("    " + oneDarrayToString(ticketNumbers));
		}
	}

	private static void printCustomerTicketsPurchased() {
		System.out.println("There are " + customers_tickets.size() + " customers purchased tickets");
		for (Customer c : customers_tickets.keySet()) {
			System.out.println("Below are ticket types which the customer " + c.getID() + " purchased:");
			Set<Integer> types = new HashSet<Integer>();
			Set<Ticket> tickets = customers_tickets.get(c);
			Iterator<Ticket> iterator = tickets.iterator();
			while (iterator.hasNext()) {
				Ticket t = iterator.next();
				types.add(t.getPickNum());
			}
			printSetInt(types);
			if (customersAttemptToPurchaseSoldoutTicket.contains(c)) {
				System.out.println("   and this customer attempted to purchase the sold out ticket");
			}
		}
	}

	private static void printSetInt(Set<Integer> types) {
		Iterator<Integer> iterator = types.iterator();
		String s = "";
		while (iterator.hasNext()) {
			int type = iterator.next();
			s = s + type;
		}
		System.out.println(s);
	}
}
