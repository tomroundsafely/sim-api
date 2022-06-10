package com.rs.sim.service;

import com.rs.sim.controller.MediaController;
import com.rs.sim.controller.model.CreateMediaRequest;
import com.rs.sim.dao.ConsumerRepository;
import com.rs.sim.dao.MediaRepository;
import com.rs.sim.dao.model.Consumer;
import com.rs.sim.dao.model.Media;
import com.rs.sim.dao.model.Organisation;
import com.rs.sim.exception.UserAuthenticationException;
import com.rs.sim.types.AccessLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {

	@Mock
	MediaRepository mediaRepository;

	@Mock
	ConsumerRepository consumerRepository;

	@InjectMocks
	MediaService mediaService;

	@Test
	public void shouldCreateMedia() throws Exception {

		CreateMediaRequest request =
				new CreateMediaRequest(
						com.rs.sim.types.MediaType.LIVE, AccessLevel.PRIVATE, "heart", "test1", "desc");

		Consumer consumer = Consumer.builder()
				.id("3b6e0900-da43-4168-8f9c-967991e5ae6f")
				.email("test@test.com")
				.name("test mctest")
				.organisation(Organisation.builder()
						.id("4b23b316-c237-4aaa-882f-fb501a904250")
						.name("test org")
						.disabled(false)
						.build())
				.disabled(false)
				.build();
		when(consumerRepository.findById("3b6e0900-da43-4168-8f9c-967991e5ae6f"))
				.thenReturn(Optional.of(consumer));

		mediaService.createMedia(request);

		verify(mediaRepository, times(1)).save(any());
	}

	@Test
	public void shouldCreateMediaUserNotFound() throws Exception {

		CreateMediaRequest request =
				new CreateMediaRequest(
						com.rs.sim.types.MediaType.LIVE, AccessLevel.PRIVATE, "heart", "test1", "desc");

		when(consumerRepository.findById("3b6e0900-da43-4168-8f9c-967991e5ae6f"))
				.thenReturn(Optional.empty());

		try {
			mediaService.createMedia(request);
			fail("Expected UserAuthenticationException to be thrown");
		} catch (UserAuthenticationException e) {}

		verify(mediaRepository, times(0)).save(any());
	}
}
