package uk.co.unclealex.rokta.internal.quotient;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

public abstract class CompareToPreviousTransformedPredicate<S, T> implements Predicate<S>, Transformer<S, T> {

	private T i_previous;
	private T i_previousSelected;
	private T i_previousRejected;
	
	@Override
	public boolean evaluate(S object) {
		T transformedObject = transform(object);
		boolean result = doEvaluate(transformedObject);
		setPrevious(transformedObject);
		if (result) {
			setPreviousSelected(transformedObject);
		}
		else {
			setPreviousRejected(transformedObject);
		}
		return result;
	}

	public abstract boolean doEvaluate(T transformedObject);
	
	public T getPrevious() {
		return i_previous;
	}

	public void setPrevious(T previous) {
		i_previous = previous;
	}

	public T getPreviousSelected() {
		return i_previousSelected;
	}

	public void setPreviousSelected(T previousSelected) {
		i_previousSelected = previousSelected;
	}

	public T getPreviousRejected() {
		return i_previousRejected;
	}

	public void setPreviousRejected(T previousRejected) {
		i_previousRejected = previousRejected;
	}

}
