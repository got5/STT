package net.atos.survey.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Table@Entity
public class Survey implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private boolean publish = false;
	
	
	
	
	
	@OneToMany
	@OrderColumn
	private List<Question> questions = new ArrayList<Question>();

	
	
	
	


	public long getId() {
		return id;
	}
	
	
	
	public void addQuestion(Question question) {
		if(!questions.contains(question)){
			
			question.setNumQ(getQuestionSize());
			questions.add(question);
		}	
	}
	
	public void deleteQuestion(Question question){
		
			
		int index = questions.indexOf(question);
		if(index!=-1)
			questions.remove(index);
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Survey other = (Survey) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	public void publier(){
		publish = true;
	}
	
	private int getQuestionSize(){
		return questions.size();
	}

}
