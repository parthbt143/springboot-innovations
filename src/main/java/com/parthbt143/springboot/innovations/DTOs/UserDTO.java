package com.parthbt143.springboot.innovations.DTOs;

import com.parthbt143.springboot.innovations.annotations.StringProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @StringProcessor(
            transform = StringProcessor.TransformType.CAPITALIZE_WORDS,
            defaultValue = "Unknown User"
    )
    private String fullName;

    @StringProcessor(
            transform = StringProcessor.TransformType.UPPERCASE
    )
    private String uniqueId;

    @StringProcessor(
            transform = StringProcessor.TransformType.LOWERCASE,
            defaultValue = "noemail@example.com"
    )
    private String emailAddress;


    @StringProcessor(
            transform = StringProcessor.TransformType.CAPITALIZE_SENTENCES,
            defaultValue = "Unknown Address"
    )
    private String address;

    @StringProcessor(
            maxLength = 250,
            trimMultipleSpaces = false,
            defaultValue = "N/A"
    )
    private String userDetails;

    @Override
    public String toString() {
        return String.format(
                "UserDTO [fullName=%s, uniqueId=%s, emailAddress=%s, address=%s, userDetails=%s]",
                fullName != null ? fullName : "null",
                uniqueId != null ? uniqueId : "null",
                emailAddress != null ? emailAddress : "null",
                address != null ? address : "null",
                userDetails != null ? userDetails : "null"
        );
    }
}
