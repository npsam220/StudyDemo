package com.example.StudyDemo.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StudyDemo.dto.FileDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static final Path DOWNLOAD_DIR = Paths.get("download_files").toAbsolutePath().normalize();

    @GetMapping
    public List<FileDto> getFiles() throws IOException {

        List<FileDto> result = new ArrayList<>();

        if (!Files.isDirectory(DOWNLOAD_DIR)) {
            return result;
        }

        try (Stream<Path> files = Files.list(DOWNLOAD_DIR)) {
            files
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.getFileName().toString().startsWith("."))
                    .sorted()
                    .forEach(path -> {
                        try {
                            FileDto dto = new FileDto();

                            dto.setFileName(path.getFileName().toString());
                            dto.setSize(Files.size(path));
                            dto.setLastModified(
                                    new Date(Files.getLastModifiedTime(path).toMillis()));

                            result.add(dto);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        return result;

    }

    @GetMapping("/{fileName:.+}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName)
            throws IOException {

        Path filePath = DOWNLOAD_DIR.resolve(fileName).normalize();

        if (!filePath.startsWith(DOWNLOAD_DIR) || !Files.isRegularFile(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = createResource(filePath);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentLength(Files.size(filePath))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    private Resource createResource(Path filePath) throws MalformedURLException {
        return new UrlResource(filePath.toUri());
    }

    @DeleteMapping("/{fileName}")
    public void deleteFile(
            @PathVariable String fileName) {

        File file = new File("download_files/" + fileName);

        if (file.exists()) {

            file.delete();

        }

    }
}
