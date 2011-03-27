package uk.co.unclealex.rokta.gwt.client.view.main;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier;
import uk.co.unclealex.rokta.gwt.client.raphael.PaperFactory;
import uk.co.unclealex.rokta.gwt.client.raphael.PaperPanel;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;

public abstract class LoadingAwareRaphaelWidget<V> extends LoadingAwareComposite<V, PaperPanel> {
	
	private PaperFactory i_paperFactory;
	private int i_width;
	private int i_height;
	private PaperPanel i_paperPanel;
	
	
	public LoadingAwareRaphaelWidget(RoktaController roktaController, LoadingNotifier<V> notifier) {
		super(roktaController, notifier);
	}

	@Override
	protected PaperPanel create() {
		PaperFactory paperFactory = new PaperFactory();
		int width = 1;
		int height = 1;
		PaperPanel paperPanel = paperFactory.createPaperPanel(width, height);
		setPaperFactory(paperFactory);
		setPaperPanel(paperPanel);
		setWidth(width);
		setHeight(height);
		return paperPanel;
	}
	
	@Override
	protected void postCreate(PaperPanel widget) {
		resize(getWidth(), getHeight());
	}
	
	public void resize(int width, int height) {
		setWidth(width);
		setHeight(height);
		getPaperPanel().resize(width, height);
		setPixelSize(width, height);
	}
	
	public void onValueChanged(V value) {
		PaperPanel paperPanel = getPaperPanel();
		paperPanel.clear();
		redraw(getPaperFactory(), paperPanel, getWidth(), getHeight(), value);
	}

	protected abstract void redraw(PaperFactory paperFactory, PaperPanel paperPanel, int width, int height, V value);

	public void setPaperFactory(PaperFactory paperFactory) {
		i_paperFactory = paperFactory;
	}
	
	public PaperFactory getPaperFactory() {
		return i_paperFactory;
	}

	protected void setWidth(int width) {
		i_width = width;
	}
	
	public int getWidth() {
		return i_width;
	}

	protected void setHeight(int height) {
		i_height = height;
	}
	
	public int getHeight() {
		return i_height;
	}
	
	protected PaperPanel getPaperPanel() {
		return i_paperPanel;
	}

	protected void setPaperPanel(PaperPanel paperPanel) {
		i_paperPanel = paperPanel;
	}
}
