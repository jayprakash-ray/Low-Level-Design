package Problems.pizzapricingsystem.implementation;

public enum Size {
    REGULER(200),
    MEDIUM(400),
    LARGE(600);
    private final int price; 
    Size(int price)
    {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
