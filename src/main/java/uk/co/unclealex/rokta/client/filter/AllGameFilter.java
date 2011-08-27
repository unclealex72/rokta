package uk.co.unclealex.rokta.client.filter;


public class AllGameFilter extends AbstractGameFilter<AllGameFilter> {

	
	protected AllGameFilter() {
		super();
	}

	AllGameFilter(Modifier modifier) {
		super(modifier);
	}

	@Override
	public int completeHash() {
		return 0;
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
