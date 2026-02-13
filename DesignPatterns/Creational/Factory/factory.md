
# Factory Design Pattern

## Overview
The Factory Design Pattern is a creational pattern that provides an interface for creating objects without specifying their exact classes. It encapsulates object creation logic, promoting loose coupling and flexibility.

## Key Components
- **Product**: Interface for objects the factory creates
- **Concrete Product**: Specific implementations of the product
- **Factory**: Creates objects based on parameters
- **Client**: Uses the factory to create products

## Example: Shape Factory

```java
// Product Interface
interface Shape {
    void draw();
}

// Concrete Products
class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing Square");
    }
}

// Factory Class
class ShapeFactory {
    public static Shape createShape(String type) {
        if (type.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (type.equalsIgnoreCase("square")) {
            return new Square();
        }
        return null;
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        Shape shape1 = ShapeFactory.createShape("circle");
        shape1.draw(); // Output: Drawing Circle
        
        Shape shape2 = ShapeFactory.createShape("square");
        shape2.draw(); // Output: Drawing Square
    }
}
```

## Benefits
- Decouples client from concrete classes
- Centralizes object creation
- Easy to add new product types
- Follows Open/Closed Principle

## When to Use
- Object creation depends on runtime conditions
- System should be independent of how objects are created
- Multiple product variants exist
