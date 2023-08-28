package com.hanium.diARy.home.search.service;

import com.hanium.diARy.diary.dto.DiaryDto;
import com.hanium.diARy.diary.dto.DiaryLocationDto;
import com.hanium.diARy.diary.dto.DiaryResponseDto;
import com.hanium.diARy.diary.dto.DiaryTagDto;
import com.hanium.diARy.diary.entity.Diary;
import com.hanium.diARy.diary.entity.DiaryLocation;
import com.hanium.diARy.diary.entity.DiaryTag;
import com.hanium.diARy.diary.repository.DiaryLocationInterface;
import com.hanium.diARy.diary.repository.DiaryRepositoryInterface;
import com.hanium.diARy.diary.repository.DiaryTagRepositoryInterface;
import com.hanium.diARy.user.dto.UserDto;
import com.hanium.diARy.user.entity.User;
import com.hanium.diARy.user.repository.UserRepositoryInterface;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final DiaryRepositoryInterface diaryRepositoryInterface;
    private final DiaryTagRepositoryInterface diaryTagRepositoryInterface;
    private final DiaryLocationInterface diaryLocationInterface;
    private final UserRepositoryInterface userRepositoryInterface;

    public SearchService(
            @Autowired DiaryRepositoryInterface diaryRepositoryInterface,
            @Autowired DiaryTagRepositoryInterface diaryTagRepositoryInterface,
            @Autowired DiaryLocationInterface diaryLocationInterface,
            @Autowired UserRepositoryInterface userRepositoryInterface
            ) {
        this.diaryRepositoryInterface = diaryRepositoryInterface;
        this.diaryTagRepositoryInterface = diaryTagRepositoryInterface;
        this.diaryLocationInterface = diaryLocationInterface;
        this.userRepositoryInterface = userRepositoryInterface;
    }

    public List<DiaryResponseDto> findDiaryByTag(String searchword) {
        System.out.println("service");
        DiaryTag diaryTag = diaryTagRepositoryInterface.findByName(searchword);
        List<Diary> diaries = diaryTag.getDiaries();
        List<DiaryResponseDto> diaryResponseDtos = new ArrayList<>();

        for (Diary diary: diaries) {
            DiaryDto diaryDto = new DiaryDto();
            BeanUtils.copyProperties(diary, diaryDto);

            List<DiaryLocationDto> diaryLocationDtoList = new ArrayList<>();
            List<DiaryLocation> diaryLocations = this.diaryLocationInterface.findByDiary_DiaryId(diary.getDiaryId());
            for(DiaryLocation diaryLocation: diaryLocations) {
                DiaryLocationDto diaryLocationDto = new DiaryLocationDto();
                diaryLocationDto.setDiaryId(diary.getDiaryId());
                BeanUtils.copyProperties(diaryLocation, diaryLocationDto);
                diaryLocationDtoList.add(diaryLocationDto);
            }

            List<DiaryTagDto> tagDtos = new ArrayList<>();
            for (DiaryTag tag : diary.getTags()) {
                DiaryTagDto tagDto = new DiaryTagDto();
                BeanUtils.copyProperties(tag, tagDto);
                tagDtos.add(tagDto);
            }
            diaryDto.setTags(tagDtos);

            UserDto userDto = new UserDto();
            User user = userRepositoryInterface.findById(diary.getUser().getUserId()).get();
            BeanUtils.copyProperties(user, userDto);

            DiaryResponseDto diaryResponseDto = new DiaryResponseDto();
            diaryResponseDto.setDiaryDto(diaryDto);
            diaryResponseDto.setUserDto(userDto);
            diaryResponseDto.setDiaryLocationDtoList(diaryLocationDtoList);
            diaryResponseDtos.add(diaryResponseDto);

        }
        return diaryResponseDtos;
    }
}