package com.amii.plus.api.service;

import org.springframework.stereotype.Component;

/**
 * TODO: 消息提供者（消息发送者）
 */
@Component
public class RabbitMqProviderService
{
    /**
     * TODO: 消息队列模板
     */
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	public void sendMsg(String routingKey, String msg) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("消息发送时间:" + sdf.format(new Date()));
//
//		String exchange = RabbitMqConstant.MESSAGE_EXCHANGE_NAME1;
//
//		// MessagePostProcessor可以为队列配置一些参数,如：队列名joinName、延时时间x-delay
//		MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
//			@Override
//			public Message postProcessMessage(Message message) throws AmqpException {
//				// 如果是实时代列，只需要把这里的配置延时时间x-delay去掉即可
//				// message.getMessageProperties().setHeader("x-delay", 3000);
//				return message;
//			}
//		};
//
//		this.rabbitTemplate.convertAndSend(exchange, routingKey, msg, messagePostProcessor);
//	}
}
