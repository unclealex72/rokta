package uk.co.unclealex.rokta.shared.model;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.SortedSet;

public class Leagues implements Serializable {

	private SortedMap<String, Colour> i_htmlColoursByName;
	private SortedSet<League> i_leagues;
	
	protected Leagues() {
		// Default constructor for serialisation.
	}
	
	public Leagues(SortedMap<String, Colour> htmlColoursByName, SortedSet<League> leagues) {
		super();
		i_htmlColoursByName = htmlColoursByName;
		i_leagues = leagues;
	}

	public SortedMap<String, Colour> getHtmlColoursByName() {
		return i_htmlColoursByName;
	}

	public SortedSet<League> getLeagues() {
		return i_leagues;
	}
	
	
}
