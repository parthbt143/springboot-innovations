
package com.parthbt143.springboot.innovations.services;

import com.parthbt143.springboot.innovations.DTOs.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Service class for processing UserDTO objects.
 */
@Service
public class StringProcessorService {

    /**
     * Processes the given UserDTO object.
     * Currently, this method simply prints the UserDTO to the console and returns it.
     *
     * @param userDTO the UserDTO object to be processed
     * @return the processed UserDTO object
     */
    public UserDTO processedUserDTO(UserDTO userDTO) {
        System.out.println(userDTO.toString());
        return userDTO;
    }

}