create table LSUGQUOTA_Quota (
	quotaId LONG not null primary key,
	classNameId LONG,
	classPK VARCHAR(75) null,
	quotaAssigned LONG,
	quotaUsed LONG,
	quotaStatus INTEGER,
	quotaAlert INTEGER
);