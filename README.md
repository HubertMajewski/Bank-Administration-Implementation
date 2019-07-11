# Bank-Administration-Implementation


//The idea behind it
The idea behind the organization of my program is to catch any argument or
account errors that may come up when writing into the Input.txt file. Henceforth, 
this is why I have exceptions being passed through the functions. If there is an
exception from a given line, it will be passed to the try surrounding the line 
execution inside the for loop. The exception would be stated and shown where it 
has occured in the Input.txt file. ObjectNotActiveExceptions will be throws if 
the Input.txt file is trying to access an account that is deactivated, therefore 
it will print the line in the logfile that the given command can not do that
unless it opens the account of the client.


//Development
Each command has a corresponding command code allowing the bank administrator 
to perform actions on account and clients themselves. See "How it works." There 
have been many thoughts between Enoch and I on how to approach this project. 
I have settled with using HashMaps due to their versatility and time complexities.
One challenge I had with this project is the creative freedom associated with it.
There are many different approaches towards this problem that I couldn't finalize 
myself to choose one. But, after discussing with Enoch, I found the best one I 
should go for. Another problem I had was having everything redirected through the 
BankAdministration class to utilize all of the interfaces I have come up with. 
It is not something that I am use to but I have enjoyed the pressure of consistently 
reformatting to fit the project needs.


//How it works
This program takes a set of commands from the "Input.txt" file. 
These commands would tell the bank database to either create a new 
client, toggle (activate or inactivate) the clients checking or savings account,
deposit money into an account number, withdraw money from a given account number, 
and transfer between accounts from checking and savings, and finally print the 
current state of the database at any time. 

The syntax for any command in the Input.txt is 

[Command#] [Parameters]

Structure for creating a new client:

The syntax for creating a new user for the database is
0 [SSN] [Client's Full Name]

The SSN's value cannot be larger than 2^31 - 1 because the limitation 
of the integer primitive datatype

The Client's name can be anything of 1 word or more.

Every client is created with two accounts automatically added into the database.
Each account is deactivated and must be toggled on before inputting any money into
it or withdrawing. Account number are assigned consecutively.

The first client created will have account numbers 1 for checking and 2 for savings.
the second will have 3 for checking and 4 for savings and so on.

The syntax for activating or deactivating the client's checking account 
1 [SSN]

The int SSN will search up the client in the database and toggle the checking
account opposite to what it was initially.

The syntax for activating or deactivating the client's checking account 
2 [SSN]

See command 1; same application but for savings.

Command 3 transfer s money from an client's checking account
3 [SSN] [Value] {To Account Number}

The int SSN will search up the client and pull the value from their checking account. 
If the Account of the account number is closed, throw error to be cought and skip line.

Value cannot be negative

Command 4 transfer s money from an client's savings account
4 [SSN] [Value] {To Account Number}

See 3

Command 5 deposits a value into a given account number
5 [AccountNumber] [Value]

Searches the account number given then adds the given
value to the accounts value. Value cannot be negative

Command 6 - Withdraw
6 [AccountNumber] [Value]
Searches the given account with the account number and takes away 
from the account. Value cannot be negative.

Command 9
9

Simply prints the status of the database to the console.

After All commands have been executed a state file will be
written for a snapshot of the database
