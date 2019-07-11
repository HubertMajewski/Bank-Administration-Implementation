
/**
 * @author Hubert
 */
public final class Main {

	//Create file streams
	private final static FileLogger outputFile = new FileLogger("Log.txt");
	private final static FileLogger stateFile = new FileLogger("States.txt");
	private final static FileReader inputFile = new FileReader("Input.txt");

	//Instance of a bank administrator
	final static BankAdministration bank = new BankAdministration();

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		//Counters for numbers of successful and errored lines
		int successfulLines = 0;
		int errorLines = 0;

		//For every line
		for (int i = 0; i < inputFile.length; i++) {

			//Try to see if there was an exception thrown on the line passed
			try {
				String line = inputFile.readNextLine();

				//Blank line of any kind
				if (line.isEmpty() || line.equalsIgnoreCase("\n"))
					continue; //skip

				//Comments in Input.txt
				if (line.length() >= 2 && line.charAt(0) == '/' && line.charAt(1) == '/')
					continue; //skip

				//State line is not understood iff there is no inital command code
				if (!Character.isDigit(line.charAt(0)))
					throw new IllegalArgumentException("Unknown Command code on line " + (i + 1) + " in \"Input.txt\"");

				//Run an action if char at 0 is a number
				actionOnCase(line);

			} catch (Exception e) {
				//Find any exception that has occured and print its message for that line

				outputFile.writeLine(" \n//ERROR");
				outputFile.writeLine(e.getMessage() + " on line " + (i + 1));
				outputFile.writeLine("");
				errorLines++;
				continue;
			} // try
			successfulLines++;
		} // for

		printDatabaseStatus(errorLines, successfulLines);

		// Close and flushes (internally) the files.
		stateFile.close();
		inputFile.close();
		outputFile.close();
	}

	private static void actionOnCase(String line) {
		//If string is not empy and there is a command code iterate through string
		String[] commands = line.split("\\s");

		//Let command 0 be a new user
		switch (line.charAt(0)) {

			case '0': {
				commands = line.split("\\s", 3); //let the 3rd element be an string for names (first mid & last)
				if (commands.length < 3) {
					throw new IllegalArgumentException("Invalid number of arguments.");
				}
				if (commands[2].equals("")) {
					throw new IllegalArgumentException("Name is invalid.");
				}
				commands[2] = commands[2].replace('\n', ' ');
				Client x = Main.bank.createClient(commands[2], Integer.valueOf(commands[1]));
				outputFile.writeLine("Created client " + x.toString() + "with two closed accounts.");

				break;
			}

			case '1': {
				if (commands.length >= 3) {
					throw new IllegalArgumentException("Invalid number of arguments.");
				}

				//Command 1 toggles to open/close checking of a user with SSN
				bank.getClient(Integer.valueOf(commands[1])).toggleChecking();
				outputFile.writeLine("State of checking " + Accounts.getAccount(Client.getUser(Integer.valueOf(commands[1])).getCheckingNumber()).isOpen());
				break;
			}

			case '2': {
				//Command 2 toggles to open/close savings of a user with SSN
				Client.getUser(Integer.valueOf(commands[1])).toggleSavings();
				outputFile.writeLine("State of savings " + Accounts.getAccount(Client.getUser(Integer.valueOf(commands[1])).getSavingsNumber()).isOpen());
				break;
			}

			case '3': {
				try {
					//Command 3 to transfer money to a checking account
					Client.getUser(Integer.valueOf(commands[1]))
						.transfer(true, Integer.valueOf(commands[3]), Long.valueOf(commands[2]) * 100);
					outputFile.writeLine("Transferred from checking of user " + Client.getUser(Integer.valueOf(commands[1])) + " with value " + Long.valueOf(commands[3]));
				} catch (org.omg.PortableServer.POAPackage.ObjectNotActive ex) {
					outputFile.writeLine(ex.getMessage());
				}
				break;
			}

			case '4': {
				try {
					//Command 3 to transfer money to a checking account
					Client.getUser(Integer.valueOf(commands[1]))
						.transfer(false, Integer.valueOf(commands[3]), Long.valueOf(commands[2]) * 100);
					outputFile.writeLine("Transfered from savings of user " + Client.getUser(Integer.valueOf(commands[1])) + " with value " + Long.valueOf(commands[3]));
				} catch (org.omg.PortableServer.POAPackage.ObjectNotActive ex) {
					outputFile.writeLine(ex.getMessage());
				}
				break;
			}

			case '5': {
				//Deposit value to account x
				long value;
				try {
					try {
						value = (long) (Double.valueOf(commands[2]) * 100);
					} catch (NumberFormatException e) {
						try {
							value = Long.valueOf(commands[2]) * 100;
						} catch (NumberFormatException ex) {
							throw new IllegalArgumentException(ex);
						}
					}

					if (value <= 0)
						throw new IllegalArgumentException("Value cannot be negative.");

					((Accounts) Accounts.getAccount(Integer.valueOf(commands[1]))).addValue(value);
					outputFile.writeLine("Added " + value / 100d + " to " + commands[1]);
				} catch (org.omg.PortableServer.POAPackage.ObjectNotActive ex) {
					outputFile.writeLine(ex.getMessage());
				}
				break;
			}

			case '6': {
				//Withdraw from given account number
				long value;

				try {
					try {
						value = (long) (Double.valueOf(commands[2]) * 100);
					} catch (NumberFormatException e) {
						try {
							value = Long.valueOf(commands[2]) * 100;
						} catch (NumberFormatException ex) {
							throw new IllegalArgumentException(ex);
						}
					}

					if (value <= 0)
						throw new IllegalArgumentException("Value cannot be negative.");

					((Accounts) Accounts.getAccount(Integer.valueOf(commands[1]))).addValue(-value);
					outputFile.writeLine("Withdrew " + value / 100d + " from " + commands[1]);
				} catch (org.omg.PortableServer.POAPackage.ObjectNotActive ex) {
					outputFile.writeLine(ex.getMessage());
				}
			}

			case '7':
				//Reserved
				break;

			case '8':
				//Reserved
				break;

			case '9': {
				//Manually print status of bank
				System.out.println();
				int k = 1;
				for (Integer SSN : bank.getClients()) {
					try {
						System.out.println("Client " + k++ + "\n" + (Client.getUser(SSN).toString()));
						if (Accounts.getAccount(Client.getUser(SSN).getCheckingNumber()).isOpen())
							System.out.println("	" + "Checking: #" + Client.getUser(SSN).getCheckingNumber() + " $" + Accounts.getAccount(Client.getUser(SSN).getCheckingNumber()).getValue() / 100d);
						if (Accounts.getAccount(Client.getUser(SSN).getSavingsNumber()).isOpen())
							System.out.println("	" + "Savings: #" + Client.getUser(SSN).getSavingsNumber() + " $" + Accounts.getAccount(Client.getUser(SSN).getSavingsNumber()).getValue() / 100d);
						System.out.println(""); //new line
					} catch (org.omg.PortableServer.POAPackage.ObjectNotActive ex) {
					}
				}
				System.out.println();
			}

			default:
				break;
		}
	}

	private static void printDatabaseStatus(int errorLines, int successfulLines) {

		stateFile.writeLine("Read " + successfulLines + " lines successfully.");
		if (errorLines != 0)
			stateFile.writeLine("There are " + errorLines + " errors in Input.txt. See Log.txt");
		stateFile.writeLine("");

		// Print status of Database
		int i = 1;
		for (Integer SSN : bank.getClients()) {
			try {
				stateFile.writeLine("Client " + i++ + "\n" + Client.getUser(SSN).toString());
				if (Accounts.getAccount(Client.getUser(SSN).getCheckingNumber()).isOpen())
					stateFile.writeLine("	" + "Checking: #" + Client.getUser(SSN).getCheckingNumber() + " $" + Accounts.getAccount(Client.getUser(SSN).getCheckingNumber()).getValue() / 100d);
				if (Accounts.getAccount(Client.getUser(SSN).getSavingsNumber()).isOpen())
					stateFile.writeLine("	" + "Savings: #" + Client.getUser(SSN).getSavingsNumber() + " $" + Accounts.getAccount(Client.getUser(SSN).getSavingsNumber()).getValue() / 100d);
				stateFile.writeLine("");
			} catch (org.omg.PortableServer.POAPackage.ObjectNotActive ex) {
				java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			}
		}

	}

}

