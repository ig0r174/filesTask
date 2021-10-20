package database;

import database.UsersDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws HibernateException {
        return session.get(UsersDataSet.class, id);
    }

    public long getUserId(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        UsersDataSet userData = ((UsersDataSet) criteria
                .add(Restrictions.eq("login", login))
                .uniqueResult());
        return userData == null ? -1 : userData.getId();
    }

    public long insertUser(String login, String pass, String email) throws HibernateException {
        return (Long) session.save(new UsersDataSet(login, pass, email));
    }

}
