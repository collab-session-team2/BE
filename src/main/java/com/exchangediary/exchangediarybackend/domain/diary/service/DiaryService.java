package com.exchangediary.exchangediarybackend.domain.diary.service;

import com.exchangediary.exchangediarybackend.domain.diary.dto.request.UpdateDiaryRequest;
import com.exchangediary.exchangediarybackend.domain.diary.dto.request.WriteDiaryRequest;
import com.exchangediary.exchangediarybackend.domain.diary.dto.response.DiaryResponse;
import com.exchangediary.exchangediarybackend.domain.diary.entity.Diary;
import com.exchangediary.exchangediarybackend.domain.diary.exception.DiaryErrorCode;
import com.exchangediary.exchangediarybackend.domain.diary.repository.DiaryRepository;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.entity.DiaryRoomUserEntity;
import com.exchangediary.exchangediarybackend.domain.diaryroom.repository.DiaryRoomRepository;
import com.exchangediary.exchangediarybackend.domain.diaryroom.repository.DiaryRoomUserRepository;
import com.exchangediary.exchangediarybackend.domain.user.entity.UserEntity;
import com.exchangediary.exchangediarybackend.domain.user.repository.UserRepository;
import com.exchangediary.exchangediarybackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final DiaryRoomRepository diaryRoomRepository;
    private final DiaryRoomUserRepository diaryRoomUserRepository;

    // 일기 작성 처리 하는 로직
    public DiaryResponse writeDiary(WriteDiaryRequest writeDiaryRequest, Long diaryRoomId, Long userId){

        UserEntity user = userRepository.findById(userId).get();
        DiaryRoomEntity diaryRoom = diaryRoomRepository.findById(diaryRoomId).get();
        DiaryRoomUserEntity diaryRoomUser = diaryRoomUserRepository.findByUserAndDiaryRoom(user, diaryRoom);

        // 일기장 작성 순서가 아닌데, 작성을 시도하면 오류 띄우기
        if(diaryRoom.getCurrentSequence() != diaryRoomUser.getSequence()) throw new CustomException(DiaryErrorCode.INVALID_TURN_ACCESS);

        // 만약 오늘 작성된 일기가, 현재 인원수 만큼 존재하면 오류 띄우기
        List<DiaryResponse> todayDiaries = getDiaries(diaryRoomId, LocalDate.now());

        int currentSequence = diaryRoom.getCurrentSequence();
        int currentMember = diaryRoom.getCurrentMember();

        // 오늘 모두가 일기 다 작성한 것임 -> 작성 X
        if(todayDiaries.size() == currentMember) throw new CustomException(DiaryErrorCode.TODAY_ROUND_CLOSED);

        // 일기장 빌더로 구축
        Diary diary = Diary.builder()
                .targetDate(LocalDate.now())
                .title(writeDiaryRequest.getTitle())
                .content(writeDiaryRequest.getContent())
                .diaryImage("testUrl") // TODO : S3 이미지 업로드 로직 만들기
                .sequence(diaryRoom.getCurrentSequence())
                .user(user)
                .diaryRoom(diaryRoom)
                .build();

        // diaryRoom의 currentSequence 증가하는 로직
        int nextSequence = (currentSequence % currentMember) + 1;

        DiaryRoomEntity updateDiaryRoom = DiaryRoomEntity.builder()
                .diaryRoomId(diaryRoomId)
                .diaryRoomName(diaryRoom.getDiaryRoomName())
                .maxMember(diaryRoom.getMaxMember())
                .currentMember(diaryRoom.getCurrentMember())
                .currentSequence(nextSequence)
                .inviteCode(diaryRoom.getInviteCode())
                .exp(diaryRoom.getExp())
                .diaryRoomImage(diaryRoom.getDiaryRoomImage())
                .build();

        diaryRoomRepository.save(updateDiaryRoom);

        Diary savedDiary = diaryRepository.save(diary);

        return toDiaryResponse(savedDiary);
    }

    // 일기장 전체 조회하는 로직(targetDate 존재 시, 필터링)
    public List<DiaryResponse> getDiaries(Long diaryRoomId, LocalDate targetDate){
        List<Diary> diaries;

        DiaryRoomEntity diaryRoom = diaryRoomRepository.findById(diaryRoomId).get();

        // 날짜 조건이 없으면 전체 조회, 있으면 날짜 필터링
        if(targetDate == null){
            diaries = diaryRepository.findAllByDiaryRoom(diaryRoom);
        } else {
            diaries = diaryRepository.findAllByDiaryRoomAndTargetDate(diaryRoom, targetDate);
        }

        List<DiaryResponse> diaryResponses = new ArrayList<>();

        for(Diary diary : diaries){
            diaryResponses.add(toDiaryResponse(diary));
        }

        return diaryResponses;
    }

    // ID 기반 일기 조회
    public DiaryResponse getDiaryById(Long diaryId){
        return toDiaryResponse(diaryRepository.findById(diaryId).get());
    }

    // 일기장 업데이트
    public DiaryResponse updateDiary(Long diaryId, UpdateDiaryRequest updateDiaryRequest, Long userId){
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

        // 오늘 일기장만 수정 가능
        if(!(diary.getTargetDate().isEqual(LocalDate.now()))) throw new CustomException(DiaryErrorCode.NOT_TODAY_DIARY);

        // 내 일기장만 수정 가능
        if(diary.getUser().getUserId() != userId) throw new CustomException(DiaryErrorCode.NOT_DIARY_OWNER);

        diary.updateDiary(updateDiaryRequest);

        return toDiaryResponse(diary);
    }

    // Diary -> DiaryResponse로 변환
    private DiaryResponse toDiaryResponse(Diary diary){
        return DiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .targetDate(diary.getTargetDate())
                .sequence(diary.getSequence())
                .title(diary.getTitle())
                .content(diary.getContent())
                .diaryImage(diary.getDiaryImage())
                .userId(diary.getUser().getUserId())
                .userName(diary.getUser().getUserName())
                .build();
    }
}
