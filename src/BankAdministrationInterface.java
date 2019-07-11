
/**
 *
 * @author Hubert
 */
public interface BankAdministrationInterface {
	// Throws an objectNotActive if the account is closed

	// ClientRelated
	public Client createClient(String name, int SSN);

	public Client getClient(int SSN);

	// Accounts Related
	public Accounts createAccount();

	public void closeAccount(int accountNumber) throws org.omg.PortableServer.POAPackage.ObjectNotActive;

	public void openAccount(int accountNumber) throws org.omg.PortableServer.POAPackage.ObjectNotActive;

	public boolean isAccountOpen(int accountNumber) throws org.omg.PortableServer.POAPackage.ObjectNotActive;

	public long getValue(int accountNumber) throws org.omg.PortableServer.POAPackage.ObjectNotActive;

	public void setValue(int accountNumber, long value) throws org.omg.PortableServer.POAPackage.ObjectNotActive;

	public void transfer(int fromAccount, int toAccount, long value) throws org.omg.PortableServer.POAPackage.ObjectNotActive;

	public Accounts getAccount(int accountNumber);
	
	public boolean containsAccount(int acccountNumber);
	
	public java.util.Set<Integer> getClients();
	
}
