package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.NoteNotFoundException;
import com.example.personalagendaapp.model.Note;
import com.example.personalagendaapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public Note createNote(Note note) {
        return noteRepository.createNote(note);
    }

    public Note getNoteById(long noteId) {
        Optional<Note> optionalNote = noteRepository.getNoteById(noteId);

        if (!optionalNote.isPresent()) {
            throw new NoteNotFoundException();
        }

        return optionalNote.get();
    }

    public List<Note> getNotesByTaskId(long taskId) {
        return noteRepository.getNotesByTaskId(taskId);
    }

    public void deleteNotesForTask(long taskId) {
        noteRepository.deleteNotesForTask(taskId);
    }
}
