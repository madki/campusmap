package in.workarounds.instimap.bus;

public class NormalEvents {

    public static class HideKeyboard {

    }

    public static class CloseTopFragment {

    }

    public static class ImageReadyEvent {
        public Runnable runnable;
        public ImageReadyEvent(Runnable runnable) {
            this.runnable = runnable;
        }
    }

}
