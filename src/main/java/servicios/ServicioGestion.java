package servicios;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.primefaces.model.file.UploadedFile;

import datos.ArchivosDao;
import modelos.Archivo;

@Stateless
@DeclareRoles({ "ROLE_ADMIN" })
public class ServicioGestion {

	@Inject
	private ArchivosDao archivosDaoImpl;

	public List<Archivo> obtenerListaArchivos() {

		return archivosDaoImpl.obtenerListaArchivos();

	}

	@RolesAllowed("ROLE_ADMIN")
	public void guardarArchivos(List<UploadedFile> files) {
		archivosDaoImpl.guardarArchivos(files);
	}

	@RolesAllowed("ROLE_ADMIN")
	public void modificarNombreArchivo(String nombreViejo, String nombreNuevo) {
		archivosDaoImpl.modificarNombreArchivo(nombreViejo, nombreNuevo);
	}

	@RolesAllowed("ROLE_ADMIN")
	public void eliminarArchivo(Archivo archivo) {
		archivosDaoImpl.eliminarArchivo(archivo);
	}

}
