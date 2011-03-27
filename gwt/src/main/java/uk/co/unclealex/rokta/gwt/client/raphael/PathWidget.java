package uk.co.unclealex.rokta.gwt.client.raphael;

public class PathWidget extends RaphaelWidget<PathObject, PathWidget> {

	PathWidget(PathObject PathObject) {
		super(PathObject);
	}
	
	/**
	 * Sets a trigger to count all following units as absolute ones, unless said otherwise. (This is on by default.)
	 * @return this
	 */
	public PathWidget absolutely() {
		getPathObject().absolutely();
		return this;
	}

	/**
	 * Sets a trigger to count all following units as relative ones, unless said otherwise.
	 * @return this
	 */
	public PathWidget relatively() {
		getPathObject().relatively();
		return this;
	}

	/**
	 * Moves the drawing point to the given coordinates.
	 * @param x <i>x</i> coordinate
	 * @param y <i>y</i> coordinate
	 * @return this
	 */
	public PathWidget moveTo(int x, int y) {
		getPathObject().moveTo(x, y);
		return this;
	}

	/**
	 * Draws a straight line to the given coordinates.
	 * @param x <i>x</i> coordinate
	 * @param y <i>y</i> coordinate
	 * @return this
	 */
	public PathWidget lineTo(int x, int y) {
		getPathObject().lineTo(x, y);
		return this;
	}

	/**
	 * Draws a curved line to the given coordinates. The line will have horizontal anchors on start and on finish.
	 * @param x <i>x</i> coordinate
	 * @param y <i>y</i> coordinate
	 * @param width the width
	 * @return this
	 */
	public PathWidget cplineTo(int x, int y, int width) {
		getPathObject().cplineTo(x, y, width);
		return this;
	}

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
	public PathWidget curveTo(int x1, int y1, int x2, int y2, int x3, int y3) {
		getPathObject().curveTo(x1, y1, x2, y2, x3, y3);
		return this;
	}

	/**
	 * Draws a quadratic curve to the given coordinates.
	 * @param x1 The first <i>x</i> coordinate
	 * @param y1 The first <i>y</i> coordinate
	 * @param x2 The second <i>x</i> coordinate
	 * @param y2 The second <i>y</i> coordinate
	 * @return this
	 */
	public PathWidget qcurveTo(int x1, int y1, int x2, int y2) {
		getPathObject().qcurveTo(x1, y1, x2, y2);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going left and up.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addLeftUpRoundedCorner(int r) {
		getPathObject().addLeftUpRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going left and down.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addLeftDownRoundedCorner(int r) {
		getPathObject().addLeftDownRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going right and up.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addRightUpRoundedCorner(int r) {
		getPathObject().addRightUpRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going right and down.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addRightDownRoundedCorner(int r) {
		getPathObject().addRightDownRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going up and left.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addUpLeftRoundedCorner(int r) {
		getPathObject().addUpLeftRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going up and right.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addUpRightRoundedCorner(int r) {
		getPathObject().addUpRightRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going down and left.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addDownLeftRoundedCorner(int r) {
		getPathObject().addDownLeftRoundedCorner(r);
		return this;
	}

	/**
	 * Draw a quarter of a circle from the current drawing point going down and right.
	 * @param r The radius of the circle
	 * @return this
	 */
	public PathWidget addDownRightRoundedCorner(int r) {
		getPathObject().addDownRightRoundedCorner(r);
		return this;
	}

	public PathWidget arcTo(int rx, int ry, boolean largeArcFlag, boolean sweepFlag, int x, int y) {
		getPathObject().arcTo(rx, ry, largeArcFlag?1:0, sweepFlag?1:0, x, y);
		return this;
	}

	public PathWidget andClose() {
		getPathObject().andClose();
		return this;
	}

	@Override
	public PathWidget me() {
		return this;
	}
	
	public PathObject getPathObject() {
		return getRaphaelObject();
	}
}
