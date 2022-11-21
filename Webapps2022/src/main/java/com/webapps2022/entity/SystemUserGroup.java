package com.webapps2022.entity;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

// Used to find users that belong into a user group ('users' or 'admins)
@NamedQuery(
    name="getAdminByUsername",
    query="SELECT u FROM SystemUserGroup u WHERE u.username = :username AND u.groupname=:groupname"
)

@Entity
public class SystemUserGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String groupname;
    
    public SystemUserGroup() {
    } 
    
    public SystemUserGroup(String username, String groupName) {
        this.username = username;
        this.groupname = groupName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.username);
        hash = 71 * hash + Objects.hashCode(this.groupname);
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
        final SystemUserGroup other = (SystemUserGroup) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.groupname, other.groupname);
    }
}