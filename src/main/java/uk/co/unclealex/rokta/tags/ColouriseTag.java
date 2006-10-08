/**
 * 
 */
package uk.co.unclealex.rokta.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.process.dataset.ColourDataset;
import de.laures.cewolf.ChartHolder;
import de.laures.cewolf.ChartValidationException;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.PostProcessingException;

/**
 * @author alex
 *
 */
public class ColouriseTag extends TagSupport {

	private String i_chartid;
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		ChartHolder holder = (ChartHolder) pageContext.findAttribute(getChartid());
		JFreeChart chart;
		Object dataset;
		try {
			chart = (JFreeChart) holder.getChart();
			dataset = holder.getDataset();
		} catch (DatasetProduceException e) {
			throw new JspException(e);
		} catch (PostProcessingException e) {
			throw new JspException(e);
		} catch (ChartValidationException e) {
			throw new JspException(e);
		}
		if (dataset instanceof ColourDataset) {
			List<Colour> colours = ((ColourDataset) dataset).getColours(); 
			CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
			List categories = chart.getCategoryPlot().getCategories();
			for (int idx = 0; idx < Math.min(colours.size(), categories.size()); idx++) {
				renderer.setSeriesPaint(idx, colours.get(idx).toColor());
			}
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the chartId
	 */
	public String getChartid() {
		return i_chartid;
	}

	/**
	 * @param chartId the chartId to set
	 */
	public void setChartid(String chartId) {
		i_chartid = chartId;
	}
}
