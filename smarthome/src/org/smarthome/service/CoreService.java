package org.smarthome.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.smarthome.entity.TextMessage;
import org.smarthome.utils.MessageUtil;
import org.smarthome.utils.SimsimiUtil;

public class CoreService {

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		BufferedReader reader = null;
		try {
			reader = request.getReader();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<String> readLines = null;
		try {
			readLines = IOUtils.readLines(reader);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
		}
		StringBuilder sb = new StringBuilder(200);
		for (String string : readLines) {
			sb.append(string);
		}
		// System.out.println(sb.toString());
		String respMessage = null;
		try {
			// xml请求解析
			TextMessage parseXml = MessageUtil.parseXml(sb.toString());
			// 发送方帐号（open_id）
			String fromUserName = parseXml.getFromUserName();
			// 公众帐号
			String toUserName = parseXml.getToUserName();
			// 消息类型
			String msgType = parseXml.getMsgType();
			// 回复文本消息
			String content = parseXml.getContent();

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			String simsimiResult = "";

			boolean skipSimsimi = false;
			if (MessageUtil.isQqFace(content)) {
				textMessage.setContent(content);
				skipSimsimi = true;
			}
			/*********************** 发送请求 ************************/
			// 文本消息
			if (!skipSimsimi) {
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
					simsimiResult = SimsimiUtil.getSimsimiResult(content);
					textMessage.setContent(simsimiResult);
				} else { // 非文本消息
					textMessage
							.setContent("这个我不知道如何回答，你去咨询我的上帝吧。微信号:sciencelxl");
				}

				/************************* 过滤结果 ***********************/
				// 空文本处理
				if (simsimiResult == null || simsimiResult == ""
						|| simsimiResult.contains("这是SimSimi不懂的话")) {
					textMessage
							.setContent("这个我不知道如何回答，你去咨询创造我的上帝吧。微信号:sciencelxl");
				} else {
					// 非空文本的处理
					MessageUtil.replaceMessage(textMessage);
					MessageUtil.uncivilizedilter(textMessage);
				}
			}
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

}
