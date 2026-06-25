package com.fitness.aiservice.service;

import com.fitness.aiservice.RecommendationRepository;
import com.fitness.aiservice.model.Recommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class RecommendationService {
    private final RecommendationRepository recommendationRepository;


    public List<Recommendations> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendations getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found for activityId " + activityId));
    }
}
