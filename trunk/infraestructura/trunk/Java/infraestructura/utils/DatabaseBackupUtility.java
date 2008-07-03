package infraestructura.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.quartz.JobExecutionException;

import com.salmonllc.properties.Props;

public class DatabaseBackupUtility {
	/**
	 * Realiza el backup de la base de datos indicada.
	 * 
	 * @param database Base de datos de la cual se desea realizar el backup
	 * @throws JobExecutionException
	 */
	public static void backupInventarioDB(String database) throws JobExecutionException {
		String user = Props.getProps("inventario", "inventario").getProperty(
				"inventario.DBUser");
		String password = Props.getProps("inventario", "inventario").getProperty(
				"inventario.DBPassword");		
		user = database;
		password = database;
		try {
			Runtime rt = Runtime.getRuntime();
			System.out.println("Iniciando backup de base de datos: "+database);
			Process child = rt.exec("mysqldump --opt --password=" + password
					+ " --user=" + user + " "+database);
			
			// arma el nombre del directorio de backups diario y lo crea si no existe
			Calendar date = Calendar.getInstance();			
			String dirName = "C:\\Backups\\Bkp"+date.get(Calendar.DAY_OF_MONTH)+"-"+date.get(Calendar.MONTH)+"-"+date.get(Calendar.YEAR);
			File directorio = new File(dirName);
			directorio.mkdirs();			
			
			//crea el archivo de backup con el nombre de la base de datos
			File bkpFile = new File(directorio.getAbsolutePath()+"\\"+database+".sql");
						
			FileWriter fw = null;

			fw = new FileWriter(bkpFile);			

			// lee el input stream del proceso de backup y llena el archivo
			InputStream in = child.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(in,
					"latin1");
			char[] chars = new char[1024];
			while (inputStreamReader.read(chars) > 0) {
				fw.write(chars);
			}
			fw.close();
			System.out.println("Finalizado backup de base de datos: "+database);
		} catch (Throwable t) {
			System.out.println("Error en backup de base de datos: "+database);
			t.printStackTrace();
		}
	}
}
