package com.backend.api.global.common.code;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * ******************************* Global Error CodeList ***************************************
     * HTTP Status Code
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 409 : Conflict
     * 500 : Internal Server Error
     * *********************************************************************************************
     */
    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(400, "G001", "Bad Request Exception"),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(400, "G002", "Required request body is missing"),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(400, "G003", "Invalid Type Value"),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(400, "G004", "Missing Servlet RequestParameter Exception"),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(400, "G005", "I/O Exception"),

    // com.google.gson JSON 파싱 실패
    JSON_PARSE_ERROR(400, "G006", "JsonParseException"),

    // com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G007", "com.fasterxml.jackson.core Exception"),

    // 권한이 없음
    FORBIDDEN_ERROR(403, "G008", "Forbidden Exception"),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404, "G009", "Not Found Exception"),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(404, "G010", "Null Point Exception"),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(404, "G011", "Handle Validation Exception"),

    // Header 가 유효하지 않은 경우
    NOT_VALID_HEADER_ERROR(404, "G012", "Header에 데이터가 존재하지 않는 경우 "),
    // 로컬 파일 업로드 실패,
    FAILED_TO_UPLOAD_LOCAL_FILE(404, "F001", "로컬 파일 업로드 실패"),
    // 로컬 파일 업로드 실패,
    FAILED_TO_UPLOAD_S3_FILE(404, "F002", "S3 파일 업로드 실패"),
    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),

    /**
     * ******************************* Business Error CodeList ***************************************
     */
    //TODO : 예시로 남겨놓되, 배포시에 없앨 것. ~ 3/14

    // 사용자 권한 인증 실패 (CODE: 100)
    UNAUTHORIZED_USER_EXCEPTION(403, "B100", "권한이 없는 사용자입니다."),
    FAILED_OAUTH2_AUTHENTICATION_EXCEPTION(403, "B101", "소셜 로그인에 실패했습니다."),
    INVALID_ACCESS_TOKEN_EXCEPTION(403, "B102", "유효하지 않은 ACCESS TOKEN 입니다."),
    EXPIRED_ACCESS_TOKEN_EXCEPTION(403, "B103", "만료된 ACCESS TOKEN 입니다."),
    INCONSISTENT_ACCESS_TOKEN_EXCEPTION(403, "B104", "일치하지 않는 ACCESS TOKEN 입니다."),
    INVALID_REFRESH_TOKEN_EXCEPTION(403, "B105", "유효하지 않은 REFRESH TOKEN 입니다."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(403, "B106", "만료된 REFRESH TOKEN 입니다."),
    INCONSISTENT_REFRESH_TOKEN_EXCEPTION(403, "B107", "일치하지 않는 REFRESH TOKEN 입니다."),
    ILLEGAL_TOKEN_EXCEPTION(403, "B107", "헤더에 토큰 정보가 존재하지 않습니다."),

    // 유저 에러 (CODE: 200)
    // 유저가 존재하지 않음
    NOT_FOUND_USER(404, "B200", "존재하지 않는 유저입니다."),
    NOT_ENOUGH_POINT(404, "B021", "포인트가 부족합니다."),
    // 메뉴 에러 (CODE: 300)
    // 메뉴가 존재하지 않음 (CODE: 300)
    NOT_FOUND_MENU(404, "B300", "존재하지 않는 메뉴 입니다."),
    FAILED_TO_UPDATE_MEMBER(404, "B301", "멤버 정보 업데이트에 실패 하였습니다."),
    // 음식점 에러 (CODE: 400)
    NOT_FOUND_RESTAURANT(404, "B400", "존재하지 않는 음식점 입니다."),
    // S3 File 에러 (CODE: 900)
    NOT_FOUND_S3FILE(404, "B900", "존재하지 않는 S3 파일입니다."),
    // 친구 요청 에러 (CODE: 201)
    ALREADY_EXIST_FRIEND_REQUEST(409,"B201" ,"이미 요청을 보냈습니다" ),
    // 친구 에러 (CODE: 202)
    ALREADY_EXIST_FRIEND(409,"B202" ,"이미 친구입니다." ),
    //매칭 에러 (CODE: 500)

    NOT_MATCHING(404,"B501" , "매칭중이 아닙니다."),
    NOT_FOUND_WAITING_ROOM(404, "B502", "존재하지 않는 대기실입니다."),
    NOT_HOST(404,"B503" ,"방장이 아닙니다." ),

    //펀드 에러 (CODE: 600)
    NOT_FOUND_FUND(404,"B600" , "존재하지 않는 펀드입니다" ),
    FULL_CAPACITY_EXCEPTION(400, "B601" , "모집 인원이 가득 찼습니다"),
    NOT_RECRUITING_FUND(400,"B602" , "모집 중인 펀드가 아닙니다" ),
    MINIMUM_AMOUNT_EXCEPTION(400,"B603" , "최소 금액보다 적습니다" ),
    ALREADY_REGISTERED_FUND(400, "B604" , "이미 가입한 펀드입니다"),
    CLOSED_FUND_EXCEPTION(400, "B605" , "이미 종료된 펀드입니다" ),
    NOT_ENOUGH_ASSET(400,"B606" ,"자산이 부족합니다" ),
    NOT_FUND_MANAGER(400,"B607", "펀드매니저가 아닙니다"),
    NO_FUND_STOCK(400, "B608","펀드관련 주식 종목 정보가 없습니다"),
    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 주식 에러 (CODE: 300)
    NOT_ENOUGH_STOCK_AMOUNT(404, "S300", "보유중인 주식이 부족합니다."),
    NO_SINGLE_GAME_STOCK(404, "S301", "해당하는 주식 정보가 없습니다."),
    NOT_ENOUGH_MONEY(404, "S302", "보유중인 자금이 부족합니다."),
    NOT_ENOUGH_CHANCE(400, "S303", "기회가 부족합니다."),
    NO_SINGLE_GAME_LOG(404, "S304", "게임 로그가 존재하지 않습니다."),
    NO_SINGLE_LOG_STOCK_CHART(404, "S305", "해당하는 주식 차트 정보가 없습니다."),
    NO_MULTI_GAME_LOG(404, "S306", "해당하는 멀티 게임 로그가 없습니다."), 
    NO_MULTI_GAME_LOG_STOCK(404, "S307", "멀티 게임 로그에 해당하는 주식 정보가 없습니다." ),

    // JSON 파싱 에러 (CODE: 500)
    JSON_PROCESSING_ERROR(500, "J500", "Json Processing Exception"),

    // 멀티 게임 에러 (CODE: 600)
    FULL_ROOM(404,"M600" , "방이 가득 찼습니다."),
    IS_PLAYING(404,"M601" , "게임이 진행중입니다."),
    NOT_ENOUGH_PARTICIPANTS(404,"M602" ,"2명 이상의 플레이어가 필요합니다." );
    




    /**
     * ******************************* Error Code Constructor ***************************************
     */
    // 에러 코드의 '코드 상태'을 반환한다.
    private final int status;

    // 에러 코드의 '코드간 구분 값'을 반환한다.
    private final String divisionCode;

    // 에러 코드의 '코드 메시지'을 반환한다.
    private final String message;

    // 생성자 구성
    ErrorCode(final int status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}
