package uk.co.unclealex.rokta.actions;

import uk.co.unclealex.rokta.model.Person;

public interface GameStartingAction {

	public abstract Person getInstigator();

	public abstract void setInstigator(Person instigator);

	public abstract Person[] getParticipants();

	public abstract void setParticipants(Person[] participants);

	public abstract Long getReplacingGameId();

	public abstract void setReplacingGameId(Long replacingGameId);

}