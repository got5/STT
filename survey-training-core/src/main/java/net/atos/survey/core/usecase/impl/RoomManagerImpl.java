package net.atos.survey.core.usecase.impl;

import net.atos.survey.core.dao.RoomDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Room;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TypeTraining;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.UserNotExistException;
import net.atos.survey.core.usecase.RoomManager;
import net.atos.survey.core.usecase.TrainingManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RoomManagerImpl implements RoomManager {

	@Inject
    RoomDao roomDao;

    @Override
	public Room createRoom(String name)   {
		
		Room room = new Room(name);
		return roomDao.save(room);
	}

	@Override
	public List<Room> listRoomName(String roomName) {
		
		return roomDao.listByName(roomName);
		
			
	}

}
