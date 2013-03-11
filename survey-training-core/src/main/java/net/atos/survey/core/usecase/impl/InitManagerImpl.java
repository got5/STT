package net.atos.survey.core.usecase.impl;

import static net.atos.survey.core.tool.Param.MCQ1;
import static net.atos.survey.core.tool.Param.MCQ2;
import static net.atos.survey.core.tool.Param.TF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.atos.survey.core.dao.CategoryDao;
import net.atos.survey.core.dao.ChoiceDao;
import net.atos.survey.core.dao.OQuestionDao;
import net.atos.survey.core.dao.OResponseDao;
import net.atos.survey.core.dao.ResponseSurveyDao;
import net.atos.survey.core.dao.RoomDao;
import net.atos.survey.core.dao.SimpleMCQResponseDao;
import net.atos.survey.core.dao.SimpleMCQuestionDao;
import net.atos.survey.core.dao.SurveyDao;
import net.atos.survey.core.dao.SurveyTemplateDao;
import net.atos.survey.core.dao.ThemeDao;
import net.atos.survey.core.dao.TrainingDao;
import net.atos.survey.core.dao.TrainingSessionDao;
import net.atos.survey.core.dao.UserDao;
import net.atos.survey.core.entity.Category;
import net.atos.survey.core.entity.Choice;
import net.atos.survey.core.entity.OQuestion;
import net.atos.survey.core.entity.Room;
import net.atos.survey.core.entity.SimpleMCQuestion;
import net.atos.survey.core.entity.Survey;
import net.atos.survey.core.entity.Theme;
import net.atos.survey.core.entity.Training;
import net.atos.survey.core.entity.TrainingSession;
import net.atos.survey.core.entity.TypeTraining;
import net.atos.survey.core.entity.User;
import net.atos.survey.core.exception.NotInitaliazedSurveyDataBaseException;
import net.atos.survey.core.exception.TrainingNotExistException;
import net.atos.survey.core.usecase.InitManager;
import net.atos.survey.core.usecase.OQuestionManager;
import net.atos.survey.core.usecase.SimpleMCQuestionManager;
import net.atos.survey.core.usecase.SurveyManager;
import net.atos.survey.core.usecase.TrainingManager;
import net.atos.survey.core.usecase.TrainingSessionManager;

@Stateless
public class InitManagerImpl implements InitManager {

	@Inject
	CategoryDao categoryDao;
	@Inject
	ChoiceDao choiceDao;
	@Inject
	OQuestionDao oQuestionDao;
	@Inject
	OResponseDao oResponseDao;
	@Inject
	ResponseSurveyDao responseSurveyDao;
	@Inject
	RoomDao roomDao;
	@Inject
	SimpleMCQResponseDao simpleMCQResponseDao;
	@Inject
	SimpleMCQuestionDao simpleMCQuestionDao;
	@Inject
	SurveyDao surveyDao;
	@Inject
	SurveyTemplateDao surveyTemplateDao;
	@Inject
	ThemeDao themeDao;
	@Inject
	TrainingDao trainingDao;
	@Inject
	TrainingSessionDao trainingSessionDao;
	@Inject
	UserDao userDao;

	@Inject
	SimpleMCQuestionManager simpleMCQuestionManager;
	@Inject
	OQuestionManager oQuestionManager;
	@Inject
	TrainingManager trainingManager;
	@Inject
	SurveyManager surveyManager;
	@Inject
	TrainingSessionManager trainingSessionManager;

	private Category ca1;
	private Category ca2;
	private Category ca3;

	private Theme t1;
	private Theme t2;
	private Theme t3;
	private Theme t4;
	private Theme t5;
	private Theme t6;

	private List<User> tapestry_instructors = new ArrayList<User>();
	private List<TrainingSession> tapestry_Session = new ArrayList<TrainingSession>();

	private Training tapestryBasic;

	private Survey survey;

	@Override
	public void initDB() {

		try {

			long nb = categoryDao.countLister();

			if (nb == 0) {

				// create the categories
				Category ca0 = new Category("DEFAULT");
				ca1 = new Category("PRE - REQUIS");
				ca1.setRemarque("A remplir après la présentation de l’introduction");
				ca2 = new Category("VOTRE EVALUATION");
				ca2.setRemarque("A remplir en fin de stage");
				ca3 = new Category("BILAN PERSONEL");

				ca0 = categoryDao.save(ca0);
				ca1 = categoryDao.save(ca1);
				ca2 = categoryDao.save(ca2);
				ca3 = categoryDao.save(ca3);

				// create the Choices
				Choice ch1 = new Choice("Très bonne");
				Choice ch2 = new Choice("Bonne");
				Choice ch3 = new Choice("Moyenne");
				Choice ch4 = new Choice("Aucune");
				
				Choice ch7 = new Choice("Très bien");
				Choice ch8 = new Choice("Bien");
				Choice ch9 = new Choice("Moyen");
				Choice ch10 = new Choice("Insufisant");

				Choice ch5 = new Choice("Oui");
				Choice ch6 = new Choice("Non");

				

				ch1 = choiceDao.save(ch1);
				ch2 = choiceDao.save(ch2);
				ch3 = choiceDao.save(ch3);
				ch4 = choiceDao.save(ch4);

				ch5 = choiceDao.save(ch5);
				ch6 = choiceDao.save(ch6);

				ch7 = choiceDao.save(ch7);
				ch8 = choiceDao.save(ch8);
				ch9 = choiceDao.save(ch9);
				ch10 = choiceDao.save(ch10);

				// question
				SimpleMCQuestion mcq1 = new SimpleMCQuestion(MCQ1, true, null,
						false);
				mcq1.addChoice(ch1);
				mcq1.addChoice(ch2);
				mcq1.addChoice(ch3);
				mcq1.addChoice(ch4);

				SimpleMCQuestion mcq2 = new SimpleMCQuestion(TF, true,
						"Si non pourquoi ?", false);
				mcq2.addChoice(ch5);
				mcq2.addChoice(ch6);
				mcq2.setTrigger(ch6);

				SimpleMCQuestion mcq3 = new SimpleMCQuestion(MCQ2, true,
						"merci de justifier toute note inférieure à "
								+ ch9.getName(), true);
				mcq3.addChoice(ch7);
				mcq3.addChoice(ch8);
				mcq3.addChoice(ch9);
				mcq3.addChoice(ch10);
				mcq3.setTrigger(ch9);

				mcq1 = simpleMCQuestionDao.save(mcq1);
				mcq2 = simpleMCQuestionDao.save(mcq2);
				mcq3 = simpleMCQuestionDao.save(mcq3);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Already done");
		}

	}

	@Override
	public void testOneSurvey() throws Exception {

		User responsable = new User("Facon", "François", "AWL", "GPS", "TD","");
		responsable.setLogin("facon");
		responsable.setPassword("facon");

		User instructeur = new User("Khadri", "Nourredine", "AWL", "GPS", "TD","");
		instructeur.setLogin("khadri");
		instructeur.setPassword("khadri");

		User instructeur2 = new User("Demey", "Emmanuel", "AWL", "GPS", "TD","");
		instructeur2.setLogin("demey");
		instructeur2.setPassword("demey");

		User instructeur3 = new User("Verachten", "Bruno", "AWL", "GPS", "TD","");
		instructeur3.setLogin("bruno");
		instructeur3.setPassword("bruno");

		User instructeur4 = new User("Marot", "Pierre", "AWL", "GPS", "TD","");
		instructeur4.setLogin("marot");
		instructeur4.setPassword("marot");

		User instructeur5 = new User("Wroblewski", "Laurent", "AWL", "GPS","TD","");
		instructeur5.setLogin("laurent");
		instructeur5.setPassword("laurent");

		tapestry_instructors.add(instructeur);
		tapestry_instructors.add(instructeur2);
		tapestry_instructors.add(instructeur3);
		tapestry_instructors.add(instructeur4);
		tapestry_instructors.add(instructeur5);

		responsable = userDao.save(responsable);
		saveInstructor();

		User stagiaire2 = new User("Dupont", "Jean", "AWL", "GPS", "TD","");
		stagiaire2.setLogin("dupont");
		stagiaire2.setPassword("dupont");

		User stagiaire3 = new User("Dupond", "Luc", "AWL", "GPS", "TD","");
		stagiaire3.setLogin("dupond");
		stagiaire3.setPassword("dupond");

		stagiaire2 = userDao.save(stagiaire2);
		stagiaire3 = userDao.save(stagiaire3);

		// THEME
		t1 = new Theme("Partie théorique");
		t2 = new Theme("Exercices et TP");
		t3 = new Theme("Documentation");
		t4 = new Theme("Formateur");
		t5 = new Theme("Divers");
		t6 = new Theme("Le stage dans son ensemble");

		t1 = themeDao.save(t1);
		t2 = themeDao.save(t2);
		t3 = themeDao.save(t3);
		t4 = themeDao.save(t4);
		t5 = themeDao.save(t5);
		t6 = themeDao.save(t6);

		// TRAINING
		tapestryBasic = trainingManager.createTraining("Tapestry Basic",
				TypeTraining.LECTURE_AND_TP, responsable.getId());
		Training ejbBasic = trainingManager.createTraining("EJB Basic",
				TypeTraining.LECTURE_AND_TP, stagiaire3.getId());

		ejbBasic.addUserInCharge(stagiaire2);

		
		addInCharge();
		createSurvey();

		Room room = new Room("De Vinci");
		room = roomDao.save(room);
		
		tapestryBasic.setDefaultRoom(room);
		ejbBasic.setDefaultRoom(room);
		// Training Session
		Calendar start;
		Calendar end;

		

		TrainingSession ts;

		for (int y = 2012; y <= 2013; y++) {
			
			
			for (int i = 0; i < 12; i++) {
				start = new GregorianCalendar(y, i, 20, 0, 0, 0);
				 end = new GregorianCalendar(y, i,start.get(Calendar.DAY_OF_MONTH) + 2, 0, 0);
				
				ts = trainingSessionManager.createTrainingSession(start,
						end, tapestryBasic.getId(),tapestry_instructors.get(i%5).getId(), room.getId());
				
				if(y==2012 && i==9)
					ts.addTrainee(instructeur4);
				if(y==2013 && i==0)
					ts.addTrainee(instructeur5);
				ts.addTrainee(stagiaire2);
				ts.addTrainee(stagiaire3);
				
				ts.setSurvey(survey);
				ts = trainingSessionManager
						.updateTrainingSession(ts);
				tapestryBasic.addTrainingSession(ts);
				tapestry_Session.add(ts);
			}
		}

		tapestryBasic = trainingDao.update(tapestryBasic);

	}

	private void createSurvey() throws NotInitaliazedSurveyDataBaseException,
			TrainingNotExistException {
		// QUESTION

		SimpleMCQuestion q1 = simpleMCQuestionManager.createMCQ1Question();
		SimpleMCQuestion q2 = simpleMCQuestionManager.createTFQuestion();
		SimpleMCQuestion q3 = simpleMCQuestionManager.createTFQuestion();

		ca1.addQuestion(q1);
		ca1.addQuestion(q2);
		ca1.addQuestion(q3);

		q1.setTitle("Quelle est votre connaissance actuelle du sujet ?");
		q2.setTitle("Les objectifs rappelés en début de stage correspondent-ils à ceux que vous avez définis avec votre entreprise ?");
		q3.setTitle("Estimez-vous avoir les pré-requis pour suivre ce stage ?");

		SimpleMCQuestion q4 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q5 = simpleMCQuestionManager.createMCQ2Question();
		q4.setTheme(t1);
		q5.setTheme(t1);
		SimpleMCQuestion q6 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q7 = simpleMCQuestionManager.createMCQ2Question();
		q6.setTheme(t2);
		q7.setTheme(t2);
		SimpleMCQuestion q8 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q9 = simpleMCQuestionManager.createMCQ2Question();
		q8.setTheme(t3);
		q9.setTheme(t3);
		SimpleMCQuestion q10 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q11 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q12 = simpleMCQuestionManager.createMCQ2Question();
		q10.setTheme(t4);
		q11.setTheme(t4);
		q12.setTheme(t4);
		SimpleMCQuestion q13 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q14 = simpleMCQuestionManager.createMCQ2Question();
		q13.setTheme(t5);
		q14.setTheme(t5);

		SimpleMCQuestion q15 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q16 = simpleMCQuestionManager.createMCQ2Question();
		q15.setTheme(t6);
		q16.setTheme(t6);

		ca2.addQuestion(q4);
		ca2.addQuestion(q5);
		ca2.addQuestion(q6);
		ca2.addQuestion(q7);
		ca2.addQuestion(q8);
		ca2.addQuestion(q9);
		ca2.addQuestion(q10);
		ca2.addQuestion(q11);
		ca2.addQuestion(q12);
		ca2.addQuestion(q13);
		ca2.addQuestion(q14);
		ca2.addQuestion(q15);
		ca2.addQuestion(q16);

		q4.setTitle("Points abordés");
		q5.setTitle("Progression pédagogique");
		q6.setTitle("Aide à la compréhension");
		q7.setTitle("Répartition théorique / pratique");
		q8.setTitle("Possibilité de reutilisation");
		q9.setTitle("Présentation");
		q10.setTitle("Compétences pédagogiques");
		q11.setTitle("Compétences techniques");
		q12.setTitle("Animation");
		q13.setTitle("Locaux");
		q14.setTitle("Organisation matérielle");
		q15.setTitle("Adapté à vos connaissances initiales");
		q16.setTitle("Durée du stage");

		SimpleMCQuestion q17 = simpleMCQuestionManager.createMCQ2Question();
		SimpleMCQuestion q18 = simpleMCQuestionManager.createMCQ2Question();
		OQuestion q19 = oQuestionManager.createDefaultOQuestion();
		OQuestion q20 = oQuestionManager.createDefaultOQuestion();

		ca3.addQuestion(q17);
		ca3.addQuestion(q18);
		ca3.addQuestion(q19);
		ca3.addQuestion(q20);

		q17.setTitle("Les objectifs du stage sont-ils atteints ?");
		q18.setTitle("Pensez-vous être apte à mettre en oeuvre les acquis de ce stage ?");
		q19.setTitle("Quels sont, selon vous, les points forts de ce stage ?");
		q20.setTitle("Quelles amélioration suggérez-vous d'apporter à ce stage ?");

		q1 = simpleMCQuestionManager.updateQuestion(q1);
		q2 = simpleMCQuestionManager.updateQuestion(q2);
		q3 = simpleMCQuestionManager.updateQuestion(q3);
		q4 = simpleMCQuestionManager.updateQuestion(q4);
		q5 = simpleMCQuestionManager.updateQuestion(q5);
		q6 = simpleMCQuestionManager.updateQuestion(q6);
		q7 = simpleMCQuestionManager.updateQuestion(q7);
		q8 = simpleMCQuestionManager.updateQuestion(q8);
		q9 = simpleMCQuestionManager.updateQuestion(q9);
		q10 = simpleMCQuestionManager.updateQuestion(q10);
		q11 = simpleMCQuestionManager.updateQuestion(q11);
		q12 = simpleMCQuestionManager.updateQuestion(q12);
		q13 = simpleMCQuestionManager.updateQuestion(q13);
		q14 = simpleMCQuestionManager.updateQuestion(q14);
		q15 = simpleMCQuestionManager.updateQuestion(q15);
		q16 = simpleMCQuestionManager.updateQuestion(q16);
		q17 = simpleMCQuestionManager.updateQuestion(q17);
		q18 = simpleMCQuestionManager.updateQuestion(q18);

		q19 = oQuestionManager.updateQuestion(q19);
		q20 = oQuestionManager.updateQuestion(q20);

		// Survey

		survey = surveyManager.createSurvey("Questionnaire d'évaluation",
				tapestryBasic.getId());

		survey.addCategory(ca1);
		survey.addCategory(ca2);
		survey.addCategory(ca3);

		ca1 = categoryDao.update(ca1);
		ca2 = categoryDao.update(ca2);
		ca3 = categoryDao.update(ca3);

		surveyManager.updateSurvey(survey);
		
	}

	private void saveInstructor() {
		for (User i : tapestry_instructors)
			tapestry_instructors.set(tapestry_instructors.indexOf(i),
					userDao.save(i));
	}

	private void addInCharge() {
		for (User i : tapestry_instructors)
			tapestryBasic.addUserInCharge(i);

	}

}
