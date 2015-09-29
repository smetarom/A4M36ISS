package com.redhat.brq.integration.examination.inventory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.brq.integration.examination.common.Item;

@Startup
@Singleton
public class Inventory {
	
	@PersistenceContext
	private EntityManager em;
	
	@PostConstruct
	public void fillDatabase() {
		em.createQuery("delete from Item").executeUpdate();
		em.persist(new Item("fedora", 1, 2));
		em.persist(new Item("rhel", 3, 4));
		em.persist(new Item("ubuntu", 5, 6));
	}
}
