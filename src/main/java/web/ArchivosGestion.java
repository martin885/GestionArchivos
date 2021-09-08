package web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import modelos.Archivo;
import servicios.ServicioGestion;

@Named("gestionBean")
@RequestScoped
public class ArchivosGestion {

	@Inject
	private ServicioGestion servicioGestion;

	private List<Archivo> archivos;

	private boolean isLogged;

	private UploadedFiles files;

	private String flag;

	private Archivo archivoSeleccionado;
	private static Archivo archivoAntesEditar;

	private static boolean isEdit;

	public Archivo getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(Archivo archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	@PostConstruct
	public void obtenerArchivos() {
		archivos = servicioGestion.obtenerListaArchivos();

	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public String agregarArchivos() {

		servicioGestion.guardarArchivos(files.getFiles());
		files = null;
		return "admin.xhtml?faces-redirect=true";
	}

	public void modificarNombre(RowEditEvent<Archivo> event) {

		String nombreNuevo = event.getObject().getNombre();
		servicioGestion.modificarNombreArchivo(archivoAntesEditar.getNombre(), nombreNuevo);
//		FacesMessage msg = new FacesMessage("Evento " + nombreNuevo + " " + archivoAntesEditar.getNombre());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void archivoEditar(RowEditEvent<Archivo> event) {
		archivoAntesEditar = event.getObject();
		isEdit=true;
//		FacesMessage msg = new FacesMessage("Evento Editar" + archivoAntesEditar.getNombre());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void eliminarArchivo() {
		servicioGestion.eliminarArchivo(archivoSeleccionado);
		archivoSeleccionado = null;

	}

	public void verArchivo(SelectEvent<Archivo> event) throws IOException {
		if(!isEdit) {
//			FacesMessage msg = new FacesMessage("Evento Select" );
//			FacesContext.getCurrentInstance().addMessage(null, msg);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext
					.redirect(externalContext.getApplicationContextPath() + "/files/" + archivoSeleccionado.getNombre());
		}
			isEdit=false;
	

	}

	public UploadedFiles getFiles() {
		return files;
	}

	public void setFiles(UploadedFiles files) {
		this.files = files;
	}
}
