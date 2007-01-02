package uk.co.unclealex.rokta.predicate;

public interface GamePredicateVisitor {

	void visit(FirstGameOfTheDayPredicate predicate);

	void visit(FirstGameOfTheWeekPredicate predicate);

}
