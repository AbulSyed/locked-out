package com.syed.identityservice.utility;

import com.syed.identityservice.exception.custom.InvalidRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Utility {

    public static Pageable createPageable(Integer page, Integer size, Sort sort) {
        // page/size null check caught by MissingServletRequestParameterException - see ExceptionHandlerControllerAdvice

        if (page < 1 || size < 1) {
            throw new InvalidRequestException("Page & size must be greater than or equal to 1");
        }

        return PageRequest.of(page - 1, size, sort);
    }
}
