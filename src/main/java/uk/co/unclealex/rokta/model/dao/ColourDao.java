package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Colour;

public interface ColourDao {

	public Colour getColourByName(String name);
	public Colour getColourByHtmlName(String name);

	public SortedSet<Colour> getColours();	
}