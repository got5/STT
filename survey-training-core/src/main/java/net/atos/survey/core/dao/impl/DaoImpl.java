package net.atos.survey.core.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import net.atos.survey.core.dao.Dao;


public abstract class DaoImpl<K, E> implements Dao<K, E> {

	private Class<E> entityClass;

	
	@PersistenceContext(unitName = "survey")
	private EntityManager entityManagerRW;

	@SuppressWarnings("unchecked")
	public DaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass
				.getActualTypeArguments()[1];
	}

	@Override
	public E findById(K id) {
		return entityManagerRW.find(entityClass, id);
	}

	@Override
	public E save(E entite) {
		try{
		
		entityManagerRW.persist(entite);
		}catch(Exception e){
			e.printStackTrace();
		}
		return entite;
	}

	@Override
	public E update(E entite) {
		return entityManagerRW.merge(entite);
	}

	@Override
	public E reLoad(K id) {
		E entite = findById(id);
		entityManagerRW.refresh(entite);
		return entite;
	}

	@Override
	public void delete(K id) {
		E entite = findById(id);
		entityManagerRW.remove(entite);
	}

	@Override
	public List<E> list(Integer first, Integer max) {
		return list(first, max,
				"select distinct e from " + entityClass.getName() + " e ");
	}

	protected List<E> list(Integer first, Integer max, String queryString,Object... params) {
		TypedQuery<E> query = buildQuery(first, max, queryString, params);
		return query.getResultList();
	}

	protected E find(String queryString, Object... params) {
		TypedQuery<E> query = buildQuery(queryString, params);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null; // throw new InternalError();
		}
	}

	protected TypedQuery<E> buildQuery(String queryString, Object... params) {

		TypedQuery<E> query = entityManagerRW.createQuery(queryString,
				entityClass);

		int i = 0, j = 1;
		while (i < params.length) {
			if (params[i] instanceof Date) {
				query.setParameter(j, (Date) params[i],
						(TemporalType) params[i + 1]);
				i += 2;
			} else if (params[i] instanceof Calendar) {
				query.setParameter(j, (Calendar) params[i],
						(TemporalType) params[i + 1]);
				i += 2;
			} else {
				query.setParameter(j, params[i]);
				i++;
			}
			j++;
		}
		return query;
	}

	protected TypedQuery<E> buildQuery(Integer first, Integer max,
			String queryString, Object... params) {

		TypedQuery<E> query = entityManagerRW.createQuery(queryString,
				entityClass);
		int i = 0, j = 1;
		if (first != null && max != null) {
			query.setFirstResult(first);
			query.setMaxResults(max);
		}
		while (i < params.length) {
			if (params[i] instanceof Date) {
				query.setParameter(j, (Date) params[i],
						(TemporalType) params[i + 1]);
				i += 2;
			} else if (params[i] instanceof Calendar) {
				query.setParameter(j, (Calendar) params[i],
						(TemporalType) params[i + 1]);
				i += 2;
			} else {
				query.setParameter(j, params[i]);
				i++;
			}
			j++;
		}
		return query;
	}

	protected TypedQuery<Long> buildQueryCount(String queryString,
			Object... params) {

		TypedQuery<Long> query = entityManagerRW.createQuery(queryString,
				Long.class);

		int i = 0, j = 1;
		while (i < params.length) {
			if (params[i] instanceof Date) {
				query.setParameter(j, (Date) params[i],
						(TemporalType) params[i + 1]);
				i += 2;
			} else if (params[i] instanceof Calendar) {
				query.setParameter(j, (Calendar) params[i],
						(TemporalType) params[i + 1]);
				i += 2;
			} else {
				query.setParameter(j, params[i]);
				i++;
			}
			j++;
		}
		return query;
	}

	@Override
	public void flush() {
		entityManagerRW.flush();
	}

	@Override
	public Long countLister() {

		return count("select count(e) from " + entityClass.getName() + " e ");
	}

	protected Long count(String queryString, Object... params) {
		TypedQuery<Long> query = buildQueryCount(queryString, params);
		return query.getSingleResult();
	}

	@Override
	public Integer getFirst(int page) {

		if (page != 0)
			return 1 * (page - 1);
		return null;

	}

	@Override
	public Integer getMax(int page) {
		if (page != 0)
			return 1;
		return null;
	}
	
}