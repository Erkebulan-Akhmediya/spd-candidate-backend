package kz.afm.candidate.file;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {

    private final MinioClient minioClient;

    public FileService(
            @Value("${MINIO_USER}") String minioUser,
            @Value("${MINIO_PASSWORD}") String minioPassword
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials(minioUser, minioPassword)
                .build();
    }

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {

        final boolean exists = this.minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );
        if (exists) return;

        this.minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
    }

    private String generateUniqueName(MultipartFile file) throws IOException {
        final String[] splitFileName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        final String fileExtension = Arrays.stream(splitFileName).toList().getLast();
        final UUID fileId = UUID.nameUUIDFromBytes(file.getBytes());
        return fileId + "." + fileExtension;
    }

    public String save(MultipartFile file) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {

        final String fileName = this.generateUniqueName(file);

        this.minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("files")
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );
        return fileName;
    }

}
