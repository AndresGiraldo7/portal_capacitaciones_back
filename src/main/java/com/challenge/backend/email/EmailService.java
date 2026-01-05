package com.challenge.backend.email;
import com.challenge.backend.usuario.entity.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Envía un email de bienvenida cuando se registra un usuario
     */
    public void enviarEmailBienvenida(Usuario usuario) {
        try {
            Context context = new Context();
            context.setVariable("nombreUsuario", usuario.getNombre());
            context.setVariable("username", usuario.getUsername());
            
            String htmlContent = templateEngine.process("emails/bienvenida", context);
            
            enviarEmail(
                usuario.getEmail(),
                "¡Bienvenido a Core Banca!",
                htmlContent
            );
            
            log.info("✅ Email de bienvenida enviado a: {}", usuario.getEmail());
        } catch (Exception e) {
            log.error("❌ Error al enviar email de bienvenida: {}", e.getMessage());
        }
    }

    /**
     * Envía notificación cuando se completa un curso
     */
    public void enviarNotificacionCursoCompletado(Usuario usuario, String nombreCurso) {
        try {
            Context context = new Context();
            context.setVariable("nombreUsuario", usuario.getNombre());
            context.setVariable("nombreCurso", nombreCurso);
            context.setVariable("fecha", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            
            String htmlContent = templateEngine.process("emails/curso-completado", context);
            
            enviarEmail(
                usuario.getEmail(),
                "🎉 ¡Felicitaciones! Has completado un curso",
                htmlContent
            );
            
            log.info("✅ Email de curso completado enviado a: {}", usuario.getEmail());
        } catch (Exception e) {
            log.error("❌ Error al enviar notificación de curso completado: {}", e.getMessage());
        }
    }

    /**
     * Envía notificación cuando se obtiene una insignia
     */
    public void enviarNotificacionInsignia(Usuario usuario, String nombreInsignia, String descripcionInsignia) {
        try {
            Context context = new Context();
            context.setVariable("nombreUsuario", usuario.getNombre());
            context.setVariable("nombreInsignia", nombreInsignia);
            context.setVariable("descripcionInsignia", descripcionInsignia);
            
            String htmlContent = templateEngine.process("emails/insignia-obtenida", context);
            
            enviarEmail(
                usuario.getEmail(),
                "🏆 ¡Has obtenido una nueva insignia!",
                htmlContent
            );
            
            log.info("✅ Email de insignia enviado a: {}", usuario.getEmail());
        } catch (Exception e) {
            log.error("❌ Error al enviar notificación de insignia: {}", e.getMessage());
        }
    }

    /**
     * Envía recordatorio de cursos pendientes
     */
    public void enviarRecordatorioCursosPendientes(Usuario usuario, int cursosEnProgreso) {
        try {
            Context context = new Context();
            context.setVariable("nombreUsuario", usuario.getNombre());
            context.setVariable("cantidadCursos", cursosEnProgreso);
            
            String htmlContent = templateEngine.process("emails/recordatorio", context);
            
            enviarEmail(
                usuario.getEmail(),
                "📚 Tienes cursos pendientes",
                htmlContent
            );
            
            log.info("✅ Email de recordatorio enviado a: {}", usuario.getEmail());
        } catch (Exception e) {
            log.error("❌ Error al enviar recordatorio: {}", e.getMessage());
        }
    }

    /**
     * Método genérico para enviar emails
     */
    private void enviarEmail(String destinatario, String asunto, String contenidoHtml) 
            throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");
        
        helper.setFrom("noreply@corebanca.com");
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml, true);
        
        mailSender.send(mensaje);
    }
}