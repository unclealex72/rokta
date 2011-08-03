package uk.co.unclealex.rokta.actions;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import uk.co.unclealex.rokta.process.ImportExportManager;
import uk.co.unclealex.rokta.views.Rokta;

import com.opensymphony.xwork.ActionSupport;

public class ImportAction extends ActionSupport {

	private ImportExportManager i_importExportManager;
	private File i_rokta;
	private String i_roktaContentType;
	private String i_roktaFileName;
	
	@Override
	public String execute() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Rokta.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Rokta rokta = (Rokta) unmarshaller.unmarshal(getRokta());
		getImportExportManager().importAll(rokta);
		return SUCCESS;
	}

	public ImportExportManager getImportExportManager() {
		return i_importExportManager;
	}

	public void setImportExportManager(ImportExportManager importExportManager) {
		i_importExportManager = importExportManager;
	}

	public String getRoktaContentType() {
		return i_roktaContentType;
	}

	public void setRoktaContentType(String roktaContentType) {
		i_roktaContentType = roktaContentType;
	}

	public File getRokta() {
		return i_rokta;
	}

	public void setRokta(File roktaFile) {
		i_rokta = roktaFile;
	}

	public String getRoktaFileName() {
		return i_roktaFileName;
	}

	public void setRoktaFileName(String roktaFileName) {
		i_roktaFileName = roktaFileName;
	}
}
