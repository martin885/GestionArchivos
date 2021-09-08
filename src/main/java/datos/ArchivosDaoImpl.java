package datos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.servlet.http.Part;

import org.primefaces.model.file.UploadedFile;

import modelos.Archivo;

@Stateless
public class ArchivosDaoImpl implements ArchivosDao {
	private File[] listFiles;

	private List<Archivo> listaArchivos;

	File uploads = new File(System.getProperty("user.home") + "/Descargas/uploads");

	@PostConstruct
	public void inicializar() {
		// this.listNames = new ArrayList<String>();
		listaArchivos = new ArrayList<Archivo>();

	}

	@Override
	public void listFiles() {
		// TODO Auto-generated method stub
		listaArchivos.clear();
		File uploaded = new File(System.getProperty("user.home") + "/Descargas/uploads");

		if (!uploaded.exists()) {
			uploaded.mkdir();
		}

		listFiles = uploaded.listFiles();
		Arrays.sort(listFiles, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}

		});
		for (int i = listFiles.length - 1; i >= 0; i--) {
			listaArchivos.add(new Archivo(listFiles[i].getName()));
		}
	}

	@Override
	public List<Archivo> obtenerListaArchivos() {
		// TODO Auto-generated method stub
		listFiles();
		return listaArchivos;
	}

	@Override
	public void guardarArchivos(List<UploadedFile> files) {

		for (UploadedFile file : files) {
			// String fileName =
			// Paths.get(part.getSubmittedFileName()).getFileName().toString();
			String fileName = file.getFileName();

			File nuevoArchivo = new File(uploads, fileName);
			// file.setLastModified(System.currentTimeMillis());
			if (nuevoArchivo.exists()) {
				int count = 0;
				while (nuevoArchivo.exists()) {
					count++;
					String[] fileArray = fileName.split("[.]");
					if (fileArray[0].contains("(")) {
						fileArray[0] = fileArray[0].split("[(]")[0];
					}
					fileName = fileArray[0] + "(" + count + ")" + "." + fileArray[1];
					nuevoArchivo = new File(uploads, fileName);
				}

			}
			try (InputStream input = file.getInputStream()) {

				Files.copy(input, nuevoArchivo.toPath());
				listFiles();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void modificarNombreArchivo(String nombreViejo, String nombreNuevo) {

		if (nombreNuevo != null && nombreViejo != null) {

			for (File file : listFiles) {
				if (file.getName().equals(nombreViejo)) {

					file.renameTo(new File(uploads, nombreNuevo));
				}
			}
			listFiles();
		}

	}

	@Override
	public void eliminarArchivo(Archivo archivo) {

		for (File file : listFiles) {
			if (file.getName().equals(archivo.getNombre())) {
				file.delete();
			}
		}
		listaArchivos.remove(archivo);

	}

}
