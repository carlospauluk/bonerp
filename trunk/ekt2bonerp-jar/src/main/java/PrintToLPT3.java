import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class PrintToLPT3 {

	public final static String to = "LPT3";

	public final static String lockFile = "C:\\PrintToLPTx\\PrintToLPT3.lock";

	public static void main(String[] args) throws InterruptedException {
		try {

			if (args.length < 1) {
				System.err.println("Informe o nome do arquivo.");
				System.exit(-1);
			}

			Path path = Paths.get(lockFile);
			FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
			System.out.println("File channel opened for read write. Acquiring lock...");

			FileLock lock;

			int tentativas = 0;
			while ((lock = fileChannel.tryLock()) == null) {
				tentativas++;
				Thread.sleep(500);
				if (tentativas > 100) {
					System.out.println("*** LOCKED ***");
					System.out.println("SAINDO");
					System.exit(-1);
				}
			};
			System.out.println("Lock is shared: " + lock.isShared());


			File source = new File(args[0]);
			File dest = new File("LPT1:");

			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
			} finally {
				is.close();
				os.close();
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
