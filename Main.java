/*	Program Name:   Lab 05 Revised Salesperson Travel Report and Log
	Programmer:     Marcus Ross
	Date Due:       11 October 2013
	Description:	This program asks the salesperson user their name, then gives them with a choice between adding a trip to the log, generating a monthly report based on the log, starting a new monthly log, switching to a different salesperson (and therefore different sales log file name), or quitting. Now with functions and methods and a class!
*/

package lab05;

import java.util.Scanner;
import java.io.*;
import lab05.Trip;

public class Main {
    public static void main(String[] args) {
		Trip trip = new Trip();
		char choice='0';

		do { //Display Menu
			System.gc(); //You were right when you wrote on my third lab that memory is not a concern unless filled, but I don't like it! If a customer were to leave the program running for weeks at a time then ask why a program uses so much memory, I'd rather include gc than tell them, "it doesn't matter until your memory is full"!
			trip.getUserName(choice); //Get user name
			choice = GetUserChoice(); //Get user choice
			DoUserChoice(choice,trip); //Perform user choice
		} while (choice!='q');
	}

	public static char GetUserChoice() {
		String temp;
		Scanner kbd = new Scanner(System.in);
		char choice;
		System.out.println("\n(A)dd a trip to the sales log");
		System.out.println("(G)enerate a monthly report");
		System.out.println("(S)tart a new month");
		System.out.println("(C)hange user");
		System.out.println("(Q)uit");
		while (true) {
			System.out.print("Enter choice: ");
			temp = kbd.nextLine();
			try {
				choice = temp.toLowerCase().charAt(0); //take lower case of first char
				if (choice=='a' || choice=='g' || choice=='s' || choice=='c' || choice=='q')
					return choice; //if choice is valid, return it
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Invalid entry");
			}
		}
	}

	public static void DoUserChoice(char choice, Trip trip) {
		switch (choice) {
		case 'a':
			trip.addTrip(); //Add trip to sales log
			return;
		case 'g':
			ReportGen(trip); //Generate a monthly report
			return;
		case 's':
			NewMonth(trip.userName); //Start a new month
			return;
		}
		System.out.println();
	}

	public static void ReportGen(Trip trip) {
		File inFileR;
		Scanner inFile;
		inFileR = new File(trip.userName + ".txt");
		try {
			inFile = new Scanner(inFileR);
		} catch (FileNotFoundException e) { // message if file not found
			System.out.printf("\nNo sales log found for user %s\n",trip.userName);
			return;
		}
		if (!inFile.hasNext()) { //message+quit if file is empty
			System.out.printf("\nSales log for user %s is empty\n",trip.userName);
			return;
		}
		DisplayHeader(trip.userName); //Display company info
		trip.processTrip(); //Process each trip
		trip.dispTripTotals(); //Display trip totals
	}

	public static void NewMonth(String userName) {
		Scanner kbd = new Scanner(System.in);
		String temp;
		PrintWriter outFile;
		char choice;
		System.out.println("\nAll data in existing log will be lost");
		while (true) {
			System.out.print("Are you sure? Y/N: ");
			temp = kbd.nextLine();
			try {
				choice = temp.toLowerCase().charAt(0); //take lower case of first char
				break;
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Invalid entry");
			}
		}
		if (choice == 'y') {
			try {
				outFile = new PrintWriter(userName + ".txt");
				outFile.close();
				System.out.println("Task completed");
			} catch (IOException e) {
				System.out.println("Error opening file");
			}
		}
		else
			System.out.println("Task aborted");
	}

	public static void DisplayHeader(String userName) {
		System.out.printf("\n┬──────────────────────────────┬\n");
		System.out.printf("│%25s%5s│\n","Culver City Meat Co.","");
		System.out.printf("│%26s%4s│\n│%30s│\n","You can't beat our meat","","");
		System.out.printf("│%s%-17.17s│\n│%30s│\n","Salesperson: ",userName,"");
	}
}