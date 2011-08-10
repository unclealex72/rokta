package uk.co.unclealex.rokta.client.filter;

public class LastGameOfTheDayModifier extends AbstractModifier {

	@Override
	public <T> T accept(ModifierVistor<T> modifierVisitor) {
		return modifierVisitor.visit(this);
	}
}
