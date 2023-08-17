package ocbcadvancedtest.app.controller;

import ocbcadvancedtest.app.model.UserRequest;
import ocbcadvancedtest.app.model.WebResponse;
import ocbcadvancedtest.app.service.FileUploadService;
import ocbcadvancedtest.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users",
            consumes= MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE
    )
    @Async
    public CompletableFuture<WebResponse<String>> request(@RequestBody UserRequest req) {
        userService.request(req);
        return CompletableFuture.completedFuture(WebResponse.<String>builder().data("OK").build());
    }

    @PostMapping( path = "/api/files" )
    @Async
    public CompletableFuture<WebResponse<String>> uploadFile(
            @RequestParam("file") MultipartFile file) throws IOException {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String[] part_filename = filename.split("\\.");
        long size = file.getSize();

        WebResponse<String> webResponse = new WebResponse<String>();

        if (!Objects.equals(part_filename[part_filename.length - 1], "csv")) {
            return CompletableFuture.completedFuture(WebResponse.<String>builder().errors("Extension of the file uploaded must be .csv!").build());
        }

        int countFile = new File("file-uploads").list().length;
        String newFilename = "csv-" + countFile + ".csv";
        FileUploadService.saveFile(newFilename, file);

        String line = "";
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("file-uploads/" + newFilename));
            while ((line = br.readLine()) != null) {
                i++;
                String[] data = line.split(",");
                if (i == 1) {
//                    if (data.length != 3 || data[0] != "username" || data[1] != "password" || data[2] != "name") {
//                        return WebResponse.<String>builder().errors("CSV Format doesn't Match with The Requirements!").build();
//                    }
                    continue;
                }

                System.out.print(data[0]);
                UserRequest user = new UserRequest();
                user.setUsername(data[0]);
                user.setPassword(data[1]);
                user.setName(data[2]);
                userService.request(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return CompletableFuture.completedFuture(WebResponse.<String>builder().data("OK").build());
    }

}
