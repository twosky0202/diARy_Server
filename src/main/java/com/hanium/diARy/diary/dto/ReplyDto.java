package com.hanium.diARy.diary.dto;

import com.hanium.diARy.diary.entity.Comment;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private Long commentId;
    private Long diaryId;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private UserDto userDto;

}