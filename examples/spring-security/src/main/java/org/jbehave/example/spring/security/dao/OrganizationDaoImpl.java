package org.jbehave.example.spring.security.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.jbehave.example.spring.security.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


@Repository("organizationDao")
public class OrganizationDaoImpl implements OrganizationDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public Organization load(Long id) {
    return (Organization) sessionFactory.getCurrentSession().load(Organization.class, id);
  }

  @Transactional
  public Organization persist(Organization organization) {
    sessionFactory.getCurrentSession().saveOrUpdate(organization);
    return organization;
  }

  @SuppressWarnings("unchecked")
  @Transactional
  public Organization findByName(String orgName) {
    List<Organization> query = sessionFactory.getCurrentSession()
            .createQuery("from Organization where name = ?")
            .setParameter(0, orgName)
            .list();
    if (query.size() == 1) {
      return query.get(0);
    }
    return null;
  }

}
