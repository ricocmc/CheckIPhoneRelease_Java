/*
 * DesktopApplication1.java
 */

package desktopapplication1;

import java.awt.List;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class DesktopApplication1 extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try {
            String musicFilePath = "M:/Music/gangnam.mp3";
            
            Map<String,String> websiteLinkMap = new HashMap<String,String>();
            websiteLinkMap.put("http://store.apple.com/hk", "");
            websiteLinkMap.put("http://www.apple.com/hk/en/iphone/", "");
            websiteLinkMap.put("http://store.apple.com/hk/browse/home/shop_iphone/family/iphone", "");
            websiteLinkMap.put("http://store.apple.com/hk/browse/home/shop_iphone/family/iphone/iphone5", "");
            websiteLinkMap.put("http://www.apple.com/hk/en/retail/iphone/", "");
            websiteLinkMap.put("http://concierge.apple.com/reservation/hk/en/", "");
//            websiteLinkMap.put("http://hk.yahoo.com/", "");
            
            show(new DesktopApplication1View(this, websiteLinkMap, musicFilePath));
            
        } catch (MalformedURLException ex) {
//            Logger.getLogger(DesktopApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(DesktopApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
//            Logger.getLogger(DesktopApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DesktopApplication1
     */
    public static DesktopApplication1 getApplication() {
        return Application.getInstance(DesktopApplication1.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(DesktopApplication1.class, args);
    }
}
