package com.rs.sim.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.rs.sim.controller.model.CreateMediaRequest;
import com.rs.sim.dao.ConsumerRepository;
import com.rs.sim.dao.MediaRepository;
import com.rs.sim.dao.model.Consumer;
import com.rs.sim.dao.model.Media;
import com.rs.sim.exception.UserAuthenticationException;
import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MediaService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MediaService.class);

  private final MediaRepository mediaRepository;
  private final ConsumerRepository consumerRepository;
  private AmazonS3 amazonS3;
  private String s3ArchiveBucket;

  @Autowired
  public MediaService(
      final MediaRepository mediaRepository,
      final ConsumerRepository consumerRepository,
      final AmazonS3 amazonS3,
      @Value("${s3.bucket.name}") final String s3ArchiveBucket) {
    this.mediaRepository = mediaRepository;
    this.consumerRepository = consumerRepository;
    this.amazonS3 = amazonS3;
    this.s3ArchiveBucket = s3ArchiveBucket;
  }

  @Transactional
  public String createMedia(CreateMediaRequest request) throws UserAuthenticationException {

    Optional<Consumer> consumer =
        consumerRepository.findById("3b6e0900-da43-4168-8f9c-967991e5ae6f");
    if (!consumer.isPresent()) {
      throw new UserAuthenticationException("user not found");
    }

    Media media =
        Media.builder()
            .id(UUID.randomUUID().toString())
            .access(request.access().name())
            .type(request.type().name())
            .name(request.name())
            .description(request.description())
            .tag(request.tag())
            .consumer(consumer.get())
            .build();
    mediaRepository.save(media);

    String key =
        String.format(
            "%s/%s/%s",
            consumer.get().getOrganisation().getId(), consumer.get().getId(), media.getId());
    return generateUrl(s3ArchiveBucket, key, HttpMethod.PUT);
  }

  private String generateUrl(String s3BucketName, String key, HttpMethod httpMethod) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DATE, 1); // Generated URL will be valid for 24 hours

    return amazonS3
        .generatePresignedUrl(s3BucketName, key, calendar.getTime(), httpMethod)
        .toString();
  }
}
