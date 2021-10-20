package database;

import database.UsersDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDAO() {
        SessionFactory sessionFactory = DBService.getSessionFactory();
        Session session = sessionFactory.openSession();
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public long getUserId(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria
                .add(Restrictions.eq("login", login))
                .uniqueResult())
                .getId();
    }

    public long addUser(String login, String pass, String email) throws HibernateException {
        Transaction transaction = session.beginTransaction();
        Long userId = (Long) session.save(new UsersDataSet(login, pass, email));
        transaction.commit();
        session.close();
        return userId;
    }

    public UsersDataSet getUserByLogin(String login) {
        long userId = getUserId(login);
        UsersDataSet dataSet = get(userId);
        session.close();
        return dataSet;
    }

    public UsersDataSet getUser(long id) {
        UsersDataSet dataSet = get(id);
        session.close();
        return dataSet;
    }

}
