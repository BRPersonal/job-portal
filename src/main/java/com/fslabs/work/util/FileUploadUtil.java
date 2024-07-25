package com.fslabs.work.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtil
{

    public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException
    {

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream();)
        {
            Path path = uploadPath.resolve(filename);
            log.debug("FilePath:{}",path);
            log.debug("fileName:{}",filename);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ioe)
        {
            throw new IOException("Could not save image file: " + filename, ioe);
        }
    }
}
