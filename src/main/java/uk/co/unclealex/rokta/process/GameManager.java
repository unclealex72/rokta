package uk.co.unclealex.rokta.process;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.SortedSet;

import uk.co.unclealex.rokta.exceptions.InvalidRoundException;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Hand;
import uk.co.unclealex.rokta.model.Person;

public interface GameManager {

	public void startGame(Collection<Person> participants, Person instigator, Date date);

	public void nextRound(Map<Person, Hand> plays, Person counter, int round)
			throws InvalidRoundException;

	public void finishGame() throws IllegalStateException;

	public Game getGame();

	public Person getCounter();
	
	public SortedSet<Person> getParticipants();

	public int getRounds();

	public boolean isFinished();

}