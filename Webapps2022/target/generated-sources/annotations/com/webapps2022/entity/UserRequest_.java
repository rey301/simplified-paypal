package com.webapps2022.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-11-17T17:42:20")
@StaticMetamodel(UserRequest.class)
public class UserRequest_ { 

    public static volatile SingularAttribute<UserRequest, Long> requesterid;
    public static volatile SingularAttribute<UserRequest, Double> valuerequested;
    public static volatile SingularAttribute<UserRequest, Long> recipientid;
    public static volatile SingularAttribute<UserRequest, Long> id;
    public static volatile SingularAttribute<UserRequest, String> status;

}