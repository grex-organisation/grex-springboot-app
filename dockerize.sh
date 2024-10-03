mvn clean install -U

# clean any existing image
docker rmi -f grex-springboot-app-image

# create image
docker build -t grex-springboot-app-image .

# show images
docker image ls