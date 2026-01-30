class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance()
    {
        if(instance == null) {
            instance = new Singleton();
            System.out.println("Returning new");
        } 
        else {
            System.out.println("Returning old");
        }
            
        return instance;
    }
}

public class Main {
    public static void main(String[] args) {
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();
        Singleton obj4 = Singleton.getInstance();
       
    }
}

/*
Returning new
Returning old
Returning old

*/