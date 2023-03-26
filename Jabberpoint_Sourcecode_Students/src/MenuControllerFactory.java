import java.awt.*;

public class MenuControllerFactory {

    //Creating a menu-item
    public static MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
