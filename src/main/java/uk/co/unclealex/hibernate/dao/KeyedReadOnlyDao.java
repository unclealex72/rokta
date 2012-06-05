package uk.co.unclealex.hibernate.dao;

import java.util.SortedSet;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.model.KeyedBean;

@Transactional
public interface KeyedReadOnlyDao<T extends KeyedBean<T>> {

	public SortedSet<T> getAll();
	public long count();
	public T findById(int id);
	public void dismiss(T keyedBean);
	public void flush();
	public void clear();

}
