
# ✅ SOLID Principles (with Simple Explanations and Examples)

## 🧱 S — Single Responsibility Principle (SRP)
> A class should have only one reason to change.

Each class should have only **one job** or **responsibility**. This helps reduce coupling and makes the code easier to maintain and test.

**Example:**
```java
class Invoice {
    void calculateTotal() { /* compute total */ }
}

class InvoicePrinter {
    void print(Invoice invoice) { /* logic to print */ }
}
```
🧠 *Why?* Changes to printing logic won't affect invoice calculations and vice versa.

---

## 🧰 O — Open/Closed Principle (OCP)
> Software should be open for extension, but closed for modification.

You should be able to add new functionality **without changing existing code**, usually by using abstraction.

**Example:**
```java
interface DiscountStrategy {
    double apply(double price);
}

class PercentageDiscount implements DiscountStrategy {
    public double apply(double price) { return price * 0.9; }
}

class BillingService {
    double calculate(DiscountStrategy discount, double price) {
        return discount.apply(price);
    }
}
```
🧠 *Why?* New discount types can be added without modifying BillingService.

---

## 🔄 L — Liskov Substitution Principle (LSP)
> Subclasses should be substitutable for their base classes.

Derived classes must behave in such a way that they **can replace the base class** without breaking the application.

**Bad Example:**
```java
class Bird {
    void fly() {}
}

class Ostrich extends Bird {
    void fly() { throw new UnsupportedOperationException(); }
}
```
🧠 *Problem:* Ostrich violates expectations from Bird's behavior.

**Fix:** Use separate abstractions like `FlyingBird` and `NonFlyingBird`.

---

## 🔌 I — Interface Segregation Principle (ISP)
> Clients should not be forced to depend on methods they do not use.

Design **smaller and more specific interfaces** instead of one large interface with unrelated methods.

**Example:**
```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}
```
🧠 *Why?* A printer-only machine doesn’t need to implement scanning behavior.

---

## 🔁 D — Dependency Inversion Principle (DIP)
> High-level modules should not depend on low-level modules. Both should depend on abstractions.

Instead of tightly coupling classes together, **depend on interfaces or abstractions**, which makes code more reusable and testable.

**Example:**
```java
interface NotificationService {
    void send(String message);
}

class EmailService implements NotificationService {
    public void send(String message) { /* send email */ }
}

class OrderProcessor {
    NotificationService service;

    public OrderProcessor(NotificationService service) {
        this.service = service;
    }
}
```
🧠 *Why?* OrderProcessor doesn’t care how the message is sent — could be email, SMS, etc.


💡 What does this mean?

A high-level module (like an order system) should not be tightly coupled to specific details (like email or SMS sending logic).

Instead, both high-level and low-level modules should communicate through an interface (abstraction).

---
