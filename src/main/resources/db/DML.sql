USE petcarelog_dev;

-- =========================================================
-- 1. 기본 반려동물 데이터
-- 로그인 기능이 없으므로 임시 user_id = 1 사용
-- =========================================================

INSERT INTO pets (id, user_id, name, species, birth_date)
VALUES
    (1, 1, '뽀야', '강아지', NULL),
    (2, 1, '밥이', '고양이', NULL)
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    name = VALUES(name),
    species = VALUES(species),
    birth_date = VALUES(birth_date);

-- =========================================================
-- 2. 기본 프리셋 데이터
-- 기본 프리셋은 user_id = NULL, is_default = TRUE
-- =========================================================

INSERT INTO care_presets
(id, user_id, name, category, icon, color, is_default, is_active, sort_order)
VALUES
    (1,  NULL, '자연식',     'FOOD',     'FOOD_BOWL',    '#6f9ed8', TRUE, TRUE, 1),
    (2,  NULL, '사료',       'FOOD',     'FEED',         '#6f9ed8', TRUE, TRUE, 2),
    (3,  NULL, '물',         'FOOD',     'WATER_DROP',   '#6f9ed8', TRUE, TRUE, 3),
    (4,  NULL, '유산균',     'FOOD',     'SUPPLEMENT',   '#6f9ed8', TRUE, TRUE, 4),
    (5,  NULL, '오메가3',    'FOOD',     'OMEGA3',       '#6f9ed8', TRUE, TRUE, 5),

    (6,  NULL, '몸무게측정', 'HEALTH',   'WEIGHT',       '#8ebd69', TRUE, TRUE, 1),
    (7,  NULL, '주사',       'HEALTH',   'INJECTION',    '#8ebd69', TRUE, TRUE, 2),
    (8,  NULL, '구충제',     'HEALTH',   'PILL',         '#8ebd69', TRUE, TRUE, 3),
    (9,  NULL, '연고',       'HEALTH',   'OINTMENT',     '#8ebd69', TRUE, TRUE, 4),
    (10, NULL, '혈액검사',   'HEALTH',   'BLOOD_TEST',   '#8ebd69', TRUE, TRUE, 5),

    (11, NULL, '산책',       'ACTIVITY', 'WALK',         '#9168ca', TRUE, TRUE, 1),
    (12, NULL, '산책1시간',  'ACTIVITY', 'LONG_WALK',    '#9168ca', TRUE, TRUE, 2),
    (13, NULL, '놀이',       'ACTIVITY', 'PLAY',         '#9168ca', TRUE, TRUE, 3),
    (14, NULL, '등산',       'ACTIVITY', 'HIKING',       '#9168ca', TRUE, TRUE, 4),
    (15, NULL, '친구와놀기', 'ACTIVITY', 'FRIENDS',      '#9168ca', TRUE, TRUE, 5),

    (16, NULL, '양치',       'GROOMING', 'TOOTH',        '#de8fa2', TRUE, TRUE, 1),
    (17, NULL, '미용',       'GROOMING', 'GROOMING',     '#de8fa2', TRUE, TRUE, 2),
    (18, NULL, '목욕',       'GROOMING', 'BATH',         '#de8fa2', TRUE, TRUE, 3),
    (19, NULL, '귀청소',     'GROOMING', 'EAR_CLEANING', '#de8fa2', TRUE, TRUE, 4),
    (20, NULL, '발톱',       'GROOMING', 'NAIL',         '#de8fa2', TRUE, TRUE, 5),

    (21, NULL, '배변',       'POTTY',    'POOP',         '#dcb85a', TRUE, TRUE, 1),
    (22, NULL, '소변',       'POTTY',    'PEE',          '#dcb85a', TRUE, TRUE, 2),

    (23, NULL, '구토',       'SYMPTOM',  'VOMIT',        '#a77964', TRUE, TRUE, 1),
    (24, NULL, '설사',       'SYMPTOM',  'DIARRHEA',     '#a77964', TRUE, TRUE, 2),
    (25, NULL, '기침',       'SYMPTOM',  'COUGH',        '#a77964', TRUE, TRUE, 3),
    (26, NULL, '가려움',     'SYMPTOM',  'ITCHING',      '#a77964', TRUE, TRUE, 4)
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    name = VALUES(name),
    category = VALUES(category),
    icon = VALUES(icon),
    color = VALUES(color),
    is_default = VALUES(is_default),
    is_active = VALUES(is_active),
    sort_order = VALUES(sort_order);

-- =========================================================
-- 3. 주간 트래커 기본 선택값
-- user_id = 1 사용자가 기본적으로 추적할 프리셋
-- =========================================================

INSERT INTO user_preset_settings (user_id, preset_id, is_tracked)
VALUES
    (1, 1, TRUE),   -- 자연식
    (1, 2, TRUE),   -- 사료
    (1, 3, TRUE),   -- 물
    (1, 6, TRUE),   -- 몸무게측정
    (1, 11, TRUE),  -- 산책
    (1, 13, TRUE),  -- 놀이
    (1, 16, TRUE)   -- 양치
ON DUPLICATE KEY UPDATE
    is_tracked = VALUES(is_tracked);

-- 나머지 기본 프리셋은 user_preset_settings row가 없어도
-- 백엔드 updateTracking 과정에서 필요 시 생성된다.

-- =========================================================
-- 4. 테스트용 돌봄 기록 데이터
-- 필요 없으면 이 섹션은 실행하지 않아도 됨
-- =========================================================

INSERT INTO care_logs
(id, pet_id, preset_id, user_id, care_name, category, icon, color, memo, recorded_at)
VALUES
    (1, 1, 1, 1, '자연식', 'FOOD', 'FOOD_BOWL', '#6f9ed8', NULL, '2026-05-19 09:00:00'),
    (2, 1, 1, 1, '자연식', 'FOOD', 'FOOD_BOWL', '#6f9ed8', NULL, '2026-05-20 09:00:00'),
    (3, 1, 3, 1, '물',     'FOOD', 'WATER_DROP', '#6f9ed8', NULL, '2026-05-20 10:30:00')
ON DUPLICATE KEY UPDATE
    pet_id = VALUES(pet_id),
    preset_id = VALUES(preset_id),
    user_id = VALUES(user_id),
    care_name = VALUES(care_name),
    category = VALUES(category),
    icon = VALUES(icon),
    color = VALUES(color),
    memo = VALUES(memo),
    recorded_at = VALUES(recorded_at);

-- =========================================================
-- 5. AUTO_INCREMENT 정리
-- =========================================================

ALTER TABLE pets AUTO_INCREMENT = 3;
ALTER TABLE care_presets AUTO_INCREMENT = 27;
ALTER TABLE care_logs AUTO_INCREMENT = 4;