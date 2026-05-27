CREATE DATABASE IF NOT EXISTS petcarelog_dev
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE petcarelog_dev;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS care_logs;
DROP TABLE IF EXISTS user_preset_settings;
DROP TABLE IF EXISTS care_presets;
DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================================================
-- 1. 회원 테이블
-- =========================================================
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =========================================================
-- 2. 반려동물 테이블
-- =========================================================
CREATE TABLE pets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    species VARCHAR(30) NOT NULL,
    birth_date DATE NULL,
    image_url VARCHAR(500) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    INDEX idx_pets_user_id (user_id),

    CONSTRAINT fk_pets_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =========================================================
-- 3. 돌봄 프리셋 테이블
-- 기본 프리셋: user_id = NULL
-- 사용자 추가 프리셋: user_id = users.id
-- =========================================================
CREATE TABLE care_presets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NULL,
    name VARCHAR(50) NOT NULL,
    category VARCHAR(30) NOT NULL,
    icon VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    sort_order INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    INDEX idx_care_presets_user_id (user_id),
    INDEX idx_care_presets_category (category),
    INDEX idx_care_presets_active (is_active),
    INDEX idx_care_presets_default (is_default),

    CONSTRAINT fk_care_presets_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =========================================================
-- 4. 사용자별 주간 트래커 설정 테이블
-- =========================================================
CREATE TABLE user_preset_settings (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    preset_id BIGINT NOT NULL,
    is_tracked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE KEY uk_user_preset_settings_user_preset (user_id, preset_id),
    INDEX idx_user_preset_settings_user_id (user_id),
    INDEX idx_user_preset_settings_preset_id (preset_id),
    INDEX idx_user_preset_settings_tracked (is_tracked),

    CONSTRAINT fk_user_preset_settings_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_preset_settings_preset
        FOREIGN KEY (preset_id)
        REFERENCES care_presets(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- =========================================================
-- 5. 돌봄 기록 테이블
-- =========================================================
CREATE TABLE care_logs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pet_id BIGINT NOT NULL,
    preset_id BIGINT NULL,
    user_id BIGINT NOT NULL,
    care_name VARCHAR(50) NOT NULL,
    category VARCHAR(30) NOT NULL,
    icon VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    memo VARCHAR(255) NULL,
    recorded_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    INDEX idx_care_logs_pet_recorded_at (pet_id, recorded_at),
    INDEX idx_care_logs_user_recorded_at (user_id, recorded_at),
    INDEX idx_care_logs_preset_id (preset_id),

    CONSTRAINT fk_care_logs_pet
        FOREIGN KEY (pet_id)
        REFERENCES pets(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_care_logs_preset
        FOREIGN KEY (preset_id)
        REFERENCES care_presets(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_care_logs_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;