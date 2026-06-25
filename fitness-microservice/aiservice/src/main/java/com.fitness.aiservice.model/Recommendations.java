package com.fitness.aiservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recommendations")
@Data
@Builder
public class Recommendations {

    private String id;
    private String activityId;
    private String userId;
    private String type;
    private String recommendations;
    private List<String> improvements;
    private List<String> safety;
    private List<String> suggestions;

    @CreatedDate
    private LocalDateTime createdAt;
}
