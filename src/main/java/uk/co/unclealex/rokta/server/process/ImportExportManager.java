package uk.co.unclealex.rokta.server.process;

import uk.co.unclealex.rokta.server.model.Rokta;

public interface ImportExportManager {

	public Rokta exportAll();
	public void importAll(Rokta rokta);
}
