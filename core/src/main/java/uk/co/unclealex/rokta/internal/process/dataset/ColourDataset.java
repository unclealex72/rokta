/**
 * 
 */
package uk.co.unclealex.rokta.process.dataset;

import java.util.List;

import org.jfree.data.general.Dataset;

import uk.co.unclealex.rokta.model.Colour;

/**
 * @author alex
 *
 */
public interface ColourDataset extends Dataset {

	public List<Colour> getColours();
}
