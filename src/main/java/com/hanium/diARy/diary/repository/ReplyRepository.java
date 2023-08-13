package com.hanium.diARy.diary.repository;

import com.hanium.diARy.diary.ReplyMapper;
import com.hanium.diARy.diary.dto.ReplyDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.Reply;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ReplyRepository {
    private final ReplyRepositoryInterface replyRepositoryInterface;
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;
    private final ReplyMapper replyMapper;

    public ReplyRepository(
        @Autowired ReplyRepositoryInterface replyRepositoryInterface,
        @Autowired CommentRepositoryInterface commentRepositoryInterface,
        @Autowired ReplyMapper replyMapper,
        @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
        @Autowired UserRepositoryInterface userRepositoryInterface
        ){
        this.replyRepositoryInterface = replyRepositoryInterface;
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.replyMapper = replyMapper;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;

    }

    public void createReply(ReplyDto dto) {
        Reply reply = new Reply();
        Diary diary = this.diaryRepositoryInterface.findById(dto.getDiaryId()).get();
        User user = this.userRepositoryInterface.findById(dto.getUserId()).get();
        Comment comment = this.commentRepositoryInterface.findById(dto.getCommentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        reply.setDiary(diary);
        reply.setComment(comment);
        reply.setUser(user);
        reply.setContent(dto.getContent());
        comment.getReplies().add(this.replyMapper.toEntity(dto));


        this.replyRepositoryInterface.save(reply);
    }

    public Reply readReply(Long id) {
        Optional<Reply> reply = this.replyRepositoryInterface.findById(id);
        if(reply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return reply.get();
    }

    public Iterator<Reply> readReplyAll() {
        return this.replyRepositoryInterface.findAll().iterator();
    }

    public List<Reply> readCommentReplyAll(Long id) {
        Comment comment = this.commentRepositoryInterface.findByCommentId(id);
        return comment.getReplies();
    }

    public List<Reply> readUserReplyAll(Long id) {
        return this.replyRepositoryInterface.findByUser_UserId(id);
    }

    public void updateReply(Long id, ReplyDto dto) {
        if (id == null) {
            throw new IllegalArgumentException("Reply id cannot be null.");
        }

        Optional<Reply> targetReply = this.replyRepositoryInterface.findById(id);
        if (targetReply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Reply reply = targetReply.get();

        // Update content if dto.getContent() is not null
        if (dto.getContent() != null) {
            reply.setContent(dto.getContent());
        }

        // Update user if dto.getUserId() is not null
        if (dto.getUserId() != null) {
            User newUser = this.userRepositoryInterface.findById(dto.getUserId()).orElse(null);
            if (newUser != null) {
                reply.setUser(newUser);
            }
        }

        // Update comment if dto.getCommentId() is not null
        if (dto.getCommentId() != null) {
            Comment newComment = this.commentRepositoryInterface.findById(dto.getCommentId()).orElse(null);
            if (newComment != null) {
                reply.setComment(newComment);
            }
        }

        // Update diary if dto.getDiaryId() is not null
        if (dto.getDiaryId() != null) {
            Diary newDiary = this.diaryRepositoryInterface.findById(dto.getDiaryId()).orElse(null);
            if (newDiary != null) {
                reply.setDiary(newDiary);
            }
        }

        this.replyRepositoryInterface.save(reply);
    }
    public void deleteReply(Long id) {
        Optional<Reply> targetReply = this.replyRepositoryInterface.findById(id);
        if(targetReply.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.replyRepositoryInterface.delete(targetReply.get());
    }

}