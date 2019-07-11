/**
 *
 * @author Hubert
 */
public class BankAdministration implements BankAdministrationInterface {

	private static final java.util.HashMap<java.lang.Integer, Accounts> accounts = new java.util.HashMap<>();
	private static final java.util.HashMap<java.lang.Integer, Client> clients = new java.util.HashMap<>();

	@java.lang.Override
	public Accounts createAccount() {
		Accounts temp = new Accounts() {
		};
		temp.close();
		accounts.put(temp.accountNumber, temp);
		return temp;
	}

	@Override
	public void closeAccount(int accountNumber) {
		accounts.get(accountNumber).close();
	}

	@Override
	public void openAccount(int accountNumber) {
		accounts.get(accountNumber).open();
	}

	@Override
	public boolean isAccountOpen(int accountNumber) {
		return accounts.get(accountNumber).isOpen();
	}

	@Override
	public long getValue(int accountNumber) throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		return accounts.get(accountNumber).getValue();
	}

	@Override
	public void setValue(int accountNumber, long value) throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		accounts.get(accountNumber).setValue(value);
	}

	@Override
	public void transfer(int fromAccount, int toAccount, long value)
		throws org.omg.PortableServer.POAPackage.ObjectNotActive {
		if (!accounts.get(fromAccount).isOpen() || !Accounts.getAccount((int) toAccount).isOpen())
			throw new org.omg.PortableServer.POAPackage.ObjectNotActive("Cannot transfer value to a closed account.");
		if (value <= 0)
			throw new IllegalArgumentException("Can only transfer a positive number to the given account.");
		if (this.getValue(fromAccount) < value)
			throw new IllegalArgumentException("Cannot transfer due to an over-balance.");
		Accounts a = accounts.get(fromAccount);
		Accounts b = accounts.get(toAccount);
		
		a.addValue(-value);
		b.addValue(value);
		
	}

	@java.lang.Override
	public java.util.Set<java.lang.Integer> getClients() {
		return clients.keySet();
	}

	@java.lang.Override
	public boolean containsAccount(int accountNumber) {
		return accounts.containsKey(accountNumber);
	}

	@java.lang.Override
	public Accounts getAccount(int accountNumber) {
		return accounts.get(accountNumber);
	}

	@Override
	public Client createClient(java.lang.String name, int SSN) {
		if (clients.containsKey(SSN))
			throw new IllegalArgumentException("There is a user with the givin SSN already.");

		Client temp = new Client(name, SSN);
		clients.put(SSN, temp);
		return temp;
	}

	@Override
	public Client getClient(int SSN) {
		return clients.get(SSN);
	}

}
