package com.caso3.inventario.inventario.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.caso3.inventario.inventario.dto.LogDTO;

import java.io.IOException;

@Component
public class MonitoringFilter implements Filter {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        long inicio = System.currentTimeMillis();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);

        } finally {

            long fin = System.currentTimeMillis();

            LogDTO log = new LogDTO();

            log.setServicio("productos-api");
            log.setEndpoint(req.getRequestURI());
            log.setMetodo(req.getMethod());
            log.setEstado(res.getStatus());
            log.setTiempoRespuesta(fin - inicio);
            log.setIp(req.getRemoteAddr());

            try {
                restTemplate.postForObject(
                        "http://localhost:8091/logs",
                        log,
                        String.class
                );

            } catch (Exception e) {
                System.out.println("No se pudo enviar log");
            }
        }
    }
}