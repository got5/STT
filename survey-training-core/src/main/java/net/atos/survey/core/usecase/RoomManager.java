package net.atos.survey.core.usecase;

import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.entity.Room;

import javax.ejb.Local;
import java.util.List;


@Local
public interface RoomManager {
    Room createRoom(String name);

    List<Room> listRoomName(String mot);
}
