package uk.co.unclealex.rokta.server.model;

import java.io.Serializable;
import java.util.SortedSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(propOrder={"people", "games"})
public class Rokta implements Serializable {

	private SortedSet<Person> i_people;
	private SortedSet<Game> i_games;
	
	public Rokta() {
	}
	
	public Rokta(SortedSet<Person> people, SortedSet<Game> games) {
		super();
		i_people = people;
		i_games = games;
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
}
