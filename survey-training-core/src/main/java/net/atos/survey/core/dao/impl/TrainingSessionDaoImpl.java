package net.atos.survey.core.dao.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TemporalType;

import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.User;

@Stateless(name="net.atos.survey.core.dao.TrainingSessionDao")
public class TrainingSessionDaoImpl extends DaoImpl<Long, TrainingSession> implements TrainingSessionDao {

	@Override
	public List<TrainingSession> findIncompleteByUser(User loggedUser,
			Calendar dateXMonthBefore) {
		Calendar now = new GregorianCalendar();
		
		String orderQuery = " order by t.dateS DESC ";
		String queryString = "select t from TrainingSession t "
				+" where not exists ( select r.id from t.responses r "
				    +" where key(r) = ?1) " 
				+ " and t.dateS > ?2 "
				+ " and ?1 MEMBER OF t.trainees "
				+ " and t.dateS < ?3"
				;
		
		queryString+=orderQuery;
		
		return list(null,null, queryString, loggedUser,dateXMonthBefore,TemporalType.DATE,now,TemporalType.DATE);
	
	}

	@Override
	public List<TrainingSession> listByCriteria(Training training,
			User instructor, Calendar from, Calendar to) {
		
		if(to!=null){
		System.out.println("to " + " day " + to.get(Calendar.DAY_OF_MONTH)
				+ " month " + to.get(Calendar.MONTH) + " year "
				+ to.get(Calendar.YEAR));
		}
		String queryString= "select ts from TrainingSession ts "
				+ "where ts.training = ?1 ";
		String orderByString = " order by ts.dateS";
		
		if(to==null){
			//rien
			if(from == null){
				//rien
				if(instructor == null){
					return list(null, null, queryString+orderByString, training);
					
				}else{
					queryString += " and ts.instructor = ?2 ";
					return list(null, null, queryString+orderByString, training,instructor);
				}
			}else{
				queryString+=" and ts.dateS > ?2 ";
				if(instructor == null){
					return list(null, null, queryString+orderByString, training,from,TemporalType.DATE);
					
				}else{
					queryString += " and ts.instructor = ?3 ";
					return list(null, null, queryString+orderByString, training,from,TemporalType.DATE,instructor);
				}
				
			}
		}
		else{
			queryString+=" and ts.dateE < ?2 ";
			
			if(from == null){
				//rien
				if(instructor == null){
					return list(null, null, queryString+orderByString, training,to,TemporalType.DATE);
					
				}else{
					queryString += " and ts.instructor = ?3 ";
					return list(null, null, queryString+orderByString, training, to,TemporalType.DATE,instructor);
				}
			}else{
				queryString+=" and ts.dateS > ?3 ";
				if(instructor == null){
					return list(null, null, queryString+orderByString, training,to,TemporalType.DATE,from,TemporalType.DATE);
					
				}else{
					queryString += " and ts.instructor = ?4 ";
					return list(null, null, queryString+orderByString, training,to,TemporalType.DATE,from,TemporalType.DATE,instructor);
				}
				
			}
		}
		
	}

	
	
	
}
