package com.takirahal.srfgroup.services.impl;

import com.takirahal.srfgroup.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    Logger log = LoggerFactory.getLogger(StorageService.class);

    //    @Value("${dynamicsvariables.namepathsstorage}")
    //    public String namePathStorage;

    private String directoryUploadProductImages = "/srf-group/upload-dir/offersimages/";
    private String directoryUploadUserAvatar = "/srf-group/upload-dir/avatar/";

    public void store(MultipartFile file, Path rootLocation) {
        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            log.error("Exception store file : {}", e.getMessage());
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename, Path rootLocation) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            return null; // CommonUtil.loadDefaultFile();
        } catch (MalformedURLException e) {
            log.error("Exception MalformedURLException : {}", e.getMessage());
            return null; // CommonUtil.loadDefaultFile();
        }
    }

    public void deleteFiles(Path rootLocation) {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init(String path) {
        File theDir = new File(path);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public boolean existPath(String path) {
        File theDir = new File(path);
        return theDir.exists();
    }

    public String getBaseStorageProductImages() {
        try {
            // /home/taki
            return System.getProperty("user.home") + "" + directoryUploadProductImages;
        } catch (Exception e) {
            return "";
        }
    }

    public String getBaseStorageUserImages() {
        try {
            // /home/taki
            return System.getProperty("user.home") + "" + directoryUploadUserAvatar;
        } catch (Exception e) {
            return "";
        }
    }
}
