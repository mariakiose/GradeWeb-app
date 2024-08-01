package com.grade.gradeweb.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.grade.gradeweb.models.Course;
import com.grade.gradeweb.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseInitializer implements CommandLineRunner {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseInitializer(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Course> mandatoryCourses = new ArrayList<>();
        mandatoryCourses.add(new Course("Human-Computer Interaction", null));
        mandatoryCourses.add(new Course("Machine Learning", null));
        mandatoryCourses.add(new Course("Information Retrieval", null));
        mandatoryCourses.add(new Course("Software Development II", null));
        mandatoryCourses.add(new Course("Computer Architecture II", null));
        mandatoryCourses.add(new Course("Wireless Networks", null));
        mandatoryCourses.add(new Course("Computer and Communication Systems Security", null));
        mandatoryCourses.add(new Course("Optimization", null));
        mandatoryCourses.add(new Course("Linear Programming and Combinatorial Optimization", null));
        mandatoryCourses.add(new Course("Testing and Reliability of Electronic Systems", null));
        mandatoryCourses.add(new Course("Evolutionary Computation", null));
        mandatoryCourses.add(new Course("Data Mining", null));
        mandatoryCourses.add(new Course("Applied Statistics", null));
        mandatoryCourses.add(new Course("Graph Theory", null));
        mandatoryCourses.add(new Course("Information Theory and Coding", null));
        mandatoryCourses.add(new Course("Telecommunication Systems", null));
        mandatoryCourses.add(new Course("Databases", null));
        mandatoryCourses.add(new Course("Computer Graphics and Interaction Systems", null));
        mandatoryCourses.add(new Course("Computer Networks I", null));
        mandatoryCourses.add(new Course("Computer Networks II", null));
        mandatoryCourses.add(new Course("Compilers", null));
        mandatoryCourses.add(new Course("Software Technology", null));
        mandatoryCourses.add(new Course("Microprocessors", null));
        mandatoryCourses.add(new Course("Diploma Thesis MYY101 English for Computer Science I", null));
        mandatoryCourses.add(new Course("Calculus I", null));
        mandatoryCourses.add(new Course("General Physics", null));
        mandatoryCourses.add(new Course("Linear Algebra", null));
        mandatoryCourses.add(new Course("Introduction to Programming", null));
        mandatoryCourses.add(new Course("Introduction to Computers and Informatics", null));
        mandatoryCourses.add(new Course("English for Computer Science II", null));
        mandatoryCourses.add(new Course("Calculus II", null));
        mandatoryCourses.add(new Course("Basic Circuit Theory", null));
        mandatoryCourses.add(new Course("Discrete Mathematics I", null));
        mandatoryCourses.add(new Course("Object-Oriented Programming Techniques", null));
        mandatoryCourses.add(new Course("Software Development", null));
        mandatoryCourses.add(new Course("Discrete Mathematics II", null));
        mandatoryCourses.add(new Course("Data Structures", null));
        mandatoryCourses.add(new Course("Probability and Statistics", null));
        mandatoryCourses.add(new Course("Digital Design I", null));
        mandatoryCourses.add(new Course("Principles of Programming Languages", null));
        mandatoryCourses.add(new Course("Introduction to Numerical Analysis", null));
        mandatoryCourses.add(new Course("Electronics", null));
        mandatoryCourses.add(new Course("Design and Analysis of Algorithms", null));
        mandatoryCourses.add(new Course("Digital Design II", null));
        mandatoryCourses.add(new Course("Theory of Computation", null));
        mandatoryCourses.add(new Course("Systems Programming", null));
        mandatoryCourses.add(new Course("Signals and Systems", null));
        mandatoryCourses.add(new Course("Computational Mathematics", null));
        mandatoryCourses.add(new Course("Computer Architecture", null));
        mandatoryCourses.add(new Course("Operating Systems", null));
        mandatoryCourses.add(new Course("Artificial Intelligence", null));

        courseRepository.saveAll(mandatoryCourses);
    }
}
