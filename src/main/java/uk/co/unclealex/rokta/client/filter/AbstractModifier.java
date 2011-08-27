package uk.co.unclealex.rokta.client.filter;

public abstract class AbstractModifier implements Modifier {

	@Override
	public int hashCode() {
		return getClass().getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && getClass().equals(obj.getClass());
	}
}
