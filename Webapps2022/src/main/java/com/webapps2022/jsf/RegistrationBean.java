package com.webapps2022.jsf;

import com.webapps2022.ejb.UserService;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class RegistrationBean {

    @EJB
    UserService usrSrv;
    
    String username;
    String userpassword;
    String name;
    String surname;
    String currency;

    public RegistrationBean() {
    }
   
    // Register the user using the injected EJB
    public String register(String usergroup, String returnpage, String errorpage) {
        FacesContext cxt = FacesContext.getCurrentInstance();  

        try {  
             usrSrv.registerUser(username, userpassword, name, surname, currency, usergroup);  
        } catch (EJBException e) {  
             cxt.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Username already exists!", null));  
             return errorpage;  
        }
        return returnpage;
    }
    
    public UserService getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }  
  
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }  
}
