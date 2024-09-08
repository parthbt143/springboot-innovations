package com.parthbt143.springboot.innovations.controllers;

import com.parthbt143.springboot.innovations.DTOs.UserDTO;
import com.parthbt143.springboot.innovations.services.StringProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for processing UserDTO objects.
 * This controller handles HTTP requests related to UserDTO processing.
 */
@RestController
@RequestMapping("/users")
public class StringProcessorController {

    @Autowired
    private StringProcessorService stringProcessorService;

    /**
     * Endpoint to process a UserDTO object.
     * This endpoint accepts a UserDTO object in the request body, processes it using StringProcessorService,
     * and returns the processed UserDTO object.
     *
     * @param userDTO the UserDTO object to be processed
     * @return the processed UserDTO object
     */
    @PostMapping("/get-processed-dto")
    public UserDTO process(@RequestBody UserDTO userDTO) {
        return stringProcessorService.processedUserDTO(userDTO);
    }

}