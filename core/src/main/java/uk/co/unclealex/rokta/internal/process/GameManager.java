package uk.co.unclealex.rokta.internal.process;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Hand;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.exceptions.InvalidRoundException;

public interface GameManager extends Serializable {

	public void startGame(Collection<Person> participants, Person instigator, DateTime date);

	public void nextRound(Map<Person, Hand> plays, Person counter, int round)
			throws InvalidRoundException;

	public void finishGame(Integer replacingGameId) throws InvalidRoundException;

	public Game getGame();

	public Person getCounter();
	
	public SortedSet<Person> getParticipants();

	public int getRounds();

	public boolean isFinished();

}