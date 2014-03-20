package net.atos.survey.core.dao;

import javax.ejb.Local;

import net.atos.survey.core.entity.Room;

import java.util.List;


@Local
public interface RoomDao extends Dao<Long, Room> {


    List<Room> listByName(String roomName);
}
