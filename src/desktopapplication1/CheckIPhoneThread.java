/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapplication1;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.SingleFrameApplication;

/**
 *
 * @author rico
 */
public class CheckIPhoneThread extends Thread {
    
    public CheckIPhoneThread(SingleFrameApplication app, Map<String,String> websiteLinkMap, String musicFilePath) {
        this.app = app;
        this.websiteLinkMap = websiteLinkMap;
        this.musicFilePath = musicFilePath;
    }
    
    public void run(){
        int counter = 1;
        boolean changed = false;
        String postOpenWebsiteLink = "";
        Map<String, String> tempMap = new HashMap<String, String>();
        while (!changed){
            tempMap.clear();
            for (String websiteLink : websiteLinkMap.keySet()){
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CheckIPhoneThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                String currentSiteContent = "";
                tempMap.put(websiteLink, currentSiteContent);
                try {
                    currentSiteContent = getSiteContent(websiteLink);
                } catch(Exception ex) {
                    System.out.println("Fail: " + websiteLink);
                    System.out.println("Error: " + ex.getMessage());
                    continue;
                }
                
                String originalContent = websiteLinkMap.get(websiteLink);
                if (originalContent.equals("")){
                    System.out.println(" - Initialize");
                } else if (originalContent.equals(currentSiteContent)){
                    System.out.println(" - Same");
                } else {
                    System.out.println(" - Different");
                    
                    PrintWriter out;
                    try {
                        out = new PrintWriter(new FileWriter("C:\\Users\\rico\\Desktop\\originalContent.txt"));
                        out.print(originalContent);
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(CheckIPhoneThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    PrintWriter out2;
                    try {
                        out2 = new PrintWriter(new FileWriter("C:\\Users\\rico\\Desktop\\currentSiteContent.txt"));
                        out2.print(currentSiteContent);
                        out2.close();
                    } catch (IOException ex) {
                        Logger.getLogger(CheckIPhoneThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    postOpenWebsiteLink = websiteLink;
                    this.notification(musicFilePath, postOpenWebsiteLink);
                }
            }
            websiteLinkMap.clear();
            websiteLinkMap.putAll(tempMap);
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CheckIPhoneThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println();
            System.out.println(counter++);
        }
    }
    
    public void notification(String musicFilePath, String postOpenWebsiteLink) {
        try {
        Robot robot = new Robot();

        // Simulate a key press
        robot.keyPress(KeyEvent.VK_F6);
        robot.keyRelease(KeyEvent.VK_F6);
        

        Runtime.getRuntime().exec( "cmd.exe /C " + musicFilePath );
        Runtime.getRuntime().exec( "cmd.exe /C start " + postOpenWebsiteLink );
        } catch(Exception ex) {
            //no handle
        }
    }
    
    public String getSiteContent(String websiteLink) throws MalformedURLException, IOException {
        System.out.print("getSiteContent: " + websiteLink);
        
        String finalString = "";
        
        URL siteURL = new URL(websiteLink);
        BufferedReader in = new BufferedReader(new InputStreamReader(siteURL.openStream()));
        String inputLine;
        
        while ((inputLine = in.readLine()) != null){
            if (inputLine.indexOf("Set-Cookie")<0
                && inputLine.indexOf("s.server")<0
                && inputLine.indexOf("dciddstr")<0
                && inputLine.indexOf("suggestionsUrl")<0
                && inputLine.indexOf("pn-icloud")<0
                    ){
                finalString += inputLine + "\n";
            }
        }
        in.close();
        
        return finalString;
    }
    
    private SingleFrameApplication app;
    private Map<String,String> websiteLinkMap;
    private String musicFilePath;
}
