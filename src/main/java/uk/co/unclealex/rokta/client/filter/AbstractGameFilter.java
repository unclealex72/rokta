package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

import com.google.common.base.Objects;

public abstract class AbstractGameFilter<G extends AbstractGameFilter<G>> implements GameFilter {

	private Modifier i_modifier;
	
	protected AbstractGameFilter() {
		super();
	}

	AbstractGameFilter(Modifier modifier) {
		super();
		i_modifier = modifier;
	}

	public abstract int completeHash();
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getClass().getName(), getModifier(), completeHash());
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return 
			other != null && 
			getClass().equals(other.getClass()) && 
			getModifier().equals(((G) other).getModifier()) && isEqual((G) other);
	}
	
	protected abstract boolean isEqual(G other);
	
	@SuppressWarnings("deprecation")
	protected boolean isSameDay(Date d1, Date d2) {
		return d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate();
	}
	
	public abstract <T> T accept(GameFilterVisitor<T> gameFilterVisitor);

	public Modifier getModifier() {
		return i_modifier;
	}
}
