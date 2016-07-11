package dingwen.jersey.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dingwen.jersey.entity.User;
import dingwen.jersey.utilities.HibernateUtil;

public class UserDao {

	private static final Logger log = LogManager.getLogger(UserDao.class);

	public List<User> getUserByNameAndPass(final String userName, final String password) {
		List<User> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("userName", userName));
			c.add(Restrictions.eq("password", password));
			list = c.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			log.error(e);
			return list;
		}
	}

	public List<User> getUserById(Integer userId) {
		if (userId == null)
			return null;
		List<User> list = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("userId", userId));
			list = c.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			log.error(e);
			return list;
		}
	}

	public Integer registerNewUser(User user) {
		Session session = null;
		Integer userId = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			userId = (Integer) session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		}
		return userId;
	}

	public List<User> queryUserByName(String userName) {
		Session session = null;
		List<User> list = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("userName", userName));
			list = c.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			log.error(e);
			return list;
		}
	}

}
