package uk.co.unclealex.rokta.gwt.client.raphael;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;

public abstract class RaphaelObject<R extends RaphaelObject<R>> extends JavaScriptObject {

	protected RaphaelObject() {
		// Required protected no-args constructor.
	}

	/**
	 * Gives you a reference to the DOM object, so you can assign event handlers or just mess around. 
	 * @return The DOM object.
	 */
	public final native Element getElement() 
	/*-{ return this.node; }-*/;
 
	/**
	 * Removes element from the DOM. You cannot use it after this method call. 
	 */
	protected final native void remove()
	/*-{ this.remove(); }-*/;
	
	/**
	 *  Makes element invisible. 
	 * @return this
	 */
	public final native R hide()
	/*-{ return this.hide(); }-*/;

	/**
	 *  Makes element visible. 
	 * @return this
	 */	
	public final native R show()
	/*-{ return this.show(); }-*/;
	
	/**
	 *  Rotates the element by the given degree from its center point. 
	 * @param degrees Degree of rotation (0 – 360)
	 * @return this
	 */
	public final native R rotate(double degrees)
	/*-{ return this.rotate(degrees); }-*/;

	/**
	 *  Rotates the element by the given degree from its centre point. 
	 * @param degrees Degree of rotation (0 – 360)
	 * @param isAbsolute Specifies is degree is relative to previous position (false) or is it absolute angle (true)
	 * @return this
	 */
	public final native R rotate(double degrees, boolean isAbsolute)
	/*-{ return this.rotate(degrees, isAbsolute); }-*/;

	/**
	 * Rotates the element by the given degree from a given point.
	 * @param degrees Degree of rotation (0 – 360)
	 * @param cx The <i>x</i> centre point.
	 * @return this
	 */
	public final native R rotate(double degrees, int cx) 
	/*-{ return this.rotate(degrees, cx); }-*/;
	
	/**
	 * Rotates the element by the given degree from a given point.
	 * @param degrees Degree of rotation (0 – 360)
	 * @param cx The <i>x</i> centre point.
	 * @param cy The <i>y</i> centre point.
	 * @return this
	 */
	public final native R rotate(double degrees, int cx, int cy)
	/*-{ return this.rotate(degrees, cx, cy); }-*/;

	/**
	 * Moves the element around the canvas by the given distances. 
	 * @param dx Pixels of translation along <i>x</i> axis.
	 * @param dy Pixels of translation along <i>y</i> axis.
	 * @return
	 */
	public final native R translate(int dx, int dy)
	/*-{ return this.translate(dx, dy); }-*/;

	/**
	 * Resizes the element by the given multiplier. 
	 * @param x Amount to scale horizontally
	 * @param y Amount to scale vertically
	 * @return this
	 */
	public final native R scale(double x, double y)
	/*-{ return this.scale(x, y); }-*/;

	/**
	 * Set the SVG attributes of this object directly.
	 * @param attributes The attributes
	 * @return this
	 */
	public final native R setAttributes(Attributes attributes)
		/*-{ return this.attr(attributes); }-*/;

	/**
	 * Set the SVG attributes of this object directly.
	 * @param attributeName The name of the attribute.
	 * @param value The value to set it to.
	 * @return this
	 */
	public final native R setAttribute(String attributeName, String value)
	/*-{ return this.attr(attributeName, value); }-*/;

	/**
	 * Return the value of an SVG attribute
	 * @param attributeName The name of the attribute
	 * @return The value of the attribute.
	 */
	public final native String getAttribute(String attributeName)
	/*-{ return this.attr(attributeName); }-*/;

	/**
	 * Return the value of SVG attributes
	 * @param attributeNames The names of the attribute to return
	 * @return The attributes
	 */
	public final native Attributes getAttributes(String[] attributeNames)
	/*-{ return this.attr(attributeNames); }-*/;
	
	/**
	 *  Linearly changes an attribute from its current value to its specified value in the given amount of milliseconds.
	 * @param attributes A parameters object of the animation results. (Not all attributes can be animated.)
	 * @param ms The duration of the animation, given in milliseconds.
	 * @return this
	 */
	public final native R animate(AnimatableAttributes attributes, int ms)
	/*-{ return this.animate(attributes, ms); }-*/;

	/**
	 * @return The bounds of this object.
	 */
	public final native BoundBox getBBox()
	/*-{ return this.getBBox(); }-*/;

	/**
	 * Moves the element so it is the closest to the viewer's eyes, on top of other elements.
	 * @return this
	 */
	public final native R toFront()
	/*-{ return this.toFront(); }-*/;
	
	/**
	 * Moves the element so it is the furthest from the viewer’s eyes, behind other elements.
	 * @return this
	 */
	public final native R toBack()
	/*-{ return this.toBack(); }-*/;

	/**
	 * Inserts current object before the given one.
	 * @param r The given object
	 * @return this
	 */
	public final native R insertBefore(RaphaelObject<?> r)
	/*-{ return this.insertBefore(r); }-*/;

	/**
	 * Inserts current object after the given one.
	 * @param r The given object
	 * @return this
	 */
	public final native R insertAfter(RaphaelObject<?> r)
	/*-{ return this.insertAfter(r); }-*/;

}
