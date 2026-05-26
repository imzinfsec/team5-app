USE petcarelog_dev;

-- =========================================================
-- 기본 프리셋 데이터
-- 기본 프리셋은 user_id = NULL, is_default = TRUE
-- 회원가입한 모든 사용자가 공통으로 사용할 수 있는 항목
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

ALTER TABLE care_presets AUTO_INCREMENT = 27;