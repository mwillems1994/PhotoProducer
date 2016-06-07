/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import SocketClient.SocketManager;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author Luc
 */
public class Uploader implements Runnable{

    private File photo;
    private Album selectedAlbum;

    public Uploader(File photo, Album selectedAlbum) {
        this.photo = photo;
        this.selectedAlbum = selectedAlbum;
    }
    private String writePicture(String path, String format) throws IOException, InterruptedException {
        byte[] bytes;
        Gson gson = new Gson();

        BufferedImage buffi = ImageIO.read(new File(path));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.08f);

        writer.setOutput(ios);
        writer.write(null, new IIOImage(buffi, null, null), param);

        bytes = baos.toByteArray();

        writer.dispose();

        String json = gson.toJson(bytes);

        if (json.length() > 65000) {
            return "Photo too big";
        }
        Path p = Paths.get(path);
        String filename = selectedAlbum.getID() + " " + p.getFileName().toString();
        filename = filename.substring(0, filename.indexOf("."));
        return SocketManager.sendCommand("sendPhoto|" + json + "|" + filename + "|" + selectedAlbum.getID());
    }
    
    @Override
    public void run() {
        try {
            String uploadmessage = writePicture(photo.getPath(), "jpg");
            
        } catch (IOException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
