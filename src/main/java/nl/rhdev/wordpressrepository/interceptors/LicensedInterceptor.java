package nl.rhdev.wordpressrepository.interceptors;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.rhdev.wordpressrepository.annotations.Licensed;
import nl.rhdev.wordpressrepository.repositories.LicenseRepository;

@Component
public class LicensedInterceptor implements HandlerInterceptor {

    private final LicenseRepository licenseRepository;

    @Autowired
    public LicensedInterceptor(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Licensed licensed = handlerMethod.getMethodAnnotation(Licensed.class);
        if (licensed == null)  {
            return true;
        }

        try {
            licenseRepository.findById(request.getHeader("X-License")).orElseThrow();
            return true;
        } catch (NoSuchElementException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        } 
    }
}
