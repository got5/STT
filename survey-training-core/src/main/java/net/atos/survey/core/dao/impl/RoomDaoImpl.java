package net.atos.survey.core.dao.impl;

import javax.ejb.Stateless;

import net.atos.survey.core.dao.RoomDao;
import net.atos.survey.core.entity.Room;
import net.atos.survey.core.entity.User;

import java.util.List;

@Stateless
public class RoomDaoImpl extends DaoImpl<Long, Room> implements RoomDao {


    @Override
    public List<Room> listByName(String mot) {
        String queryString;
        if (mot != null)
            if (!mot.equals("")) {
                mot = "%" +mot.toLowerCase() + "%";

                queryString = "select r from Room r "
                        + "where (lower(r.name)) like ?1 order by r.name";

                return list(null, null, queryString, mot);
            }
        queryString = "select r from Room R order by r.name";
        return list(null, null, queryString);
    }
}
