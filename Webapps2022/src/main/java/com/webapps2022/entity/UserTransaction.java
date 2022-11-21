package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    // Used to get transactions for a specific user
    @NamedQuery(
        name="getUserTransactions",
        query="SELECT u FROM UserTransaction u WHERE u.senderid=:id OR u.recipientid=:id"
    ),

    // Used to get all transactions from the table
    @NamedQuery(
        name="getAllUserTransactions",
        query="SELECT u FROM UserTransaction u"
    )
})

@Entity
public class UserTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long senderid;
    private long recipientid;
    private double valuetransferred;
    private String timestamp;
   
    public UserTransaction() {
    }
  
    public UserTransaction(long senderid, long recipientid, double valuetransferred, String timestamp) {
        this.senderid = senderid;
        this.recipientid = recipientid;
        this.valuetransferred = valuetransferred;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public double getValuetransferred() {
        return valuetransferred;
    }

    public long getSenderid() {
        return senderid;
    }

    public long getRecipientid() {
        return recipientid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setSenderid(long senderid) {
        this.senderid = senderid;
    }

    public void setRecepientid(long recepientid) {
        this.recipientid = recepientid;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setValuetransferred(double valuetransferred) {
        this.valuetransferred = valuetransferred;
    }
 
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.senderid);
        hash = 71 * hash + Objects.hashCode(this.recipientid);
        hash = 71 * hash + Objects.hashCode(this.valuetransferred);
        hash = 71 * hash + Objects.hashCode(this.timestamp);
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
        final UserTransaction other = (UserTransaction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.senderid, other.senderid)) {
            return false;
        }
        if (!Objects.equals(this.recipientid, other.recipientid)) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        return Objects.equals(this.valuetransferred, other.valuetransferred);
    }
    
}
