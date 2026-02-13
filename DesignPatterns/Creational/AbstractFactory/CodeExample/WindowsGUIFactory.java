public class WindowsGUIFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public DialogueBox createDialogueBox() {        return new WindowsDialogueBox();
    }
}