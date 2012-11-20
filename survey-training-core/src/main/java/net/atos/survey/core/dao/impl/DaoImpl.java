package net.atos.survey.core.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
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
	public E rechercher(K id) {
		return entityManagerRW.find(entityClass, id);
	}

	@Override
	public E enregistrer(E entite) {
		try{
		
		entityManagerRW.persist(entite);
		}catch(Exception e){
			e.printStackTrace();
		}
		return entite;
	}

	@Override
	public E mettreAJour(E entite) {
		return entityManagerRW.merge(entite);
	}

	@Override
	public E recharger(K id) {
		E entite = rechercher(id);
		entityManagerRW.refresh(entite);
		return entite;
	}

	@Override
	public void supprimer(K id) {
		E entite = rechercher(id);
		entityManagerRW.remove(entite);
	}

	@Override
	public List<E> lister(Integer first, Integer max, boolean del) {
		return liste(first, max,
				"select distinct e from " + entityClass.getName() + " e "
						+ "where e.supprime =?1", del);
	}

	protected List<E> liste(Integer first, Integer max, String queryString,
			Object... params) {
		TypedQuery<E> query = buildQuery(first, max, queryString, params);
		return query.getResultList();
	}

	protected E recherche(String queryString, Object... params) {
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
	public Long countLister(boolean del) {

		return count("select count(e) from " + entityClass.getName() + " e "
				+ "where e.supprime =?1", del);
	}

	protected Long count(String queryString, Object... params) {
		TypedQuery<Long> query = buildQueryCount(queryString, params);
		return query.getSingleResult();
	}

	
}