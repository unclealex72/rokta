package uk.co.unclealex.rokta.gwt.client.raphael;

public class CircleWidget extends RaphaelWidget<CircleObject, CircleWidget> {

	CircleWidget(CircleObject circleObject) {
		super(circleObject);
	}

	@Override
	public CircleWidget me() {
		return this;
	}
	
	public CircleObject getCircleObject() {
		return getRaphaelObject();
	}
}
