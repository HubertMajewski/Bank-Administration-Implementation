/**
 *
 * @author Hubert
 */
public abstract class Accounts {

	private boolean state;
	final int accountNumber;
	private static int lastAccountNumber = 1;
	private long value = 0;

	public Accounts() {
		accountNumber = lastAccountNumber++;
	}

	public static Accounts getAccount(int accountNumber) {
		return Main.bank.getAccount(accountNumber);
	}

	public long getValue() throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		if (!isOpen())
			throw new org.omg.PortableServer.POAPackage.ObjectNotActive("Cannot get the value of a closed account.");
		return value;
	}

	public void addValue(long value) throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		if (!isOpen())
			throw new org.omg.PortableServer.POAPackage.ObjectNotActive("Cannot add to the value of a closed account.");
		this.value += value;
	}

	public void transferValue(int accountNumber, long value) throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		Main.bank.transfer(this.accountNumber, accountNumber, value);
	}

	public void setValue(long value) throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		if (!isOpen())
			throw new org.omg.PortableServer.POAPackage.ObjectNotActive("Cannot set the value of a closed account.");
		this.value = value;
	}

	public void close() {
		state = false;
	}

	public void open() {
		state = true;
	}

	public boolean isOpen() {
		return state;
	}

}





