public class MacOsGUIFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacOsButton();
    }

    @Override
    public DialogueBox createDialogueBox() {        return new MacOsDialogueBox();
    }
}