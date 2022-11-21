package com.webapps2022.ejb;

import com.webapps2022.thrift.TimestampService;
import java.text.SimpleDateFormat;
import java.util.Date;  
import org.apache.thrift.TException;

public class TimestampHandler implements TimestampService.Iface {
    
    // Returns the timestamp into the format provided as a string
    @Override
    public String timestamp() throws TException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();  
        return formatter.format(date);  
    }
}
