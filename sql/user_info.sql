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


