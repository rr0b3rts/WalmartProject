package com.walmart.reminder.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.walmart.reminder.model.Reminder;
import com.walmart.reminder.model.Status;
import com.walmart.util.HibernateUtil;

/**
 * This class is a singleton and is intended to be called using the static member, INSTANCE.
 * 
 * Ex. ReminderDAO.INSTANCE
 * 
 * @author rroberts
 *
 */
public class ReminderDAO {
	public static ReminderDAO INSTANCE = new ReminderDAO();
	
	private ReminderDAO() {}
	
	/**
	 * Persist a reminder to the data store.
	 * 
	 * @param reminder
	 */
	public void addReminder(Reminder reminder) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
        session.save(reminder);
        session.getTransaction().commit();
        session.close();
	}
	
	/**
	 * Update a reminder in the data store.
	 * 
	 * @param reminder
	 */
	public void updateReminder(Reminder reminder) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Reminder queriedReminder = (Reminder) session.find(Reminder.class, reminder.getId());
        
        queriedReminder.setName(reminder.getName());
        queriedReminder.setDescription(reminder.getDescription());
        queriedReminder.setDueDate(reminder.getDueDate());
        queriedReminder.setStatus(reminder.getStatus());
 
        session.getTransaction().commit();
        session.close();
	}
	
	/**
	 * Get reminders based on due date and/or status.
	 * 
	 * @param dueDate
	 * @param status
	 * @return
	 */
	public List<Reminder> getReminders(Date dueDate, Status status) {
		EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
 
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reminder> criteriaQuery = criteriaBuilder.createQuery(Reminder.class);
        Root<Reminder> reminderRoot = criteriaQuery.from(Reminder.class);
        criteriaQuery.select(reminderRoot);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if(dueDate != null) {
        	Predicate dueDatePredicate = criteriaBuilder.equal(reminderRoot.get("dueDate"), dueDate);
        	predicates.add(dueDatePredicate);
        }
        
        if(status != null) {
        	Predicate statusPredicate = criteriaBuilder.equal(reminderRoot.get("status"), status);
        	predicates.add(statusPredicate);
        }
        
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        
        TypedQuery<Reminder> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
	}
}
