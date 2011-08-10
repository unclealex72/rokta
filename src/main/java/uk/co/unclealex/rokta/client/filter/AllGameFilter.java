package uk.co.unclealex.rokta.client.filter;


public class AllGameFilter extends AbstractGameFilter<AllGameFilter> {

	
	protected AllGameFilter() {
		super();
	}

	public AllGameFilter(Modifier modifier) {
		super(modifier);
	}

	@Override
	protected boolean isEqual(AllGameFilter other) {
		return true;
	}
	
	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}	
}
