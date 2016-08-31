package lab05;

import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Trip {
	private String cust, tripDate, temp;
	String userName;
	private double miles, fuel, miscExpense, milesTotal, fuelTotal, miscExpenseTotal;
	private File inFileR;
	private Scanner inFile;
	private FileWriter outFileW;
	private PrintWriter outFile;
	private Scanner kbd = new Scanner(System.in); //I tried to declare kbd on the same line as inFile and then just instantiate kbd on this line instead of initialize, but compiler gave me "error: <identifier> expected"--any idea why??
	

	public void getUserName(char choice) {
		if (choice=='c' || choice=='0')
			while (true) {
				System.out.print("Salesperson: ");
				userName = kbd.nextLine();
				if (!userName.isEmpty())
					return;
				System.out.println("Invalid entry");
			}
	}

	public void addTrip() {
		getTripInfo(); //Get user's trip info
		enterTripToLog(); //Display trip info to sales log
	}

	private void getTripInfo() {
		System.out.println();
		while (true) {
			System.out.print("Customer: ");
			cust = kbd.nextLine();
			if (!cust.isEmpty())
				break;
			System.out.println("Invalid entry");
		}

		while (true) {
			System.out.print("Trip date: ");
			tripDate = kbd.nextLine();
			if (!tripDate.isEmpty())
				break;
			System.out.println("Invalid entry");
		}

		while (true) {
			try {
				System.out.print("Miles travelled: ");
				temp = kbd.nextLine();
				miles = Double.parseDouble(temp);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid entry");
			}
		}

		while (true) {
			try {
				System.out.print("Gas used: ");
				temp = kbd.nextLine();
				fuel = Double.parseDouble(temp);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid entry");
			}
		}

		while (true) {
			try {
				System.out.print("Misc. expenses: ");
				temp = kbd.nextLine();
				miscExpense = Double.parseDouble(temp);
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid entry");
			}
		}
	}

	private void enterTripToLog() {
		try {
			outFileW = new FileWriter(userName + ".txt",true);
		} catch (IOException e) {
			System.out.printf("\nError opening log for user %s\n",userName);
			return;
		}
			outFile = new PrintWriter(outFileW);
			outFile.println(cust);
			outFile.println(tripDate);
			outFile.printf("%1.2f\n",miles);
			outFile.printf("%1.2f\n",fuel);
			outFile.printf("%1.2f\n",miscExpense);
			outFile.close();
	}

	public void processTrip() {
		milesTotal = 0;
		fuelTotal = 0;
		miscExpenseTotal = 0;
		inFileR = new File(userName + ".txt");
		try {
			inFile = new Scanner(inFileR);
		} catch (FileNotFoundException e) {
			System.out.printf("\nNo sales log found for user %s\n",userName);
			return;
		}
		while (inFile.hasNext()) {
			try {
				getTripData(inFile); //Get trip data from sales log
			} catch (NoSuchElementException e) { //getTripData throws exceptions here to prevent incomplete entries from affecting the totals
				System.out.printf("│%-30s│\n│%30s│\n","Incomplete Entry Encountered","");
				return;
			}
			addTripTotals(); //Calculate trip totals
			dispTripReport(); //Display trip report
		}
		inFile.close();
	}

	private void getTripData(Scanner inFile) throws NoSuchElementException {
		cust = inFile.nextLine();
		tripDate = inFile.nextLine();
		miles = inFile.nextDouble();
		fuel = inFile.nextDouble();
		miscExpense = inFile.nextDouble();
		inFile.nextLine();
	}

	private void addTripTotals() {
		milesTotal += miles;
		fuelTotal += fuel;
		miscExpenseTotal += miscExpense;
	}

	private void dispTripReport() {
		System.out.printf("│%s%20s│\n","Customer: ",cust);
		System.out.printf("│%s%16s│\n","Date of Trip: ",tripDate);
		System.out.printf("│%-17s%13.2f│\n","Miles travelled: ",miles);
		System.out.printf("│%-17s%9.2f %s│\n","Gas used: ",fuel,"gal");
		System.out.printf("│%-17s$%12.2f│\n│%30s│\n","Misc. expenses: ",miscExpense,"");
	}

	public void dispTripTotals() {
		System.out.printf("│%-17s%13.2f│\n","Total miles: ",milesTotal);
		System.out.printf("│%-17s%9.2f %s│\n","Total gas: ",fuelTotal,"gal");
		System.out.printf("│%-17s$%12.2f│\n","Total expenses: ",miscExpenseTotal);
		System.out.printf("┴──────────────────────────────┴\n");
	}
}