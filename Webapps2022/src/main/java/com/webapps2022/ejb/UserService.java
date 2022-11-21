package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import com.webapps2022.entity.UserRequest;
import com.webapps2022.entity.UserTransaction;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;
    
    // Ids for recipients, requests and the user login id is stored here
    int recId = -1;
    int reqId = -1;
    long loginId;
   
    public UserService() {
    }

    // Registers the user and depending on their currency, convert it (default is 1000GBP)
    public void registerUser(String username, String userpassword, String name, String surname, String currency, String usergroup) {
        try {
            double value = 1000;
            SystemUser sys_user;
            SystemUserGroup sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();
            
            if (currency.equals("USD")) {
                value = value*1.31;
            }
            else if (currency.equals("EUR")) {
                value = value*1.20;
            }
            else {
                System.out.println("Error: currency not recognised");
            }

            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, value);
            sys_user_group = new SystemUserGroup(username, usergroup);

            em.persist(sys_user);
            em.persist(sys_user_group);
            em.flush();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Transaction is recorded onto the database
    public void recordTransaction(double value, String timestamp) {
        // Value will be based on the sender's currency, if needed to be converted, refer to sender's currency
        UserTransaction transaction = new UserTransaction(this.loginId, this.recId, value, timestamp);
        em.persist(transaction);
        em.flush();
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public int getReqId() {
        return reqId;
    }

    public int getCurrId() {
        return recId;
    }

    public int getRecId() {
        return recId;
    }

    public long getLoginId() {
        return loginId;
    }
   
    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    // Database query methods
    public List<SystemUser> getOtherUsers() {
        List<SystemUser> users = em.createNamedQuery("getOtherUsers").setParameter("id",this.getLoginId()).getResultList();
        return users;
    }

    public List<SystemUser> getAllUsers() {
        List<SystemUser> users = em.createNamedQuery("getAllUsers").getResultList();
        return users;
    }

    public Boolean isAdmin(String username) {
        return !em.createNamedQuery("getAdminByUsername").setParameter("username",username).setParameter("groupname","admins").getResultList().isEmpty();
    }

    public List<UserTransaction> getAllUserTransactions() {
        List<UserTransaction> transactions = em.createNamedQuery("getAllUserTransactions").getResultList();
        return transactions;
    }

    public long getLoginId(String username) {
        //SystemUser user = em.find(SystemUser.class, username);
        List<SystemUser> loginUser = em.createNamedQuery("getLoginUserInfo").setParameter("username", username).getResultList();
        long id = loginUser.get(0).getId();
        return id;
    }

    public List<UserTransaction> getUserTransactions() {
        List<UserTransaction> userTransactions;
        userTransactions = em.createNamedQuery("getUserTransactions").setParameter("id", this.loginId).getResultList();
        return userTransactions;
    }

    public List<UserRequest> getUserRequests() {
        List<UserRequest> userRequests = em.createNamedQuery("getUserRequests").setParameter("id", this.loginId).getResultList();
        return userRequests;
    }
}
