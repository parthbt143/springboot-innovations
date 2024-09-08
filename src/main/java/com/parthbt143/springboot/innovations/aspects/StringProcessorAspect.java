package com.parthbt143.springboot.innovations.aspects;

import com.parthbt143.springboot.innovations.annotations.StringProcessor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Aspect to process fields annotated with {@link StringProcessor}.
 * Applies transformations and processing rules as specified by the annotation.
 */
@Aspect
@Component
public class StringProcessorAspect {

    /**
     * Pointcut expression to match all methods in the specified package.
     */
    @Pointcut("execution(* com.parthbt143.springboot.innovations..*(..))")
    public void applicationLayer() {
    }

    /**
     * Advice that runs before the execution of methods matched by the pointcut.
     * Processes fields annotated with {@link StringProcessor} in method arguments.
     *
     * @param joinPoint the join point providing access to method arguments
     * @throws IllegalAccessException if access to the field is denied
     */
    @Before("applicationLayer()")
    public void applyStringProcessing(JoinPoint joinPoint) throws IllegalAccessException {
        for (Object arg : joinPoint.getArgs()) {
            processFields(arg);
        }
    }

    /**
     * Processes fields of an object that are annotated with {@link StringProcessor}.
     * Applies text transformations, trimming, and default values based on the annotation settings.
     *
     * @param obj the object to process
     * @throws IllegalAccessException if access to the field is denied
     */
    private void processFields(Object obj) throws IllegalAccessException {
        if (obj == null) return;

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(StringProcessor.class) && field.getType().equals(String.class)) {
                field.setAccessible(true);
                String value = (String) field.get(obj);

                StringProcessor processorAnnotation = field.getAnnotation(StringProcessor.class);

                // Apply default value if field is null or empty
                if (value == null || value.isEmpty()) {
                    value = processorAnnotation.defaultValue();
                }

                // Process value if not null
                if (value != null) {
                    value = processValue(value, processorAnnotation);
                    field.set(obj, value);
                }
            }
        }
    }

    /**
     * Processes the value of a string field according to the {@link StringProcessor} annotation settings.
     * Applies text transformation, enforces maximum length, trims spaces, and replaces multiple spaces.
     *
     * @param value               the value of the string field
     * @param processorAnnotation the annotation containing processing rules
     * @return the processed string value
     */
    private String processValue(String value, StringProcessor processorAnnotation) {
        // Apply text transformation based on annotation settings
        value = applyTextTransformation(value, processorAnnotation.transform());

        // Enforce maximum length if specified
        int maxLength = processorAnnotation.maxLength();
        if (maxLength > 0 && value.length() > maxLength) {
            value = value.substring(0, maxLength);
        }

        // Trim leading and trailing spaces if required
        if (processorAnnotation.trimSpaces()) {
            value = value.trim();
        }

        // Replace multiple spaces between words with a single space if required
        if (processorAnnotation.trimMultipleSpaces()) {
            value = value.replaceAll("\\s+", " ");
        }

        return value;
    }

    /**
     * Applies text transformation based on the specified type.
     *
     * @param value         the string value to transform
     * @param transformType the type of transformation to apply
     * @return the transformed string value
     */
    private String applyTextTransformation(String value, StringProcessor.TransformType transformType) {
        return switch (transformType) {
            case UPPERCASE -> value.toUpperCase();
            case LOWERCASE -> value.toLowerCase();
            case CAPITALIZE_WORDS -> capitalizeFirstLetters(value);
            case CAPITALIZE_SENTENCES -> capitalizeSentences(value);
            default -> value;
        };
    }

    /**
     * Capitalizes the first letter of each sentence in the input string.
     *
     * @param input the input string to process
     * @return the string with capitalized first letters of each sentence
     */
    private String capitalizeSentences(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : input.toCharArray()) {
            if (capitalizeNext && Character.isLetter(c)) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
                if (c == '.' || c == '?' || c == '!') {
                    capitalizeNext = true;
                }
            }
        }

        return result.toString();
    }

    /**
     * Capitalizes the first letter of each word in the input string.
     *
     * @param input the input string to process
     * @return the string with capitalized first letters of each word
     */
    private String capitalizeFirstLetters(String input) {
        String[] parts = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return result.toString().trim();
    }
}
