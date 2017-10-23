create table LSUGQUOTA_Quota (
	quotaId LONG not null primary key,
	companyId LONG,
	classNameId LONG,
	classPK LONG,
	quotaAssigned LONG,
	quotaUsed LONG,
	quotaStatus INTEGER,
	quotaAlert INTEGER
);