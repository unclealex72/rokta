package uk.co.unclealex.rokta.internal.process;

import uk.co.unclealex.rokta.internal.model.Rokta;

public interface ImportExportManager {

	public Rokta exportAll();
	public void importAll(Rokta rokta);
}
