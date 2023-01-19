package com.takirahal.srfgroup.services.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ResizeImage {

    private final Logger log = LoggerFactory.getLogger(ResizeImage.class);

    @Value("${dynamicsvariables.heightAvatar}")
    private String heightAvatar;

    @Value("${dynamicsvariables.widthAvatar}")
    private String widthAvatar;

    @Value("${dynamicsvariables.maxHeightImgOffer}")
    private String maxHeightImgOffer;

    @Value("${dynamicsvariables.maxWidthImgOffer}")
    private String maxWidthImgOffer;

    @Async
    public void resizeAvatar(String path) {
        try {
            File input = new File(path);
            BufferedImage image = ImageIO.read(input);
            BufferedImage resized = resize(image, Integer.parseInt(heightAvatar), Integer.parseInt(widthAvatar));
            File output = new File(path);
            ImageIO.write(resized, "png", output);
        } catch (Exception e) {
            log.error("Exception for resize image: {}", e.getMessage());
        }
    }

    @Async
    public void resizeImageOffer(String path) {
        try {
            log.info("Resize image for path: {}", path);
            File input = new File(path);
            BufferedImage image = ImageIO.read(input);
            int newWidthResize = image.getWidth();
            int newHightResize = image.getHeight();
            boolean isResize = false;
            if (newWidthResize > Integer.parseInt(maxWidthImgOffer)) {
                newWidthResize = Integer.parseInt(maxWidthImgOffer);
                isResize = true;
            }

            if (newHightResize > Integer.parseInt(maxHeightImgOffer)) {
                newHightResize = Integer.parseInt(maxHeightImgOffer);
                isResize = true;
            }

            if (isResize) {
                BufferedImage resized = resize(image, newHightResize, newWidthResize);
                File output = new File(path);
                ImageIO.write(resized, "png", output);

                // Add text for every image after resize
                addTextToImage(resized, path);
            }
            else{
                // Add text for every image without resize
                addTextToImage(image, path);
            }


        } catch (Exception e) {
            log.error("Exception for resize image: {}", e.getMessage());
        }
    }

    private BufferedImage resize(BufferedImage img, int height, int width) {
        try {
            Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return resized;
        } catch (Exception e) {
            log.error("Exception for resize image: {}", e.getMessage());
            return null;
        }
    }


    /**
     *
     * @param image
     */
    private void addTextToImage(BufferedImage image, String path){
        try {
            log.info("Add text to image : {}", path);
            // BufferedImage image = ImageIO.read(new File(path));
            Font font = new Font("Arial", Font.BOLD, 12);
            Graphics graphics = image.getGraphics();
            graphics.setFont(font);
            graphics.setColor(Color.BLACK);
            graphics.drawString("SrfGroup", 40, 20);

            File output = new File(path);
            ImageIO.write(image, "png", output);
        }
        catch (Exception e){
            log.error("Exception whene add text to image : {}", e.getMessage());
        }
    }


    public void rotateImages(String path) {
        File file = new File(path);
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            StringBuilder description = new StringBuilder();

            metadata.getDirectories().forEach(directory -> {

                directory.getTags().forEach(tag -> {

                    if (tag.getTagType() == ExifDirectoryBase.TAG_ORIENTATION) {
                        description.append(tag.getDescription().replaceAll(" ", ""));
                    }

                });

            });

            if (description.length() > 0) {

                int rotateIndex = description.indexOf("Rotate");

                int cwIndex = description.indexOf("CW");

                if (rotateIndex >= 0 && cwIndex > 0 && rotateIndex < cwIndex) {

                    int angel = Integer.valueOf(description.substring(rotateIndex + 6, cwIndex));

                    log.info("============ Picture orientation correction , Clockwise rotation {}°, Picture path ：{}===========", angel, path);

                    BufferedImage oldImage = ImageIO.read(file);

                    BufferedImage newImage = Rotate(oldImage, angel);

                    ImageIO.write(newImage, "jpg", file);

                    newImage.getGraphics().dispose();

                    oldImage.getGraphics().dispose();

                }

            }
        } catch (ImageProcessingException e) {

            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static BufferedImage Rotate(Image src, int angel) {

        int srcWidth = src.getWidth(null);

        int srcHeight = src.getHeight(null);

        // Calculate the size of the rotated image

        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(

                srcWidth, srcHeight)), angel);

        BufferedImage res = null;

        res = new BufferedImage(rect_des.width, rect_des.height,

                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = res.createGraphics();

        // convert

        g2.translate((rect_des.width - srcWidth) / 2,

                (rect_des.height - srcHeight) / 2);

        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);
        g2.drawImage(src, null, null);

        return res;

    }

    /**

     * Calculate the rotated image

     *

     * @param src Rotated pictures

     * @param angel Rotation Angle

     * @return The rotated picture

     */

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {

        // If the angle of rotation is greater than 90 Do the corresponding conversion

        if (angel >= 90) {

            if (angel / 90 % 2 == 1) {

                int temp = src.height;

                src.height = src.width;

                src.width = temp;

            }

            angel = angel % 90;

        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;

        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;

        double angelDeltaWidth = Math.atan((double) src.height / src.width);

        double angelDeltaHeight = Math.atan((double) src.width / src.height);
        int lenDeltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha

                - angelDeltaWidth));

        int lenDeltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha

                - angelDeltaHeight));

        int desWidth = src.width + lenDeltaWidth * 2;

        int desHeight = src.height + lenDeltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }
}
