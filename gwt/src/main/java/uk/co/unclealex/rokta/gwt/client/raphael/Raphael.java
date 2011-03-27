package uk.co.unclealex.rokta.gwt.client.raphael;

import com.google.gwt.user.client.Element;

public interface Raphael<R extends Raphael<R>> {

	/**
	 * Gives you a reference to the DOM object, so you can assign event handlers or just mess around. 
	 * @return The DOM object.
	 */
	public Element getElement();

	/**
	 * Removes element from the DOM. You cannot use it after this method call. 
	 */
	public void remove();

	/**
	 *  Makes element invisible. 
	 * @return this
	 */
	public R hide();

	/**
	 * Makes element visible. 
	 * @return this
	 */
	public R show();

	/**
	 *  Rotates the element by the given degree from its center point. 
	 * @param degrees Degree of rotation (0 - 360)
	 * @return this
	 */
	public R rotate(double degrees);

	/**
	 *  Rotates the element by the given degree from its centre point. 
	 * @param degrees Degree of rotation (0 - 360)
	 * @param isAbsolute Specifies is degree is relative to previous position (false) or is it absolute angle (true)
	 * @return this
	 */
	public R rotate(double degrees, boolean isAbsolute);

	/**
	 * Rotates the element by the given degree from a given point.
	 * @param degrees Degree of rotation (0 - 360)
	 * @param cx The <i>x</i> centre point.
	 * @return this
	 */
	public R rotate(double degrees, int cx);

	/**
	 * Rotates the element by the given degree from a given point.
	 * @param degrees Degree of rotation (0 - 360)
	 * @param cx The <i>x</i> centre point.
	 * @param cy The <i>y</i> centre point.
	 * @return this
	 */
	public R rotate(double degrees, int cx, int cy);

	/**
	 * Moves the element around the canvas by the given distances. 
	 * @param dx Pixels of translation along <i>x</i> axis.
	 * @param dy Pixels of translation along <i>y</i> axis.
	 * @return
	 */
	public R translate(int dx, int dy);

	/**
	 * Resizes the element by the given multiplier. 
	 * @param x Amount to scale horizontally
	 * @param y Amount to scale vertically
	 * @return this
	 */
	public R scale(double x, double y);

	/**
	 * Set the SVG attributes of this object directly.
	 * @param attributes The attributes
	 * @return this
	 */
	public R setAttributes(Attributes attributes);

	/**
	 * Set the SVG attributes of this object directly.
	 * @param attributeName The name of the attribute.
	 * @param value The value to set it to.
	 * @return this
	 */
	public R setAttribute(String attributeName, String value);

	/**
	 * Return the value of an SVG attribute
	 * @param attributeName The name of the attribute
	 * @return The value of the attribute.
	 */
	public String getAttribute(String attributeName);

	/**
	 * Return the value of SVG attributes
	 * @param attributeNames The names of the attribute to return
	 * @return The attributes
	 */
	public Attributes getAttributes(String[] attributeNames);

	/**
	 * @return The bounds of this object.
	 */
	public BoundBox getBBox();

	/**
	 * Moves the element so it is the closest to the viewer's eyes, on top of other elements.
	 * @return this
	 */
	public R toFront();

	/**
	 * Moves the element so it is the furthest from the viewer’s eyes, behind other elements.
	 * @return this
	 */
	public R toBack();

	/**
	 * Inserts current object before the given one.
	 * @param r The given object
	 * @return this
	 */
	public R insertBefore(RaphaelObject<?> r);

	/**
	 * Inserts current object after the given one.
	 * @param r The given object
	 * @return this
	 */
	public R insertAfter(RaphaelObject<?> r);

}