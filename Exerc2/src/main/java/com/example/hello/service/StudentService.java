package com.example.hello.service;

import com.example.hello.dto.StudentDto;

import java.util.List;

/**
 * Service interface for student operations
 * Works directly with DTOs to handle both data conversion and business logic
 */
public interface StudentService {
    /**
     * Get all students from the system as DTOs
     * @return List of all students as DTOs
     */
    List<StudentDto> getAllStudents();

    /**
     * Get student by ID as DTO
     * @param id The student ID to retrieve
     * @return The found student as DTO
     * @throws com.example.hello.exception.NotExists If a student doesn't exist
     */
    StudentDto getStudentById(Long id);

    /**
     * Add a new student
     * @param studentDto Student data to add (as DTO)
     * @return The added student as DTO with generated ID
     * @throws com.example.hello.exception.AlreadyExists If student with the same email already exists
     */
    StudentDto addStudent(StudentDto studentDto);

    /**
     * Update an existing student
     * @param studentDto Updated student data (as DTO)
     * @param id The ID from the path parameter
     * @return The updated student as DTO
     * @throws com.example.hello.exception.NotExists If a student doesn't exist
     * @throws com.example.hello.exception.StudentIdAndIdMismatch If ID in a path doesn't match student ID
     * @throws com.example.hello.exception.AlreadyExists If email is already in use by another student
     */
    StudentDto updateStudent(StudentDto studentDto, Long id);

    /**
     * Delete a student by ID
     * @param id Student ID to delete
     * @throws com.example.hello.exception.NotExists If student doesn't exist
     */
    void deleteStudent(Long id);
}