package com.example.personalagendaapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoteRequest {
    @NotNull
    @NotBlank
    @Size(max = 500)
    private String content;

    public NoteRequest() {
    }

    public NoteRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
