package Problems.pizzapricingsystem.implementation;


public class PizzaShop { 
    public static void main(String[] args)
    {
        Pizza pizza = new Cheese(new PlainPizza(Size.MEDIUM));
        System.out.println(pizza.getDescription() + " of price " + pizza.getCost());
    }
}