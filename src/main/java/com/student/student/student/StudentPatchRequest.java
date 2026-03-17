package com.student.student.student;

import jakarta.validation.constraints.Email;

public record StudentPatchRequest(String firstName, String lastName, @Email String email, String major) {}

