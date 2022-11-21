package com.webapps2022.ejb;

import com.webapps2022.entity.SystemUser;
import java.text.DecimalFormat;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(REQUIRED)
public class TransactionService {

    @PersistenceContext(unitName = "WebappsDBPU")
    EntityManager em;

    private static final DecimalFormat dfZero = new DecimalFormat("0.00");

    public String readName(long id) {
        SystemUser user = em.find(SystemUser.class, id);
        return user.getName();
    }

    public String readSurname(long id) {
        SystemUser user = em.find(SystemUser.class, id);
        return user.getSurname(); 
    }
    
    public String readCurrSymbol(long id) {
        SystemUser user = em.find(SystemUser.class, id);
        String currSym = "";        
      
        if (user.getCurrency().equals("GBP")) {
            currSym = "£";
        }    
        else if (user.getCurrency().equals("EUR")) {
            currSym = "€";
        }   
        else if (user.getCurrency().equals("USD")) {
            currSym = "$";
        } 
        return currSym;
    }  
 
    public String readCurrency(long id) {
        SystemUser user = em.find(SystemUser.class, id);
        return user.getCurrency(); 
    }

    public double readValue(long id) {
        SystemUser user = em.find(SystemUser.class, id);
        return user.getValue();
    }
    
    // Sends the value to the recipient id and converts it if necessary using the converter method with the currencies retrieved))
    public void sendValue(long senderId, long recipientId, double senderValue) {
        SystemUser sender = em.find(SystemUser.class, senderId);
        SystemUser recipient = em.find(SystemUser.class, recipientId);
        String senderCurrency = sender.getCurrency();
        String recipientCurrency = recipient.getCurrency();

        recipient.addValue(convertCurrency(senderValue, senderCurrency, recipientCurrency));
        sender.subValue(Double.parseDouble(dfZero.format(senderValue)));
       
        em.persist(sender);
        em.persist(recipient);
        em.flush();
    }

    public double convertCurrency(double value, String currency, String desiredCurrency) {
        if (!currency.equals(desiredCurrency)) {
            if (currency.equals("GBP")) {
                if (desiredCurrency.equals("USD")) {
                    return Double.parseDouble(dfZero.format(value*1.31));
                }
                else if (desiredCurrency.equals("EUR")) {
                    return Double.parseDouble(dfZero.format(value*1.20));
                }
            }
            if (currency.equals("USD")) {
                if (desiredCurrency.equals("GBP")) {
                    return Double.parseDouble(dfZero.format(value*0.8));
                }
                else if (desiredCurrency.equals("EUR")) {
                    return Double.parseDouble(dfZero.format(value*0.95));
                }
            }
            if (currency.equals("EUR")) {
                if (desiredCurrency.equals("GBP")) {
                    return Double.parseDouble(dfZero.format(value*0.84));
                }
                else if (desiredCurrency.equals("USD")) {
                    return Double.parseDouble(dfZero.format(value*1.05));
                }
            }
        }
        return Double.parseDouble(dfZero.format(value));
    }
}
