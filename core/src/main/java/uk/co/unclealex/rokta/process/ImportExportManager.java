package uk.co.unclealex.rokta.process;

import uk.co.unclealex.rokta.views.Rokta;

public interface ImportExportManager {

	public Rokta exportAll();
	public void importAll(Rokta rokta);
}
