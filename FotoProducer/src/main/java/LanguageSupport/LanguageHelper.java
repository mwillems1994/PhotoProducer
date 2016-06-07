/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageSupport;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcowillems
 */
public class LanguageHelper {

    private static ResourceBundle dutch;
    private static ResourceBundle english;
    private static ResourceBundle selectedLanguage;

    static {
        File file = new File("src/main/java/LanguageFiles");
        try {
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            Locale dutchBundle = new Locale("nl", "NL");
            dutch = ResourceBundle.getBundle("LanguageBundle", dutchBundle, loader);

            Locale englishBundle = new Locale("en", "EN");
            english = ResourceBundle.getBundle("LanguageBundle", englishBundle, loader);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LanguageHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ResourceBundle Dutch(){
        return dutch;
    }
    
    public static ResourceBundle English(){
        return english;
    }

    public static ResourceBundle selectedLanguage() {
        return selectedLanguage;
    }
    
    public static void setSelectedLanguage(ResourceBundle language){
        selectedLanguage = language;
    }
}
