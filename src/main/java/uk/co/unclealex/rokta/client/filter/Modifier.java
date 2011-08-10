package uk.co.unclealex.rokta.client.filter;

import java.io.Serializable;

public interface Modifier extends Serializable {

	public <T> T accept(ModifierVistor<T> modifierVisitor);
}
