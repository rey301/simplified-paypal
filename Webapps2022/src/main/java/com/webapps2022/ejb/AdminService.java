package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Mazon
 */

@Singleton 
@Startup
public class AdminService {

    @PersistenceContext 
    EntityManager em;   

    public AdminService () {
    }
    
    // Initialise an admin as soon as the web application deploys. If the admin is already present 
    // (i.e. 'admin1' username is checked) then no admin doesn't need to be added
    @PostConstruct
    public void postConstruct() {
        try {
            Query q = em.createNamedQuery("findInitAdmin").setParameter("adminName", "admin1");
            List qList = q.getResultList();
            Query d = em.createNamedQuery("getAllUsers");
            List dList = d.getResultList();
            System.out.println("Result list: "+Arrays.toString(qList.toArray()));
            System.out.println("Result list 2: "+Arrays.toString(dList.toArray()));
            if (em.createNamedQuery("findInitAdmin").setParameter("adminName", "admin1").getResultList().isEmpty()) {
                SystemUser sys_user;
                SystemUserGroup sys_user_group;

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String passwd = "admin1";
                md.update(passwd.getBytes("UTF-8"));
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < digest.length; i++) {
                    sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                }
                String paswdToStoreInDB = sb.toString();
      
                // apart from the default constructor which is required by JPA
                // you need to also implement a constructor that will make the following code succeed
                sys_user = new SystemUser("admin1", paswdToStoreInDB, "Money", "BuddyAdmin", "GBP", 1000);
                sys_user_group = new SystemUserGroup("admin1", "admins");

                em.persist(sys_user);
                em.persist(sys_user_group);
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
