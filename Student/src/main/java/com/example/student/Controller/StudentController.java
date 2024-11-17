package com.example.student.Controller;

import com.example.student.ApiResponse.ApiResponse;
import com.example.student.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    ArrayList<Student> students = new ArrayList<>();

    @PostMapping("/create-student")
    public ApiResponse createStudent(@RequestBody Student student) {
        for (Student s : students) {
            if (s.getId().equals(student.getId())) {
                return new ApiResponse("A student with this ID already exists");
            }
        }
        if (student.getId().isEmpty()){
            return new ApiResponse("Student id cannot be empty");
        }
        if (student.getName().isEmpty()){
            return new ApiResponse("Student name cannot be empty");
        }
        if (student.getAge() < 18){
            return new ApiResponse("Student age cannot be less than 18");
        }
        if (student.getGPA()<0)
            return new ApiResponse("Student gpa cannot be negative");

        students.add(student);
        return new ApiResponse("Student created successfully");
    }

    @GetMapping("/display-students")
    public ArrayList<Student> displayAllStudents() {
        return students;
    }

    @PutMapping("/update-student/{id}")
    public ApiResponse updateStudent(@RequestBody Student student,@PathVariable String id) {
        if(!student.getId().equals(id)){
            for (Student value : students) {
                if (value.getId().equals(id)) {
                    return new ApiResponse("A student with this ID already exists");
                }
            }
        }
        if (student.getId().isEmpty()){
            return new ApiResponse("Student id cannot be empty");
        }
        if (student.getName().isEmpty()){
            return new ApiResponse("Student name cannot be empty");
        }
        if (student.getAge() < 18){
            return new ApiResponse("Student age cannot be less than 18");
        }
        if (student.getGPA()<0)
            return new ApiResponse("Student gpa cannot be negative");

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.set(i, student);
                return new ApiResponse("Student updated successfully");
            }
        }
        return new ApiResponse("Student not found");
    }

    @DeleteMapping("/delete-student/{id}")
    public ApiResponse deleteStudent(@PathVariable String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                return new ApiResponse("Student deleted successfully");
            }
        }
        return new ApiResponse("Student not found");
    }

    @GetMapping("check-honor/{id}")
    public ApiResponse checkHonor(@PathVariable String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                if (student.getGPA() >= 4.75)
                    return new ApiResponse("First class honor");
                else if (student.getGPA() >= 4.25)
                    return new ApiResponse("Second class honor");
                else {
                    return new ApiResponse("Student didn't achieve an honor classification");
                }
            }
        }
        return new ApiResponse("Student not found");
    }

    @GetMapping("/greater-than-average")
    public ArrayList<Student> greaterThanAverage() {
        //ArrayList that will contain all the students whose GPA is bigger than the average
        ArrayList<Student> greaterThanAverage = new ArrayList<>();

        double sum = 0;
        for(Student student : students) {
            sum += student.getGPA();
        }
        double average = sum/students.size();

        for (Student student : students) {
            if (student.getGPA() > average) {
                greaterThanAverage.add(student);
            }
        }
        return greaterThanAverage;
    }



}
