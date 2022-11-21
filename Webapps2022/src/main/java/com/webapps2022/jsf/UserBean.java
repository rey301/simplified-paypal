/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.jsf;

import com.webapps2022.ejb.RequestService;
import com.webapps2022.ejb.TransactionService;
import com.webapps2022.ejb.UserService;
import com.webapps2022.entity.SystemUser;
import com.webapps2022.entity.UserRequest;
import com.webapps2022.entity.UserTransaction;
import com.webapps2022.thrift.TimestampService;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author Mazon
 */
@Named
@SessionScoped
public class UserBean implements Serializable{
    @EJB
    private UserService usrSrv;

    @EJB
    private TransactionService trsSrv;

    @EJB
    private RequestService reqSrv;

    private String timestamp;
    private double sentAmount;
    private double requestAmount;
    private Boolean revealPayText = false;
    private Boolean revealReqText = false;

    public UserService getUsrSrv() {
        return usrSrv;
    }

    public List<SystemUser> getUserList() {
        return usrSrv.getOtherUsers();
    }

    public List<SystemUser> getAllUserList() {
        return usrSrv.getAllUsers();
    }

    public List<UserTransaction> getAllUserTransactionList() {
        return usrSrv.getAllUserTransactions();
    }

    public List<UserTransaction> getUserTransactions() {
        return usrSrv.getUserTransactions();
    }

    public List<UserRequest> getUserRequests() {
        return usrSrv.getUserRequests();
    }

    public double getSentAmount() {
        return sentAmount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Boolean getRevealPayText() {
        return revealPayText;
    }

    public Boolean getRevealReqText() {
        return revealReqText;
    }

    public double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public void setSentAmount(double sentAmount) {
        this.sentAmount = sentAmount;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String viewUser(int id, String returnpage) {
        usrSrv.setRecId(id);
        this.revealPayText = false;
        return returnpage;
    }

    public String viewRequest(int reqId, int requesterId, String returnpage, double requestAmount) {
        usrSrv.setReqId(reqId);
        usrSrv.setRecId(requesterId);
        this.revealReqText = false;
        this.sentAmount = trsSrv.convertCurrency(requestAmount, trsSrv.readCurrency(usrSrv.getRecId()), trsSrv.readCurrency(usrSrv.getLoginId()));
        return returnpage;
    }
     
    // Sends the value (using the transaction service EJB) that was already set when the user inputted the value in the form
    public void sendAmount(boolean isRequest) { 
        trsSrv.sendValue(usrSrv.getLoginId(),usrSrv.getRecId(), this.sentAmount);
        this.revealPayText = true;
        this.revealReqText = true;
       
        // Is the method call was paying a request, then the status of the request is updated
        if (isRequest) {
            reqSrv.updateStatus(usrSrv.getReqId());
        }

        // Initiates connection with the timestamp server and retrieves the timestamp
        try {
            String ts; 

            TTransport transport;

            // instantiate a new TTransport protocol (will use a TCP socket)
            transport = new TSocket("localhost", 10001);
            transport.open();

            // instantiate a TProtocol using the TTransport instantiated above
            TProtocol protocol = new TBinaryProtocol(transport);

            //Finally, instantiate a synchronous client using the TProtocol instantiated above
            TimestampService.Client client = new TimestampService.Client(protocol);

            ts = client.timestamp();

            this.setTimestamp(ts);

            usrSrv.recordTransaction(sentAmount, this.timestamp);         

            transport.close();
        } catch (TException x) {
            System.err.println(x);
        }
    }
    
    public void sendRequest() { 
        reqSrv.sendRequest(usrSrv.getLoginId(), usrSrv.getRecId(), this.requestAmount);
        this.revealReqText = true;
    }

    public String readName(long id) {
        return trsSrv.readName(id);
    }

    public String readSurname(long id) {
        return trsSrv.readSurname(id); 
    }

    public String readCurrency(long id) {
        return trsSrv.readCurrency(id);
    }

    public double readValue(long id) {
        return trsSrv.readValue(id);
    }

    public String readCurrSymbol(long id) {
        return trsSrv.readCurrSymbol(id);
    } 

    public long getLoginId() {
        return usrSrv.getLoginId();
    }

    public boolean isFulfilled(String status) {
    if (status.equals("Fulfilled")) {
        return false;
    }
        return true;
    }
}
