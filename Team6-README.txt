TEAM : 봉준호 손흥민 황두영 let's go

MEMBER :
서동혁 컴퓨터학부 2021115360
김승준 영어영문학과 2019110248
황두영 영어영문학과 2018110976

Oracle 19C*sqlplus로 작성

README 파일은 각 Phase별로 계속 추가예정
2023-11-07 현재 Phase 3 추가됨
Phase의 수정사항은 다음 Phase에서 서술
ex) er 수정사항 -> Phase 2에서 서술됨

-- Phase 1

1. 구상 개요

축구에 대한 관심도가 점점 증가하고 있다. 국가대표 경기가 있을 때마다 치킨집에 사람이 가득한 것만 봐도 알 수 있다. 
하지만 축구를 즐기기는 쉽지 않다. 사람을 모아야 하며, 사람을 모은 후에도 경기장을 빌려야 한다. 빌린 후에는 경기할 상대 팀도 찾아야 한다. 
심지어 축구 입문자들은 환경이 갖춰지더라도 바로 경기에 참여하기가 부담스럽다. 
SoccerLink는 그런 어려움을 해결하기 위해 구상한 시스템이다. 
대구에 거주하는 사람들이 누구나 쉽게 축구를 배우고 즐기게 도와줄 것이다. 
소셜 매칭 시스템을 도입해, 자신이 원하는 시간대에 원하는 사람들이 모여 경기를 진행할 수 있도록 하며, 
원한다면 비슷한 실력대의 사람들과 경기를 가질 수 있도록 구현할 것이다. 
또한, 트레이닝 시스템을 통해 자신에게 필요한 튜터를 직접 선택하여 축구 입문자나, 
축구를 배우고 싶은 사람들이 맞춤형 레슨을 받을 수 있게 될 것이다.


2. entity type, attribute 설명

(1) USER
시스템을 사용하는 사람에 대한 entity 사용자의 ID_Number, Name, Sex, Age, Job, passwd을 포함.
ID Number를 key attribute로 설정

(2) MANAGER
소셜 매치를 관리해주는 매니저의 entity Bank_Account라는 attribute를 포함한다.

(3) MEMBER
소셜 매치와 트레이닝을 이용할 수 있는 사용자 Prepaid_Money, Tier, Role을 attribute로 설정
Tier를 통해 자신과 수준이 비슷한 사람들과 경기를 가질 수 있으며, Role을 통해 원하는 포지션에서 경기가 이루어질 수 있게 돕는다.

(4) TEAM
멤버가 원한다면 들어갈 수 있는 팀. Team_Name, Team_Member, Team_Tier가 attribute, Team_Member는 여러 명이 포함될 수 있다.

(5) TRAINING
멤버가 튜터나 튜티로 참여할 수 있는 트레이닝을 의미. Class_Number, Tutor, Tutee, Place, Date/Time이 attribute이다. Tutee는 여러 명이 포함될 수 있다. Class_Number을 key attribute로 설정한다.

(6) MATCH
소셜 매치, 즉 축구 경기를 의미. Match_Number, Manager, Date/Time, Place, Type, Sex_Constraint, Game_Tier가 attribute이다. Match_Number을 key attribute로 설정. Game_Tier는 참여자들의 평균 tier로 계산한다.

(7) FIELD
경기장에 대한 정보이다. ID_Number, Type, Phone_Number, Name, Address가 attribute이다. ID_Number를 key attribute로 설정

(8) OWNER
경기장 소유자에 대한 정보이다. Phone_Number, Name이 attribute이고 Phone_Number를 key attribute로 설정.

(9) SERVER
서버에 대한 정보이다. URL을 단일 key attribute로 설정하였다.


3. relation

(1) SIGN_UP_MANAGER
유저는 매니저와 멤버로 구분 지을 수 있는데, 매니저로 등록함을 의미한다.

(2) SIGN_UP_COMMON
멤버로 등록함을 의미한다.

(3) EVALUATES
멤버의 수준을 평가함을 의미한다. 매니저가 멤버를 평가할 수 있고, 멤버 또한 같이 경기를 뛴 멤버를 평가 할 수 있다. 
이 평가가 멤버의 Tier에 영향을 준다. N:M의 관계로 나타낸다.

(4) ORGANIZES
팀과 멤버의 관계 의미한다. 멤버는 팀에 가입할 수 있으며, N:M의 관계로 나타낸다.

(5) TRAINING
트레이닝을 의미한다. 멤버가 튜터나 튜티 모두로 참여 할 수 있으며 Cost도 직접 설정할 수 있다.

(6) APPLIES
3개의 APPLIES가 존재하는데, 하나는 매니저가 경기의 관리를 지원하는 것이고, 하나는 멤버가 MATCH에 참여함을 나타낸다. 
나머지 하나는 팀이 팀과 팀끼리의 경기를 신청함을 나타낸다. 모두 N:M의 관계로 나타낸다.

(7) HOLDS
경기 장소를 유치하는 것을 의미한다. 필드와 N:M 관계로 나타낸다.

(8) HAS
경기장을 소유하고 있는 소유자와 경기장의 관계를 의미한다. 1:N 관계로 나타낸다.

(9) CONTRACT
서버와 경기장 소유주의 계약을 나타낸다. 이러한 연락을 통해 경기장을 대여할 수 있으며 1:N 관계로 나타낸다.

(10) ENROLLS
트레이닝에 튜티로 참여함을 의미한다. N:M의 관계로 나타낸다.

(11) REGISTERS
트레이닝에 튜터로서 참여함을 의미한다. Wage나 Recommned_Tier, Subject, Max_Number, Place 등을 직접 설정 할 수 있다. N:M의 관계로 나타낸다.

4. 구현 목표
소셜 매칭 시스템과 트레이닝 시스템을 구축할 것이다. 
소셜 매칭 시스템은 시간과 인원에 구애받지 않고 축구를 즐기게 도와줄 것이며, Tier를 통해 자신과 실력이 비슷한 사람들과 경기를 가질 수 있을 것이다. 
트레이닝 시스템은 경기를 뛰기 부담스러운 입문자나, 실력 정진을 원하는 사람들에게 도움을 줄 것이다. 이러한 시스템을 통해 대구시민들이 축구를 일상에서 즐기기 쉽게 될 것으로 생각한다. 

-- Phase 2

CAUTION

1. 모든 ID는 각 Relation의 식별자 + 3digit-2digit-4digit 문자열로 구성됨
   * 단, FIELD_ID는 '_____-__-____' (식별자 + 4digit-2digit-4digit)
   Relation : 식별자
   USERS : U / TEAM : T / FILED : F / MATCH : M / TRAIN : C
   ex) TEAM.TEAM_ID = T624-65-8794, TRAINING.CLASS_ID = C891-87-9875 

2. 성별은 'M' OR 'F' 로 표기

3. MATCH.TYPE은 'F'(FUTSAL) OR 'S'(SOCCER) 로 표기

. TIER는 개별 VIEW로 유지함, EVALUATION이나 TEAM or MATCH에 변경이 이루어지면 TIER 자동 산정 

4. REFERENCE INTEGRITY 유지를 위한 INSERT 권장 순서
   순위. RELATION명 : 선행 RELATION명
   1st. USERS, TEAM, OWNER: FK 제약 없음
   2nd. MANAGER, MEMBER : USERS / FIELD : OWNER
   3rd. MAN_EVAL_MEM : MANAGER, MEMBER / TEAM_MEM : TEAM, MEMBER / TRAINING : MEMBER / MATCH : FIELD
   4th. TRAIN_ENROLLS : TRAINING, MEMBER / TRAIN_REG : TRAINING / MATCH_APP_MANAGER : MATCH, MANAGER / MATCH_APP_MEMBER : MATCH, MEMBER
   * RELATIONAL SCHEMA에 좌측부터 순서대로 기입되어 있으니 확인하시면 좋습니다. (Team6_er_modify+relational.pdf)

5. MAN_EVAL_MEM에서, 같은 MANAGER가 같은 MEMBER를 재평가하게 된다면, UPDATE문을 사용해 이전 RECORD를 갱신한다.

-- Phase 3

1. login 기능을 추가하면서, 개인 보안을 위해 USERS Relation에 PASSWD Attribute를 추가함.

2. 모든 ID_NUMBER, TRAINING_ID, TEAM_ID, MATCH_ID는  고유한 번호로 랜덤하게 배정한 후 형식에 맞추어 INSERT 한다. (코레일톡이나 SR의 회원번호 시스템)

3. ProjectMain : 메인화면, 크레딧 / SQLx : SQL문 입력 편하게 만드는 클래스 / USERS : 회원가입, 로그인 / ADMIN : 관리자 모드 / APPLICATION : 유저(멤버, 매니저) 모드
3-1. ADMIN
: ADMIN에서 UPDATE 하거나 DELETE할 때, 관계를 맺고 있는 Table에도 CASCADE 된다.
3-1-1. User
: User의 Attribute 값 UPDATE, User를 DELETE 할 수 있다.
3-1-2. Team
: Team의 Attriute 값 UPDATE, TEAM을 DELETE 할 수 있다.
3-1-3. Owner
: Owner의 Attribute 값 UPDATE, Owner를 DELETE, 새로운 Owner를 INSERT 할 수 있다.
3-1-4. Field
: Field의 Attribute 값 UPDATE, Field를 DELETE, 새로운 Field를 INSERT 할 수 있다.
3-1-5. Match
: Match의 Attribute 값 UPDATE, Match를 DELETE, 새로운 MATCH를 INSERT 할 수 있다.
3-1-6. Training
: Training의 Attribute 값 UPDATE, Training을 DELETE 할 수 있다.
3-1-7. Check Some Information
: Check Sum of Prepaid_money(모든 계좌가 가지는 금액의 합계), Check the Fields/Owner (Owner hp에 따른 Field 정보), MatchData Order by date (Match 정보를 Date에 따라 정렬한 후 출력), TrainingData Order by date (Training 정보를 Date에 따라 정렬한 후 출력)

3-2 APPLICATION
: 모든 부분에서 table과 table 사이에서 발생할 수 있는 CASCADE에 대한 처리를 구현했음

3-2-1. MyPage (MEMBER)
1) Change my info: member의 정보를 바꿀 수 있음
2) Cash Charge: member의 prepaid money에 cash charge
3) Check My Info: Member의 자기 정보 및 캐시 정보 조회
4) Check My Team :Member가 속한 Team 조회
5) Secession: 회원탈퇴

3-2-2. MyPage (MANAGER)
1) Change my info: manager의 정보를 바꿀 수 있음
2) Check My Info: Manager의 자기 정보 조회 
3) Secession: 회원탈퇴

3-2-3. UserEval
: Manager가 user를 평가하는 기능

3-2-4 Apply Match(manager)
: Manager로 Match에 Apply 기능

3-2-5 Training (MEMBER)
: View My training (내가 속한 training 확인 가능), Search training (주제와 추천 티어를 통해서 training 검색 가능), make training, delete training, apply training,  cancel training 기능
: training 신청시 prepaid money에서 cost만큼 돈이 빠져나가도록 설정, 만약 cost보다 돈이 없을 경우 reject
: tutor가 training delete시 training에 신청한 user의 data delete

3-2-6 Match (MEMBER)
: Search Match(시간과 장소를 통해서 match 검색 가능), View My Match(내가 참가하는 match 확인 가능), Apply Match, Cancel Match 기능

3-2-7 Team (MEMBER)
: make team, delete team, apply team, cancel team 기능

4. Manager-Match, Training-tutor는  1:n관계로 재판단하여 MATCH_APP_MANAGER + MATCH = MATCH, TRAIN_REG + TRAINING = TRAINING로 릴레이션 간소화함.

5. Query 구현, Phase 2에 비해 대부분 수정 : 개발한 프로그램에 알맞은 조회 서비스 제공을 위해

6. MATCH에 MAX_NUM attribute 추가, TRAINING, MATCH MAX_NUM에 따라 apply시에 reject하는 기능 및 현재 인원 체크 기능 제공

7. MATCH, TRAINING에 COST_PER_ONE 추가, 참가자 한 명당 지불금액
