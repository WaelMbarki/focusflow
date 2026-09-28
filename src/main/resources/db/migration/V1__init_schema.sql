-- ============================================================
-- V1__init_schema.sql
-- Baseline schema: users, projects, tasks, focus_sessions, daily_goals
-- ============================================================

CREATE TABLE users (
                       id            VARCHAR(255) PRIMARY KEY,
                       email         VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name     VARCHAR(255),
                       birth_date    DATE,
                       created_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE projects (
                          id      VARCHAR(255) PRIMARY KEY,
                          name    VARCHAR(255) NOT NULL,
                          color   VARCHAR(20),
                          user_id VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tasks (
                       id                          VARCHAR(255) PRIMARY KEY,
                       title                       VARCHAR(255) NOT NULL,
                       description                 TEXT,
                       status                      VARCHAR(20) NOT NULL DEFAULT 'TODO'
                           CHECK (status IN ('TODO', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
                       priority                    VARCHAR(10) NOT NULL DEFAULT 'MEDIUM'
                           CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
                       start_date                  TIMESTAMP,
                       due_date                    TIMESTAMP,
                       estimated_duration_minutes  INTEGER,
                       created_at                  TIMESTAMP NOT NULL DEFAULT now(),
                       completed_at                TIMESTAMP,
                       user_id                     VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       project_id                  VARCHAR(255) REFERENCES projects(id) ON DELETE SET NULL
);

CREATE TABLE focus_sessions (
                                id                          VARCHAR(255) PRIMARY KEY,
                                user_id                     VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                task_id                     VARCHAR(255) REFERENCES tasks(id) ON DELETE SET NULL,
                                start_time                  TIMESTAMP NOT NULL,
                                planned_duration_seconds    INTEGER NOT NULL,
                                duration_seconds            INTEGER NOT NULL DEFAULT 0,
                                status                      VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
                                    CHECK (status IN ('ACTIVE', 'PAUSED', 'COMPLETED', 'CANCELLED'))
);

CREATE TABLE daily_goals (
                             id             VARCHAR(255) PRIMARY KEY,
                             user_id        VARCHAR(255) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             title          VARCHAR(255) NOT NULL,
                             goal_type      VARCHAR(20) NOT NULL
                                 CHECK (goal_type IN ('TASK_COMPLETION', 'FOCUS_TIME', 'CUSTOM')),
                             target_value   INTEGER NOT NULL,
                             current_value  INTEGER NOT NULL DEFAULT 0,
                             goal_date      DATE NOT NULL,
                             completed      BOOLEAN NOT NULL DEFAULT false
);

-- Helpful indexes for common query patterns (dashboard lookups, filters)
CREATE INDEX idx_tasks_user_id ON tasks(user_id);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_focus_sessions_user_id ON focus_sessions(user_id);
CREATE INDEX idx_focus_sessions_start_time ON focus_sessions(start_time);
CREATE INDEX idx_daily_goals_user_date ON daily_goals(user_id, goal_date);