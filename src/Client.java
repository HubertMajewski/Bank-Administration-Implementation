
/**
 *
 * @author Hubert
 */
public final class Client {

	private final String[] name;

	private final Accounts checking;
	private final Accounts savings;

	public Client(String fullName, int SSN) {
		name = fullName.split(" ", 2); // Assuming first & middle

		// Note clients must activate these accounts
		checking = Main.bank.createAccount();
		savings = Main.bank.createAccount();
	}

	public Integer getCheckingNumber() {
		return checking.accountNumber;
	}

	public Integer getSavingsNumber() {
		return savings.accountNumber;
	}

	public void toggleChecking() {
		if (Main.bank.isAccountOpen(checking.accountNumber))
			Main.bank.closeAccount(checking.accountNumber);
		else
			Main.bank.openAccount(checking.accountNumber);
	}

	public void toggleSavings() {
		if (Main.bank.isAccountOpen(savings.accountNumber))
			Main.bank.closeAccount(savings.accountNumber);
		else
			Main.bank.openAccount(savings.accountNumber);
	}

	public static Client getUser(int SSN) {
		return Main.bank.getClient(SSN);
	}

	public void transfer(boolean fromChecking, int accountNumber, long value)
		throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		Accounts working = (fromChecking ? this.checking : this.savings);
		Main.bank.transfer(working.accountNumber, accountNumber, value);
	}

	boolean isCheckingOpen() {
		return checking.isOpen();
	}

	boolean isSavingsOpen() {
		return savings.isOpen();
	}

	@java.lang.Override
	public String toString() {
		String x = "";
		for (String c : name) {
			x += (c.trim()) + " ";
		}
		return x;
	}

}

