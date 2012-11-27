package net.atos.survey.core.dao;

import java.util.List;

public interface Dao<K, E> {
	E save(E entite);

	E update(E entite);

	void delete(K id);

	E findById(K id);

	E reLoad(K id);

	List<E> list(Integer first, Integer max);

	Long countLister();

	void flush();
	
	Integer getFirst(int page);

	Integer getMax(int page);

}
