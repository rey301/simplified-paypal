package com.webapps2022.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-17T17:42:20")
@StaticMetamodel(UserTransaction.class)
public class UserTransaction_ { 

    public static volatile SingularAttribute<UserTransaction, Long> senderid;
    public static volatile SingularAttribute<UserTransaction, Double> valuetransferred;
    public static volatile SingularAttribute<UserTransaction, Long> recipientid;
    public static volatile SingularAttribute<UserTransaction, Long> id;
    public static volatile SingularAttribute<UserTransaction, String> timestamp;

}