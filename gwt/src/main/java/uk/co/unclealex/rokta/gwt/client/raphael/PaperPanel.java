package uk.co.unclealex.rokta.gwt.client.raphael;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import uk.co.unclealex.rokta.gwt.client.controller.SourcesMouseEventsSupport;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

public class PaperPanel extends Panel implements SourcesMouseEvents {

	private PaperObject i_paperObject;
	private Collection<Widget> i_widgets = new ArrayList<Widget>();
	private SourcesMouseEventsSupport i_sourcesMouseEventsSupport = new SourcesMouseEventsSupport(this);

	public PaperPanel(PaperObject paperObject, Element element) {
		i_paperObject = paperObject;
		setElement(element);
	}
	
	/**
	 * Draws a circle.
	 * @param x <i>x</i> coordinate of the centre
	 * @param y <i>y</i> coordinate of the centre
	 * @param r The radius
	 * @return A new circle.
	 */
	public CircleWidget circle(int x, int y, int r) {
		return doAdd(new CircleWidget(getPaperObject().circle(x, y, r)));
	}

	/**
	 * Draws a rectangle.
	 * @param x <i>x</i> coordinate of top left corner
	 * @param y <i>y</i> coordinate of top left corner
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @return A new rectangle
	 */
	public RectangleWidget rect(int x, int y, int width, int height) {
		return doAdd(new RectangleWidget(getPaperObject().rect(x, y, width, height)));
	}

	/**
	 * Draws a rectangle.
	 * @param x <i>x</i> coordinate of top left corner
	 * @param y <i>y</i> coordinate of top left corner
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param roundingRadius The radius for rounded corners
	 * @return A new rectangle
	 */
	public RectangleWidget rect(int x, int y, int width, int height, int roundingRadius) {
		return doAdd(new RectangleWidget(getPaperObject().rect(x, y, width, height, roundingRadius)));
	}

	/**
	 * Draws an ellipse.
	 * @param x <i>x</i> coordinate of the centre
	 * @param y <i>x</i> coordinate of the centre
	 * @param rx The horizontal radius
	 * @param ry The vertical radius
	 * @return A new ellipse.
	 */
	public EllipseWidget ellipse(int x, int y, int rx, int ry) {
		return doAdd(new EllipseWidget(getPaperObject().ellipse(x, y, rx, ry)));
	}

	/**
	 * Embeds an image into the SVG/VML canvas.
	 * @param uri URI of the source image
	 * @param x <i>x</i> coordinate position
	 * @param y <i>y</i> coordinate position
	 * @param width The width of the image
	 * @param height The height of the image
	 * @return A new image
	 */
	public ImageWidget image(String uri, int x, int y, int width, int height) {
		return doAdd(new ImageWidget(getPaperObject().image(uri, x, y, width, height)));
	}

	/**
	 * Creates array-like object to keep and operate couple of elements at once. 
	 * Warning: it does not create any elements for itself in the page.
	 * @return A new set
	 */
	public Set set() {
		return getPaperObject().set();
	}

	/**
	 * Draws a text string. If you need line breaks, put "\n" in the string.
	 * @param x <i>x</i> coordinate position
	 * @param y <i>y</i> coordinate position
	 * @param text The text string to draw
	 * @return A new text object
	 */
	public TextWidget text(int x, int y, String text) {
		return doAdd(new TextWidget(getPaperObject().text(x, y, text)));
	}

	/**
	 * Initialises path drawing. Typically, this function returns an empty path object and to draw paths you use its built-in methods like lineTo and curveTo. 
	 * However, you can also specify a path literally by supplying the path data as a second argument.
	 * @param attributes Attributes for the resulting path
	 * @return A new path
	 */
	public PathWidget path(Attributes attributes) {
		return doAdd(new PathWidget(getPaperObject().path(attributes)));
	}

	/**
	 * Some sort of Safari fix.
	 */
	public void safari() {
		getPaperObject().safari();
	}

	public PathWidget drawGrid(int x, int y, int width, int height, int columns, int rows, String colour) {
		return doAdd(new PathWidget(getPaperObject().drawGrid(x, y, width, height, columns, rows, colour)));
	}

	/**
	 * Initialises path drawing. Typically, this function returns an empty path object and to draw paths you use its built-in methods 
	 * like lineTo and curveTo. 
	 * However, you can also specify a path literally by supplying the path data as a second argument.
	 * @param attributes Attributes for the resulting path
	 * @param svgPath Path data in SVG path string format
	 * @return A new Path
	 */
	public PathWidget path(Attributes attributes, String svgPath) {
		return doAdd(new PathWidget(getPaperObject().path(attributes, svgPath)));
	}
	
	/**
	 * Remove all child widgets and clear the paper for any redrawing.
	 */
	public void clear() {
		for (Widget widget : new ArrayList<Widget>(getWidgets())) {
			remove(widget);
		}
		getPaperObject().clear();
	}
	
	public void resize(int width, int height) {
		getPaperObject().setSize(width, height);
		setPixelSize(width, height);
	}
	
	protected <O extends RaphaelObject<O>, R extends RaphaelWidget<O, R>> R doAdd(R raphaelWidget) {
		getWidgets().add(raphaelWidget);
		adopt(raphaelWidget);
		return raphaelWidget;
	}
	
	@Override
	public boolean remove(Widget child) {
		if (child.getParent() != this) {
			return false;
		}
		orphan(child);
		((RaphaelWidget<?,?>) child).getRaphaelObject().remove();
		getWidgets().remove(child);
		return true;
	}
	
	public Iterator<Widget> iterator() {
		return getWidgets().iterator();
	}

	public void addMouseListener(MouseListener listener) {
		getSourcesMouseEventsSupport().addMouseListener(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		getSourcesMouseEventsSupport().removeMouseListener(listener);
	}

	@Override
	public void onBrowserEvent(Event event) {
		if (!getSourcesMouseEventsSupport().onBrowserEvent(event)) {
			super.onBrowserEvent(event);
		}
	}
	
	public SourcesMouseEventsSupport getSourcesMouseEventsSupport() {
		return i_sourcesMouseEventsSupport;
	}

	public PaperObject getPaperObject() {
		return i_paperObject;
	}
	
	public Collection<Widget> getWidgets() {
		return i_widgets;
	}
}
