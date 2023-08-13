package com.hanium.diARy.diary.service;

import com.hanium.diARy.diary.DiaryMapper;
import com.hanium.diARy.diary.ReplyMapper;
import com.hanium.diARy.diary.dto.CommentDto;
import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.repository.*;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepository;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentRepositoryInterface commentRepositoryInterface;
    private final DiaryMapper diaryMapper;
    private final ReplyMapper replyMapper;
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            CommentRepositoryInterface commentRepositoryInterface,
            DiaryMapper diaryMapper,
            ReplyMapper replyMapper,
            DiaryRepositoryInterface diaryRepositoryInterface,
            UserRepositoryInterface userRepositoryInterface

    ) {
        this.commentRepository = commentRepository;
        this.commentRepositoryInterface = commentRepositoryInterface;
        this.diaryMapper = diaryMapper;
        this.replyMapper = replyMapper;
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Transactional
    public void createComment(CommentDto commentDto) {
        this.commentRepository.createComment(commentDto);
    }

    public CommentDto readComment(Long id) {
        Comment comment = this.commentRepository.readComment(id);
        return new CommentDto(
                diaryRepositoryInterface.findById(comment.getDiary().getDiaryId()).get().getDiaryId(),
                comment.getContent(),
                userRepositoryInterface.findById(comment.getUser().getUserId()).get().getUserId(),
                this.replyMapper.toDtoList(comment.getReplies())
        );
    }

    public List<CommentDto> readCommentAll() {
        Iterator<Comment> iterator = this.commentRepository.readCommentAll();
        List<CommentDto> commentDtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            Comment comment = iterator.next();
            CommentDto dto = new CommentDto();
            dto.setContent(comment.getContent());
            dto.setUserId(comment.getUser().getUserId());
            dto.setDiaryId(comment.getDiary().getDiaryId());
            commentDtoList.add(dto);
        }

        return commentDtoList;
    }

/*    public List<CommentDto> readUserCommentAll(Long userId) {
        List<Comment> commentList = this.commentRepository.readUserCommentAll(userId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentDtoList.add(new CommentDto(
                    comment.getDiary().getDiaryId(),
                    comment.getContent(),
                    comment.getUser().getUserId(),
                    this.replyMapper.toDtoList(comment.getReplies())
            ));
        }

        return commentDtoList;
    }*/

    public List<CommentDto> readDiaryCommentAll(Long diaryId) {
        List<Comment> commentList = this.commentRepository.readDiaryCommentAll(diaryId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for(Comment comment : commentList) {
            commentDtoList.add(new CommentDto(
                    comment.getDiary().getDiaryId(),
                    comment.getContent(),
                    comment.getUser().getUserId(),
                    this.replyMapper.toDtoList(comment.getReplies())
            ));
        }

        return commentDtoList;
    }

    public void updateComment(Long id, CommentDto dto) {
        this.commentRepository.updateComment(id, dto);
    }

    public void deleteComment(Long id) {
        this.commentRepository.deleteComment(id);
    }

}