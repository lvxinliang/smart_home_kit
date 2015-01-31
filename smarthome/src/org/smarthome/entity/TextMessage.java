package org.smarthome.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("xml")
public class TextMessage {

	@JsonProperty("ToUserName")
	private String toUserName;
	@JsonProperty("FromUserName")
	private String fromUserName;
	@JsonProperty("CreateTime")
	private long createTime;
	@JsonProperty("MsgType")
	private String msgType;
	@JsonProperty("FuncFlag")
	private int funcFlag;
	@JsonProperty("Content")
	private String content;
	@JsonProperty("MsgId")
	private String msgId;
	@JsonProperty("Encrypt")
	private String encrypt;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public int getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(int funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	@Override
	public String toString() {
		return "TextMessage [toUserName=" + toUserName + ", fromUserName=" + fromUserName + ", createTime=" + createTime + ", msgType=" + msgType + ", funcFlag=" + funcFlag + ", content=" + content + ", msgId=" + msgId + "]";
	}

}
