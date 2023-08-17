package ocbcadvancedtest.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadService {
    public static void saveFile(String filename, MultipartFile multipartFile) throws IOException {
        Path uploadDirectory = Paths.get("file-uploads");

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadDirectory.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
             throw new IOException("Error saving uploaded file: " + filename, e);
        }

    }
}
