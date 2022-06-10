package com.rs.sim.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "media")
public class Media {

  @Id private String id;
  private String type;
  private String access;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Consumer consumer;

  private String kinesis_url;
  private String tag;
  private String name;
  private String description;
}
