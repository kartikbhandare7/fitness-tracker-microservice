package com.fitness.activityservice.service;

import com.fitness.activityservice.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository repository;
    private final UserValidationService userValidationService;
//    private final KafkaTemplate<String, Activity> kafkaTemplate;kafkaTemplate
    private final KafkaTemplate<String, Activity> kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValid = userValidationService.validateUser(request.getUserId());

        if(!isValid){
            throw new RuntimeException("Invalid user " + request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startedAt(request.getStartedAt())
                .additionalMatrics(request.getAdditionalMatrics())
                .build();
        Activity savedActivity = repository.save(activity);

        try{
            kafkaTemplate.send(topicName, savedActivity.getUserId(), (savedActivity));
        }catch(Exception e){
            e.printStackTrace();
        }

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartedAt(activity.getStartedAt());
        response.setAdditionalMatrics(activity.getAdditionalMatrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = repository.findByUserId(userId);

        return activityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
