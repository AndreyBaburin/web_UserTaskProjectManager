package com.aston.andrey_baburin.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionTest {
    @Test
    void testCustomExceptionMessage() {
        String errorMessage = "Test error message";

        try {
            throw new CustomException(errorMessage);
        } catch (CustomException e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }
}