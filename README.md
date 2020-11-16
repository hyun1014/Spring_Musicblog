# Spring_Musicblog
Spring framework 연습을 위한 Music blog

## 기능

- 회원가입 (User)
  - 회원가입한 유저들만 새로운 정보 추가 가능
  - Id, password, 닉네임 정보가 담겨있음
- 조회 항목
  - Artist
  - Member
  - Album
  - Track
 
- Artist
  - 아티스트 정보
  - 아티스트 이름, 소속사, 설명 조회 가능
  - 아티스트 이름은 필수 항목

- Member
  - 밴드, 그룹일 경우 소속 팀원 정보
  - 이름, 팀 조회 가능
  - Team 항목에는 Artist가 들어가며, 해당 artist가 DB에 없을 경우 등록 불가 (Foreign Key)

- Album
  - 음반 정보
  - 음반 제목, 아티스트 조회 가능
  - 아티스트 항목에는 Artist가 들어가며, 해당 artist가 DB에 없을 경우 등록 불가 (Foreign Key)

- Track
  - 곡 정보
  - 제목, 아티스트, 음반, 가사, youtube 링크 ID 포함
  - youtube id가 존재할 경우 embed 형식으로 영상 조회 가능
  - 아티스트 항목에는 Artist가 들어가며, 해당 artist가 DB에 없을 경우 등록 불가 (Foreign Key)
  
모든 테이블에는 작성자(User)가 author 필드로 들어감