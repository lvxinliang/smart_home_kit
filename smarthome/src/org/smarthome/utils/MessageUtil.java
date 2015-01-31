package org.smarthome.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.smarthome.entity.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class MessageUtil {

	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	public static final Map<String, String> civilizationMap = new HashMap<String, String>();

	public static final Map<String, String> replaceMap = new HashMap<String, String>();

	public static ObjectMapper xmlMapper = new XmlMapper();

	static {
		civilizationMap.put("傻逼", "猪头");

		replaceMap.put("加微信", "呵呵 我对这个问题保持沉默");
		replaceMap.put("加\\微\\信", "呵呵 我对这个问题保持沉默");
		replaceMap.put("又萌又贱的陪你聊天", "呵呵 我对这个问题保持沉默");
		replaceMap.put("Sharejoes", "呵呵 我对这个问题保持沉默");
		replaceMap.put("加QQ", "呵呵 我对这个问题保持沉默");
	}

	public static TextMessage parseXml(String xml) {
		try {
			return xmlMapper.readValue(xml, TextMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String textMessageToXml(TextMessage textMessage) {
		try {
			return xmlMapper.writeValueAsString(textMessage).replace(
					" xmlns=\"\"", "");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isQqFace(String content) {
		boolean result = false;
		// 判断QQ表情的正则表达式
		String qqfaceRegex = "(/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>)+";
		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}

	public static void main(String[] args) {
		
		System.out.println(isQqFace("/::)/::B/::)/::<"));
		
		/*
		 * String s =
		 * " <xml> <ToUserName>adsasdasda</ToUserName> <FromUserName><![CDATA[from<User]]></FromUserName> <CreateTime>1348831860</CreateTime> <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[this is a test]]></Content> <MsgId>1234567890123456</MsgId> </xml>"
		 * ;
		 * 
		 * TextMessage parseXml = MessageUtil.parseXml(s);
		 * 
		 * System.out.println(parseXml);
		 * 
		 * String textMessageToXml = textMessageToXml(parseXml);
		 * System.out.println(textMessageToXml);
		 */

		/*
		 * TextMessage textMessage = new TextMessage(); textMessage.setContent(
		 * "又萌又贱的陪你聊天！加\\微\\信:Sharejoes 腐女最爱哦，你懂的[强]*,,`',你是大傻逼");
		 * uncivilizedilter(textMessage); replaceMessage(textMessage);
		 * System.out.println(textMessage.getContent());
		 */
	}

	/**
	 * 非文明信息的处理
	 * 
	 * @param textMessage
	 * @return
	 */
	public static TextMessage uncivilizedilter(TextMessage textMessage) {
		for (String key : MessageUtil.civilizationMap.keySet()) {
			textMessage.setContent(textMessage.getContent().replaceAll(key,
					MessageUtil.civilizationMap.get(key)));
		}
		return textMessage;
	}

	/**
	 * 非合理信息的替换
	 * 
	 * @param textMessage
	 * @return
	 */
	public static TextMessage replaceMessage(TextMessage textMessage) {
		for (String key : MessageUtil.replaceMap.keySet()) {
			if (StringUtils.containsIgnoreCase(textMessage.getContent(), key))
				textMessage.setContent(replaceMap.get(key));
		}
		return textMessage;
	}
}
