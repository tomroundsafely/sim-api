package com.rs.sim.controller;

import com.rs.sim.controller.model.CreateMediaRequest;
import com.rs.sim.controller.model.CreateMediaResponse;
import com.rs.sim.exception.UserAuthenticationException;
import com.rs.sim.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MediaController {

  private final MediaService mediaService;

  @Autowired
  public MediaController(final MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @PostMapping(
      value = "/media",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<CreateMediaResponse> createMedia(
      @Valid @RequestBody final CreateMediaRequest request) throws UserAuthenticationException {

    String url = mediaService.createMedia(request);
    return ResponseEntity.ok(new CreateMediaResponse(url));
  }

  @ExceptionHandler(UserAuthenticationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public void bearerAuthException(final UserAuthenticationException exception) {}
}
