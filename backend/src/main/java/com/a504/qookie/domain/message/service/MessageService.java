package com.a504.qookie.domain.message.service;

import java.util.NoSuchElementException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Service;

import com.a504.qookie.domain.heart.entity.Heart;
import com.a504.qookie.domain.heart.repository.HeartRepository;
import com.a504.qookie.domain.heart.service.HeartService;
import com.a504.qookie.domain.message.dto.MessageRequest;
import com.a504.qookie.domain.message.dto.MessageResponse;
import com.a504.qookie.global.firebase.FirebaseService;
import com.a504.qookie.global.rabbitMQ.RabbitMqConfig;
import com.google.firebase.messaging.FirebaseMessagingException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
	private final RabbitTemplate rabbitTemplate;
	private final HeartRepository heartRepository;
	private final FirebaseService firebaseService;

	public void sendMessage(MessageRequest messageRequest) {
		rabbitTemplate.convertAndSend(
			RabbitMqConfig.gptExchange,
			RabbitMqConfig.routingKeyToFlask,
			messageRequest);
	}

	@RabbitListener(queues = RabbitMqConfig.queueFromFlask)
	@Transactional
	public void receiveMessage(MessageResponse messageResponse) {

		try {
			// save reply to db
			Heart heart = heartRepository.findById(messageResponse.getHeartId())
				.orElseThrow(NoSuchElementException::new);
			heart.saveReply(messageResponse.getContent());
			// send firebase notification
			firebaseService.sendMessage("답장왔어", "요", heart.getMember().getMessageToken());
		} catch (FirebaseMessagingException | NoSuchElementException | NullPointerException e) {
			// delete message if error occured
			e.printStackTrace();
			throw new ListenerExecutionFailedException("Error Handler converted exception to fatal", e);
		}
	}
}
