package net.atos.survey.core.dao;

import java.util.List;

public interface Dao<K, E> {
	E enregistrer(E entite);

	E mettreAJour(E entite);

	void supprimer(K id);

	E rechercher(K id);

	E recharger(K id);

	List<E> lister(Integer first, Integer max, boolean supprime);

	Long countLister(boolean supprime);

	void flush();

}
