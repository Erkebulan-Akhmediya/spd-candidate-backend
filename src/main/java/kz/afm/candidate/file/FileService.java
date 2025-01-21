package kz.afm.candidate.file;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
        this.minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
    }

}
