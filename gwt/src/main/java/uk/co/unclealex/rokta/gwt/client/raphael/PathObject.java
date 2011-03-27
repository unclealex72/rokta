package uk.co.unclealex.rokta.gwt.client.raphael;

public class PathObject extends RaphaelObject<PathObject> {

	protected PathObject() {
		// Required protected no-args constructor.
	}

	/**
	 * Sets a trigger to count all following units as absolute ones, unless said otherwise. (This is on by default.)
	 * @return this
	 */
	public native final PathObject absolutely()
	/*-{ return this.absolutely(); }-*/;

	/**
	 * Sets a trigger to count all following units as relative ones, unless said otherwise.
	 * @return this
	 */
	public native final PathObject relatively()
	/*-{ return this.relatively(); }-*/;

	/**
	 * Moves the drawing point to the given coordinates.
	 * @param x <i>x</i> coordinate
	 * @param y <i>y</i> coordinate
	 * @return this
	 */
	public native final PathObject moveTo(int x, int y)
	/*-{ return this.moveTo(x, y); }-*/;

	/**
	 * Draws a straight line to the given coordinates.
	 * @param x <i>x</i> coordinate
	 * @param y <i>y</i> coordinate
	 * @return this
	 */
	public native final PathObject lineTo(int x, int y)
	/*-{ return this.lineTo(x, y); }-*/;


	/**
	 * Draws a curved line to the given coordinates. The line will have horizontal anchors on start and on finish.
	 * @param x <i>x</i> coordinate
	 * @param y <i>y</i> coordinate
	 * @param width the width
	 * @return this
	 */
	public native final PathObject cplineTo(int x, int y, int width)
	/*-{ return this.cplineTo(x, y, width); }-*/;

	/**
	 * Draws a bicubic curve to the given coordinates.
	 * @param x1 The first <i>x</i> coordinate
	 * @param y1 The first <i>y</i> coordinate
	 * @param x2 The second <i>x</i> coordinate
	 * @param y2 The second <i>y</i> coordinate
	 * @param x3 The third <i>x</i> coordinate
	 * @param y3 The third <i>y</i> coordinate
	 * @return this
	 */
	public native final PathObject curveTo(int x1, int y1, int x2, int y2, int x3, int y3)
	/*-{ return this.curveTo(x1, y1, x2, y2, x3, y3); }-*/;

	/**
	 * Draws a quadratic curve to the given coordinates.
	 * @param x1 The first <i>x</i> coordinate
	 * @param y1 The first <i>y</i> coordinate
	 * @param x2 The second <i>x</i> coordinate
	 * @param y2 The second <i>y</i> coordinate
	 * @return this
	 */
	public native final PathObject qcurveTo(int x1, int y1, int x2, int y2)
	/*-{ return this.qcurveTo(x1, y1, x2, y2); }-*/;


	/**
	 * Draws a quarter of a circle from the current drawing point.
	 * @param r The radius
	 * @param direction
	 * One of the following:
	 * <ul>
	 * <li>lu -left up</li>
	 * <li>ld - left down</li>
	 * <li>ru - right up</li>
	 * <li>rd - right down</li>
	 * <li>ur -up right</li>
	 * <li>ul - up left</li>
	 * <li>dr - down right</li>
	 * <li>dl - down left</li>
	 * </ul> 
	 * @return this
	 */
	private native final PathObject addRoundedCorner(int r, String direction)
	/*-{ return this.addRoundedCorner(r, direction); }-*/;

	/**
	 * Draw a quarter of a circle from the current drawing point going left and up.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addLeftUpRoundedCorner(int r) {
		return addRoundedCorner(r, "lu");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going left and down.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addLeftDownRoundedCorner(int r) {
		return addRoundedCorner(r, "ld");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going right and up.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addRightUpRoundedCorner(int r) {
		return addRoundedCorner(r, "ru");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going right and down.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addRightDownRoundedCorner(int r) {
		return addRoundedCorner(r, "rd");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going up and left.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addUpLeftRoundedCorner(int r) {
		return addRoundedCorner(r, "ul");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going up and right.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addUpRightRoundedCorner(int r) {
		return addRoundedCorner(r, "ur");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going down and left.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addDownLeftRoundedCorner(int r) {
		return addRoundedCorner(r, "dl");
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going down and right.
	 * @param r The radius of the circle
	 * @return this
	 */
	public final PathObject addDownRightRoundedCorner(int r) {
		return addRoundedCorner(r, "dr");
	}
	
	public final native PathObject arcTo(int rx, int ry, int large_arc_flag, int sweep_flag, int x, int y)
	/*-{ return this.arcTo(rx, ry, large_arc_flag, sweep_flag, x, y); }-*/;
	
	public native final PathObject andClose()
	/*-{ return this.andClose(); }-*/;

}
