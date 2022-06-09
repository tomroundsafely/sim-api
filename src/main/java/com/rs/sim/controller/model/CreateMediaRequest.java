package com.rs.sim.controller.model;

import com.rs.sim.types.AccessLevel;
import com.rs.sim.types.MediaType;

public record CreateMediaRequest(
    MediaType type, AccessLevel access, String tag, String name, String description) {}
