package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long diaryId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<ReplyDto> replyDtos;
    private UserDto userDto;
}