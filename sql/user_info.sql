USE [TWNEWA]


CREATE TABLE [dbo].[user_info](
	[id] [bigint] IDENTITY(1,1) primary key NOT NULL,
	[account] [nvarchar](255) NULL,
	[password] [nvarchar](255) NULL,
	[name] [nvarchar](255) NULL,
	[createdAt] [datetime2](6) NULL,
	[updatedAt] [datetime2](6) NULL,
	CONSTRAINT AK__account UNIQUE (account)
)

CREATE TABLE [dbo].[user_info](
	[id] [bigint] IDENTITY(1,1) primary key NOT NULL,
	[account] [nvarchar](255) NULL,
	[password] [nvarchar](255) NULL,
	[name] [nvarchar](255) NULL,
	[createdAt] [datetime2](6) NULL,
	[updatedAt] [datetime2](6) NULL,
	CONSTRAINT AK__account UNIQUE (account)
)


CREATE TABLE [dbo].[role_info] (
    [id] [bigint] IDENTITY(1,1) PRIMARY KEY,
    [name] [nvarchar](255)  NOT NULL,
    [description] [nvarchar](255)  NOT NULL,
    [createdAt] [datetime2](6) NULL,
    [updatedAt] [datetime2](6) NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,       -- 指向 UserInfo 的外鍵
    role_id BIGINT NOT NULL,       -- 指向 Role 的外鍵
    PRIMARY KEY (user_id, role_id),  -- 複合主鍵，確保每個關聯是唯一的
    FOREIGN KEY (user_id) REFERENCES user_info(id) ON DELETE CASCADE,  -- 參照 UserInfo 表
    FOREIGN KEY (role_id) REFERENCES role_info(id) ON DELETE CASCADE   -- 參照 Role 表
);