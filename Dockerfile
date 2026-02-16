FROM eclipse-temurin:17-jdk
WORKDIR /app

# Install GUI libraries
RUN apt-get update && apt-get install -y \
    libx11-6 libxext6 libxrender1 libxtst6 libxi6 libgtk-3-0 mesa-utils wget unzip \
    && rm -rf /var/lib/apt/lists/*

# Download and unzip JavaFX Linux SDK
RUN mkdir -p /javafx-sdk \
    && wget -O javafx.zip https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip\
    && unzip javafx.zip -d /javafx-sdk \
    && mv /javafx-sdk/javafx-sdk-21.0.2/lib /javafx-sdk/lib \
    && rm -rf /javafx-sdk/javafx-sdk-21.0.2 javafx.zip

# Copy your fat JAR
COPY target/studyplanner.jar app.jar

ENV DISPLAY=host.docker.internal:0.0
ENV DB_HOST=host.docker.internal
# Run JavaFX app
CMD ["java", "--module-path", "/javafx-sdk/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]

