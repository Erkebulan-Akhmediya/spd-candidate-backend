package kz.afm.candidate.file;

import io.minio.*;
import io.minio.errors.*;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {

    private final MinioClient minioClient;
    private final Tika tika;

    public FileService(
            @Value("${MINIO_USER}") String minioUser,
            @Value("${MINIO_PASSWORD}") String minioPassword
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials(minioUser, minioPassword)
                .build();
        this.tika = new Tika();
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

    public void save(MultipartFile file) throws RuntimeException {
        try {
            System.out.println("file name: " + file.getOriginalFilename());
            this.minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("files")
                            .object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getBase64Url(String fileName) throws RuntimeException {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs
                            .builder()
                            .bucket("files")
                            .object(fileName)
                            .build()
            );

            final byte[] bytes = stream.readAllBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            String mimeType = tika.detect(new ByteArrayInputStream(bytes));
            return "data:" + mimeType + ";base64," + base64;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Ошибка получения или считывания файла");
        }
    }

}
