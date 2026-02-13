package DesignPatterns.Creational.AbstractFactory.CodeExample;

public class MacOsButton implements Button {

    @Override
    public void click(){
        System.out.println("Clicking MacOS Button");
    }
}
