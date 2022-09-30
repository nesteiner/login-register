package com.example.loginregister.filter;

import com.example.loginregister.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null, jwtToken = null;
        ObjectMapper mapper = new ObjectMapper();
        // enter request logic
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                request.setAttribute("username", username);
                request.setAttribute("jwtToken", jwtToken);
            } catch (IllegalArgumentException exception) {
                log.error("unable to get jwt token");
                ObjectNode node = mapper.createObjectNode();
                node.put("status", "access failed");
                node.put("message", "in LoginFilter: unable to get jwt token");
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                response.setStatus(401);
                response.getWriter().write(json);
                return;
            } catch (ExpiredJwtException exception) {
                log.error("jwt token has been expired");
                ObjectNode node = mapper.createObjectNode();
                node.put("status", "access failed");
                node.put("message", "in LoginFilter: jwt token has been expired");
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                response.setStatus(401);
                response.getWriter().write(json);
                return;
            } catch (Exception exception) {
                log.error("unknown error");
                ObjectNode node = mapper.createObjectNode();
                node.put("status", "access failed");
                node.put("message", "in LoginFilter: jwt token parse error");
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                response.setStatus(400);
                response.getWriter().write(json);
                return;
            } finally {
//                response.setHeader("Access-Control-ALlow-Origin", "*");
            }
        } else if (requestTokenHeader != null){
            log.warn("jwt token does not begin with bearer string");
            ObjectNode node = mapper.createObjectNode();
            node.put("status", "access failed");
            node.put("message", "in LoginFilter: jwt token parse error");
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
            response.setStatus(400);
//            response.setHeader("Access-Control-ALlow-Origin", "*");
            response.getWriter().write(json);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
