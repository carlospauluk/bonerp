import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PrintToLPT1 {

	public final static String lockFile = "C:\\PrintToLPTx\\PrintToLPT1.lock";

	public final static String ARQUIVOLISTA = "C:\\PrintToLPTx\\lista.txt";

	public final static String PRINT_TO = "LPT1";

	public final static int TIME_TO_LIVE = 100;

	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String hrexec = new SimpleDateFormat("dd-MM-yyyy.HH:mm:ss:S").format(new Date());
		// args = new String[] { "D:\\ocabit\\bonerp\\trunk\\ekt2bonerp-jar\\target\\classes\\ehcache.xml" };

		try {
			if (args.length < 1) {
				System.err.println("Informe o nome do arquivo.");
				System.exit(-1);
			}

			File fLista = new File(ARQUIVOLISTA);

			long ultimaModificacao = (new Date().getTime() - fLista.lastModified()) / 1000;

			if (ultimaModificacao > TIME_TO_LIVE) {
				deleteLista();
			}

			String nomeArquivo = args[0];

			colocaNaFila(nomeArquivo);

			// Espera 3 segundos para ver se não foi adicionando mais nada na lista.
			long tamanhoDaLista = fLista.length();
			Thread.sleep(3000);
			if (tamanhoDaLista == fLista.length()) {
				System.out.println(hrexec + ") Imprimindo... sou o último.");
				imprimeLista();
				System.out.println(hrexec + ") Imprimida!");
				System.out.println(hrexec + ") Deletada. Tchau!!!");
			} else {
				System.out.println(hrexec + ") Não vou imprimir... alguém colocou mais coisas na fila.");
			}
			
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.exit(-1);
		} finally {
			deleteLista();
		}

	}

	/**
	 * Coloca o arquivo na lista.
	 * 
	 * @param nomeArquivo
	 * @throws Exception 
	 */
	public static void colocaNaFila(String nomeArquivo) throws Exception {
		try {
			byte[] data = (nomeArquivo + System.lineSeparator()).getBytes();
			Files.write(Paths.get(ARQUIVOLISTA), data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new Exception("Erro ao colocar na fila: [" + nomeArquivo + "]", e);
		}

	}

	/**
	 * @throws Exception 
	 * 
	 */
	public static void imprimeLista() throws Exception {
		try {

			InputStream is = null;
			OutputStream os = null;
			try {
				List<String> lista = Files.readAllLines(Paths.get(ARQUIVOLISTA));
				
				Path path = Paths.get(lockFile);
				if (!path.toFile().exists()) {
					new FileOutputStream(lockFile).close();
				}
				FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
				System.out.println("File channel opened for read write. Acquiring lock...");

				FileLock lock;

				int tentativas = 0;
				while ((lock = fileChannel.tryLock()) == null) {
					tentativas++;
					Thread.sleep(500);
					if (tentativas > 100) {
						throw new Exception("Erro... LOCKED");
					}
				} ;
				System.out.println("Lock is shared: " + lock.isShared());
				File dest = new File(PRINT_TO);
				

				os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;

				for (int i = lista.size() - 1; i >= 0; i--) {
					File source = new File(lista.get(i));
					is = new FileInputStream(source);

					while ((length = is.read(buffer)) > 0) {
						os.write(buffer, 0, length);
					}
				}


			} catch (Exception e) {
				throw new Exception("Erro ao imprimir a lista", e);
			} finally {
				if (is != null)	is.close();
				if (os != null) os.close();
			}

		} catch (IOException e) {
			System.out.println("Erro ao imprimir a lista");
			System.out.println(e);
		}
	}

	public static void deleteLista() {
		if (!new File(ARQUIVOLISTA).delete()) {
			System.out.println("LISTA DELETADA COM SUCESSO");
		}
	}

}
