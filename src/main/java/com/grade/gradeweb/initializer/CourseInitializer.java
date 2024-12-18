//package com.grade.gradeweb.initializer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import com.grade.gradeweb.models.Course;
//import com.grade.gradeweb.repositories.CourseRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class CourseInitializer implements CommandLineRunner {
//
//    @Autowired
//    private final CourseRepository courseRepository;
//
//    public CourseInitializer(CourseRepository courseRepository) {
//        this.courseRepository = courseRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Course> mandatoryCourses = new ArrayList<>();
//        mandatoryCourses.add(new Course("Human-Computer Interaction"));
//        mandatoryCourses.add(new Course("Machine Learning"));
//        mandatoryCourses.add(new Course("Information Retrieval"));
//        mandatoryCourses.add(new Course("Software Development II"));
//        mandatoryCourses.add(new Course("Computer Architecture II"));
//        mandatoryCourses.add(new Course("Wireless Networks"));
//        mandatoryCourses.add(new Course("Computer and Communication Systems Security"));
//        mandatoryCourses.add(new Course("Optimization"));
//        mandatoryCourses.add(new Course("Linear Programming and Combinatorial Optimization"));
//        mandatoryCourses.add(new Course("Testing and Reliability of Electronic Systems"));
//        mandatoryCourses.add(new Course("Evolutionary Computation"));
//        mandatoryCourses.add(new Course("Data Mining"));
//        mandatoryCourses.add(new Course("Applied Statistics"));
//        mandatoryCourses.add(new Course("Graph Theory"));
//        mandatoryCourses.add(new Course("Information Theory and Coding"));
//        mandatoryCourses.add(new Course("Telecommunication Systems"));
//        mandatoryCourses.add(new Course("Databases"));
//        mandatoryCourses.add(new Course("Computer Graphics and Interaction Systems"));
//        mandatoryCourses.add(new Course("Computer Networks I"));
//        mandatoryCourses.add(new Course("Computer Networks II"));
//        mandatoryCourses.add(new Course("Compilers"));
//        mandatoryCourses.add(new Course("Software Technology"));
//        mandatoryCourses.add(new Course("Microprocessors"));
//        mandatoryCourses.add(new Course("Diploma Thesis MYY101 English for Computer Science I"));
//        mandatoryCourses.add(new Course("Calculus I"));
//        mandatoryCourses.add(new Course("General Physics"));
//        mandatoryCourses.add(new Course("Linear Algebra"));
//        mandatoryCourses.add(new Course("Introduction to Programming"));
//        mandatoryCourses.add(new Course("Introduction to Computers and Informatics"));
//        mandatoryCourses.add(new Course("English for Computer Science II"));
//        mandatoryCourses.add(new Course("Calculus II"));
//        mandatoryCourses.add(new Course("Basic Circuit Theory"));
//        mandatoryCourses.add(new Course("Discrete Mathematics I"));
//        mandatoryCourses.add(new Course("Object-Oriented Programming Techniques"));
//        mandatoryCourses.add(new Course("Software Development"));
//        mandatoryCourses.add(new Course("Discrete Mathematics II"));
//        mandatoryCourses.add(new Course("Data Structures"));
//        mandatoryCourses.add(new Course("Probability and Statistics"));
//        mandatoryCourses.add(new Course("Digital Design I"));
//        mandatoryCourses.add(new Course("Principles of Programming Languages"));
//        mandatoryCourses.add(new Course("Introduction to Numerical Analysis"));
//        mandatoryCourses.add(new Course("Electronics"));
//        mandatoryCourses.add(new Course("Design and Analysis of Algorithms"));
//        mandatoryCourses.add(new Course("Digital Design II"));
//        mandatoryCourses.add(new Course("Theory of Computation"));
//        mandatoryCourses.add(new Course("Systems Programming"));
//        mandatoryCourses.add(new Course("Signals and Systems"));
//        mandatoryCourses.add(new Course("Computational Mathematics"));
//        mandatoryCourses.add(new Course("Computer Architecture"));
//        mandatoryCourses.add(new Course("Operating Systems"));
//        mandatoryCourses.add(new Course("Artificial Intelligence"));
//
//        for (Course course : mandatoryCourses) {
//            course.setActive(true); 
//        }
//
//        courseRepository.saveAll(mandatoryCourses);
//    }
//}
