package com.takirahal.srfgroup.utils;

import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class CommonUtil {

    /**
     *
     * @return
     */
    public static Resource loadDefaultFile() {
        try {
            String directoryDefaultUpload = System.getProperty("user.home") + "/srf-group/upload-dir/";
            Path rootLocation = Paths.get(directoryDefaultUpload);
            Path file = rootLocation.resolve("default_image00.jpg");
            Resource resource = new UrlResource(file.toUri());
            return resource;
        } catch (MalformedURLException e) {
            return null;
        }
    }


    /**
     *
     * @param user
     * @return
     */
    public static String getFullNameUser(UserDTO user){
        return ((user.getFirstName()!=null && !user.getFirstName().equals("")) || (user.getLastName()!=null && !user.getLastName().equals(""))) ? user.getFirstName()+" "+user.getLastName() : user.getEmail();
    }

    /**
     *
     * @param originalInput
     * @return
     */
    public static String encodeToString(String originalInput){
        return "puorgfrs"+Base64.getEncoder().encodeToString(originalInput.getBytes())+"SrfGroup";
    }

    /**
     *
     * @param encodedString
     * @return
     */
    public static String decodeToString(String encodedString){
        String newStr = encodedString.substring(8, encodedString.length());
        byte[] decodedBytes = Base64.getDecoder().decode(newStr.substring(0, newStr.length()-8));
        return new String(decodedBytes);
    }
}
