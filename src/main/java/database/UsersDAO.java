package database;

import database.UsersDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {

    public UsersDAO() {

    }

    public UsersDataSet get(long id) throws HibernateException {
        Session session = DBService.getSessionFactory().openSession();
        UsersDataSet userData = session.get(UsersDataSet.class, id);
        session.close();
        return userData;
    }

    public long getUserId(String login) throws HibernateException {
        Session session = DBService.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        UsersDataSet userData = ((UsersDataSet) criteria
                .add(Restrictions.eq("login", login))
                .uniqueResult());

        return userData == null ? -1 : userData.getId();
    }

    public long addUser(String login, String pass, String email) throws HibernateException {

        Session session = DBService.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Long userId = (Long) session.save(new UsersDataSet(login, pass, email));
            tx.commit();
            return userId;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return 0;
    }

    public UsersDataSet getUserByLogin(String login) {
        long userId = getUserId(login);
        return userId > -1 ? get(userId) : null;
    }

}
