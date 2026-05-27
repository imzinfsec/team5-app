package com.example.petcarelog.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PetImageService {

    private static final String URL_PREFIX = "/api/pets/images/";
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("jpg", "jpeg", "png", "gif", "webp");

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.pet-image-prefix:pets}")
    private String petImagePrefix;

    public String save(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. jpg, jpeg, png, gif, webp만 가능합니다.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 크기는 10MB를 초과할 수 없습니다.");
        }

        String savedFilename = UUID.randomUUID() + "." + extension;
        String objectKey = toObjectKey(savedFilename);
        String contentType = resolveContentType(file, extension);

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(contentType)
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new RuntimeException("이미지 파일을 읽는 데 실패했습니다.", e);
        } catch (S3Exception e) {
            throw new RuntimeException("S3 이미지 업로드에 실패했습니다. bucket=" + bucketName + ", key=" + objectKey, e);
        }

        return URL_PREFIX + savedFilename;
    }

    public PetImageFile load(String filename) {
        validateSavedFilename(filename);

        String objectKey = toObjectKey(filename);

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);

            String contentType = response.response().contentType();
            if (contentType == null || contentType.isBlank()) {
                contentType = contentTypeByExtension(getExtension(filename));
            }

            return new PetImageFile(response.asByteArray(), contentType);
        } catch (S3Exception e) {
            throw new RuntimeException("S3 이미지 조회에 실패했습니다. bucket=" + bucketName + ", key=" + objectKey, e);
        }
    }

    public void delete(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        String filename = imageUrl.replace(URL_PREFIX, "");
        validateSavedFilename(filename);

        String objectKey = toObjectKey(filename);

        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.deleteObject(request);
        } catch (S3Exception e) {
            throw new RuntimeException("S3 이미지 삭제에 실패했습니다. bucket=" + bucketName + ", key=" + objectKey, e);
        }
    }

    private String toObjectKey(String filename) {
        String prefix = petImagePrefix == null ? "pets" : petImagePrefix.trim();
        prefix = prefix.replaceAll("^/+", "").replaceAll("/+$", "");

        if (prefix.isBlank()) {
            return filename;
        }

        return prefix + "/" + filename;
    }

    private void validateSavedFilename(String filename) {
        if (filename == null || filename.isBlank()
                || filename.contains("/")
                || filename.contains("\\")
                || filename.contains("..")) {
            throw new IllegalArgumentException("잘못된 이미지 파일명입니다.");
        }

        String extension = getExtension(filename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("허용되지 않는 이미지 파일 형식입니다.");
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("파일 확장자가 없습니다.");
        }

        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private String resolveContentType(MultipartFile file, String extension) {
        String contentType = file.getContentType();

        if (contentType != null && contentType.startsWith("image/")) {
            return contentType;
        }

        return contentTypeByExtension(extension);
    }

    private String contentTypeByExtension(String extension) {
        return switch (extension.toLowerCase()) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }

    public record PetImageFile(byte[] bytes, String contentType) {
    }
}