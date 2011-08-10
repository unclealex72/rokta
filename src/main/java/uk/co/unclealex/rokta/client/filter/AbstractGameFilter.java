package uk.co.unclealex.rokta.client.filter;

public abstract class AbstractGameFilter<G extends AbstractGameFilter<G>> implements GameFilter {

	private Modifier i_modifier;
	
	protected AbstractGameFilter() {
		super();
	}

	public AbstractGameFilter(Modifier modifier) {
		super();
		i_modifier = modifier;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return 
			other != null && 
			getClass().equals(other.getClass()) && 
			getModifier().equals(((G) other).getModifier()) && isEqual((G) other);
	}
	
	protected abstract boolean isEqual(G other);
	
	public abstract <T> T accept(GameFilterVisitor<T> gameFilterVisitor);

	public Modifier getModifier() {
		return i_modifier;
	}
}
