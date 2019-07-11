
/**
 *
 * @author Hubert
 */
public class FileLogger {

	private final java.io.PrintWriter fileStream;

	FileLogger(String fileName) {
		java.io.PrintWriter temp = null;
		try {
			temp = new java.io.PrintWriter(new java.io.FileWriter(fileName));
		} catch (java.io.IOException ex) {
			java.util.logging.Logger.getLogger(FileLogger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		fileStream = temp;
	}

	public void writeLine(String value) {
		fileStream.append(value);
		System.out.println(value);
		fileStream.append("\n");
		fileStream.flush();
	}

	public void close() {
		fileStream.flush();
		fileStream.close();
	}

}
