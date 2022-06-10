package com.rs.sim.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rs.sim.controller.model.CreateMediaRequest;
import com.rs.sim.service.MediaService;
import com.rs.sim.types.AccessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class MediaControllerTest {

  private static final String ACCEPTED_BEARER_TOKEN = "foo";
  private final String requestJson =
      """
			{
				"name": "test1",
				"description": "desc",
				"tag": "heart",
				"type": "LIVE",
				"access": "PRIVATE"
			}
			""";
  @Mock MediaService mediaService;

  @InjectMocks private MediaController controller;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void shouldCreateMedia() throws Exception {

    mockMvc
        .perform(
            post("/media")
                .header("Authorization", "Bearer %s".formatted(ACCEPTED_BEARER_TOKEN))
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());

    CreateMediaRequest request =
        new CreateMediaRequest(
            com.rs.sim.types.MediaType.LIVE, AccessLevel.PRIVATE, "heart", "test1", "desc");
    verify(mediaService, times(1)).createMedia(request);
  }
}
