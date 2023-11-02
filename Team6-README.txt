SoccerLink 

TEAM : 봉준호 손흥민 황두영 let's go

MEMBER :
서동혁 컴퓨터학부 2021115360
김승준 영어영문학과 2019110248
황두영 영어영문학과 2018110976

Oracle 19C*sqlplus로 작성

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
