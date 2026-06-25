package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendations;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;

    public Recommendations generateRecommendation(Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getRecommendations(prompt);
//        log.info("Response from AI: {}" , aiResponse);
        return processAIRecommendation(activity, aiResponse);
    }

    private Recommendations processAIRecommendation(Activity activity, String aiResponse) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();

            log.info("Response after cleaned : {}" , jsonContent);
            JsonNode analysisjson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisjson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode , "overall" ,"Overall: ");
            addAnalysisSection(fullAnalysis, analysisNode , "pace" ,"Pace: : ");
            addAnalysisSection(fullAnalysis, analysisNode , "heartRate" ,"Heart Rate: ");
            addAnalysisSection(fullAnalysis, analysisNode , "caloriesBurned" ,"Calories Burned: ");
            List<String> improvements = extractImprovements(analysisjson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisjson.path("suggestions"));
            List<String> safety = extractSafetyGuidelines(analysisjson.path("safety"));

            return Recommendations.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType().toString())
                    .recommendations(fullAnalysis.toString().trim())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }

    }

    private Recommendations createDefaultRecommendation(Activity activity) {
        return Recommendations.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType().toString())
                .recommendations("Unable to generate detailed analysis, but you should keep suggestions and safety in mind")
                .improvements(Collections.singletonList("Continue with you current methods , you are doing great...."))
                .suggestions(Collections.singletonList("You are doing great!!! But if you feel any pain consult a fitness doctor asap"))
                .safety(Arrays.asList(
                        "Do proper warm up before doing any exercise",
                        "Listen to your body, wherever you feel pain",
                        "Stay Hydrated, drink water on regular basis"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {
        List<String> safety = new ArrayList<>();
        if(safetyNode.isArray()){
            safetyNode.forEach(item ->safety.add(item.asText()));

        }
        return safety.isEmpty() ?
                Collections.singletonList("Follow general instructions") :
                safety;
    }

    private List<String> extractSuggestions(JsonNode suggestionNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionNode.isArray()){
            suggestionNode.forEach(suggestion ->{
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s : %s", workout, description));
            });
        }
        return suggestions.isEmpty() ?
                Collections.singletonList("No Specific suggestions required") :
                suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementNode) {
        List<String> improvements = new ArrayList<>();
        if(improvementNode.isArray()){
            improvementNode.forEach(improvement ->{
               String area = improvement.path("area").asText();
               String details = improvement.path("recommendation").asText();
               improvements.add(String.format("%s : %s", area, details));
            });
        }
        return improvements.isEmpty() ?
                Collections.singletonList("No Specific improvement required") :
                improvements;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()){
            fullAnalysis.append(prefix)
                            .append(analysisNode.path(key).asText())
                            .append("\n\n");

        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness recommendation and provide detailed recommendations in the following exact json format:
                {
                    "analysis" = {
                    "overall": "overall analysis here",
                    "pace" : "pace analysis here",
                    "heartRate" : "heartRate analysis here",
                    "caloriesBurned" :"calories Burned analysis here" 
                    },
                    "improvements" : [
                    {
                        "area" : "area name",
                        "recommendation" : "detailed recommendation"
                    }
                    ],
                    "suggestions" : [
                    {
                        "workout" : "workout name",
                        "description" : "detailed description"
                    }
                    ],
                    "safety" : [
                        "safety point 1",
                        "safety point 2"
                    ]
                }
                Analyze this activity:
                Activity type : %s
                Duration : %d minutes
                Calories Burned : %d 
                Additional Metrics : %s
                
                Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
                Ensure the response follows Exact json format shown above.
                
                """ ,
                    activity.getType(),
                    activity.getDuration(),
                    activity.getCaloriesBurned(),
                    activity.getAdditionalMatrics()
                );
    }


}
