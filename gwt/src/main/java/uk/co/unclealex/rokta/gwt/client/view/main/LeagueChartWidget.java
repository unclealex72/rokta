package uk.co.unclealex.rokta.gwt.client.view.main;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier;
import uk.co.unclealex.rokta.gwt.client.raphael.Attributes;
import uk.co.unclealex.rokta.gwt.client.raphael.CircleWidget;
import uk.co.unclealex.rokta.gwt.client.raphael.PaperFactory;
import uk.co.unclealex.rokta.gwt.client.raphael.PaperPanel;
import uk.co.unclealex.rokta.gwt.client.raphael.PathWidget;
import uk.co.unclealex.rokta.gwt.client.raphael.RectangleWidget;
import uk.co.unclealex.rokta.gwt.client.raphael.Set;
import uk.co.unclealex.rokta.gwt.client.raphael.TextObject;
import uk.co.unclealex.rokta.gwt.client.raphael.TextWidget;
import uk.co.unclealex.rokta.pub.views.ChartEntryView;
import uk.co.unclealex.rokta.pub.views.ChartView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

public class LeagueChartWidget extends LoadingAwareRaphaelWidget<ChartView<Double>> {
	
	private boolean i_labelVisible = false;
	private Timer i_hidingTimer;
	private RectangleWidget i_frame;
	private TextWidget i_topLabel;
	private TextWidget i_bottomLabel;

	public LeagueChartWidget(RoktaController roktaController, LoadingNotifier<ChartView<Double>> notifier) {
		super(roktaController, notifier);
	}

	@Override
	protected void postCreate(final PaperPanel paperPanel) {
		Timer timer = new Timer() {
			@Override
			public void run() {
				if (!isLabelVisible()) {
					getFrame().hide();
					getTopLabel().hide();
					getBottomLabel().hide();
					paperPanel.safari();
				}
			}
		};
		setHidingTimer(timer);
		super.postCreate(paperPanel);
		resize(800, 300);
	}
	
	@Override
	protected void redraw(
			final PaperFactory paperFactory, final PaperPanel paperPanel, final int width, final int height, ChartView<Double> chart) {
		final LeagueChartWidgetMessages messages = GWT.create(LeagueChartWidgetMessages.class);
		MouseListener defaultMouseListener = wrapMouseListener(new MouseListenerAdapter());
		int leftGutter = 30;
		final int bottomGutter = 70;
		int topGutter = 20;
		Attributes textAttributes = paperFactory.createAttributes().setFont("12px \"Arial\"").setStroke("none").setFill("#00f");
		Attributes textAttributes1 = paperFactory.createAttributes().setFont("9px \"Arial\"").setStroke("none").setFill("#00f");
		List<String> labels = chart.getLabels();
		int columns = labels.size();
		double deltaX = (width - leftGutter) / (double) columns;
		int max = ((int) (chart.getMaximumValue() / 10)) * 10 + 10;
		int min = ((int) (chart.getMinimumValue() / 10)) * 10;
		double deltaY = (height - bottomGutter - topGutter) / (double) (max - min);
		paperPanel.drawGrid(
				(int) (leftGutter + deltaX * .5), 
				topGutter, 
				(int) (width - leftGutter - deltaX), 
				height - topGutter - bottomGutter,
				columns / 3,
				(max - min) / 10,
				"#333");
		RectangleWidget background =
			paperPanel.rect(
				(int) (leftGutter + deltaX * .5), topGutter, 
				(int) (width - leftGutter - deltaX), height - topGutter - bottomGutter).setAttributes(
						paperFactory.createAttributes().setOpacity(0.5d).setFill("#333").setFillOpacity(0.5d));
		Set majorBlanket = paperPanel.set();
		RectangleWidget frame = 
			paperPanel.rect(10, 10, 100, 40, 5).setAttributes(
					paperFactory.createAttributes().setFill("#000").setStroke("#474747").setStrokeWidth(2)).hide();
		TextWidget topLabel = paperPanel.text(60, 10, "").setAttributes(textAttributes).hide();
		TextWidget bottomLabel = paperPanel.text(60, 40, "").setAttributes(textAttributes1).hide();
		setFrame(frame);
		setTopLabel(topLabel);
		setBottomLabel(bottomLabel);
		List<TextObject> labelObjects = new ArrayList<TextObject>();
		for (ListIterator<String> labelIterator = labels.listIterator(); labelIterator.hasNext(); ) {
			final int x = (int) Math.round(leftGutter + deltaX * (labelIterator.nextIndex() + .5));
			final TextWidget labelWidget = paperPanel.text(x, height - bottomGutter + 16, labelIterator.next()).setAttributes(textAttributes).toBack();
			labelWidget.addClickListener(
				new ClickListener() {
					public void onClick(Widget sender) {
						labelWidget.rotate(90, x - 6, height - bottomGutter + 16);
					}
				});
			labelObjects.add(labelWidget.getTextObject());
		}
		for (ChartEntryView<Double> entryView : chart.getChartEntryViews()) {
			String colour = entryView.getColourView().toHexString();
			final PathWidget path = 
				paperPanel.path(
						paperFactory.createAttributes().setStroke(colour).setStrokeWidth(4).setStrokeLinejoin("round"));
			final Set blanket = paperPanel.set();
			for (ListIterator<Double> dataIterator = entryView.getValues().listIterator(); dataIterator.hasNext(); ) {
				int idx = dataIterator.nextIndex();
				final double datum = dataIterator.next();
				final int y = (int) Math.round(height - bottomGutter - deltaY * (datum - min));
				final int x = (int) Math.round(leftGutter + deltaX * (idx + .5));
				if (idx == 0) {
					path.moveTo(x, y);
				}
				else {
					path.cplineTo(x, y, 10);
				}
				final CircleWidget dot =
					paperPanel.circle(x, y, 5).setAttributes(paperFactory.createAttributes().setFill(colour).setStroke("#000")); 
				blanket.push(dot.getCircleObject());
				majorBlanket.push(dot.getCircleObject());
				final String label = labels.get(idx);
				MouseListener dotMouseListener = new MouseListenerAdapter() {
					@Override
					public void onMouseEnter(Widget sender) {
						double newX = x + 7.5;
						double newY = y - 19;
						if (newX + 100 > width) {
							newX -= 114;
						}
						int ms = 200 * (isLabelVisible()?1:0);
						path.toFront();
						blanket.toFront();
						getFrame().show().animate(
								paperFactory.createAnimatableAttributes().setX((int) newX).setY((int) newY), ms);
						getTopLabel().
							setAttributes(
									paperFactory.createAttributes().setText(messages.datum(datum))).
							show().
							animate(
									paperFactory.createAnimatableAttributes().setX((int) newX + 50).setY((int) newY + 12), ms);
						getBottomLabel().
							setAttributes(
									paperFactory.createAttributes().setText(label)).
							show().
							animate(paperFactory.createAnimatableAttributes().setX((int) newX + 50).setY((int) newY + 27), ms);
						dot.setAttributes(paperFactory.createAttributes().setR(7));
						paperPanel.safari();
					}
					@Override
					public void onMouseLeave(Widget sender) {
						dot.setAttributes(paperFactory.createAttributes().setR(5));
					}
				};
				dot.addMouseListener(wrapMouseListener(dotMouseListener));
				MouseListenerAdapter pathMouseListener = new MouseListenerAdapter() {
					@Override
					public void onMouseEnter(Widget sender) {
						path.toFront();
						blanket.toFront();
					}
				};
				path.addMouseListener(wrapMouseListener(pathMouseListener));
			}
	  }
		for (SourcesMouseEvents source : new SourcesMouseEvents[] { frame, topLabel, bottomLabel }) {
			source.addMouseListener(defaultMouseListener);
		}
		background.addMouseListener(wrapMouseListener(new MouseListenerAdapter(), 2000));
		frame.toFront();
		topLabel.toFront();
		bottomLabel.toFront();
		majorBlanket.toFront();
		
		for (TextObject labelObject : labelObjects) {
			labelObject.rotate(0, 0, 0);
		}
	}

	protected MouseListener wrapMouseListener(final MouseListener mouseListener) {
		return wrapMouseListener(mouseListener, 1);
	}
	
	protected MouseListener wrapMouseListener(final MouseListener mouseListener, final int delay) {
		MouseListener newMouseListener = new MouseListener() {

			public void onMouseDown(Widget sender, int x, int y) {
				mouseListener.onMouseDown(sender, x, y);
			}

			public void onMouseEnter(Widget sender) {
				setLabelVisible(true);
				getHidingTimer().cancel();
				mouseListener.onMouseEnter(sender);
				getFrame().toFront();
				getTopLabel().toFront();
				getBottomLabel().toFront();
			}

			public void onMouseLeave(Widget sender) {
				mouseListener.onMouseLeave(sender);
				setLabelVisible(false);
				getHidingTimer().schedule(delay);
			}

			public void onMouseMove(Widget sender, int x, int y) {
				mouseListener.onMouseMove(sender, x, y);
			}

			public void onMouseUp(Widget sender, int x, int y) {
				mouseListener.onMouseUp(sender, x, y);
			}		
		};
		return newMouseListener;
	}
	
	protected boolean isLabelVisible() {
		return i_labelVisible;
	}

	protected void setLabelVisible(boolean isLabelVisible) {
		this.i_labelVisible = isLabelVisible;
	}

	protected Timer getHidingTimer() {
		return i_hidingTimer;
	}

	protected void setHidingTimer(Timer hidingTimer) {
		i_hidingTimer = hidingTimer;
	}

	protected RectangleWidget getFrame() {
		return i_frame;
	}

	protected void setFrame(RectangleWidget frame) {
		i_frame = frame;
	}

	protected TextWidget getTopLabel() {
		return i_topLabel;
	}

	protected void setTopLabel(TextWidget topLabel) {
		i_topLabel = topLabel;
	}

	protected TextWidget getBottomLabel() {
		return i_bottomLabel;
	}

	protected void setBottomLabel(TextWidget bottomLabel) {
		i_bottomLabel = bottomLabel;
	}
}
