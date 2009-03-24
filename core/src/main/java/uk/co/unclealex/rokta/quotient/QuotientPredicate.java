package uk.co.unclealex.rokta.quotient;

import org.apache.commons.collections15.Transformer;

public class QuotientPredicate<S, T> extends CompareToPreviousTransformedPredicate<S, T> {

	private Transformer<S, T> i_quotientTransformer;
	
	public QuotientPredicate(Transformer<S, T> quotientTransformer) {
		super();
		i_quotientTransformer = quotientTransformer;
	}

	@Override
	public boolean doEvaluate(T transformedObject) {
		T previous = getPrevious();
		return previous == null || !previous.equals(transformedObject);
	}

	@Override
	public T transform(S input) {
		return getQuotientTransformer().transform(input);
	}

	public Transformer<S, T> getQuotientTransformer() {
		return i_quotientTransformer;
	}
}
