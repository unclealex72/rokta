package uk.co.unclealex.rokta.views;

import java.io.Serializable;
import java.util.SortedSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;

@XmlRootElement
@XmlType(propOrder={"people", "games", "colours"})
public class Rokta implements Serializable {

	private SortedSet<Person> i_people;
	private SortedSet<Game> i_games;
	private SortedSet<Colour> i_colours;
	
	public Rokta() {
	}
	
	public Rokta(SortedSet<Person> people, SortedSet<Game> games, SortedSet<Colour> colours) {
		super();
		i_people = people;
		i_games = games;
		i_colours = colours;
	}

	@XmlElementWrapper(name="games")
	@XmlElement(name="game")
	public SortedSet<Game> getGames() {
		return i_games;
	}

	public void setGames(SortedSet<Game> games) {
		i_games = games;
	}

	@XmlElementWrapper(name="people")
	@XmlElement(name="person")
	public SortedSet<Person> getPeople() {
		return i_people;
	}

	public void setPeople(SortedSet<Person> people) {
		i_people = people;
	}

	@XmlElementWrapper(name="colours")
	@XmlElement(name="colour")
	public SortedSet<Colour> getColours() {
		return i_colours;
	}

	public void setColours(SortedSet<Colour> colours) {
		i_colours = colours;
	}
	
	
}
