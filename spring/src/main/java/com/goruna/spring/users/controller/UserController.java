package com.goruna.spring.users.controller;

import com.goruna.spring.book.dto.BookListReadResDTO;
import com.goruna.spring.book.service.BookService;
import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.users.dto.NickNameRequestDto;
import com.goruna.spring.users.dto.UserInfoRequestDto;
import com.goruna.spring.users.dto.UserInfoResponse;
import com.goruna.spring.users.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Info API", description = "user info API")
public class UserController {

    private final UserInfoService userInfoService;

    @PostMapping("/{userSeq}/nickname")
    @Operation(summary = "회원 닉네임 추가", description = "회원의 닉네임을 추가합니다.")
    public ApiResponse<?> addNickname(@PathVariable Long userSeq, @RequestBody NickNameRequestDto nickNameRequestDto) {

        if (nickNameRequestDto.getUserNickname() == null || nickNameRequestDto.getUserNickname().isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 항목입니다.");
        }
        userInfoService.createNickname(userSeq, nickNameRequestDto);
        return ResponseUtil.successResponse("회원 닉네임이 성공적으로 추가되었습니다").getBody();
    }

    @GetMapping("/{userSeq}/info")
    @Operation(summary = "회원 정보 조회", description = "회원의 정보를 조회합니다.")
    public ApiResponse<?> getUserInfo(@PathVariable Long userSeq) {

        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(userSeq);
        return ResponseUtil.successResponse("회원이 성공적으로 조회되었습니다.",userInfoResponse).getBody();
    }

    @PutMapping("/{userSeq}/info")
    @Operation(summary = "회원 정보 수정", description = "회원의 정보를 수정합니다.")
    public ApiResponse<?> updateUserInfo(@PathVariable Long userSeq, @RequestBody UserInfoRequestDto userInfoRequestDto) {
         userInfoService.updateUserInfo(userSeq, userInfoRequestDto);
        return ResponseUtil.successResponse("회원이 성공적으로 수정되었습니다.",userInfoRequestDto).getBody();
    }

    // 회원 이용 내역 조회
    @GetMapping("/{userSeq}/history")
    @Operation(summary="회원 이용 내역 조회", description="회원 이용 내역을 조회합니다.")
    public ApiResponse<?> getUserHistory(@PathVariable(value="userSeq") Long userSeq) {
        List<BookListReadResDTO> bookListReadResDTOs = userInfoService.readUserHistory(userSeq);
        return ResponseUtil.successResponse("회원 예약 내역 조회가 성공적으로 완료되었습니다.", bookListReadResDTOs).getBody();
    }

    // 회원 탈퇴
    @DeleteMapping("/{userSeq}")
    @Operation(summary="회원 탈퇴", description="회원을 탈퇴합니다.")
    public ApiResponse<?> deleteUser(@PathVariable(value="userSeq") Long userSeq) {
        userInfoService.deleteUser(userSeq);
        return ResponseUtil.successResponse("회원 탈퇴가 성공적으로 진행되었습니다.", null).getBody();
    }
}
