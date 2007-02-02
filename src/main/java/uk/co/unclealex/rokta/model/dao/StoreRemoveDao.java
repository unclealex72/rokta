package uk.co.unclealex.rokta.model.dao;

public interface StoreRemoveDao<T> {

	public void store(T obj);

	public void remove(T obj);
}
