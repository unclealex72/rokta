package uk.co.unclealex.rokta.client.places;

public interface RoktaPlaceVisitor {

	void visit(RoktaPlace roktaPlace);
	void visit(LeaguePlace leaguePlace);
	void visit(WinningStreaksPlace winningStreaksPlace);
	void visit(LosingStreaksPlace losingStreaksPlace);
	void visit(HeadToHeadsPlace headToHeadsPlace);
	void visit(AdminPlace adminPlace);
	void visit(GamePlace gamePlace);
	void visit(ProfilePlace profilePlace);

}
