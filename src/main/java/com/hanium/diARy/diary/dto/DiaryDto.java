package com.hanium.diARy.diary.dto;


import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    private Long diaryId;
    private String title;
    private String travelDest;
    private String memo;
    private int satisfaction;
    private boolean isPublic;
    private Date travelStart;
    private Date travelEnd;
    private Date createdAt;
    private Date updatedAt;
    private List<DiaryTagDto> tags;
    private List<DiaryLikeDto> likes = new ArrayList<>();
    private int likesCount;
    private List<CommentDto> comments = new ArrayList<>();

}

