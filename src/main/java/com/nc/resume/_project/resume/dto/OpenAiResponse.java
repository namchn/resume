package com.nc.resume._project.resume.dto;

import java.util.List;

import lombok.Data;

@Data
public class OpenAiResponse {
    private String id;
    private String object;
    private long created;
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;
        private String finish_reason;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}

/*
	{
	  "id": "chatcmpl-7",
	  "object": "chat.completion",
	  "created": 1691639119,
	  "choices": [
	    {
	      "index": 0,
	      "message": {
	        "role": "assistant",
	        "content": "Hello, how can I help you?"
	      },
	      "finish_reason": "stop"
	    }
	  ]
	}
*/