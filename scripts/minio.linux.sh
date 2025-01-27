docker run \
  --name candidate-files \                # Sets the container name
  -p 9000:9000 \                             # Maps port 2100 on the host to 9000 in the container
  -p 9001:9001 \                             # Maps port 2101 on the host to 9001 in the container
  quay.io/minio/minio \                      # Specifies the MinIO Docker image
  server /files --console-address ":9001"     # Configures MinIO server and management console