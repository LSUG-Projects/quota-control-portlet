create table LSUGQUOTA_Quota (
	quotaId LONG not null primary key,
	classNameId LONG,
	classPK LONG,
	quotaAssigned LONG,
	quotaUsed LONG,
	quotaStatus INTEGER,
	quotaAlert INTEGER
);