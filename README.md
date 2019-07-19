## myblog

### 제약조건
- HTML 중복제거
- 정적 파일 수정 시 재시작 하지 않고 변경사항 반영하기
- class 파일 수정 시 자동으로 재시작 하기

### 기능 목록
1. HTML 중복 제거
2. 게시글 작성 페이지로 이동
3. 게시글 작성
4. 게시글 조회
5. 게시글 수정 페이지로 이동
6. 게시글 수정
7. 게시글 삭제


## Spring Data JPA/회원가입
### Todo
1. 회원 등록/조회 기능 구현하기
    - 회원 등록
        - 회원가입 시 아래의 회원가입 규칙을 지켜야 하고, 위반 시 사용자에게 알려준다.
            
        
2. 로그인 구현하기
    - 로그인 기능
        - 로그인 성공 시 메인 화면을 띄우고 우측 상단에 사용자 이름을 띄운다.
        - 로그인 실패 시 상황에 맞는 실패 메시지를 띄운다.
            - 이메일이 없는 경우
            - 비밀번호가 틀린 경우
    - 로그아웃 기능
        - 로그아웃 시 메인 화면을 띄운다.
    - 기타
        - 로그인 한 유저가 로그인/회원가입 화면에 접근할 경우 메인 화면을 띄운다.
        - 회원 수정 시 본인 여부를 판단하여 다른사람이 수정을 시도하는 경우 제한한다.
        - 자신의 글만 수정/삭제 가능하도록 제한한다.

3. 회원 수정/탈퇴 기능 구현하기
    - 본인의 회원정보만 수정할 수 있어야 하고 본인만 탈퇴할 수 있어야 한다.
    - 로그인 후 본인 확인하는 과정이 필요하다.
    - 회원 수정
        - 회원수정페이지(signup.html)에서 PUT 메소드를 이용하여 수정 요청
    - 회원 탈퇴
        - MyPage > profile 하단 > 탈퇴 버튼을 추가
        - DELETE 메소드를 이용하여 탈퇴 요청
        
4. 기타
    - 테스트시에만 DB가 초기화 되도록 설정하는 방법 찾아보기(UserControllerTest) - profile
    - 테스트 코드 중복 제거(UserControllerTests)
    - logging
### Done
0. 페어와 코드 동기화

1. 회원 등록/조회 기능 구현하기
    - 회원 등록
        - 로그인 페이지로 이동
        - 회원가입 페이지로 이동
        - 회원가입페이지(sign-up.html)에서 POST /users 로 요청
        - Spring Data JPA를 이용하여 DB에 user 정보를 저장
        - 생성 후 로그인 화면으로 이동
        - 회원가입 시 아래의 회원가입 규칙을 지켜야 하고, 위반 시 사용자에게 알려준다.
            - 동일한 email로 중복가입을 할 수 없다.
            - 이름은 2~10자로 제한하며 숫자나 특수문자가 포함될 수 없다.
            - 비밀번호는 8자 이상의 소문자, 대문자, 숫자, 특수문자의 조합이다.
            - 비밀번호 확인 기능이 동작해야 한다. 
        - 회원 조회
            - GET /users 로 요청하여 회원목록페이지(user-list.html) 이동
            - DB에 저장된 회원 정보 노출
