package servicios;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.Part;

public class ArchivosLogica {
	private File[] listFiles;
	private List<String> listNames;

	private void lista() {
		File upload = new File(System.getProperty("user.home") + "/Descargas/uploads");

		if (!upload.exists()) {
			upload.mkdir();
		}

		listFiles = upload.listFiles();
		Arrays.sort(listFiles, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {
				// TODO Auto-generated method stub
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}

		});
		listNames = new ArrayList<String>();
	}

	public List<String> obtenerLista() {
		lista();
		for (int i=listFiles.length-1;i>=0;i--) {
			listNames.add(listFiles[i].getName());

		}
	
		return listNames;
	}

	public void eliminarArchivo(String eliminarNombre) {
		lista();
		for (File file : listFiles) {
			if (file.getName().equals(eliminarNombre)) {
				file.delete();
			} else
				listNames.add(file.getName());
		}
	}

	public void guardarArchivos(Collection<Part> parts) throws IOException {

		for (Part part : parts) {
			//String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			String fileName = part.getSubmittedFileName();

			File uploads = new File(System.getProperty("user.home") + "/Descargas/uploads");

			File file = new File(uploads, fileName);
			//file.setLastModified(System.currentTimeMillis());
			if (file.exists()) {
				int count = 0;
				while (file.exists()) {
					count++;
					String[] fileArray = fileName.split("[.]");
					if (fileArray[0].contains("=")) {
						fileArray[0] = fileArray[0].split("=")[0];
					}
					fileName = fileArray[0] + "=" + count + "." + fileArray[1];
					file = new File(uploads, fileName);
				}

			}
			try (InputStream input = part.getInputStream()) {
				Files.copy(input, file.toPath());
			}

		}

	}
}
