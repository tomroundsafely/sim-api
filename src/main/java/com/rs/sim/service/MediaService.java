package com.rs.sim.service;

import com.rs.sim.controller.model.CreateMediaRequest;
import com.rs.sim.dao.MediaRepository;
import com.rs.sim.dao.ConsumerRepository;
import com.rs.sim.dao.model.Media;
import com.rs.sim.dao.model.Consumer;
import com.rs.sim.exception.UserAuthenticationException;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(MediaService.class);

	private final MediaRepository mediaRepository;
	private final ConsumerRepository consumerRepository;

	@Autowired
	public MediaService(
			final MediaRepository mediaRepository,
			final ConsumerRepository consumerRepository) {
		this.mediaRepository = mediaRepository;
		this.consumerRepository = consumerRepository;
	}

	@Transactional
	public String createMedia(CreateMediaRequest request) throws UserAuthenticationException {

		Optional<Consumer> consumer = consumerRepository.findById(
				"3b6e0900-da43-4168-8f9c-967991e5ae6f");
		if (!consumer.isPresent()) {
			throw new UserAuthenticationException("user not found");
		}

    	Media media = Media.builder()
					.id(UUID.randomUUID().toString())
            .access(request.access().name())
            .type(request.type().name())
            .name(request.name())
            .description(request.description())
            .tag(request.tag())
            .consumer(consumer.get())
            .build();
		mediaRepository.save(media);

		//S3 todo

		return "todo";
	}
}
