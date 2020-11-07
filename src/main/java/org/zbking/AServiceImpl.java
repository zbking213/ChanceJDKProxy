package org.zbking;

public class AServiceImpl implements AService{
    @Override
    public String aMethod() throws Exception{
        System.out.println("Amethod");
        return "AMethod";
    }

    @Override
    public String bMethod(String a) throws Exception {
        System.out.println("BMethod");
        return null;
    }

    @Override
    public String cMethod(String a, String b) throws Exception {
        System.out.println("CMethod");
        return null;
    }

}
