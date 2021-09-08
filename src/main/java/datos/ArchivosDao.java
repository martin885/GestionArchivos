package datos;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.Part;

import org.primefaces.model.file.UploadedFile;

import modelos.Archivo;

public interface ArchivosDao {

	public void listFiles();

	public List<Archivo> obtenerListaArchivos();

	public void guardarArchivos(List<UploadedFile> files);

	public void modificarNombreArchivo(String nombreViejo, String nombreNuevo);

	public void eliminarArchivo(Archivo archivo);
}
