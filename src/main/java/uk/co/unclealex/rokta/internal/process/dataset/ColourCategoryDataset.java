/**
 * 
 */
package uk.co.unclealex.rokta.internal.process.dataset;

import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

import uk.co.unclealex.rokta.internal.model.Colour;

/**
 * @author alex
 *
 */
public class ColourCategoryDataset extends DefaultCategoryDataset implements ColourDataset {

	private List<Colour> i_colours;

	/**
	 * @return the colours
	 */
	public List<Colour> getColours() {
		return i_colours;
	}

	/**
	 * @param colours the colours to set
	 */
	public void setColours(List<Colour> colours) {
		i_colours = colours;
	}
}
