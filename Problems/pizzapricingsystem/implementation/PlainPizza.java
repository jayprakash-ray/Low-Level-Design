package Problems.pizzapricingsystem.implementation;

public class PlainPizza implements Pizza {
    private Size size;

    public PlainPizza(Size size) {
        this.size = size;
    }

    public double getCost() {
        return size.getPrice();
    }

    public String getDescription() {
        return "This is a Plain Pizza " + this.size;
    }
    
}
