package uk.co.unclealex.rokta.gwt.client.view.main;

import java.util.Map;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.HandDistributionChartModel;
import uk.co.unclealex.rokta.gwt.client.raphael.Attributes;
import uk.co.unclealex.rokta.gwt.client.raphael.PaperFactory;
import uk.co.unclealex.rokta.gwt.client.raphael.PaperPanel;
import uk.co.unclealex.rokta.gwt.client.raphael.PathWidget;
import uk.co.unclealex.rokta.pub.views.Hand;

public class HandDistributionChartWidget extends LoadingAwareRaphaelWidget<Map<Hand,Integer>> {

	public HandDistributionChartWidget(RoktaController roktaController, HandDistributionChartModel handDistributionChartModel) {
		super(roktaController, handDistributionChartModel);
	}

	@Override
	protected void postCreate(PaperPanel paperPanel) {
		super.postCreate(paperPanel);
		resize(700, 700);
	}
	
	@Override
	protected void redraw(PaperFactory paperFactory, PaperPanel paperPanel, int width, int height, Map<Hand, Integer> data) {
		double angle = 0;
		int total = 0;
		for (int value : data.values()) {
			total += value;
		}
		int cx = getWidth() / 2;
		int cy = getHeight() / 2;
		int radius = cx - 150;
		int delta = 30;
		final int ms = 200;
		for (Map.Entry<Hand, Integer> entry : data.entrySet()) {
			Hand hand = entry.getKey();
			int value = entry.getValue();
			double anglePlus = 360 * value / (double) total;
			double popAngle = angle + (anglePlus / 2d);
			String colour = getColour(hand);
			PathWidget sectorWidget = 
				sector(cx, cy, radius, angle, angle + anglePlus, getPaperFactory().createAttributes().setStroke("#000").setFill(colour));
			getPaperPanel().text(
				toInteger(cx + (radius + delta + 55) * cos(-popAngle)),
				toInteger(cy + (radius + delta + 25) * sin(-popAngle)),
				hand.getDescription());
			angle += anglePlus;
		}
	}	

	protected PathWidget sector(int centreX, int centreY, int radius, double startAngle, double endAngle, Attributes attributes) {
		int x1 = (int) Math.round(centreX + radius * Math.cos(-radians(startAngle)));
		int x2 = (int) Math.round(centreX + radius * Math.cos(-radians(endAngle)));
		int y1 = (int) Math.round(centreY + radius * Math.sin(-radians(startAngle)));
		int y2 = (int) Math.round(centreY + radius * Math.sin(-radians(endAngle)));
		return 
			getPaperPanel().path(attributes).moveTo(centreX, centreY).lineTo(x1, y1).
			arcTo(radius, radius, endAngle - startAngle > 180, false, x2, y2).andClose();
	}

	protected int toInteger(double d) {
		return (int) Math.round(d);
	}
	
	protected double sin(double degrees) {
		return Math.sin(radians(degrees));
	}
	
	protected double cos(double degrees) {
		return Math.sin(radians(degrees));
	}
	
	protected double radians(double degrees) {
		return Math.PI / 180d * degrees;
	}
	
	protected String getColour(Hand hand) {
		if (hand == Hand.ROCK) {
			return "#f00";
		}
		else if (hand == Hand.SCISSORS) {
			return "#0f0";
		}
		else {
			return "#00f";
		}
	}


}
