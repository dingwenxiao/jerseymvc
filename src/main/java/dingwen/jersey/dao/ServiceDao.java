package dingwen.jersey.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dingwen.jersey.entity.Association;
import dingwen.jersey.entity.ServiceAttributeModel;
import dingwen.jersey.entity.ServiceModel;
import dingwen.jersey.utilities.HibernateUtil;


public class ServiceDao {
	private static final Logger log = LogManager.getLogger(ServiceDao.class);

	public List<ServiceAttributeModel> getServiceAttributesByAssociation(final Association association) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria c = session.createCriteria(ServiceAttributeModel.class);
			c.add(Restrictions.eq("association", association));
			List<ServiceAttributeModel> list = (List<ServiceAttributeModel>) c
					.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	public List<ServiceModel> queryServiceById(String serviceId){
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria c = session.createCriteria(ServiceModel.class);
			c.add(Restrictions.eq("serviceId", serviceId));
			List<ServiceModel> list = (List<ServiceModel>) c
					.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
