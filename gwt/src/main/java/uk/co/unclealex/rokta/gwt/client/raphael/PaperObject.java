package uk.co.unclealex.rokta.gwt.client.raphael;

import com.google.gwt.core.client.JavaScriptObject;

public class PaperObject extends JavaScriptObject {

	protected PaperObject() {
		// Required constructor
	}
	
	/**
	 * Draws a circle.
	 * @param x <i>x</i> coordinate of the centre
	 * @param y <i>y</i> coordinate of the centre
	 * @param r The radius
	 * @return A new circle.
	 */
	public final native CircleObject circle(int x, int y, int r)
	/*-{ return this.circle(x, y, r); }-*/;

	/**
	 * Draws a rectangle.
	 * @param x <i>x</i> coordinate of top left corner
	 * @param y <i>y</i> coordinate of top left corner
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @return A new rectangle
	 */
	public final native RectangleObject rect(int x, int y, int width, int height)
	/*-{ return this.rect(x, y, width, height); }-*/;

	/**
	 * Draws a rectangle.
	 * @param x <i>x</i> coordinate of top left corner
	 * @param y <i>y</i> coordinate of top left corner
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param roundingRadius The radius for rounded corners
	 * @return A new rectangle
	 */
	public final native RectangleObject rect(int x, int y, int width, int height, int roundingRadius)
	/*-{ return this.rect(x, y, width, height, roundingRadius); }-*/;

	/**
	 * Draws an ellipse.
	 * @param x <i>x</i> coordinate of the centre
	 * @param y <i>x</i> coordinate of the centre
	 * @param rx The horizontal radius
	 * @param ry The vertical radius
	 * @return A new ellipse.
	 */
	public final native EllipseObject ellipse(int x, int y, int rx, int ry)
	/*-{ return this.ellipse(x, y, rx, ry); }-*/;

	/**
	 * Embeds an image into the SVG/VML canvas.
	 * @param uri URI of the source image
	 * @param x <i>x</i> coordinate position
	 * @param y <i>y</i> coordinate position
	 * @param width The width of the image
	 * @param height The height of the image
	 * @return A new image
	 */
	public final native ImageObject image(String uri, int x, int y, int width, int height)
	/*-{ return this.image(uri, x, y, width, height); }-*/;

	/**
	 * Creates array-like object to keep and operate couple of elements at once. 
	 * Warning: it does not create any elements for itself in the page.
	 * @return A new set
	 */
	public final native Set set()
	/*-{ return this.set(); }-*/;

	/**
	 * Draws a text string. If you need line breaks, put "\n" in the string.
	 * @param x <i>x</i> coordinate position
	 * @param y <i>y</i> coordinate position
	 * @param text The text string to draw
	 * @return A new text object
	 */
	public final native TextObject text(int x, int y, String text)
	/*-{ return this.text(x, y, text); }-*/;

	/**
	 * Initialises path drawing. Typically, this function returns an empty path object and to draw paths you use its built-in methods like lineTo and curveTo. 
	 * However, you can also specify a path literally by supplying the path data as a second argument.
	 * @param attributes Attributes for the resulting path
	 * @return A new path
	 */
	public final native PathObject path(Attributes attributes)
	/*-{ return this.path(attributes); }-*/;

	/**
	 * Initialises path drawing. Typically, this function returns an empty path object and to draw paths you use its built-in methods 
	 * like lineTo and curveTo. 
	 * However, you can also specify a path literally by supplying the path data as a second argument.
	 * @param attributes Attributes for the resulting path
	 * @param svgPath Path data in SVG path string format
	 * @return A new Path
	 */
	public final native PathObject path(Attributes attributes, String svgPath)
	/*-{ return this.path(attributes, svgPath); }-*/;

	/**
	 * Some sort of safari fix.
	 */
	public final native void safari()
	/*-{ this.safari(); }-*/;

	public final native PathObject drawGrid(int x, int y, int width, int height, int columns, int rows, String colour)
	/*-{ return this.drawGrid(x, y, width, height, columns, rows, colour); }-*/;

	/**
	 * Clear all objects from the paper.
	 */
	public final native void clear()
	/*-{ this.clear(); }-*/;

	public final native void setSize(int width, int height)
	/*-{ this.setSize(width, height); }-*/;
}
