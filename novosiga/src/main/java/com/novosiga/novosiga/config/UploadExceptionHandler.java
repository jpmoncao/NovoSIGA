package com.novosiga.novosiga.config;

import java.net.URI;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class UploadExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String handleUpload(MultipartException ex, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        boolean tamanhoExcedido = ex instanceof MaxUploadSizeExceededException
                || causaEhTamanhoExcedido(ex);
        redirectAttributes.addFlashAttribute("erro", tamanhoExcedido
                ? "A foto excede o limite de 2 MB. Escolha um arquivo menor."
                : "Não foi possível enviar a foto. Tente novamente.");
        return "redirect:" + destino(request);
    }

    private boolean causaEhTamanhoExcedido(Throwable ex) {
        Throwable atual = ex;
        while (atual != null) {
            String nome = atual.getClass().getSimpleName();
            if (nome.contains("SizeLimit") || nome.contains("MaxUpload")) {
                return true;
            }
            atual = atual.getCause();
        }
        return false;
    }

    private String destino(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            try {
                String path = URI.create(referer).getPath();
                if (path != null && path.startsWith("/") && !path.startsWith("//")
                        && (path.contains("/aluno/") || path.contains("/produto/"))) {
                    return path;
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        String requestUri = request.getRequestURI();
        if (requestUri != null && requestUri.contains("/produto/")) {
            return "/produto/listar";
        }
        return "/aluno/listar";
    }
}
