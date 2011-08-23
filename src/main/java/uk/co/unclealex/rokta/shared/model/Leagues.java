package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.Map;
import java.util.SortedSet;

public class Leagues implements Serializable {

	private Map<String, String> i_htmlColoursByName;
	private SortedSet<League> i_leagues;
	
	protected Leagues() {
		// Default constructor for serialisation.
	}
	
	public Leagues(Map<String, String> htmlColoursByName, SortedSet<League> leagues) {
		super();
		i_htmlColoursByName = htmlColoursByName;
		i_leagues = leagues;
	}

	public Map<String, String> getHtmlColoursByName() {
		return i_htmlColoursByName;
	}

	public SortedSet<League> getLeagues() {
		return i_leagues;
	}
	
	
}
