package org.zbking;

import javax.xml.bind.annotation.XmlType;

public interface AService {

    default void v1(){
        System.out.println("a");
    }

    public String aMethod() throws Exception;

    public String bMethod(String a) throws  Exception;
    public String cMethod(String a,String b) throws  Exception;

}
