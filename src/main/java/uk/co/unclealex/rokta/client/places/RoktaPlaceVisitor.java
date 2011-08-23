package uk.co.unclealex.rokta.client.places;

public interface RoktaPlaceVisitor<T> {

	T visit(RoktaPlace roktaPlace);
	T visit(LeaguePlace leaguePlace);
	T visit(GraphPlace graphPlace);
	T visit(WinningStreaksPlace winningStreaksPlace);
	T visit(LosingStreaksPlace losingStreaksPlace);
	T visit(HeadToHeadsPlace headToHeadsPlace);
	T visit(AdminPlace adminPlace);
	T visit(GamePlace gamePlace);
	T visit(ProfilePlace profilePlace);

}
