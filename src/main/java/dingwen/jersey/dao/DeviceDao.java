package dingwen.jersey.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dingwen.jersey.entity.DeviceModel;
import dingwen.jersey.utilities.HibernateUtil;


public class DeviceDao {
	private static final Logger log = LogManager.getLogger(DeviceDao.class);

	public List<DeviceModel> queryList(final String pin) {
		Session session = null;
		try {
			if (pin == null) {
				return null;
			}
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Criteria c = session.createCriteria(DeviceModel.class);
			c.add(Restrictions.eq("PIN", pin.trim()));
			List<DeviceModel> list = (List<DeviceModel>) c.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
