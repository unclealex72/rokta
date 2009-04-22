package uk.co.unclealex.rokta.gwt.client.model;

public interface LoadingModel<V> extends Model<V> {

	public boolean isLoading();
	
	public void setLoading(boolean loading);
}
