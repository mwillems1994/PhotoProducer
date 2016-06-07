/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author marcowillems
 */
public class Photo {

    private int photoId;
    private String filePath;
    
    public Photo(int photoId, String filePath) {
        this.photoId = photoId;
        this.filePath = filePath;
    }

    public int getId() {
        return this.photoId;
    }

    public String getFilePath() {
        return this.filePath;
    }
    
    public String toString() {
        Path p = Paths.get(this.filePath);
        return p.getFileName().toString();
    }
    
    /**
     * Save photo to the server and database
     * @param json
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */    
    public static String savePhoto(String json, String filename) throws FileNotFoundException, IOException {
    String path = "E://Pics/" + filename + ".bin";
    FileOutputStream fos = new FileOutputStream(path);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeUTF(json);
    oos.close();
    fos.close();
    return path;
    }

    /**
     * loads photo from the server at selected path
     * @param path
     * @return
     * @throws IOException 
     */
    public static String loadPhoto(String path) throws IOException {
    FileInputStream fis;
    try {
        fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        String json = ois.readUTF();
        ois.close();
        fis.close();
        return json;
        } catch (FileNotFoundException ex) {
        return ("photo not found");
        }
    }
}
