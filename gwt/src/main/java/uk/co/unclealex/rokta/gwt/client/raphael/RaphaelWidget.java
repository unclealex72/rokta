package uk.co.unclealex.rokta.gwt.client.raphael;


public abstract class RaphaelWidget<O extends RaphaelObject<O>, R extends RaphaelWidget<O, R>> extends MouseFocusWidget
implements Raphael<R> {

	private O i_raphaelObject;
	
	protected RaphaelWidget(O raphaelObject) {
		i_raphaelObject = raphaelObject;
		setElement(raphaelObject.getElement());
	}

	public String getAttribute(String attributeName) {
		return getRaphaelObject().getAttribute(attributeName);
	}

	public Attributes getAttributes(String[] attributeNames) {
		return getRaphaelObject().getAttributes(attributeNames);
	}

	public R animate(AnimatableAttributes attributes, int ms) {
		getRaphaelObject().animate(attributes, ms);
		return me();
	}

	public BoundBox getBBox() {
		return getRaphaelObject().getBBox();
	}

	public R hide() {
		getRaphaelObject().hide();
		return me();
	}

	public R insertAfter(RaphaelObject<?> r) {
		getRaphaelObject().insertAfter(r);
		return me();
	}

	public R insertBefore(RaphaelObject<?> r) {
		getRaphaelObject().insertBefore(r);
		return me();
	}

	public final void remove() {
		getRaphaelObject().remove();
	}

	public R rotate(double degrees, boolean isAbsolute) {
		getRaphaelObject().rotate(degrees, isAbsolute);
		return me();
	}

	public R rotate(double degrees, int cx, int cy) {
		getRaphaelObject().rotate(degrees, cx, cy);
		return me();
	}

	public R rotate(double degrees, int cx) {
		getRaphaelObject().rotate(degrees, cx);
		return me();
	}

	public R rotate(double degrees) {
		getRaphaelObject().rotate(degrees);
		return me();
	}

	public R scale(double x, double y) {
		getRaphaelObject().scale(x, y);
		return me();
	}

	public R setAttribute(String attributeName, String value) {
		getRaphaelObject().setAttribute(attributeName, value);
		return me();
	}

	public R setAttributes(Attributes attributes) {
		getRaphaelObject().setAttributes(attributes);
		return me();
	}

	public R show() {
		getRaphaelObject().show();
		return me();
	}

	public R toBack() {
		getRaphaelObject().toBack();
		return me();
	}

	public R toFront() {
		getRaphaelObject().toFront();
		return me();
	}

	public R translate(int dx, int dy) {
		getRaphaelObject().translate(dx, dy);
		return me();
	}

	/**
	 * Delegated returning "this" to subclasses.
	 * @return
	 */
	public abstract R me();
	
	protected O getRaphaelObject() {
		return i_raphaelObject;
	}

}
