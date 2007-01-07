package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.model.Rokta;
import uk.co.unclealex.rokta.process.ImportExportManager;

import com.opensymphony.xwork.ActionSupport;

public class ExportAction extends ActionSupport {

	private ImportExportManager i_importExportManager;
	private Rokta i_rokta;
	
	@Override
	public String execute() {
		setRokta(getImportExportManager().exportAll());
		return SUCCESS;
	}
	
	public ImportExportManager getImportExportManager() {
		return i_importExportManager;
	}
	public void setImportExportManager(ImportExportManager importExportManager) {
		i_importExportManager = importExportManager;
	}

	public Rokta getRokta() {
		return i_rokta;
	}

	public void setRokta(Rokta rokta) {
		i_rokta = rokta;
	}
}
