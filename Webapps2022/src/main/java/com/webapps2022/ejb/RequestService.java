package com.webapps2022.ejb;

import com.webapps2022.entity.UserRequest;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RequestService {
    @PersistenceContext(unitName = "WebappsDBPU")
    EntityManager em;

    // Adds the request to the database, sets it initially to pending
    public void sendRequest(long requesterId, long recipientId, double valueRequested) {
        UserRequest request = new UserRequest(requesterId, recipientId, valueRequested, "Pending");
       
        em.persist(request);
        em.flush();
    }

    // Update the status to fulfilled to indicate the request has been paid
    public void updateStatus(int reqId) {
        em.createNamedQuery("updateUserRequest").setParameter("status","Fulfilled").setParameter("id",reqId).executeUpdate();
        em.flush();
    }
}
