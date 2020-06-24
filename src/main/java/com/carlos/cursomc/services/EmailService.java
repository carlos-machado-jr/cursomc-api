package com.carlos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.carlos.cursomc.domain.Cliente;
import com.carlos.cursomc.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
