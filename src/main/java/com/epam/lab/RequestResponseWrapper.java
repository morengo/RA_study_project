package com.epam.lab;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class RequestResponseWrapper {

    @JsonProperty("RestResponse")
    private RestResponse restResponse;

    public RestResponse getRestResponse() {
        return restResponse;
    }

    static class RestResponse {
        @JsonProperty("result")
        private Country result;
        @JsonProperty("messages")
        private List<String> messages = new ArrayList<String>();

        public Country getCountry() {
            return result;
        }

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        @Override
        public String toString() {
            return "RestResponse{" +
                    "result=" + result +
                    ", messages=" + messages +
                    '}';
        }
    }
}
