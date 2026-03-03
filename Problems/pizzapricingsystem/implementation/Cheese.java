package Problems.pizzapricingsystem.implementation;

public class Cheese extends PizzaDecorator {

    public Cheese(Pizza pizza)
    {
        super(pizza);
    }

    public String getDescription(){
        return this.pizza.getDescription() + "and Cheese";
    }

    public double getCost() {
        return this.pizza.getCost() + 10.0;
    }
}
