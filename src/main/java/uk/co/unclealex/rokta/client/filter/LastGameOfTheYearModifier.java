package uk.co.unclealex.rokta.client.filter;

public class LastGameOfTheYearModifier extends AbstractModifier {

	@Override
	public <T> T accept(ModifierVistor<T> modifierVisitor) {
		return modifierVisitor.visit(this);
	}
}
