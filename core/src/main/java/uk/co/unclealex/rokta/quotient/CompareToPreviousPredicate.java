package uk.co.unclealex.rokta.quotient;

public abstract class CompareToPreviousPredicate<T> extends CompareToPreviousTransformedPredicate<T, T> {

	public T transform(T input) {
		return input;
	}
}
