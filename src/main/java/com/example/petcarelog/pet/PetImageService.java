package com.example.petcarelog.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

@Service
public class PetImageService {

    @Value("${upload.dir}")
    private String uploadDir;

    private static final String URL_PREFIX = "/images/pets/";

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("jpg", "jpeg", "png", "gif", "webp");

    public String save(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);

        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. (jpg, jpeg, png, gif, webp만 가능)");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("파일 크기는 10MB를 초과할 수 없습니다.");
        }

        String savedFilename = UUID.randomUUID() + "." + extension;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new RuntimeException("업로드 디렉토리 생성 실패: " + uploadDir);
            }
        }

        File dest = new File(dir, savedFilename);

        try (InputStream in = file.getInputStream();
             FileOutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다. 경로: " + uploadDir, e);
        }

        return URL_PREFIX + savedFilename;
    }

    public void delete(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) return;

        String filename = imageUrl.replace(URL_PREFIX, "");
        File file = new File(uploadDir, filename);

        if (file.exists()) {
            file.delete();
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 확장자가 없습니다.");
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
