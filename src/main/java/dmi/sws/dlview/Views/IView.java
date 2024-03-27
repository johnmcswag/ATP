package dmi.sws.dlview.Views;

public interface IView {
    String title = "Test!";
    default String getTitle() {
        return title;
    }
}
