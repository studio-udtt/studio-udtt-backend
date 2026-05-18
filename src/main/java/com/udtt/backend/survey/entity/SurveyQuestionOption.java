package com.udtt.backend.survey.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "survey_question_options")
public class SurveyQuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private SurveyQuestion surveyQuestion;

    @Column(name = "option_text", length = 255, nullable = false)
    private String optionText;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}