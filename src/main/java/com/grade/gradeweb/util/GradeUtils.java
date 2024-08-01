package com.grade.gradeweb.util;

import java.util.Map;

import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.models.Grade;

public class GradeUtils {

    public static double calculateAverageGrade(Map<Course, Grade> coursesWithGrades) {
        if (coursesWithGrades == null || coursesWithGrades.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        int count = 0;

        for (Grade grade : coursesWithGrades.values()) {
            if (grade.getGradeValue() >= 5) { // Consider only valid grades
                sum += grade.getGradeValue();
                count++;
            }
        }

        return count > 0 ? sum / count : 0.0;
    }

    public static int countPassedCourses(Map<Course, Grade> coursesWithGrades) {
        if (coursesWithGrades == null || coursesWithGrades.isEmpty()) {
            return 0;
        }

        int count = 0;

        for (Grade grade : coursesWithGrades.values()) {
            if (grade.getGradeValue() >= 5) { // Consider passed courses (grades >= 5)
                count++;
            }
        }

        return count;
    }
}
