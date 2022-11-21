package com.webapps2022.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    // Used to get requests for a specific user
    @NamedQuery(
        name="getUserRequests",
        query="SELECT u FROM UserRequest u WHERE u.recipientid=:id"
    ),

    // Used to update a request record so that the status is fulfilled
    @NamedQuery(
        name="updateUserRequest",
        query="UPDATE UserRequest r SET r.status = :status WHERE r.id = :id"
    )
})
@Entity
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long requesterid;
    private long recipientid;
    private double valuerequested;
    private String status;

    public UserRequest() {
    }

    public UserRequest(long requesterid, long recipientid, double valuerequested, String status) {
        this.requesterid = requesterid;
        this.recipientid = recipientid;
        this.valuerequested = valuerequested;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public long getRequesterid() {
        return requesterid;
    }

    public long getRecipientid() {
        return recipientid;
    }

    public double getValuerequested() {
        return valuerequested;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRequesterid(long requesterid) {
        this.requesterid = requesterid;
    }

    public void setRecipientid(long recipientid) {
        this.recipientid = recipientid;
    }

    public void setValuerequested(double valuerequested) {
        this.valuerequested = valuerequested;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.requesterid);
        hash = 71 * hash + Objects.hashCode(this.recipientid);
        hash = 71 * hash + Objects.hashCode(this.valuerequested);
        hash = 71 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserRequest other = (UserRequest) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.requesterid, other.requesterid)) {
            return false;
        }
        if (!Objects.equals(this.recipientid, other.recipientid)) {
            return false;
        }
        if (!Objects.equals(this.valuerequested, other.valuerequested)) {
            return false;
        }
        return Objects.equals(this.status, other.status);
    }
}
