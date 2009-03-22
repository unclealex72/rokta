package uk.co.unclealex.rokta.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.unclealex.rokta.model.Rokta;
import uk.co.unclealex.rokta.process.ImportExportManager;

import com.opensymphony.xwork.ActionSupport;

public class ExportAction extends ActionSupport {

	private ImportExportManager i_importExportManager;
	private Rokta i_rokta;
	private String i_fileName;
	@Override
	public String execute() {
		DateFormat fmt = new SimpleDateFormat("ddMMyyyy-HHmmss");
		setFileName("export-" + fmt.format(new Date()));
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

	public String getFileName() {
		return i_fileName;
	}

	public void setFileName(String fileName) {
		i_fileName = fileName;
	}
}
