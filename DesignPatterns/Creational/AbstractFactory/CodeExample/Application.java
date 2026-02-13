package DesignPatterns.Creational.AbstractFactory.CodeExample;
public class Application {
    private Button button;
    private DialogueBox dialogueBox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        dialogueBox = factory.createDialogueBox();
    }

    public void render() {
        button.click();
        dialogueBox.render();
    }

    public static void main(String[] args) {
        // Create MacOS GUI
        GUIFactory macFactory = new MacOsGUIFactory();
        Application macApp = new Application(macFactory);
        System.out.println("=== MacOS GUI ===");
        macApp.render();

        System.out.println();

        // Create Windows GUI
        GUIFactory windowsFactory = new WindowsGUIFactory();
        Application windowsApp = new Application(windowsFactory);
        System.out.println("=== Windows GUI ===");
        windowsApp.render();
    }
}
