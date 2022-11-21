package com.webapps2022.jsf;

import com.webapps2022.ejb.UserService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
// Is session scoped so that the login id, recipient ids, and request ids are 
// retained after moving to different pages
@SessionScoped
public class LoginBean implements Serializable {
    @EJB
    UserService usrSrv;
    private String username;
    private String password;
    private Boolean disableAdminButton = false;
   
    public UserService getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }
   
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will disassociate the principal from the session (effectively logging him/her out)
            request.logout();
            context.addMessage(null, new FacesMessage("User is logged out"));
            return "index";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
        return null;
    }

    public String login(){
       FacesContext cxt = FacesContext.getCurrentInstance();  
       HttpServletRequest request = (HttpServletRequest) cxt.getExternalContext().getRequest();  

       try {  
            request.login(this.username, this.password);
            
            // Retrieves the login id via the username and sets it
            long loginId = usrSrv.getLoginId(this.username);
            usrSrv.setLoginId(loginId);

            // Checks if the user is an admin and disables the admin button accordingly
            if (!usrSrv.isAdmin(username)) {
                this.disableAdminButton = false;
            }
            else {
                this.disableAdminButton = true;
            }
       } catch (ServletException e) {  
            cxt.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect password or username.", null));  
            return "login";  
       }  

       if(request.isUserInRole("admin")) {  
            return "/faces/admins/admin.xhtml";  
       } else {  
            return "/faces/users/user.xhtml";  
       }  
    }   

    public Boolean getDisableAdminButton() {
        return disableAdminButton;
    }
 
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setDisableAdminButton(Boolean disableAdminButton) {
        this.disableAdminButton = disableAdminButton;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
}
