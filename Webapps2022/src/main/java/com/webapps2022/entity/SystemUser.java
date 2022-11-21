package com.webapps2022.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@NamedQueries({
    // Retrieves the whole user table
    @NamedQuery(
        name="getAllUsers",
        query="SELECT a FROM SystemUser a"
    ),

    // Excludes admin user and logged in user
    @NamedQuery(
        name="getOtherUsers",
        query="SELECT a FROM SystemUser a WHERE a.id NOT IN (SELECT u.id FROM SystemUser u WHERE u.id = :id)"
    ),

    // Used to find the first admin
    @NamedQuery(
        name="findInitAdmin",
        query="SELECT a FROM SystemUser a WHERE a.username = :adminName"
    ),

   // Used to get specific user info
    @NamedQuery(
        name="getUserInfo",
        query="SELECT u FROM SystemUser u WHERE u.id = :id"
    ),

    // Used to get the logged in user info
    @NamedQuery(
        name="getLoginUserInfo",
        query="SELECT u FROM SystemUser u WHERE u.username = :username"
    )
})

@Entity
public class SystemUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    String username;

    private double value;

    @Version
    private long version;

    @NotNull
    private String userpassword;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String currency;
    
    public SystemUser() {
    }
    
    public SystemUser(String username, String userpassword, String name, String surname, String currency, double value) {
        this.username = username;
        this.userpassword = userpassword;
        this.name = name;
        this.surname = surname;
        this.currency = currency;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void subValue(double value) {
        this.value = this.value - value;
    } 
   
    public void addValue(double value) {
        this.value = this.value + value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.username);
        hash = 71 * hash + Objects.hashCode(this.userpassword);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.surname);
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
        final SystemUser other = (SystemUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.userpassword, other.userpassword)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.surname, other.surname);
    }
}